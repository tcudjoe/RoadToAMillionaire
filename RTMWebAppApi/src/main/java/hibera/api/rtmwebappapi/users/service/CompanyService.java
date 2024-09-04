package hibera.api.rtmwebappapi.users.service;

import hibera.api.rtmwebappapi.repository.CompanyRepository;
import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.dto.CreateCompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void createCompany(CreateCompanyRequest request) {
        Company company = new Company();
        company.setCompany_name(request.getCompany_name());
        company.setCompany_address(request.getCompany_address());
        company.setCompany_email(request.getCompany_email());
        company.setCompany_phonenumber(request.getCompany_phonenumber());
        company.setCompany_btw_number(request.getCompany_btw_number());
        company.setCompany_kvk_number(request.getCompany_kvk_number());
        company.setCompany_creation_date(LocalDateTime.now());

        companyRepository.save(company);
    }

}
