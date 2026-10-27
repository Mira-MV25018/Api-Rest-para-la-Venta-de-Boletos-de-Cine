package com.unisal.api_venta_boletos_cine.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "boleto",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_funcion", "numero_asiento"})})
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleto")
    private Long idBoleto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venta")
    @ToString.Exclude
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_funcion")
    private Funcion funcion;

    @Column(name = "numero_asiento")
    private String numeroAsiento;

    @Column(name = "precio_pagado")
    private Double precioPagado;
}