package com.duoc.gestionpedidos.controller;

import java.util.List;

import com.duoc.gestionpedidos.dto.EmpleadoRequestDTO;
import com.duoc.gestionpedidos.dto.EmpleadoResponseDTO;
import com.duoc.gestionpedidos.service.EmpleadoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    //test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(){
        return ResponseEntity.ok("El servicio de Empleados se encuentra funcionando correctamente");
    }

    // obtener lista de empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarEmpleados(){
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    //obtener empleado por id
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerEmpleadoId(@PathVariable Long id){
        EmpleadoResponseDTO empleado = empleadoService.obtenerEmpleadoId(id);
        if(empleado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleado);
    }

    // registrar nuevo empleado
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crearEmpleado(@Valid @RequestBody EmpleadoRequestDTO empleadoRequestDTO){
        EmpleadoResponseDTO nuevoEmpleado = empleadoService.crearEmpleado(empleadoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    //eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> eliminarEmpleado(@PathVariable Long id){
        boolean eliminado = empleadoService.eliminarEmpleado(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
