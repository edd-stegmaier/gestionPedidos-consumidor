package com.duoc.gestionpedidos.controller;

import java.util.List;

import com.duoc.gestionpedidos.dto.GuiaDespachoRequestDTO;
import com.duoc.gestionpedidos.dto.GuiaDespachoResponseDTO;
import com.duoc.gestionpedidos.service.GuiaDespachoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guias-despacho")
public class GuiaDespachoController {
    
    private final GuiaDespachoService guiaDespachoService;

    public GuiaDespachoController(GuiaDespachoService guiaDespachoService){
        this.guiaDespachoService = guiaDespachoService;
    }

    //test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(){
        return ResponseEntity.ok("El servicio de Guias de Despacho se encuentra funcionando correctamente");
    }

    // obtener lista de guias
    @GetMapping
    public ResponseEntity<List<GuiaDespachoResponseDTO>> listarGuiasDespacho(){
        return ResponseEntity.ok(guiaDespachoService.listarGuiasDeDespacho());
    }

    //obtener guia por id
    @GetMapping("/{id}")
    public ResponseEntity<GuiaDespachoResponseDTO> obtenerGuiaDespachoId(@PathVariable Long id){
        GuiaDespachoResponseDTO guiaDespacho = guiaDespachoService.obtenerGuiaDeDespachoId(id);
        if(guiaDespacho == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guiaDespacho);
    }

    // registrar nueva guia
    @PostMapping
    public ResponseEntity<GuiaDespachoResponseDTO> crearGuiaDespacho(@Valid @RequestBody GuiaDespachoRequestDTO guiaDespachoRequestDTO){
        GuiaDespachoResponseDTO nuevaGuia = guiaDespachoService.crearGuiaDeDespacho(guiaDespachoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaGuia);
    }

    // actualizar guia 
    @PutMapping("/{id}")
    public ResponseEntity<GuiaDespachoResponseDTO> actualizarGuiaDespacho(@PathVariable Long id, @RequestBody GuiaDespachoRequestDTO guiaDespachoRequestDTO){
        GuiaDespachoResponseDTO guiaActualizada = guiaDespachoService.actualizarGuiaDeDespacho(id, guiaDespachoRequestDTO);
        if (guiaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guiaActualizada);
    }

    //eliminar guia
    @DeleteMapping("/{id}")
    public ResponseEntity<GuiaDespachoResponseDTO> eliminarGuiaDespacho(@PathVariable Long id){
        boolean eliminado = guiaDespachoService.eliminarGuiaDeDespacho(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // buscar guias por transportista
    @GetMapping("/transportista/{id}")
    public ResponseEntity<List<GuiaDespachoResponseDTO>> obtenerGuiasPorTransportista(@PathVariable Long id){
        return ResponseEntity.ok(guiaDespachoService.obtenerGuiaDeDespachoTransportista(id));
    }

    // buscar guias por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<GuiaDespachoResponseDTO>> obtenerGuiasPorFecha(@PathVariable String fecha){
        try {
            java.time.LocalDate localDate = java.time.LocalDate.parse(fecha);
            return ResponseEntity.ok(guiaDespachoService.obtenerGuiaDeDespachoFecha(localDate));
        } catch (java.time.format.DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // descargar guia
    @GetMapping("/descargar/{id}")
    public ResponseEntity<byte[]> descargarGuiaDespacho(@PathVariable Long id){
        byte[] archivo = guiaDespachoService.descargarGuiaDespacho(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "guia" + id + ".txt" + "\"")
        .contentType(MediaType.APPLICATION_OCTET_STREAM).body(archivo);

    }


}
