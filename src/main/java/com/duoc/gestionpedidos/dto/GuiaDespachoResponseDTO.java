package com.duoc.gestionpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuiaDespachoResponseDTO {

    private Long id;
    private ProductoResponseDTO producto;
    private ClienteResponseDTO cliente;
    private EmpleadoResponseDTO empleado;
    private Date fecha;
    private String key;

}
