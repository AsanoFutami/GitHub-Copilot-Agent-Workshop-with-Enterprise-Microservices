package com.skishop.user.entity;

import com.skishop.user.enums.SagaStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Saga Transaction Entity
 * State management for the Saga pattern in event-driven architecture
 */
@Entity
@Table(name = "saga_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SagaTransaction {

    @Id
    @Column(name = "saga_id", length = 36, nullable = false)
    private String sagaId;

    @Column(name = "correlation_id", length = 36, nullable = false)
    private String correlationId;

    @Column(name = "original_event_id", length = 36, nullable = false)
    private String originalEventId;

    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private SagaStatus status;

    @Column(name = "current_step", length = 100)
    private String currentStep;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "max_retry_count", nullable = false)
    @Builder.Default
    private Integer maxRetryCount = 3;

    @Column(name = "timeout_at")
    private LocalDateTime timeoutAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "error_type", length = 100)
    private String errorType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "saga_transaction_context",
        joinColumns = @JoinColumn(name = "saga_id")
    )
    @MapKeyColumn(name = "context_key")
    @Column(name = "context_value", columnDefinition = "TEXT")
    @Builder.Default
    private Map<String, String> context = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "saga_completed_steps",
        joinColumns = @JoinColumn(name = "saga_id")
    )
    @Column(name = "step_name")
    @Builder.Default
    private Map<String, String> completedSteps = new HashMap<>();

    @Column(name = "processing_start_time")
    private LocalDateTime processingStartTime;

    @Column(name = "processing_end_time")
    private LocalDateTime processingEndTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Check if the Saga has completed successfully
     */
    public boolean isCompleted() {
        return SagaStatus.SAGA_COMPLETED.equals(status);
    }

    /**
     * Check if the Saga has failed
     */
    public boolean isFailed() {
        return SagaStatus.SAGA_FAILED.equals(status) || 
               SagaStatus.SAGA_COMPENSATION_FAILED.equals(status) ||
               SagaStatus.SAGA_TIMEOUT.equals(status);
    }

    /**
     * Check if compensation is required
     */
    public boolean needsCompensation() {
        return SagaStatus.SAGA_COMPENSATING.equals(status) ||
               SagaStatus.SAGA_STEP_FAILED.equals(status) ||
               (SagaStatus.SAGA_IN_PROGRESS.equals(status) && isTimedOut());
    }

    /**
     * Check if retry is possible
     */
    public boolean canRetry() {
        return retryCount < maxRetryCount && 
               !isFailed() && 
               !isCompleted() &&
               (timeoutAt == null || LocalDateTime.now().isBefore(timeoutAt));
    }
    
    /**
     * Check if the Saga has timed out
     */
    public boolean isTimedOut() {
        return timeoutAt != null && LocalDateTime.now().isAfter(timeoutAt);
    }

    /**
     * Calculate processing time (milliseconds)
     */
    public Long getProcessingTimeMs() {
        if (processingStartTime == null) {
            return null;
        }
        LocalDateTime endTime = processingEndTime != null ? processingEndTime : LocalDateTime.now();
        return java.time.Duration.between(processingStartTime, endTime).toMillis();
    }

    /**
     * Add a value to the context
     */
    public void addContext(String key, String value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }
    
    /**
     * Get a value from the context
     */
    public String getContextValue(String key) {
        if (context == null) {
            return null;
        }
        return context.get(key);
    }

    /**
     * Add a completed step
     */
    public void addCompletedStep(String stepName, String details) {
        if (completedSteps == null) {
            completedSteps = new HashMap<>();
        }
        completedSteps.put(stepName, details);
    }

    /**
     * Increment retry count
     */
    public void incrementRetryCount() {
        this.retryCount++;
    }

    /**
     * Set processing start time
     */
    public void markProcessingStart() {
        this.processingStartTime = LocalDateTime.now();
    }

    /**
     * Set processing end time
     */
    public void markProcessingEnd() {
        this.processingEndTime = LocalDateTime.now();
    }
}
