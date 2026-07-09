package com.duoc.gestionpedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.gestionpedidos.model.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{
    
}
