package kam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import kam.model.Interaction;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByLeadId(Long leadId);
}

