package com.unisal.api_venta_boletos_cine.repository;

import com.unisal.api_venta_boletos_cine.entity.Boleto;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    boolean existsByFuncionAndNumeroAsiento(Funcion funcion, String numeroAsiento);
}