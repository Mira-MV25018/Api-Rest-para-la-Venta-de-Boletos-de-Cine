
package com.unisal.api_venta_boletos_cine.service;

import java.util.List;
import org.mockito.ArgumentCaptor;
import com.unisal.api_venta_boletos_cine.dto.PeliculaDTO;
import com.unisal.api_venta_boletos_cine.entity.Pelicula;
import com.unisal.api_venta_boletos_cine.repository.PeliculaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    private PeliculaRepository peliculaRepository;

    @InjectMocks
    private PeliculaService peliculaService;

    @Test
    void testObtenerPeliculaPorId_Exito() {

        // Preparar los datos
        Pelicula pelicula = new Pelicula();
        pelicula.setIdPelicula(1L);
        pelicula.setTitulo("Spider-Man");

        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.of(pelicula));

        // Ejecutar el servicio
        PeliculaDTO resultado = peliculaService.obtenerPorId(1L);

        // Comprobar los resultados
        assertNotNull(resultado);
        assertEquals("Spider-Man", resultado.getTitulo());
        assertEquals(1L, resultado.getIdPelicula());

        verify(peliculaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPeliculaPorId_NoEncontrada() {

        when(peliculaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(
                RuntimeException.class,
                () -> peliculaService.obtenerPorId(99L)
        );

        assertEquals(
                "Película no encontrada con ID: 99",
                excepcion.getMessage()
        );
    }

    @Test
    void testObtenerTodas() {
        Pelicula pelicula = new Pelicula();
        pelicula.setIdPelicula(1L);
        pelicula.setTitulo("Avatar");

        when(peliculaRepository.findAll())
                .thenReturn(List.of(pelicula));

        List<PeliculaDTO> resultado = peliculaService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("Avatar", resultado.get(0).getTitulo());
        verify(peliculaRepository).findAll();
    }

    @Test
    void testGuardarPelicula() {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setTitulo("Titanic");
        dto.setDuracion(195);

        Pelicula guardada = new Pelicula();
        guardada.setIdPelicula(2L);
        guardada.setTitulo("Titanic");
        guardada.setDuracion(195);

        when(peliculaRepository.save(any(Pelicula.class)))
                .thenReturn(guardada);

        PeliculaDTO resultado = peliculaService.guardar(dto);

        assertEquals(2L, resultado.getIdPelicula());
        assertEquals("Titanic", resultado.getTitulo());

        ArgumentCaptor<Pelicula> captor =
                ArgumentCaptor.forClass(Pelicula.class);
        verify(peliculaRepository).save(captor.capture());
        assertEquals("Titanic", captor.getValue().getTitulo());
    }

    @Test
    void testActualizarPelicula() {
        Pelicula existente = new Pelicula();
        existente.setIdPelicula(1L);
        existente.setTitulo("Titulo anterior");

        PeliculaDTO nuevosDatos = new PeliculaDTO();
        nuevosDatos.setTitulo("Titulo actualizado");
        nuevosDatos.setDuracion(120);
        nuevosDatos.setClasificacion("PG-13");
        nuevosDatos.setGenero("Accion");

        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.of(existente));
        when(peliculaRepository.save(any(Pelicula.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        PeliculaDTO resultado =
                peliculaService.actualizar(1L, nuevosDatos);

        assertEquals("Titulo actualizado", resultado.getTitulo());
        assertEquals(120, resultado.getDuracion());
        assertEquals("PG-13", resultado.getClasificacion());
        assertEquals("Accion", resultado.getGenero());
        verify(peliculaRepository).save(existente);
    }

    @Test
    void testActualizarPeliculaNoEncontrada() {
        when(peliculaRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> peliculaService.actualizar(
                        99L, new PeliculaDTO()));

        verify(peliculaRepository, never()).save(any());
    }

    @Test
    void testEliminarPelicula() {
        when(peliculaRepository.existsById(1L))
                .thenReturn(true);

        peliculaService.eliminar(1L);

        verify(peliculaRepository).deleteById(1L);
    }

    @Test
    void testEliminarPeliculaNoEncontrada() {
        when(peliculaRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> peliculaService.eliminar(99L));

        verify(peliculaRepository, never()).deleteById(anyLong());
    }
}
