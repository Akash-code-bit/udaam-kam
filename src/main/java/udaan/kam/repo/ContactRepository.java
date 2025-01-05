package kam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import kam.model.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByLeadId(Long leadId);
}

