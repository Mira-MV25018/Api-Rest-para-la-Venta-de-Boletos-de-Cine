package com.unisal.api_venta_boletos_cine.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VentaDTO {
    private Long idVenta;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    private LocalDateTime fechaVenta;

    @NotNull(message = "El total pagado es obligatorio")
    @Min(value = 0, message = "El total pagado no puede ser negativo")
    private Double totalPagado;
}