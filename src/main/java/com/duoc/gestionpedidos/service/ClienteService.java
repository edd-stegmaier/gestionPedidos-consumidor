package com.duoc.gestionpedidos.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.duoc.gestionpedidos.repository.ClienteRepository;
import com.duoc.gestionpedidos.dto.ClienteRequestDTO;
import com.duoc.gestionpedidos.dto.ClienteResponseDTO;
import com.duoc.gestionpedidos.model.ClienteEntity;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //listar clientes
    public List<ClienteResponseDTO> listarClientes(){
        return clienteRepository.findAll().stream().map(this::toDTO).toList();
    }

    //obtener cliente por id
    public ClienteResponseDTO obtenerClienteId(Long id){
        return clienteRepository.findById(id).map(this::toDTO).orElse(null);
    }

    // crear nuevo cliente
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequestDTO){
        ClienteEntity cliente = toEntity(clienteRequestDTO);
        return toDTO(clienteRepository.save(cliente));
    }

    // eliminar cliente
    public boolean eliminarCliente(Long id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // Dto - Entity
    private ClienteResponseDTO toDTO(ClienteEntity cliente){
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNombre()
        );
    }

    private ClienteEntity toEntity(ClienteRequestDTO clienteDTO){
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(clienteDTO.getNombre());
        return cliente;
    }
    


}
