package com.grupo1.almacen.util;

import com.grupo1.almacen.entity.Proveedor;
import com.grupo1.almacen.repository.ProveedorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProveedorReporteService {
    @Autowired
    private ProveedorRepository  proveedorRepository;

    public String exportarReporte() throws FileNotFoundException, JRException {
        List<Proveedor> proveedores=proveedorRepository.findAll();

        //ESPERO SOLO AGARRE LOS ATRIBUTOS QUE COLOQUE EN EL REPORTE SINO TENDRÉ QUE MANDARLE SETEADO
        File file= ResourceUtils.getFile("classpath:proveedor.jrxml");

        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(proveedores);
        Map<String,Object> parameters= new HashMap<>();
        parameters.put("logoPolleria","classpath:/static/images/logoPolleria.jpa");
        parameters.put("imagenAlternativa","classpath:/static/images/estrellas.jpa");
        parameters.put("ds",dataSource);

        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());

        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path="C:\\Reporte";
        String fileName = "proveedores_" + timestamp + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\"+ fileName);

        return "reporte generado en la ruta:"+path;
    }
}
