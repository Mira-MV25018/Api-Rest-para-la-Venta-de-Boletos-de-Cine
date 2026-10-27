package com.unisal.api_venta_boletos_cine.repository;

import com.unisal.api_venta_boletos_cine.entity.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long> {

    boolean existsByPelicula_IdPelicula(Long idPelicula);

    boolean existsBySala_IdSala(Long idSala);

    List<Funcion> findByPelicula_IdPelicula(Long idPelicula);
}