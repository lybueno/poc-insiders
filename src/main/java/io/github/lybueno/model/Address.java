package io.github.lybueno.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    private Customer customer;

    @Column(nullable = false)
    @Size(max = 9, min = 8, message = "{field.cep.invalid")
    @NotBlank(message = "{field.cep.required}")
    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    @NotBlank(message = "{field.numero.required")
    private String numero;

    @Column(name = "main_address")
    private Boolean isMainAddress;
}
