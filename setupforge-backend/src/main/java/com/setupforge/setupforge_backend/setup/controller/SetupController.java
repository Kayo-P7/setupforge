
package com.setupforge.setupforge_backend.setup.controller;

import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.service.SetupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setup")
public class SetupController {

    private final SetupService setupService;

    @PostMapping
    public ResponseEntity<SetupResponse> criarSetup(
            @Valid @RequestBody SetupRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(setupService.criarSetup(request));
    }

    @GetMapping
    public ResponseEntity<List<SetupResponse>> listarSetups() {

        return ResponseEntity.ok(setupService.listarSetups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetupResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(setupService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerSetup(
            @PathVariable Long id) {

        setupService.removerSetup(id);

        return ResponseEntity.noContent().build();
    }
}
