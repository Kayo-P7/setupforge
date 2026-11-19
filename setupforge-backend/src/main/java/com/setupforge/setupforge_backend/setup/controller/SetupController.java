package com.setupforge.setupforge_backend.setup.controller;


import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.service.SetupService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/setup")
public class SetupController {

    private final SetupService setupService;

    @PostMapping
    public ResponseEntity<SetupResponse> saveSetup(@RequestBody SetupRequest setupRequest){

        return ResponseEntity.ok().body(setupService.saveSetup(setupRequest));
    }
}
