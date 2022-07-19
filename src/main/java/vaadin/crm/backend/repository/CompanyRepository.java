package vaadin.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vaadin.crm.backend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
