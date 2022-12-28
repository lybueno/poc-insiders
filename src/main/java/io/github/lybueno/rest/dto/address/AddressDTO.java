package io.github.lybueno.rest.dto.address;

import io.github.lybueno.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;

    public AddressDTO(Address entity){
        id = entity.getId();
        cep = entity.getCep();
        logradouro = entity.getLogradouro();
        numero = entity.getNumero();
        bairro = entity.getBairro();
        localidade = entity.getLocalidade();
        uf = entity.getUf();
    }
}
