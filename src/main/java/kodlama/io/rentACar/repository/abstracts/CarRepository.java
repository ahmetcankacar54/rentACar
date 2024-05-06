package kodlama.io.rentACar.repository.abstracts;

import kodlama.io.rentACar.entities.concretes.Car;
import kodlama.io.rentACar.entities.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByStateIsNot(State state);
    boolean existsByPlate(String plate);
}
