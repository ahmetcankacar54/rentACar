package kodlama.io.rentACar.entities.concretes;

import jakarta.persistence.*;
import kodlama.io.rentACar.constants.CarStates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private CarStates state;
    private double dailyPrice;
 
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
}

