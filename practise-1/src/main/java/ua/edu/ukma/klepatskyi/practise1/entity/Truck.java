package ua.edu.ukma.klepatskyi.practise1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trucks")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Positive
    @Column(nullable=false)
    private Double volumeCapacity;

    @Positive
    @Column(nullable=false)
    private Double weightCapacity;

    @OneToMany
    @ToString.Exclude
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Truck truck = (Truck) o;
        return id != null && Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}