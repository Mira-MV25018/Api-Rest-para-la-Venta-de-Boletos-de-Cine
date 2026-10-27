package com.unisal.api_venta_boletos_cine.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FuncionRequestDTO {

    @NotNull(message = "El ID de la película es obligatorio")
    private Long idPelicula;

    @NotNull(message = "El ID de la sala es obligatorio")
    private Long idSala;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La fecha debe ser futura")
    private LocalDateTime fechaHora;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precioBase;
}