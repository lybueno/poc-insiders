package io.github.lybueno.rest.dto.customer;

import io.github.lybueno.model.entity.Address;
import io.github.lybueno.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;

    @NotBlank(message = "{field.name.required}")
    private String name;

    @NotBlank(message = "{filed.cpf.required}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;

    public CustomerDTO(Customer entity){
        id = entity.getId();
        name = entity.getName();
        cpf = entity.getCpf();
    }

}
