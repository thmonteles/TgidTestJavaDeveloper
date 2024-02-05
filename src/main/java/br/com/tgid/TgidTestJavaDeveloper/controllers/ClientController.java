package br.com.tgid.TgidTestJavaDeveloper.controllers;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateClientDTO;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.SuccessOrFailureResponse;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.ClientServiceException;
import br.com.tgid.TgidTestJavaDeveloper.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    ClientService service;

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailureResponse> createClient(@RequestBody CreateClientDTO createClientDTO) {
        logger.info("create user from " + createClientDTO.toString());
        try {
            var userCreated = service.create(createClientDTO);
            if (userCreated) {
                return ResponseEntity.ok(new SuccessOrFailureResponse(true, "Client created successfully"));
            }
            return ResponseEntity.badRequest().body(new SuccessOrFailureResponse(false, "User not created"));
        } catch (ClientServiceException e) {
            return ResponseEntity.badRequest().body(new SuccessOrFailureResponse(false, e.getMessage()));
        }
    }

}
