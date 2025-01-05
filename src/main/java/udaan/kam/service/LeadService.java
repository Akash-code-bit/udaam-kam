package kam.service;

import org.springframework.stereotype.Service;
import kam.model.Contact;
import kam.model.Interaction;
import kam.model.Lead;
import kam.repo.ContactRepository;
import kam.repo.InteractionRepository;
import kam.repo.LeadRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final InteractionRepository interactionRepository;
    private final ContactRepository contactRepository;

    public LeadService(LeadRepository leadRepository,
                       InteractionRepository interactionRepository,
                       ContactRepository contactRepository) {
        this.leadRepository = leadRepository;
        this.interactionRepository = interactionRepository;
        this.contactRepository = contactRepository;
    }

    public Lead addLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public void recordInteraction(Long leadId,
                                  Interaction interaction) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
        interaction.setLead(lead);
        interaction.setDate(LocalDateTime.now());
        interactionRepository.save(interaction);
        lead.setLastCallDate(LocalDateTime.now());
        lead.setTotalOrders(lead.getTotalOrders() + 1);
        lead.setPerformanceScore(calculatePerformanceScore(lead));
        leadRepository.save(lead);
    }

    public List<Interaction> getInteractionsByLead(Long leadId) {
        return interactionRepository.findByLeadId(leadId);
    }

    public Contact addContactToLead(Contact contact, Long leadId) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
        contact.setLead(lead);
        return contactRepository.save(contact);
    }

    public List<Contact> getContactsByLead(Long leadId) {
        return contactRepository.findByLeadId(leadId);
    }

    private double calculatePerformanceScore(Lead lead) {
        List<Interaction> interactions = lead.getInteractions();
        if (interactions == null) {
            return 0;  // or handle it appropriately
        }
        int totalInteractions = lead.getInteractions().size();
        return totalInteractions == 0 ? 0 : (double) lead.getTotalOrders() / totalInteractions;
    }

    public List<Lead> getLeadsRequiringCall() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1); // Example: daily call requirement
        return leadRepository.findLeadsRequiringCall(cutoffDate);
    }

    public void updateLeadStatus(Long leadId, String status) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
        lead.setStatus(status);
        leadRepository.save(lead);
    }

    public List<Lead> getHighPerformingLeads(int minOrders) {
        return leadRepository.findHighPerformingLeads(minOrders);
    }

    public List<Lead> getUnderperformingLeads(int minOrders) {
        return leadRepository.findUnderperformingLeads(minOrders);
    }
}
