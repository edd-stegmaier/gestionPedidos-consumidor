package com.duoc.gestionpedidos.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import com.duoc.gestionpedidos.repository.GuiaDespachoRepository;
import com.duoc.gestionpedidos.repository.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

import com.duoc.gestionpedidos.repository.ClienteRepository;
import com.duoc.gestionpedidos.repository.EmpleadoRepository;
import com.duoc.gestionpedidos.dto.GuiaDespachoRequestDTO;
import com.duoc.gestionpedidos.dto.GuiaDespachoResponseDTO;
import com.duoc.gestionpedidos.dto.ProductoResponseDTO;
import com.duoc.gestionpedidos.dto.ClienteResponseDTO;
import com.duoc.gestionpedidos.dto.EmpleadoResponseDTO;
import com.duoc.gestionpedidos.model.GuiaDespachoEntity;
import com.duoc.gestionpedidos.model.ProductoEntity;
import com.duoc.gestionpedidos.model.ClienteEntity;
import com.duoc.gestionpedidos.model.EmpleadoEntity;

@Slf4j
@Service
public class GuiaDespachoService {

    private final GuiaDespachoRepository guiaDespachoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final FileService fileService;

    public GuiaDespachoService(GuiaDespachoRepository guiaDespachoRepository, 
                              ProductoRepository productoRepository,
                              ClienteRepository clienteRepository,
                              EmpleadoRepository empleadoRepository,
                              FileService fileService) {
        this.guiaDespachoRepository = guiaDespachoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
        this.fileService = fileService;
    }

    //listar guias de despacho
    public List<GuiaDespachoResponseDTO> listarGuiasDeDespacho(){
        return guiaDespachoRepository.findAll().stream().map(this::toDTO).toList();
    }

    //obtener guia de despacho por id
    public GuiaDespachoResponseDTO obtenerGuiaDeDespachoId(Long id){
        return guiaDespachoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    //buscar por transportista
    public List<GuiaDespachoResponseDTO> obtenerGuiaDeDespachoTransportista(Long id){
        return guiaDespachoRepository.findByEmpleado_Id(id).stream().map(this::toDTO).toList();
    }

    //buscar por fecha
        public List<GuiaDespachoResponseDTO> obtenerGuiaDeDespachoFecha(LocalDate fecha){
        return guiaDespachoRepository.findByFecha(fecha).stream().map(this::toDTO).toList();
    }

    // crear nueva guia de despacho
    public GuiaDespachoResponseDTO crearGuiaDeDespacho(GuiaDespachoRequestDTO guiaDespachoRequestDTO){
        GuiaDespachoEntity guiaDespacho = toEntity(guiaDespachoRequestDTO);
        GuiaDespachoResponseDTO nuevaGuia = toDTO(guiaDespachoRepository.save(guiaDespacho));
        
        String key = subirGuiaDespacho(guiaDespacho);
        if(key != null){
            nuevaGuia.setKey(key);
        }

        return nuevaGuia;
    }

    // editar guia de despacho 
    public GuiaDespachoResponseDTO actualizarGuiaDeDespacho(Long id, GuiaDespachoRequestDTO guiaDespachoDTO){
        return guiaDespachoRepository.findById(id).map( guia -> {
            ProductoEntity producto = productoRepository.findById(guiaDespachoDTO.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + guiaDespachoDTO.getProductoId()));
            ClienteEntity cliente = clienteRepository.findById(guiaDespachoDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + guiaDespachoDTO.getClienteId()));
            EmpleadoEntity empleado = empleadoRepository.findById(guiaDespachoDTO.getEmpleadoId())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con id: " + guiaDespachoDTO.getEmpleadoId()));
            
            guia.setProducto(producto);
            guia.setCliente(cliente);
            guia.setEmpleado(empleado);
            guia.setFecha(guiaDespachoDTO.getFecha());

            GuiaDespachoResponseDTO guiaActualizada = toDTO(guiaDespachoRepository.save(guia));
            String key = subirGuiaDespacho(guia);
            if(key != null){
                guiaActualizada.setKey(key);
            }

            return guiaActualizada;
        }).orElse(null);
    }

    // eliminar guia de despacho
    public boolean eliminarGuiaDeDespacho(Long id){
        if(guiaDespachoRepository.existsById(id)){
            guiaDespachoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public byte[] descargarGuiaDespacho(Long id){
        GuiaDespachoEntity guiaDespacho =  guiaDespachoRepository.findById(id).orElse(null);

        try{
            byte[] archivo = fileService.generarGuiaFile(guiaDespacho);
            return archivo;
        }catch(Exception e){
            log.info("No se pudo generar guia de Despacho id: " + guiaDespacho.getId().toString() + " : " + e.getMessage());
        }
        return null;

    }

    private String subirGuiaDespacho(GuiaDespachoEntity guiaDespacho){
        try{
            String key = fileService.subirGuia(guiaDespacho);
            return key;
        }catch(Exception e){
            log.info("No se pudo subir guia de Despacho id: "+ guiaDespacho.getId().toString() + " : " + e.getMessage());
        }
        return null;
    }


    // Dto - Entity
    private GuiaDespachoResponseDTO toDTO(GuiaDespachoEntity guiaDespacho){
        ProductoResponseDTO productoDTO = new ProductoResponseDTO(
            guiaDespacho.getProducto().getId(),
            guiaDespacho.getProducto().getNombre(),
            guiaDespacho.getProducto().getValor(),
            guiaDespacho.getProducto().getUnidades()
        );

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
            guiaDespacho.getCliente().getId(),
            guiaDespacho.getCliente().getNombre()
        );

        EmpleadoResponseDTO empleadoDTO = new EmpleadoResponseDTO(
            guiaDespacho.getEmpleado().getId(),
            guiaDespacho.getEmpleado().getNombre(),
            guiaDespacho.getEmpleado().getCargo()
        );

        return new GuiaDespachoResponseDTO(
            guiaDespacho.getId(),
            productoDTO,
            clienteDTO,
            empleadoDTO,
            guiaDespacho.getFecha(),
            null
        );
    }

    private GuiaDespachoEntity toEntity(GuiaDespachoRequestDTO guiaDespachoDTO){
        GuiaDespachoEntity guiaDespacho = new GuiaDespachoEntity();
        
        ProductoEntity producto = productoRepository.findById(guiaDespachoDTO.getProductoId()).orElse(null);
        ClienteEntity cliente = clienteRepository.findById(guiaDespachoDTO.getClienteId()).orElse(null);
        EmpleadoEntity empleado = empleadoRepository.findById(guiaDespachoDTO.getEmpleadoId()).orElse(null);
        
        guiaDespacho.setProducto(producto);
        guiaDespacho.setCliente(cliente);
        guiaDespacho.setEmpleado(empleado);
        guiaDespacho.setFecha(guiaDespachoDTO.getFecha());
        return guiaDespacho;
    }
    


}
