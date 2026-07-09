package com.duoc.gestionpedidos.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.duoc.gestionpedidos.repository.EmpleadoRepository;
import com.duoc.gestionpedidos.dto.EmpleadoRequestDTO;
import com.duoc.gestionpedidos.dto.EmpleadoResponseDTO;
import com.duoc.gestionpedidos.model.EmpleadoEntity;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    //listar empleados
    public List<EmpleadoResponseDTO> listarEmpleados(){
        return empleadoRepository.findAll().stream().map(this::toDTO).toList();
    }

    //obtener empleado por id
    public EmpleadoResponseDTO obtenerEmpleadoId(Long id){
        return empleadoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    // crear nuevo empleado
    public EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO empleadoRequestDTO){
        EmpleadoEntity empleado = toEntity(empleadoRequestDTO);
        return toDTO(empleadoRepository.save(empleado));
    }

    // eliminar empleado
    public boolean eliminarEmpleado(Long id){
        if(empleadoRepository.existsById(id)){
            empleadoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // Dto - Entity
    private EmpleadoResponseDTO toDTO(EmpleadoEntity empleado){
        return new EmpleadoResponseDTO(
            empleado.getId(),
            empleado.getNombre(),
            empleado.getCargo()
        );
    }

    private EmpleadoEntity toEntity(EmpleadoRequestDTO empleadoDTO){
        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setCargo(empleadoDTO.getCargo());
        return empleado;
    }
    


}
