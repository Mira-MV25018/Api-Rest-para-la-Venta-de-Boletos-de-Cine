package com.unisal.api_venta_boletos_cine.controller;

import com.unisal.api_venta_boletos_cine.dto.FuncionRequestDTO;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
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
    public ResponseEntity<Funcion> crearFuncion(@Valid @RequestBody FuncionRequestDTO dto) {
        Funcion nuevaFuncion = funcionService.crearFuncion(dto);
        return new ResponseEntity<>(nuevaFuncion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Funcion>> obtenerTodas() {
        return ResponseEntity.ok(funcionService.obtenerTodas());
    }
}