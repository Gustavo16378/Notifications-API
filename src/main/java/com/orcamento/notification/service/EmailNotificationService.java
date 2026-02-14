package com.orcamento.notification.service;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orcamento.notification.entity.Notification;
import com.orcamento.notification.entity.enums.NotificationStatus;
import com.orcamento.notification.repository.NotificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Value("${spring.mail.from}")
    private String defaultFrom;

    /**
     * Envia o e-mail para a notificação informada e atualiza o status no banco.
     */
    @Transactional
    public void send(Notification notification) {
        try {
            sendEmail(notification);
            notification.setStatus(NotificationStatus.SENT);
            notification.setErrorMessage(null);
        } catch (Exception ex) {
            LOGGER.error("Erro ao enviar e-mail para notification {}: {}", notification.getId(), ex.getMessage(), ex);
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage(ex.getMessage());
        }

        notificationRepository.save(notification);
    }

    private void sendEmail(Notification notification) throws MessagingException, MailException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setTo(notification.getRecipientEmail());
        helper.setSubject(notification.getSubject());
        helper.setText(notification.getBodyHtml(), true); // true = HTML
        helper.setFrom(defaultFrom); // usa o remetente configurado

        mailSender.send(mimeMessage);
    }
}