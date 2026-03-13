package com.bolobuddy.repository;

import com.bolobuddy.model.ConversationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationLogRepository extends JpaRepository<ConversationLog, Long> {
}
