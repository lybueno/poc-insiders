package io.github.lybueno.rest.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInsertDTO extends AddressDTO {

    private Integer customerId;
}
