package io.github.lybueno.service;

import io.github.lybueno.model.entity.Address;
import io.github.lybueno.model.entity.Customer;
import io.github.lybueno.model.repository.AddressRepository;
import io.github.lybueno.model.repository.CustomerRepository;
import io.github.lybueno.rest.dto.address.AddressDTO;
import io.github.lybueno.rest.dto.address.AddressInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository repository;
    private CustomerRepository customerRepository;

    @Autowired
    public AddressService(AddressRepository repository, CustomerRepository customerRepository){
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public AddressDTO findById(Integer id) {
        Optional<Address> obj = repository.findById(id);
        Address entity = obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não " +
                "encontrado"));
        return new AddressDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<AddressDTO> findAllPaged(Pageable pageable) {
        Page<Address> list = repository.findAll(pageable);
        return list.map(address -> new AddressDTO(address));
    }

    @Transactional
    public AddressDTO insert(AddressInsertDTO dto){
        ResponseEntity<AddressDTO> newDto = cepIsValid(dto.getCep());
        Address entity = new Address();
        dto.setLocalidade(newDto.getBody().getLocalidade());
        dto.setUf(newDto.getBody().getUf());
        copyToEntity(dto, entity);
        entity = repository.save(entity);
        return new AddressDTO(entity);
    }

    @Transactional
    public AddressDTO update(Integer id, AddressDTO dto) {
        try{
            Address entity = repository.getById(id);
            ResponseEntity<AddressDTO> newDto = cepIsValid(dto.getCep());
            copyToEntityUpdate(dto, entity);
            entity = repository.save(entity);
            return new AddressDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found.");
        }
    }

    public void delete(Integer id) {
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found.");
        }
    }

    private void copyToEntityUpdate(AddressDTO dto, Address entity) {
        String processedCep = dto.getCep().replaceAll("[^0-9]+", "");
        entity.setCep(processedCep);
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setLocalidade(dto.getLocalidade());
        entity.setUf(dto.getUf());
    }

    private ResponseEntity<AddressDTO> cepIsValid(String cep) {
        String processedCep = cep.replaceAll("[^0-9]+", "");
        System.out.println(processedCep);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/"+processedCep+"/json";
        try{
            ResponseEntity<AddressDTO> response = restTemplate.getForEntity(url, AddressDTO.class);
            return response;
        } catch (Exception e){
            throw new EntityNotFoundException("CEP not found.");
        }
    }

    private void copyToEntity(AddressInsertDTO dto, Address entity) {
        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        entity.setCustomer(customer.get());
        entity.setCep(dto.getCep().replaceAll("[^0-9]+", ""));
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setLocalidade(dto.getLocalidade());
        entity.setUf(dto.getUf());
    }

}
