package br.com.tgid.TgidTestJavaDeveloper.controllers;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.SuccessCompanyListResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.SuccessOrFailureResponse;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;
import br.com.tgid.TgidTestJavaDeveloper.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailureResponse> createCompany(@RequestBody CreateCompanyDTO createCompanyDTO) {
        logger.info("Creating company from " + createCompanyDTO.toString());
        try {
            var companyCreated = companyService.create(createCompanyDTO);
            if (companyCreated) {
                return ResponseEntity.ok(new SuccessOrFailureResponse(true, "Company created successfully"));
            }
            return ResponseEntity.badRequest().body(new SuccessOrFailureResponse(false, "Company not created"));
        } catch (CompanyServiceException e) {
            return ResponseEntity.badRequest().body(new SuccessOrFailureResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<SuccessCompanyListResponse> listAllCompanies() {
        try {
            var allCompanies = companyService.listAllCompanies();
            return ResponseEntity.ok(new SuccessCompanyListResponse(true, "all companies", allCompanies));
        } catch (CompanyServiceException e) {
            return ResponseEntity.badRequest().body(new SuccessCompanyListResponse(false, e.getMessage(), List.of()));

        }
    }
}
