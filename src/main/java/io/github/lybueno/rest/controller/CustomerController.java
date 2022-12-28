package io.github.lybueno.rest.controller;

import io.github.lybueno.rest.dto.customer.CustomerDTO;
import io.github.lybueno.rest.dto.customer.CustomerInsertDTO;
import io.github.lybueno.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Integer id){
        CustomerDTO customer = service.findById(id);
        return ResponseEntity.ok().body(customer);

    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> findAll(Pageable pageable){
        Page<CustomerDTO> list = service.findAllPaged((pageable));
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> insert(@Valid @RequestBody CustomerInsertDTO dto){
        CustomerDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Integer id, @Valid @RequestBody CustomerDTO dto){
        CustomerDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
