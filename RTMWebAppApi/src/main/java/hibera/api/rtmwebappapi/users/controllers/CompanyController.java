package hibera.api.rtmwebappapi.users.controllers;

import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.users.dto.CreateCompanyRequest;
import hibera.api.rtmwebappapi.users.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/company")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin:create', 'manager:create')")
    public ResponseEntity createCompany(@RequestBody CreateCompanyRequest request) {
        companyService.createCompany(request);
        return ResponseEntity.ok(request);
    }
}
