package com.orcamento.notification.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.orcamento.notification.entity.enums.NotificationStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de uma notificação registrada no sistema")
public class NotificationResponseDTO {

    @Schema(
        description = "ID único da notificação",
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    private UUID id;

    @Schema(
        description = "ID do orçamento na Orçamento API (quote_requests.id)",
        example = "f1e2d3c4-b5a6-7890-1234-567890abcdef"
    )
    private UUID externalReferenceId;

    @Schema(example = "cliente@email.com")
    private String recipientEmail;

    @Schema(example = "João da Silva")
    private String recipientName;

    @Schema(example = "Seu orçamento foi criado")
    private String subject;

    @Schema(
        description = "Conteúdo HTML da notificação",
        example = "<h1>Olá, João!</h1><p>Seu orçamento foi criado com sucesso.</p>"
    )
    private String bodyHtml;

    @Schema(
        description = "Status da notificação",
        example = "PENDING"
    )
    private NotificationStatus status;

    @Schema(
        description = "Mensagem de erro caso o envio tenha falhado",
        example = "Erro ao enviar e-mail: SMTP indisponível"
    )
    private String errorMessage;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getExternalReferenceId() {
        return externalReferenceId;
    }

    public void setExternalReferenceId(UUID externalReferenceId) {
        this.externalReferenceId = externalReferenceId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}