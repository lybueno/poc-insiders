package io.github.lybueno.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.lybueno.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
}
