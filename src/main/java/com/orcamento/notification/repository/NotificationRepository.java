package com.orcamento.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orcamento.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByDeletedAtIsNull();
    List<Notification> findByDeletedAtIsNotNull();

    Page<Notification> findByDeletedAtIsNull(Pageable pageable);
    Page<Notification> findByDeletedAtIsNotNull(Pageable pageable);
}
