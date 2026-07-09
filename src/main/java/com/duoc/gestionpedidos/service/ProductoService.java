package com.duoc.gestionpedidos.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.duoc.gestionpedidos.repository.ProductoRepository;
import com.duoc.gestionpedidos.dto.ProductoRequestDTO;
import com.duoc.gestionpedidos.dto.ProductoResponseDTO;
import com.duoc.gestionpedidos.model.ProductoEntity;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //listar productos
    public List<ProductoResponseDTO> listarProductos(){
        return productoRepository.findAll().stream().map(this::toDTO).toList();
    }

    //obtener producto por id
    public ProductoResponseDTO obtenerProductoId(Long id){
        return productoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    // crear nuevo producto
    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO){
        ProductoEntity producto = toEntity(productoRequestDTO);
        return toDTO(productoRepository.save(producto));
    }

    // eliminar producto
    public boolean eliminarProducto(Long id){
        if(productoRepository.existsById(id)){
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // Dto - Entity
    private ProductoResponseDTO toDTO(ProductoEntity producto){
        return new ProductoResponseDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getValor(),
            producto.getUnidades()
        );
    }

    private ProductoEntity toEntity(ProductoRequestDTO productoDTO){
        ProductoEntity producto = new ProductoEntity();
        producto.setNombre(productoDTO.getNombre());
        producto.setValor(productoDTO.getValor());
        producto.setUnidades(productoDTO.getUnidades());
        return producto;
    }
    


}
