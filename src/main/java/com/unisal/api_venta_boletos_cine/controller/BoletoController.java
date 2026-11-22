package com.unisal.api_venta_boletos_cine.controller;

import com.unisal.api_venta_boletos_cine.dto.BoletoDTO;
import com.unisal.api_venta_boletos_cine.service.BoletoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boletos")
@RequiredArgsConstructor
public class BoletoController {

    private final BoletoService boletoService;

    @GetMapping
    public ResponseEntity<List<BoletoDTO>> listarBoletos() {
        return ResponseEntity.ok(boletoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoletoDTO> obtenerBoleto(@PathVariable Long id) {
        return ResponseEntity.ok(boletoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<BoletoDTO> crearBoleto(@Valid @RequestBody BoletoDTO boletoDTO) {
        return new ResponseEntity<>(boletoService.crearBoleto(boletoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoletoDTO> actualizarBoleto(@PathVariable Long id, @Valid @RequestBody BoletoDTO boletoDTO) {
        return ResponseEntity.ok(boletoService.actualizarBoleto(id, boletoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleto(@PathVariable Long id) {
        boletoService.eliminarBoleto(id);
        return ResponseEntity.noContent().build();
    }
}