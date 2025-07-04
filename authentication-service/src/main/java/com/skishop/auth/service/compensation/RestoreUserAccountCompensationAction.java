package com.skishop.auth.service.compensation;

import com.skishop.auth.enums.UserDeletionStatus;
import com.skishop.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Compensation action for restoring user account (deletion rollback)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestoreUserAccountCompensationAction implements CompensationAction {

    private final UserRepository userRepository;

    @Override
    public boolean compensate(String sagaId, CompensationContext context) {
        try {
            log.info("Executing user account restoration compensation for saga: {}, user: {}", 
                sagaId, context.getUserId());

            // Restore user account (undo logical deletion)
            userRepository.findById(context.getUserId()).ifPresent(user -> {
                user.setStatus("ACTIVE"); // Restore to pre-deletion state
                userRepository.save(user);
                
                log.info("User account restored for user: {} in saga: {}", 
                    context.getUserId(), sagaId);
            });

            return true;

        } catch (Exception e) {
            log.error("Failed to compensate user account restoration for saga {}: {}", 
                sagaId, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isApplicable(String sagaType, String status) {
        return "USER_DELETION".equals(sagaType) && 
               (UserDeletionStatus.ACCOUNT_SOFT_DELETED.name().equals(status) ||
                UserDeletionStatus.DELETION_EVENT_PUBLISHED.name().equals(status) ||
                UserDeletionStatus.PENDING_USER_MANAGEMENT_DELETION.name().equals(status));
    }

    @Override
    public int getPriority() {
        return 1; // High priority
    }

    @Override
    public String getActionName() {
        return "RESTORE_USER_ACCOUNT";
    }
}
