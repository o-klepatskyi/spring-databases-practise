package ua.edu.ukma.klepatskyi.practise1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.ukma.klepatskyi.practise1.entity.Client;
import ua.edu.ukma.klepatskyi.practise1.entity.Department;
import ua.edu.ukma.klepatskyi.practise1.entity.Order;
import ua.edu.ukma.klepatskyi.practise1.entity.OrderStatus;
import ua.edu.ukma.klepatskyi.practise1.repository.ClientRepository;
import ua.edu.ukma.klepatskyi.practise1.repository.DepartmentRepository;
import ua.edu.ukma.klepatskyi.practise1.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DepartmentTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void uniqueNameConstraintHolds() {
        String name = "non unique name";
        long code = 1L;
        Department department1 = Department.builder()
                .code(code++)
                .name(name)
                .build();
        assertDoesNotThrow(() -> departmentRepository.save(department1));

        Department department2 = Department.builder()
                .code(code++)
                .name(name)
                .build();
        assertThrows(Exception.class, () -> departmentRepository.save(department2));
    }

    @Test
    void uniqueCodeConstraintHolds() {
        String name = "Department";
        long code = 2L;
        Department department1 = Department.builder()
                .code(code)
                .name(name)
                .build();
        assertDoesNotThrow(() -> departmentRepository.save(department1));

        Department department2 = Department.builder()
                .code(code)
                .name(name + "1")
                .build();
        assertThrows(Exception.class, () -> departmentRepository.save(department2));
    }

    Client getTestClient() {
        Optional<Client> clientOp = clientRepository.findById(1L);
        if (clientOp.isPresent())
            return clientOp.get();
        Client testClient = Client.builder()
                .fullName("Mark Zuckerberg")
                .number("0501234567")
                .build();
        assertDoesNotThrow(() -> clientRepository.save(testClient));
        return testClient;
    }

    Order getTestOrder() {
        Optional<Order> orderOptional = orderRepository.findById(1L);
        if (orderOptional.isPresent())
            return orderOptional.get();

        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .receiver(client)
                .volume(1.0)
                .weight(1.0)
                .costInCents(50000L)
                .status(OrderStatus.NEW)
                .build();
        assertDoesNotThrow(() -> orderRepository.save(order));
        return order;
    }

    @Test
    void removesAllOrdersInDepartmentWhenItIsDeleted() {
        Department department = Department.builder()
                .code(100L)
                .name("very unique name of department")
                .build();
        assertDoesNotThrow(() -> departmentRepository.save(department));
        Order order = getTestOrder();
        order.setDepartment(department);
        assertDoesNotThrow(() -> orderRepository.save(order));
        System.out.println("Department id: " + department.getId());
        List<Order> departmentOrders = orderRepository.findByDepartment(department);
        assertEquals(1, departmentOrders.size());
        assertDoesNotThrow(() -> departmentRepository.delete(department));
        System.out.println("Department id: " + department.getId());
        departmentOrders = orderRepository.findByDepartment(department);
        assertEquals(0, departmentOrders.size());
    }

}
