package io.github.lybueno.rest.dto.customer;

import io.github.lybueno.model.entity.Address;
import io.github.lybueno.rest.dto.customer.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInsertDTO extends CustomerDTO {

    @Size(max = 5, message = "{field.addresses.exceeded}")
    private List<Address> addresses = new ArrayList<>();
}
