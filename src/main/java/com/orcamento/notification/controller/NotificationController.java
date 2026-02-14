package com.orcamento.notification.controller;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orcamento.notification.dto.NotificationRequestDTO;
import com.orcamento.notification.dto.NotificationResponseDTO;
import com.orcamento.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "CRUD de notificações e operações relacionadas")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Cria uma nova notificação")
    public ResponseEntity<NotificationResponseDTO> create(
            @Valid @RequestBody NotificationRequestDTO request) {

        NotificationResponseDTO created = notificationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma notificação existente")
    public ResponseEntity<NotificationResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody NotificationRequestDTO request) {

        NotificationResponseDTO updated = notificationService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma notificação por ID")
    public ResponseEntity<NotificationResponseDTO> findById(@PathVariable UUID id) {
        NotificationResponseDTO dto = notificationService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Lista notificações ativas (não deletadas) com paginação")
    public ResponseEntity<Page<NotificationResponseDTO>> findAll(
            @ParameterObject Pageable pageable) {
        Page<NotificationResponseDTO> page = notificationService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma notificação (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        notificationService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deleted")
    @Operation(summary = "Lista notificações deletadas (soft delete) com paginação")
    public ResponseEntity<Page<NotificationResponseDTO>> findAllDeleted(
            @ParameterObject Pageable pageable) {
        Page<NotificationResponseDTO> page = notificationService.findAllDeleted(pageable);
        return ResponseEntity.ok(page);
    }
}