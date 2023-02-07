package ua.edu.ukma.klepatskyi.practise1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.edu.ukma.klepatskyi.practise1.entity.Client;
import ua.edu.ukma.klepatskyi.practise1.entity.Order;
import ua.edu.ukma.klepatskyi.practise1.entity.OrderStatus;
import ua.edu.ukma.klepatskyi.practise1.repository.ClientRepository;
import ua.edu.ukma.klepatskyi.practise1.repository.OrderRepository;

import java.util.Optional;

@SpringBootApplication
public class Practise1Application {

    public static void main(String[] args) {
        SpringApplication.run(Practise1Application.class, args);
    }

    //@Bean
    public CommandLineRunner run(ClientRepository clientRepository,
            OrderRepository orderRepository) {
        return (String[] args) -> {
            System.out.println("Hello world!");
            Client client = new Client();
            client.setFullName("Mark Zuckerberg");
            client.setNumber("0501234567");
            clientRepository.save(client);
            Order order = new Order();
            order.setCostInCents(10000L);
            order.setStatus(OrderStatus.NEW);
            order.setWeight(100.0);
            order.setVolume(50.0);
            order.setSender(client);
            order.setReceiver(client);
            order = orderRepository.save(order);
            System.out.println(order);
            Optional<Order> res = orderRepository.findById(1L);
            System.out.println(res.isPresent() ? res.get() : "Empty");
        };
    }

}
