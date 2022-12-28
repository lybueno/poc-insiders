package io.github.lybueno.rest.controller;

import io.github.lybueno.rest.dto.address.AddressDTO;
import io.github.lybueno.rest.dto.address.AddressInsertDTO;
import io.github.lybueno.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService service;

    @Autowired
    public AddressController(AddressService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Integer id) {
        AddressDTO address = service.findById(id);
        return ResponseEntity.ok().body(address);
    }

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable){
        Page<AddressDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@Valid @RequestBody AddressInsertDTO dto){
        AddressDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Integer id, @Valid @RequestBody AddressDTO dto){
        AddressDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    // TODO Criar endpoint para alterar atributo de endereco principal

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
