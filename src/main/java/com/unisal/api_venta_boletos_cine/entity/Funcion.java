package com.unisal.api_venta_boletos_cine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "funcion")
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion")
    private Long idFuncion;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    @ToString.Exclude
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    @ToString.Exclude
    private Sala sala;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "precio_base")
    private Double precioBase;
}