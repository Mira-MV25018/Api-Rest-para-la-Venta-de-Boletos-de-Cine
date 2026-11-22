package com.unisal.api_venta_boletos_cine.service;
import com.unisal.api_venta_boletos_cine.dto.SalaDTO;
import com.unisal.api_venta_boletos_cine.entity.Sala;
import com.unisal.api_venta_boletos_cine.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;

    public List<SalaDTO> obtenerTodas() {
        return salaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public SalaDTO obtenerPorId(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException("Sala no encontrada con ID: " + id));
        return convertirADTO(sala);
    }

    public SalaDTO crearSala(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setNombre(dto.getNombre());
        sala.setCapacidadTotal(dto.getCapacidadTotal());
        return convertirADTO(salaRepository.save(sala));
    }

    public SalaDTO actualizarSala(Long id, SalaDTO dto) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException("Sala no encontrada con ID: " + id));
        sala.setNombre(dto.getNombre());
        sala.setCapacidadTotal(dto.getCapacidadTotal());
        return convertirADTO(salaRepository.save(sala));

    }

    public void eliminarSala(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException("Sala no encontrada con ID: " + id));
        salaRepository.delete(sala);
    }

    private SalaDTO convertirADTO(Sala sala) {
        SalaDTO dto = new SalaDTO();
        dto.setIdSala(sala.getIdSala());
        dto.setNombre(sala.getNombre());
        dto.setCapacidadTotal(sala.getCapacidadTotal());
        return dto;
    }
}