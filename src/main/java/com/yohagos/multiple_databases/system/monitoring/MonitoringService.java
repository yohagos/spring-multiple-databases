package com.yohagos.multiple_databases.system.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yohagos.multiple_databases.system.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class MonitoringService {

    private final JdbcTemplate db1JdbcTemplate;
    private final JdbcTemplate db2JdbcTemplate;

    private final MetricsMapper metricsMapper;
    private final MonitoringRepository monitoringRepository;

    private final KafkaProducerService metricsProducerService;

    @Autowired
    public MonitoringService(
            @Qualifier("db1JdbcTemplate") JdbcTemplate db1JdbcTemplate,
            @Qualifier("db2JdbcTemplate") JdbcTemplate db2JdbcTemplate,
            MetricsMapper mapper,
            MonitoringRepository repository,
            KafkaProducerService service
    ) {
        this.db1JdbcTemplate = db1JdbcTemplate;
        this.db2JdbcTemplate = db2JdbcTemplate;
        metricsMapper = mapper;
        monitoringRepository = repository;
        metricsProducerService = service;
    }

    @Transactional
    public List<MonitoringResponse> loadDatabaseStats() {
        return monitoringRepository.findAll().stream()
                .map(metricsMapper::toMonitoringResponse)
                .toList();
    }

    @Transactional
    public List<MonitoringResponse> loadDatabaseStatsLastHundred() {
        return monitoringRepository.findTopFiftyByOrderByCreatedAtDesc().stream()
                .map(metricsMapper::toMonitoringResponse)
                .toList();
    }

    @Scheduled(fixedRate = 10000)
    public void storeMetrics() {
        var db1Stats = getAccessStats(db1JdbcTemplate);
        var db2Stats = getAccessStats(db2JdbcTemplate);

        var db1AdditionalStats = getAdditionalMetrics(db1JdbcTemplate);
        var db2AdditionalStats = getAdditionalMetrics(db2JdbcTemplate);

        var monitoringDb1 = Monitoring.builder()
                .databaseName("db1")
                .monitoringId(UUID.randomUUID())
                .tableCount(getTableCountForDatabase(db1JdbcTemplate))
                .avgResponseTime(db1Stats.get("avgresponsetime"))
                .bufferHits(db1Stats.get("bufferhits"))
                .totalAccesses(db1Stats.get("totalaccesses"))
                .ioTime(db1Stats.get("iotime"))
                .readTime(db1Stats.get("readtime"))
                .writeTime(db1Stats.get("writetime"))
                .totalExecutionTime(db1Stats.get("totalexecutiontime"))
                .sharedBlocksRead(db1Stats.get("sharedblocksread"))
                .localBlocksHit(db1Stats.get("localblockshit"))
                .localBlocksWritten(db1Stats.get("localblockswritten"))
                .activeConnections(db1Stats.get("activeconnections"))
                .totalLocks(db1Stats.get("totallocks"))
                .committedTransactions(db1AdditionalStats.get("committedtransactions"))
                .rolledBackTransactions(db1AdditionalStats.get("rolledbacktransactions"))
                .cacheHitRate(db1AdditionalStats.get("cacheHitRate"))
                .indexScans(db1AdditionalStats.get("indexscans"))
                .tuplesReadFromIndex(db1AdditionalStats.get("tuplesreadfromindex"))
                .tuplesFetchedByIndex(db1AdditionalStats.get("tuplesfetchedbyindex"))
                .checkpointsReq(db1AdditionalStats.get("checkpoints_req"))
                .checkpointsTimed(db1AdditionalStats.get("checkpoints_timed"))
                .checkpointsWriteTime(db1AdditionalStats.get("checkpoint_write_time"))
                .checkpointsSyncTime(db1AdditionalStats.get("checkpoint_sync_time"))
                .totalConnections(db1AdditionalStats.get("totalconnections"))
                .createdAt(LocalDateTime.now())
                .build();

        var monitoringDb2 = Monitoring.builder()
                .databaseName("db2")
                .monitoringId(UUID.randomUUID())
                .tableCount(getTableCountForDatabase(db2JdbcTemplate))
                .avgResponseTime(db2Stats.get("avgresponsetime"))
                .bufferHits(db2Stats.get("bufferhits"))
                .totalAccesses(db2Stats.get("totalaccesses"))
                .ioTime(db2Stats.get("iotime"))
                .readTime(db2Stats.get("readtime"))
                .writeTime(db2Stats.get("writetime"))
                .totalExecutionTime(db2Stats.get("totalexecutiontime"))
                .sharedBlocksRead(db2Stats.get("sharedblocksread"))
                .localBlocksHit(db2Stats.get("localblockshit"))
                .localBlocksWritten(db2Stats.get("localblockswritten"))
                .activeConnections(db2Stats.get("activeconnections"))
                .totalLocks(db2Stats.get("totallocks"))
                .committedTransactions(db2AdditionalStats.get("committedtransactions"))
                .rolledBackTransactions(db2AdditionalStats.get("rolledbacktransactions"))
                .cacheHitRate(db2AdditionalStats.get("cacheHitRate"))
                .indexScans(db2AdditionalStats.get("indexscans"))
                .tuplesReadFromIndex(db2AdditionalStats.get("tuplesreadfromindex"))
                .tuplesFetchedByIndex(db2AdditionalStats.get("tuplesfetchedbyindex"))
                .checkpointsReq(db2AdditionalStats.get("checkpoints_req"))
                .checkpointsTimed(db2AdditionalStats.get("checkpoints_timed"))
                .checkpointsWriteTime(db2AdditionalStats.get("checkpoint_write_time"))
                .checkpointsSyncTime(db2AdditionalStats.get("checkpoint_sync_time"))
                .totalConnections(db2AdditionalStats.get("totalconnections"))
                .createdAt(LocalDateTime.now())
                .build();

        monitoringRepository.saveAll(
                List.of(
                        monitoringDb1,
                        monitoringDb2
                )
        );
    }

    private Integer getTableCountForDatabase(JdbcTemplate template) {
        String sql = "Select count(*) from information_schema.tables where table_schema = 'public'";
        return template.queryForObject(sql, Integer.class);
    }

    private Map<String, Double> getAccessStats(JdbcTemplate template) {
        Map<String, Double> accessStats = new HashMap<>();

        String sql = "SELECT sum(total_exec_time) AS totalExecutionTime, " +
                "sum(calls) AS totalAccesses, " +
                "avg(total_exec_time / NULLIF(calls, 0)) AS avgResponseTime, " +
                "sum(blk_read_time + blk_write_time) AS ioTime, " +
                "blk_read_time AS readTime, " +
                "blk_write_time AS writeTime, " +
                "sum(shared_blks_hit) AS bufferHits, " +
                "sum(shared_blks_read) AS sharedBlocksRead, " +
                "sum(local_blks_hit) AS localBlocksHit, " +
                "sum(local_blks_written) AS localBlocksWritten, " +
                "(SELECT count(*) FROM pg_stat_activity WHERE state = 'active') AS activeConnections, " +
                "(SELECT count(*) FROM pg_locks) AS totalLocks " +
                "FROM pg_stat_statements " +
                "GROUP BY readTime, writeTime";

        Map<String, Object> stats = template.queryForMap(sql);
        stats.forEach((key, val) -> accessStats.put(key, asDouble(val)));

        return accessStats;
    }

    public Map<String, Double> getAdditionalMetrics(JdbcTemplate template) {
        Map<String, Object> metrics = new HashMap<>();

        metrics.putAll(template.queryForMap("SELECT sum(xact_commit) AS committedTransactions, sum(xact_rollback) AS rolledBackTransactions FROM pg_stat_database"));
        metrics.put("cacheHitRate", template.queryForObject("SELECT sum(blks_hit) / nullif(sum(blks_hit + blks_read), 0) AS cacheHitRate FROM pg_stat_database", Double.class));
        metrics.putAll(template.queryForMap("SELECT sum(idx_scan) AS indexScans, sum(idx_tup_read) AS tuplesReadFromIndex, sum(idx_tup_fetch) AS tuplesFetchedByIndex FROM pg_stat_user_indexes"));
        metrics.putAll(template.queryForMap("SELECT checkpoints_timed, checkpoints_req, checkpoint_write_time, checkpoint_sync_time FROM pg_stat_bgwriter"));
        metrics.putAll(template.queryForMap("SELECT count(*) AS totalConnections, sum(CASE WHEN state = 'active' THEN 1 ELSE 0 END) AS activeConnections FROM pg_stat_activity"));

        Map<String, Double> stats = new HashMap<>();
        metrics.forEach((key, val) -> stats.put(key, asDouble(val)));

        return stats;
    }

    private Double asDouble(Object o) {
        Double val = null;
        if (o instanceof Number) {
            val = ((Number) o).doubleValue();
        }
        return val;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMetrics() {
        try {
            var metrics = loadDatabaseStatsLastHundred();
            var metricsJson = new ObjectMapper().writeValueAsString(metrics);
            metricsProducerService.sendMetricsMessage(metricsJson);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
