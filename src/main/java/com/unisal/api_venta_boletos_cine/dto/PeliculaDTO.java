package com.unisal.api_venta_boletos_cine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PeliculaDTO {
    private Long idPelicula;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La duración es obligatoria")
    private Integer duracion;

    @NotBlank(message = "La clasificación es obligatoria")
    private String clasificacion;

    @NotBlank(message = "El género es obligatorio")
    private String genero;
}