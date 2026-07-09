package com.duoc.gestionpedidos.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 255, message = "El nombre del cliente no puede exceder los 255 caracteres")
    private String nombre;

}
