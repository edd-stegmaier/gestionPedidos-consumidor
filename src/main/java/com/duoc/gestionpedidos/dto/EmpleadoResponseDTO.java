package com.duoc.gestionpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {

    private Long id;
    private String nombre;
    private String cargo;

}
