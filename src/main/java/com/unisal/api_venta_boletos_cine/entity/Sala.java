package com.unisal.api_venta_boletos_cine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long idSala;

    private String nombre;

    @Column(name = "capacidad_total")
    private Integer capacidadTotal;
}