package com.duoc.gestionpedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {
    
    @NotBlank(message = "El nombre del empleado es obligatorio")
    @Size(max = 255, message = "El nombre del empleado no puede exceder los 255 caracteres")
    private String nombre;

    @NotBlank(message = "El cargo del empleado es obligatorio")
    @Size(max = 255, message = "El cargo del empleado no puede exceder los 255 caracteres")
    private String cargo;

}
