package com.duoc.gestionpedidos.service;

import java.text.SimpleDateFormat;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.duoc.gestionpedidos.model.GuiaDespachoEntity;

@Service
public class FileService {

    @Value("${s3.bucket}")
    private String S3bucket;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private EfsService efsService;

    // Generar Guia de Despacho como Bytes
    public byte[] generarGuiaFile(GuiaDespachoEntity guiaDespacho) throws Exception{
        
        if (guiaDespacho == null || guiaDespacho.getCliente() == null || guiaDespacho.getProducto() == null ){
            throw new Exception("la guia no puede ser nula");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("--------------------------------\n");
        sb.append( "  Resumen Guia de Despacho \n");
        sb.append("--------------------------------\n");

        sb.append("Nro guia: ").append(guiaDespacho.getId()).append("\n");
        sb.append("Fecha: ").append(guiaDespacho.getFecha()).append("\n");
        sb.append("--------------------------------\n");
        sb.append("Producto: ").append(guiaDespacho.getProducto().getNombre()).append("\n");
        sb.append("Unidades: ").append(guiaDespacho.getProducto().getUnidades()).append("\n");
        sb.append("Total: ").append(guiaDespacho.getProducto().getValor()).append("\n");
        sb.append("--------------------------------\n");
        sb.append("Cliente: ").append(guiaDespacho.getCliente().getNombre()).append("\n");
        sb.append("Transportista: ").append(guiaDespacho.getEmpleado().getNombre()).append("\n");
        sb.append("--------------------------------\n");

        byte[] archivo = sb.toString().getBytes(StandardCharsets.UTF_8);
        return archivo;
    }

    private String generarGuiaS3Key(GuiaDespachoEntity guiaDespacho){
        String transportistaId = guiaDespacho.getEmpleado().getId().toString();
        String guiaId = guiaDespacho.getId().toString();
        String fecha = new SimpleDateFormat("yyyyMM").format(guiaDespacho.getFecha());

        return fecha.concat("/").concat("transportista").concat(transportistaId)
            .concat("/").concat("guia").concat(guiaId).concat(".txt");
    }

    public String subirGuia(GuiaDespachoEntity guiaDespacho) throws Exception{
        
        byte[] archivo = generarGuiaFile(guiaDespacho);
        String key = generarGuiaS3Key(guiaDespacho);

        s3Service.upload(S3bucket, key, archivo);
        efsService.saveToEfs(key, archivo);

        return key;
    }



}
