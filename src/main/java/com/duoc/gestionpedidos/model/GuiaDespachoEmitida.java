package com.duoc.gestionpedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guiasdespacho")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuiaDespachoEmitida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] contenido;


}