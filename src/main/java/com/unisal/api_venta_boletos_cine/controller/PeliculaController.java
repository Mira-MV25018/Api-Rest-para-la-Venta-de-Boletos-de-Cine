package com.unisal.api_venta_boletos_cine.controller;

import com.unisal.api_venta_boletos_cine.dto.PeliculaDTO;
import com.unisal.api_venta_boletos_cine.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> obtenerTodas() {
        return ResponseEntity.ok(peliculaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> crear(@RequestBody PeliculaDTO peliculaDTO) {
        return new ResponseEntity<>(peliculaService.guardar(peliculaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> actualizar(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO) {
        return ResponseEntity.ok(peliculaService.actualizar(id, peliculaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        peliculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}