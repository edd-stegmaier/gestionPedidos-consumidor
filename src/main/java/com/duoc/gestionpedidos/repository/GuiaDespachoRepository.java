package com.duoc.gestionpedidos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.gestionpedidos.model.GuiaDespachoEntity;

@Repository
public interface GuiaDespachoRepository extends JpaRepository<GuiaDespachoEntity, Long>{

    List<GuiaDespachoEntity> findByEmpleado_Id(Long empleadoId);
    List<GuiaDespachoEntity> findByFecha(LocalDate fecha);
}
