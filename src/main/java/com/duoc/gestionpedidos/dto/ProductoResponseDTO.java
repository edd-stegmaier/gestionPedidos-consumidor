package com.duoc.gestionpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Integer valor;
    private Integer unidades;

}
