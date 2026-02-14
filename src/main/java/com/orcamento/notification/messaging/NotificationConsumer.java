package com.orcamento.notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orcamento.notification.dto.NotificationEventDTO;
import com.orcamento.notification.entity.Notification;
import com.orcamento.notification.entity.enums.NotificationStatus;
import com.orcamento.notification.repository.NotificationRepository;
import com.orcamento.notification.service.EmailNotificationService;

@Component
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @RabbitListener(queues = "notifications")
    public void receive(NotificationEventDTO event) {
        System.out.println("Mensagem recebida do RabbitMQ: " + event);

        // Converter DTO em entity
        Notification notification = new Notification();
        notification.setExternalReferenceId(event.getExternalReferenceId());
        notification.setRecipientEmail(event.getRecipientEmail());
        notification.setRecipientName(event.getRecipientName());
        notification.setSubject(event.getSubject());
        notification.setBodyHtml(event.getBodyHtml());
        notification.setStatus(NotificationStatus.PENDING);

        // Salvar no banco
        notification = notificationRepository.save(notification);

        // Enviar e-mail e atualizar status
        emailNotificationService.send(notification);
    }
}