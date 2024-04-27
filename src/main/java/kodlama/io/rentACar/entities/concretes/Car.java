package kodlama.io.rentACar.entities.concretes;

import jakarta.persistence.*;
import kodlama.io.rentACar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int modelYear;
    private String plate;
    @Enumerated(EnumType.STRING)
    private State state;
    private double dailyPrice;
 
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @OneToMany(mappedBy = "car")
    private List<Maintenance> maintenances; // Eger Car tablosunda maintenance_id olarak tutmak isetmiyorsak, List seklinde calisiyoruz.

    @OneToMany(mappedBy = "car")
    private List<Rental> rentals;
}

