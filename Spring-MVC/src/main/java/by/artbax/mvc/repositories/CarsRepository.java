package by.artbax.mvc.repositories;

import by.artbax.mvc.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarsRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByOwner_FirstName(String firstNameOwner);

    @Query(nativeQuery = true, value = "SELECT * FROM user_car WHERE model = ?1;")
    List<Car> findAllByModel(String model);
}
