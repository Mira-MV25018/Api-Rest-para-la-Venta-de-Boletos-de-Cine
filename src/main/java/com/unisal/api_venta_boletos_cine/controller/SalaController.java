package com.unisal.api_venta_boletos_cine.controller;

import com.unisal.api_venta_boletos_cine.dto.SalaDTO;
import com.unisal.api_venta_boletos_cine.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarSalas() {
        return ResponseEntity.ok(salaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> obtenerSala(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SalaDTO> crearSala(@Valid @RequestBody SalaDTO salaDTO) {
        return new ResponseEntity<>(salaService.crearSala(salaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> actualizarSala(@PathVariable Long id, @Valid @RequestBody SalaDTO salaDTO) {
        return ResponseEntity.ok(salaService.actualizarSala(id, salaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSala(@PathVariable Long id) {
        salaService.eliminarSala(id);
        return ResponseEntity.noContent().build();
    }
}