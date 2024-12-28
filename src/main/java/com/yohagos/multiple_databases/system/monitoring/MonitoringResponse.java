package com.yohagos.multiple_databases.system.monitoring;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Monitoring Response Model")
public class MonitoringResponse {

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
}
