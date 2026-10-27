package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.FuncionRequestDTO;
import com.unisal.api_venta_boletos_cine.dto.FuncionResponseDTO;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
import com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException;
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

    private FuncionResponseDTO aDTO(Funcion f) {
        return FuncionResponseDTO.builder()
                .idFuncion(f.getIdFuncion())
                .idPelicula(f.getPelicula().getIdPelicula())
                .tituloPelicula(f.getPelicula().getTitulo())
                .idSala(f.getSala().getIdSala())
                .nombreSala(f.getSala().getNombre())
                .fechaHora(f.getFechaHora())
                .precioBase(f.getPrecioBase())
                .build();
    }

    private Funcion buscar(Long id) {
        return funcionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Función no encontrada con ID: " + id));
    }

    private void aplicar(Funcion f, FuncionRequestDTO dto) {
        f.setPelicula(peliculaRepository.findById(dto.getIdPelicula()).orElseThrow(() ->
                new RecursoNoEncontradoException("Película no encontrada con ID: " + dto.getIdPelicula())));
        f.setSala(salaRepository.findById(dto.getIdSala()).orElseThrow(() ->
                new RecursoNoEncontradoException("Sala no encontrada con ID: " + dto.getIdSala())));
        f.setFechaHora(dto.getFechaHora());
        f.setPrecioBase(dto.getPrecioBase());
    }

    public FuncionResponseDTO crear(FuncionRequestDTO dto) {
        Funcion f = new Funcion();
        aplicar(f, dto);
        return aDTO(funcionRepository.save(f));
    }

    public List<FuncionResponseDTO> obtenerTodas() {
        return funcionRepository.findAll().stream().map(this::aDTO).toList();
    }

    public FuncionResponseDTO obtenerPorId(Long id) {
        return aDTO(buscar(id));
    }

    public FuncionResponseDTO actualizar(Long id, FuncionRequestDTO dto) {
        Funcion f = buscar(id);
        aplicar(f, dto);
        return aDTO(funcionRepository.save(f));
    }

    public void eliminar(Long id) {
        buscar(id);
        funcionRepository.deleteById(id); // si tiene boletos, la FK dispara el 409 del handler
    }
}