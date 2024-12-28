package com.yohagos.multiple_databases.system.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monitoring_entity")
@Schema(description = "Monitoring Entity Model")
public class Monitoring {
    @Id
    @GeneratedValue
    private UUID monitoringId;
    private String databaseName;
    private Integer tableCount;
    private Double totalAccesses;
    private Double totalExecutionTime;
    private Double avgResponseTime;
    private Double ioTime;
    private Double readTime;
    private Double writeTime;
    private Double bufferHits;
    private Double sharedBlocksRead;
    private Double localBlocksHit;
    private Double localBlocksWritten;
    private Double activeConnections;
    private Double totalLocks;
    private Double committedTransactions;
    private Double rolledBackTransactions;
    private Double cacheHitRate ;
    private Double indexScans;
    private Double tuplesReadFromIndex;
    private Double tuplesFetchedByIndex;
    private Double checkpointsTimed;
    private Double checkpointsReq;
    private Double checkpointsWriteTime;
    private Double checkpointsSyncTime;
    private Double totalConnections;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return String.format(
                "Monitoring=[id=%s, db=%s, tableCount=%d, totalAccess=%s, ioTime=%s]",
                getMonitoringId(), getDatabaseName(), getTableCount(), getTotalAccesses(), getIoTime()
        );
    }

}
