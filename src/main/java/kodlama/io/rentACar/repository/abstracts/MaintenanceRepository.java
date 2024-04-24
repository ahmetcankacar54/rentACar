package kodlama.io.rentACar.repository.abstracts;

import kodlama.io.rentACar.entities.concretes.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
    Maintenance findByCarIdAndIsCompletedIsFalse(int id);
    boolean existsByCarIdAndIsCompletedIsFalse(int carId);
}
