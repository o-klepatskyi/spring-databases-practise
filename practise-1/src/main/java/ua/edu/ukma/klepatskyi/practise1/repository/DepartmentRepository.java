package ua.edu.ukma.klepatskyi.practise1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.klepatskyi.practise1.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}