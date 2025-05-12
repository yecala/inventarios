package gm.inventarios.controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gm.inventarios.dto.ApiResponse;
import gm.inventarios.modelos.Producto;
import gm.inventarios.servicio.IProductoServicio;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("inventario-app") //http://localhost:8080/inventario-app
@CrossOrigin(value = "http://localhost:4200") //Puesto default de angular
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    private IProductoServicio servicio = null;

    public ProductoControlador(IProductoServicio servicio){
        this.servicio = servicio;
    }
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerProductos() {
        try {
            List<Producto> productos = this.servicio.listarProductos();
            logger.info("Productos obtenidos con éxito. Total: {}", productos.size());
            ApiResponse<List<Producto>> response = new ApiResponse<>();
            response.setData(productos);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener productos", e);
            ApiResponse<List<Producto>> response = new ApiResponse<>();
            response.setError("No se pudieron obtener los productos.");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
}
