package kam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import kam.model.Lead;

import java.time.LocalDateTime;
import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    @Query("SELECT l FROM Lead l WHERE l.lastCallDate IS NULL OR l.lastCallDate <= :cutoffDate")
    List<Lead> findLeadsRequiringCall(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT l FROM Lead l WHERE l.totalOrders >= :minOrders")
    List<Lead> findHighPerformingLeads(@Param("minOrders") int minOrders);

    @Query("SELECT l FROM Lead l WHERE l.totalOrders < :minOrders")
    List<Lead> findUnderperformingLeads(@Param("minOrders") int minOrders);
}
