package ua.edu.ukma.klepatskyi.practise1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.ukma.klepatskyi.practise1.entity.Client;
import ua.edu.ukma.klepatskyi.practise1.entity.Order;
import ua.edu.ukma.klepatskyi.practise1.entity.OrderStatus;
import ua.edu.ukma.klepatskyi.practise1.repository.ClientRepository;
import ua.edu.ukma.klepatskyi.practise1.repository.OrderRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

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


    @Test
    void doesNotAcceptEmptyRequiredFields() {
        Order order = Order.builder().build();
        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesCostLessThan5000Cents() {
        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .receiver(client)
                .volume(1.0)
                .weight(1.0)
                .costInCents(4999L)
                .status(OrderStatus.NEW)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesNegativeVolume() {
        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .receiver(client)
                .volume(-1.0)
                .weight(1.0)
                .costInCents(5000L)
                .status(OrderStatus.NEW)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesNegativeWeight() {
        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .receiver(client)
                .volume(1.0)
                .weight(-1.0)
                .costInCents(5000L)
                .status(OrderStatus.NEW)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesNullStatus() {
        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .receiver(client)
                .volume(1.0)
                .weight(1.0)
                .costInCents(5000L)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesNullSender() {
        Client client = getTestClient();

        Order order = Order.builder()
                .receiver(client)
                .volume(1.0)
                .weight(1.0)
                .costInCents(5000L)
                .status(OrderStatus.NEW)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void invalidatesNullReceiver() {
        Client client = getTestClient();

        Order order = Order.builder()
                .sender(client)
                .volume(1.0)
                .weight(1.0)
                .costInCents(5000L)
                .status(OrderStatus.NEW)
                .build();

        assertThrows(Exception.class, () -> orderRepository.save(order));
    }

    @Test
    void createsCorrectOrder() {
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
    }

}
