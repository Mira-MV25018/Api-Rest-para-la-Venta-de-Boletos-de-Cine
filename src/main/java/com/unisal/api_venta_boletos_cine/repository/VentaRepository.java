package com.unisal.api_venta_boletos_cine.repository;

import com.unisal.api_venta_boletos_cine.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}