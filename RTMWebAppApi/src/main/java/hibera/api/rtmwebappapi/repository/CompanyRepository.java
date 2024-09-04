package hibera.api.rtmwebappapi.repository;

import hibera.api.rtmwebappapi.users.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
