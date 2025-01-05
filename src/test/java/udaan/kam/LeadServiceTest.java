package udaan.kam;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import kam.model.Contact;
import kam.model.Interaction;
import kam.model.Lead;
import kam.repo.ContactRepository;
import kam.repo.InteractionRepository;
import kam.repo.LeadRepository;
import kam.service.LeadService;

import java.util.Arrays;
import java.util.Optional;

public class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;
    @Mock
    private InteractionRepository interactionRepository;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private LeadService leadService;

    private Lead lead;
    private Interaction interaction;
    private Contact contact;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        lead = new Lead();
        lead.setId(1L);
        lead.setTotalOrders(5);
        lead.setPerformanceScore(1.5);

        interaction = new Interaction();
        interaction.setId(1L);
        interaction.setLead(lead);

        contact = new Contact();
        contact.setId(1L);
        contact.setLead(lead);
    }

    @Test
    void testAddLead() {
        // Arrange
        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        // Act
        Lead result = leadService.addLead(lead);

        // Assert
        assertNotNull(result);
        assertEquals(lead, result);
        verify(leadRepository, times(1)).save(lead);
    }

    @Test
    void testGetAllLeads() {
        // Arrange
        when(leadRepository.findAll()).thenReturn(Arrays.asList(lead));

        // Act
        var result = leadService.getAllLeads();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(lead, result.get(0));
    }

    @Test
    void testRecordInteraction() {
        // Arrange
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(interactionRepository.save(any(Interaction.class))).thenReturn(interaction);
        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        // Act
        leadService.recordInteraction(1L, interaction);

        // Assert
        verify(interactionRepository, times(1)).save(interaction);
        verify(leadRepository, times(1)).save(lead);
        assertEquals(6, lead.getTotalOrders());
    }

    @Test
    void testGetInteractionsByLead() {
        // Arrange
        when(interactionRepository.findByLeadId(1L)).thenReturn(Arrays.asList(interaction));

        // Act
        var result = leadService.getInteractionsByLead(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(interaction, result.get(0));
    }

    @Test
    void testAddContactToLead() {
        // Arrange
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Act
        Contact result = leadService.addContactToLead(contact, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(contact, result);
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void testGetContactsByLead() {
        // Arrange
        when(contactRepository.findByLeadId(1L)).thenReturn(Arrays.asList(contact));

        // Act
        var result = leadService.getContactsByLead(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contact, result.get(0));
    }

    @Test
    void testUpdateLeadStatus() {
        // Arrange
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        // Act
        leadService.updateLeadStatus(1L, "Active");

        // Assert
        assertEquals("Active", lead.getStatus());
        verify(leadRepository, times(1)).save(lead);
    }
}