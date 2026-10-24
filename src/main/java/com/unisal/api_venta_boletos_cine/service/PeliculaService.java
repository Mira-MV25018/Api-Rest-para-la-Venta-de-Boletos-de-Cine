package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.PeliculaDTO;
import com.unisal.api_venta_boletos_cine.entity.Pelicula;
import com.unisal.api_venta_boletos_cine.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    private PeliculaDTO convertirADTO(Pelicula pelicula) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setIdPelicula(pelicula.getIdPelicula());
        dto.setTitulo(pelicula.getTitulo());
        dto.setDuracion(pelicula.getDuracion());
        dto.setClasificacion(pelicula.getClasificacion());
        dto.setGenero(pelicula.getGenero());
        return dto;
    }

    private Pelicula convertirAEntidad(PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();
        pelicula.setIdPelicula(dto.getIdPelicula());
        pelicula.setTitulo(dto.getTitulo());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setGenero(dto.getGenero());
        return pelicula;
    }

    public List<PeliculaDTO> obtenerTodas() {
        return peliculaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PeliculaDTO obtenerPorId(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id));
        return convertirADTO(pelicula);
    }

    public PeliculaDTO guardar(PeliculaDTO peliculaDTO) {
        Pelicula pelicula = convertirAEntidad(peliculaDTO);
        Pelicula guardada = peliculaRepository.save(pelicula);
        return convertirADTO(guardada);
    }

    public PeliculaDTO actualizar(Long id, PeliculaDTO peliculaDTO) {
        Pelicula peliculaExistente = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id));

        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setDuracion(peliculaDTO.getDuracion());
        peliculaExistente.setClasificacion(peliculaDTO.getClasificacion());
        peliculaExistente.setGenero(peliculaDTO.getGenero());

        Pelicula actualizada = peliculaRepository.save(peliculaExistente);
        return convertirADTO(actualizada);
    }

    public void eliminar(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new RuntimeException("Película no encontrada con ID: " + id);
        }
        peliculaRepository.deleteById(id);
    }
}