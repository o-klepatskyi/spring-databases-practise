package ua.edu.ukma.klepatskyi.practise1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.klepatskyi.practise1.entity.OrderHistoryEntry;

public interface OrderHistoryEntryRepository extends JpaRepository<OrderHistoryEntry, Long> {
}