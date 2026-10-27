package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.FuncionDTO;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
import com.unisal.api_venta_boletos_cine.repository.FuncionRepository;
import com.unisal.api_venta_boletos_cine.repository.PeliculaRepository;
import com.unisal.api_venta_boletos_cine.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionService {

    private final FuncionRepository funcionRepository;
    private final PeliculaRepository peliculaRepository;
    private final SalaRepository salaRepository;

    public Funcion crearFuncion(FuncionDTO dto) {
        // Convertimos el Integer del DTO al Long que espera el repositorio
        var pelicula = peliculaRepository.findById(Long.valueOf(dto.getIdPelicula()))
                .orElseThrow(() -> new RuntimeException("Error: La película con ID " + dto.getIdPelicula() + " no existe."));

        var sala = salaRepository.findById(Long.valueOf(dto.getIdSala()))
                .orElseThrow(() -> new RuntimeException("Error: La sala con ID " + dto.getIdSala() + " no existe."));

        Funcion funcion = new Funcion();
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        funcion.setFechaHora(dto.getFechaHora());
        funcion.setPrecioBase(dto.getPrecio()); // Esta línea soluciona el error 500

        return funcionRepository.save(funcion);
    }

    public List<Funcion> obtenerTodas() {
        return funcionRepository.findAll();
    }
    public Funcion actualizarFuncion(Long id, FuncionDTO dto) {
        Funcion funcion = funcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la función con ID: " + id));

        var pelicula = peliculaRepository.findById(
                        Long.valueOf(dto.getIdPelicula()))
                .orElseThrow(() -> new RuntimeException(
                        "La película no existe"));

        var sala = salaRepository.findById(
                        Long.valueOf(dto.getIdSala()))
                .orElseThrow(() -> new RuntimeException(
                        "La sala no existe"));

        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        funcion.setFechaHora(dto.getFechaHora());
        funcion.setPrecioBase(dto.getPrecio());

        return funcionRepository.save(funcion);
    }

}