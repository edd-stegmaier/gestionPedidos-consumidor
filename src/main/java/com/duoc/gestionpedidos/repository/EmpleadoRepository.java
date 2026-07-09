package com.duoc.gestionpedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.gestionpedidos.model.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long>{
    
}
