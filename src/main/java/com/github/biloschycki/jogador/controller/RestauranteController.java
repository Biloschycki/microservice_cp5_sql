package com.github.biloschycki.jogador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.biloschycki.jogador.dto.request.RestauranteRequestDTO;
import com.github.biloschycki.jogador.dto.response.RestauranteResponseDTO;
import com.github.biloschycki.jogador.service.RestauranteService;

import jakarta.validation.Valid;

/**
 * Controller responsável pelas rotas de pedidos (Restaurante).
 * Não manipula entidades JPA diretamente: recebe e retorna apenas DTOs,
 * delegando a lógica de negócio e conversão para a camada de Service/Mapper.
 */
@RestController
@RequestMapping("api/${api.version}/pedidos")
public class RestauranteController {

    @Autowired
    private RestauranteService service;

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> create(@Valid @RequestBody RestauranteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RestauranteRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
