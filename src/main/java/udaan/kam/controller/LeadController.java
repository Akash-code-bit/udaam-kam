package kam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kam.model.Contact;
import kam.model.Interaction;
import kam.model.Lead;
import kam.service.LeadService;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.addLead(lead));
    }

    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }

    @PostMapping("/{leadId}/contact")
    public ResponseEntity<Contact> addContactToLead(@RequestBody Contact contact, @PathVariable Long leadId) {
        leadService.addContactToLead(contact, leadId);
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.addContactToLead(contact, leadId));
    }

    @GetMapping("{leadId}/contact")
    public ResponseEntity<List<Contact>> getContactsByLead(@PathVariable Long leadId) {
        return ResponseEntity.ok(leadService.getContactsByLead(leadId));
    }

    @PostMapping("/{leadId}/interactions")
    public ResponseEntity<Void> recordInteraction(@PathVariable Long leadId, @RequestBody Interaction interaction) {
        leadService.recordInteraction(leadId, interaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{leadId}/interactions")
    public ResponseEntity<List<Interaction>> getInteractionsByLead(@PathVariable Long leadId) {
        return ResponseEntity.ok(leadService.getInteractionsByLead(leadId));
    }

    @GetMapping("/requires-call")
    public ResponseEntity<List<Lead>> getLeadsRequiringCall() {
        return ResponseEntity.ok(leadService.getLeadsRequiringCall());
    }

    @PatchMapping("/{leadId}/status")
    public ResponseEntity<Void> updateLeadStatus(@PathVariable Long leadId, @RequestParam("status") String status) {
        leadService.updateLeadStatus(leadId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/highperforming")
    public ResponseEntity<List<Lead>> getHighPerformingLeads(@RequestParam("minOrders") int minOrders) {
        return ResponseEntity.ok(leadService.getHighPerformingLeads(minOrders));
    }

    @GetMapping("/underperforming")
    public ResponseEntity<List<Lead>> getUnderperformingLeads(@RequestParam("minOrders") int minOrders) {
        return ResponseEntity.ok(leadService.getUnderperformingLeads(minOrders));
    }
}