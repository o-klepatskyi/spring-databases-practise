package ua.edu.ukma.klepatskyi.practise1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.klepatskyi.practise1.entity.Truck;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}