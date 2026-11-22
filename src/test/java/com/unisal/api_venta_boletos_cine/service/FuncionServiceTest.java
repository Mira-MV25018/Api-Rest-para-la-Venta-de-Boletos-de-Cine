
package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.FuncionRequestDTO;
import com.unisal.api_venta_boletos_cine.dto.FuncionResponseDTO;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
import com.unisal.api_venta_boletos_cine.entity.Pelicula;
import com.unisal.api_venta_boletos_cine.entity.Sala;
import com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException;
import com.unisal.api_venta_boletos_cine.repository.FuncionRepository;
import com.unisal.api_venta_boletos_cine.repository.PeliculaRepository;
import com.unisal.api_venta_boletos_cine.repository.SalaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionServiceTest {

    @Mock
    private FuncionRepository funcionRepository;

    @Mock
    private PeliculaRepository peliculaRepository;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private FuncionService funcionService;

    private Pelicula pelicula;
    private Sala sala;
    private Funcion funcion;
    private FuncionRequestDTO request;

    @BeforeEach
    void prepararDatos() {
        pelicula = new Pelicula();
        pelicula.setIdPelicula(1L);
        pelicula.setTitulo("Spider-Man");

        sala = new Sala();
        sala.setIdSala(2L);
        sala.setNombre("Sala 1");

        funcion = new Funcion();
        funcion.setIdFuncion(10L);
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        funcion.setFechaHora(
                LocalDateTime.of(2026, 12, 1, 18, 30));
        funcion.setPrecioBase(5.50);

        request = new FuncionRequestDTO();
        request.setIdPelicula(1L);
        request.setIdSala(2L);
        request.setFechaHora(
                LocalDateTime.of(2026, 12, 1, 18, 30));
        request.setPrecioBase(5.50);
    }

    private void prepararRelaciones() {
        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.of(pelicula));

        when(salaRepository.findById(2L))
                .thenReturn(Optional.of(sala));
    }

    @Test
    void testCrearFuncion_Exito() {
        prepararRelaciones();

        when(funcionRepository.save(any(Funcion.class)))
                .thenAnswer(invocacion -> {
                    Funcion guardada = invocacion.getArgument(0);
                    guardada.setIdFuncion(10L);
                    return guardada;
                });

        FuncionResponseDTO resultado =
                funcionService.crear(request);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getIdFuncion());
        assertEquals("Spider-Man",
                resultado.getTituloPelicula());
        assertEquals("Sala 1", resultado.getNombreSala());
        assertEquals(5.50, resultado.getPrecioBase());

        verify(funcionRepository).save(any(Funcion.class));
    }

    @Test
    void testCrearFuncion_PeliculaNoEncontrada() {
        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> funcionService.crear(request));

        verify(funcionRepository, never()).save(any());
    }

    @Test
    void testCrearFuncion_SalaNoEncontrada() {
        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.of(pelicula));

        when(salaRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> funcionService.crear(request));

        verify(funcionRepository, never()).save(any());
    }

    @Test
    void testObtenerTodas() {
        when(funcionRepository.findAll())
                .thenReturn(List.of(funcion));

        List<FuncionResponseDTO> resultado =
                funcionService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("Spider-Man",
                resultado.get(0).getTituloPelicula());
        assertEquals(2L, resultado.get(0).getIdSala());
    }

    @Test
    void testObtenerPorId_Exito() {
        when(funcionRepository.findById(10L))
                .thenReturn(Optional.of(funcion));

        FuncionResponseDTO resultado =
                funcionService.obtenerPorId(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getIdFuncion());
        assertEquals(5.50, resultado.getPrecioBase());
    }

    @Test
    void testObtenerPorId_NoEncontrada() {
        when(funcionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> funcionService.obtenerPorId(99L));
    }

    @Test
    void testActualizarFuncion_Exito() {
        when(funcionRepository.findById(10L))
                .thenReturn(Optional.of(funcion));

        prepararRelaciones();

        when(funcionRepository.save(any(Funcion.class)))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0));

        FuncionResponseDTO resultado =
                funcionService.actualizar(10L, request);

        assertEquals(10L, resultado.getIdFuncion());
        assertEquals(5.50, resultado.getPrecioBase());

        verify(funcionRepository).save(funcion);
    }

    @Test
    void testActualizarFuncion_NoEncontrada() {
        when(funcionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> funcionService.actualizar(99L, request));

        verify(funcionRepository, never()).save(any());
    }

    @Test
    void testEliminarFuncion_Exito() {
        when(funcionRepository.findById(10L))
                .thenReturn(Optional.of(funcion));

        funcionService.eliminar(10L);

        verify(funcionRepository).deleteById(10L);
    }

    @Test
    void testEliminarFuncion_NoEncontrada() {
        when(funcionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> funcionService.eliminar(99L));

        verify(funcionRepository, never()).deleteById(anyLong());
    }
}
