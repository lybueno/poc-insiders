package io.github.lybueno.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{field.name.required}")
    private String name;

    @Column(nullable = false, length = 11)
    @NotBlank(message = "{filed.cpf.required}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
}
