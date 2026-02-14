package com.orcamento.notification.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orcamento.notification.dto.NotificationRequestDTO;
import com.orcamento.notification.dto.NotificationResponseDTO;
import com.orcamento.notification.entity.Notification;
import com.orcamento.notification.entity.enums.NotificationStatus;
import com.orcamento.notification.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public NotificationResponseDTO create(NotificationRequestDTO request) {
        Notification notification = toEntity(request);

        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);

        // Envia o e‑mail e atualiza status (SENT/FAILED)
        emailNotificationService.send(notification);

        return toResponseDTO(notification);
    }

    // UPDATE --------------------------------------------------------
    @Transactional
    public NotificationResponseDTO update(UUID id, NotificationRequestDTO request) {
        Notification notification = notificationRepository.findById(id)
                .filter(n -> n.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Atualiza apenas campos editáveis
        notification.setExternalReferenceId(request.getExternalReferenceId());
        notification.setRecipientEmail(request.getRecipientEmail());
        notification.setRecipientName(request.getRecipientName());
        notification.setSubject(request.getSubject());
        notification.setBodyHtml(request.getBodyHtml());

        notification = notificationRepository.save(notification);

        return toResponseDTO(notification);
    }

    // READ (by id) --------------------------------------------------
    @Transactional(readOnly = true)
    public NotificationResponseDTO findById(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .filter(n -> n.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        return toResponseDTO(notification);
    }

    // LIST (paginated, only not deleted) ----------------------------
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> findAll(Pageable pageable) {
        return notificationRepository
                .findByDeletedAtIsNull(pageable)
                .map(this::toResponseDTO);
    }

    // SOFT DELETE ---------------------------------------------------
    @Transactional
    public void softDelete(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (notification.getDeletedAt() == null) {
            notification.setDeletedAt(OffsetDateTime.now());
            notificationRepository.save(notification);
        }
    }

    // (Opcional) listar deletadas - pode ser útil pra "lixeira"
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> findAllDeleted(Pageable pageable) {
        return notificationRepository
                .findByDeletedAtIsNotNull(pageable)
                .map(this::toResponseDTO);
    }

    // MAPPERS -------------------------------------------------------
    private NotificationResponseDTO toResponseDTO(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setExternalReferenceId(notification.getExternalReferenceId());
        dto.setRecipientEmail(notification.getRecipientEmail());
        dto.setRecipientName(notification.getRecipientName());
        dto.setSubject(notification.getSubject());
        dto.setBodyHtml(notification.getBodyHtml());
        dto.setStatus(notification.getStatus());
        dto.setErrorMessage(notification.getErrorMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setUpdatedAt(notification.getUpdatedAt());
        dto.setDeletedAt(notification.getDeletedAt());
        return dto;
    }

    private Notification toEntity(NotificationRequestDTO dto) {
        Notification notification = new Notification();
        notification.setExternalReferenceId(dto.getExternalReferenceId());
        notification.setRecipientEmail(dto.getRecipientEmail());
        notification.setRecipientName(dto.getRecipientName());
        notification.setSubject(dto.getSubject());
        notification.setBodyHtml(dto.getBodyHtml());
        return notification;
    }
}
