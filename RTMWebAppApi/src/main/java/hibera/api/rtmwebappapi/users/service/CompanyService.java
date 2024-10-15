package hibera.api.rtmwebappapi.users.service;

import hibera.api.rtmwebappapi.repository.CompanyRepository;
import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.dto.CompanyRequestDTO;
import hibera.api.rtmwebappapi.users.dto.CompanyResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public CompanyResponseDTO createCompany(CompanyRequestDTO request) {
        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setCompanyAddress(request.getCompanyAddress());
        company.setCompanyEmail(request.getCompanyEmail());
        company.setCompanyPhonenumber(request.getCompanyPhonenumber());
        company.setCompanyBtwNumber(request.getCompanyBtwNumber());
        company.setCompanyKvkNumber(request.getCompanyKvkNumber());
        company.setCompanyCreationDate(LocalDateTime.now());
        company.setCompanyLastUpdated(LocalDateTime.now());

        Company savedCompany = companyRepository.save(company);

        return mapToCompanyResponse(savedCompany);
    }

    @Transactional
    public CompanyResponseDTO updateCompany(Long id, Company updatedCompany) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setCompanyName(updatedCompany.getCompanyName());
                    company.setCompanyAddress(updatedCompany.getCompanyAddress());
                    company.setCompanyEmail(updatedCompany.getCompanyEmail());
                    company.setCompanyPhonenumber(updatedCompany.getCompanyPhonenumber());
                    company.setCompanyBtwNumber(updatedCompany.getCompanyBtwNumber());
                    company.setCompanyKvkNumber(updatedCompany.getCompanyKvkNumber());
                    company.setCompanyLastUpdated(LocalDateTime.now());

                    Company updatedEntity = companyRepository.save(company);
                    return mapToCompanyResponse(updatedEntity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + id));
    }

    private CompanyResponseDTO mapToCompanyResponse(Company company) {
        CompanyResponseDTO response = new CompanyResponseDTO();
        response.setCompanyName(company.getCompanyName());
        response.setCompanyAddress(company.getCompanyAddress());
        response.setCompanyEmail(company.getCompanyEmail());
        response.setCompanyPhonenumber(company.getCompanyPhonenumber());
        response.setCompanyBtwNumber(company.getCompanyBtwNumber());
        response.setCompanyKvkNumber(company.getCompanyKvkNumber());
        response.setCompanyCreationDate(company.getCompanyCreationDate());
        return response;
    }
}
