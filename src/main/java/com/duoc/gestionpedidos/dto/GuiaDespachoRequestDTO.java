package com.duoc.gestionpedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespachoRequestDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del empleado transportista es obligatorio")
    private Long empleadoId;

    @NotNull(message = "La fecha de despacho es obligatoria")
    private Date fecha;

}
