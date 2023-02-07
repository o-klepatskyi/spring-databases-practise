package ua.edu.ukma.klepatskyi.practise1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.klepatskyi.practise1.entity.Department;
import ua.edu.ukma.klepatskyi.practise1.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDepartment(Department department);
}