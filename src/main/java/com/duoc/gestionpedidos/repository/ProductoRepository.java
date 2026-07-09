package com.duoc.gestionpedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.gestionpedidos.model.ProductoEntity;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long>{
    
}
