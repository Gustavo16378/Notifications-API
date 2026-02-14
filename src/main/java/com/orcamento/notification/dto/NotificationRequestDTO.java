package com.orcamento.notification.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload para criação/atualização de uma notificação")
public class NotificationRequestDTO {

    @NotNull(message = "O ID de referência externa (orçamento) é obrigatório")
    @Schema(
        description = "ID do orçamento na Orçamento API (quote_requests.id)",
        example = "f1e2d3c4-b5a6-7890-1234-567890abcdef",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID externalReferenceId;

    @NotBlank(message = "O e-mail do destinatário é obrigatório")
    @Email(message = "Formato de e-mail do destinatário inválido")
    @Size(max = 254, message = "O e-mail pode ter até 254 caracteres")
    @Schema(example = "cliente@email.com")
    private String recipientEmail;

    @NotBlank(message = "O nome do destinatário é obrigatório")
    @Size(max = 150, message = "O nome pode ter até 150 caracteres")
    @Schema(example = "João da Silva")
    private String recipientName;

    @NotBlank(message = "O assunto é obrigatório")
    @Schema(example = "Seu orçamento foi criado")
    private String subject;

    @NotBlank(message = "O corpo da mensagem (HTML) é obrigatório")
    @Schema(
        description = "Conteúdo HTML da notificação",
        example = "<h1>Olá, João!</h1><p>Seu orçamento foi criado com sucesso.</p>"
    )
    private String bodyHtml;

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
}