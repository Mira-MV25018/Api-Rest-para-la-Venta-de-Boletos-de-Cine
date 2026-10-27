package com.unisal.api_venta_boletos_cine.controller;

import com.unisal.api_venta_boletos_cine.dto.FuncionRequestDTO;
import com.unisal.api_venta_boletos_cine.dto.FuncionResponseDTO;
import com.unisal.api_venta_boletos_cine.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService funcionService;

    @PostMapping
    public ResponseEntity<FuncionResponseDTO> crear(@Valid @RequestBody FuncionRequestDTO dto) {
        return new ResponseEntity<>(funcionService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionResponseDTO>> listar() {
        return ResponseEntity.ok(funcionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(funcionService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody FuncionRequestDTO dto) {
        return ResponseEntity.ok(funcionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        funcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}