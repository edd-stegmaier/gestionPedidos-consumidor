package com.duoc.gestionpedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre del producto no puede exceder los 255 caracteres")
    private String nombre;

    @NotNull(message = "El valor del producto es obligatorio")
    @Min(value = 0, message = "El valor del producto no puede ser negativo")
    private Integer valor;

    @NotNull(message = "Las unidades del producto es obligatorio")
    @Min(value = 0, message = "Las unidades del producto no pueden ser negativas")
    private Integer unidades;

}
