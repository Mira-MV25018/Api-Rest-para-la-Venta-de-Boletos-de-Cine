package com.unisal.api_venta_boletos_cine.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FuncionResponseDTO {
    private Long idFuncion;
    private Long idPelicula;
    private String tituloPelicula;
    private Long idSala;
    private String nombreSala;
    private LocalDateTime fechaHora;
    private Double precioBase;
}