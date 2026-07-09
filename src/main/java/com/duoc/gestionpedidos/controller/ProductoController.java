package com.duoc.gestionpedidos.controller;

import java.util.List;

import com.duoc.gestionpedidos.dto.ProductoRequestDTO;
import com.duoc.gestionpedidos.dto.ProductoResponseDTO;
import com.duoc.gestionpedidos.service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    //test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(){
        return ResponseEntity.ok("El servicio de Productos se encuentra funcionando correctamente");
    }

    // obtener lista de productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos(){
        return ResponseEntity.ok(productoService.listarProductos());
    }

    //obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoId(@PathVariable Long id){
        ProductoResponseDTO producto = productoService.obtenerProductoId(id);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    // registrar nuevo producto
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO){
        ProductoResponseDTO nuevoProducto = productoService.crearProducto(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    //eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> eliminarProducto(@PathVariable Long id){
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
