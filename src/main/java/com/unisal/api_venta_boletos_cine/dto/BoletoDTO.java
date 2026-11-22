package com.unisal.api_venta_boletos_cine.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoletoDTO {
    private Long idBoleto;

    @NotNull(message = "El ID de la venta es obligatorio")
    private Long idVenta;

    @NotNull(message = "El ID de la función es obligatorio")
    private Long idFuncion;

    @NotBlank(message = "El número de asiento es obligatorio")
    private String numeroAsiento;

    @NotNull(message = "El precio pagado es obligatorio")
    @Min(value = 0, message = "El precio pagado no puede ser negativo")
    private Double precioPagado;
}