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

        // Eliminamos setPrecio() ya que la entidad Funcion no tiene ese atributo

        return funcionRepository.save(funcion);
    }

    public List<Funcion> obtenerTodas() {
        return funcionRepository.findAll();
    }
}