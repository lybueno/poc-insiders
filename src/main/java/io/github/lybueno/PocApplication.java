package io.github.lybueno;

import io.github.lybueno.model.entity.Address;
import io.github.lybueno.model.entity.Customer;
import io.github.lybueno.model.repository.AddressRepository;
import io.github.lybueno.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PocApplication {

    @Bean
    public CommandLineRunner run(@Autowired CustomerRepository customerRepository,
                                 @Autowired AddressRepository addressRepository){
        return args -> {
            Customer customer =
                    Customer.builder().name("Obi-Wan Kenobi").cpf("23873887045").build();
            Address address =
                    Address.builder().cep("35613000").logradouro("Rua das Galáxias").numero("888").localidade(
                            "Estrela do Indaiá").uf("MG").build();
            address.setCustomer(customer);
            address.setIsMainAddress(true);
            List<Address> addresses = new ArrayList<>();
            addresses.add(address);
            customer.setAddresses(addresses);
            customerRepository.save(customer);
            addressRepository.save(address);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(PocApplication.class, args);
    }
}
