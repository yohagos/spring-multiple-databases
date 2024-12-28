package com.yohagos.multiple_databases.system.monitoring;

import org.springframework.stereotype.Service;

@Service
public class MetricsMapper {

    public MonitoringResponse toMonitoringResponse(
            Monitoring entity
    ) {
        return MonitoringResponse.builder()
                .monitoringId(entity.getMonitoringId())
                .databaseName(entity.getDatabaseName())
                .totalAccesses(entity.getTotalAccesses())
                .tableCount(entity.getTableCount())
                .avgResponseTime(entity.getAvgResponseTime())
                .ioTime(entity.getIoTime())
                .readTime(entity.getReadTime())
                .writeTime(entity.getWriteTime())
                .bufferHits(entity.getBufferHits())
                .totalExecutionTime(entity.getTotalExecutionTime())
                .sharedBlocksRead(entity.getSharedBlocksRead())
                .localBlocksHit(entity.getLocalBlocksHit())
                .localBlocksWritten(entity.getLocalBlocksWritten())
                .activeConnections(entity.getActiveConnections())
                .totalLocks(entity.getTotalLocks())
                .committedTransactions(entity.getCommittedTransactions())
                .rolledBackTransactions(entity.getRolledBackTransactions())
                .cacheHitRate(entity.getCacheHitRate())
                .indexScans(entity.getIndexScans())
                .tuplesReadFromIndex(entity.getTuplesReadFromIndex())
                .tuplesFetchedByIndex(entity.getTuplesFetchedByIndex())
                .checkpointsReq(entity.getCheckpointsReq())
                .checkpointsTimed(entity.getCheckpointsTimed())
                .checkpointsWriteTime(entity.getCheckpointsWriteTime())
                .checkpointsSyncTime(entity.getCheckpointsSyncTime())
                .totalConnections(entity.getTotalConnections())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
