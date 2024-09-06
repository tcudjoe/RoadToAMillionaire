package hibera.api.rtmwebappapi.users.controllers;

import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.dto.CompanyRequest;
import hibera.api.rtmwebappapi.users.dto.CompanyResponse;
import hibera.api.rtmwebappapi.users.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
//@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("create")
//    @PreAuthorize("hasAnyAuthority('admin:create', 'manager:create', 'user:create')")
    public ResponseEntity createCompany(@RequestBody CompanyRequest request) {
        companyService.createCompany(request);
        return ResponseEntity.ok(request);
    }

    @PutMapping("update/{id}")
//    @PreAuthorize("hasAnyAuthority('admin:create', 'manager:create', 'user:create')")
    public ResponseEntity updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        CompanyResponse company = companyService.updateCompany(id, updatedCompany);
        return ResponseEntity.ok(company);
    }


}
