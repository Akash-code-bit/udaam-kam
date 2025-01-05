package kam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String status;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL)
    private List<Interaction> interactions;

    private LocalDateTime lastCallDate;
    private int callFrequency;

    private double performanceScore;
    private int totalOrders;
}
