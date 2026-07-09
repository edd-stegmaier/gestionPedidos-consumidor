package com.duoc.gestionpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nombre;

}
