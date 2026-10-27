package com.unisal.api_venta_boletos_cine.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class FuncionRequestDTO {
    private Integer id;

    @NotNull(message = "El ID de la película es obligatorio")
    private Integer idPelicula;

    @NotNull(message = "El ID de la sala es obligatorio")
    private Integer idSala;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;
}