package io.github.lybueno.service;

import io.github.lybueno.model.entity.Address;
import io.github.lybueno.model.entity.Customer;
import io.github.lybueno.model.repository.AddressRepository;
import io.github.lybueno.model.repository.CustomerRepository;
import io.github.lybueno.rest.dto.customer.CustomerDTO;
import io.github.lybueno.rest.dto.customer.CustomerInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository repository;
    private AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository repository, AddressRepository addressRepository){
        this.repository = repository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Integer id){
        Optional<Customer> obj = repository.findById(id);
        Customer entity = obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
        return new CustomerDTO(entity);

    }

    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAllPaged(Pageable pageable){
        Page<Customer> list = repository.findAll(pageable);
        return list.map(customer -> new CustomerDTO(customer));
    }

    @Transactional
    public CustomerDTO insert(CustomerInsertDTO dto) {
        Customer entity = new Customer();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        if(!dto.getAddresses().isEmpty()){
            for (Address address : dto.getAddresses()) {
                address.setCustomer(entity);
                addressRepository.save(address);
            }
        }
        return new CustomerDTO(entity);
    }

    @Transactional
    public CustomerDTO update(Integer id, CustomerDTO dto) {
        try{
            Customer entity = repository.getById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CustomerDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found");
        }
    }

    public void delete(Integer id) {
        try{
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found");
        }
    }

    private void copyDtoToEntity(CustomerDTO dto, Customer entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
    }

}
