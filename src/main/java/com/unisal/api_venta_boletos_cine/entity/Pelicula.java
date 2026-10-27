package com.unisal.api_venta_boletos_cine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    private String titulo;
    private Integer duracion;
    private String clasificacion;
    private String genero;
}