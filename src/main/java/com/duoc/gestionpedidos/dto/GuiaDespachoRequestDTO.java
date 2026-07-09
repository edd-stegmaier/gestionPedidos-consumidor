package com.duoc.gestionpedidos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespachoRequestDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El productoID debe ser un número positivo")
    private Long productoId;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El clienteID debe ser un número positivo")
    private Long clienteId;

    @NotNull(message = "El ID del empleado transportista es obligatorio")
    @Positive(message = "El empleadoID debe ser un número positivo")
    private Long empleadoId;

    @NotNull(message = "La fecha de despacho es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private Date fecha;

}
