package gm.inventarios.controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gm.inventarios.dto.ApiResponse;
import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelos.Producto;
import gm.inventarios.servicio.IProductoServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




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

    @PostMapping("/productos")
    public ResponseEntity<ApiResponse<Producto>> agregarProducto(@RequestBody Producto producto) {
        try {
            logger.info("Producto a agregar: ", producto);
            Producto productoNuevo = this.servicio.guardarProducto(producto);
            ApiResponse<Producto> response = new ApiResponse<>();
            response.setData(productoNuevo);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al guardar producto", e);
            ApiResponse<Producto> response = new ApiResponse<>();
            response.setError("No se pudo guardar el producto.");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerProductosPorId(@PathVariable int id) {
        try {
            Producto producto = this.servicio.buscarProductoPorId(id);
            if(producto!=null){
                ApiResponse<Producto> response = new ApiResponse<>();
                response.setData(producto);
                response.setSuccess(true);
                return ResponseEntity.ok(response);
            }else{
               throw new RecursoNoEncontradoExcepcion("No se encontro el producto con el ID " + id);
            }

        } catch (Exception e) {
            logger.error("Error al obtener producto", e);
            ApiResponse<Producto> response = new ApiResponse<>();
            response.setError("No se pudo obtener el producto.");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

    @PutMapping("/productos/{id}")
    public ResponseEntity<ApiResponse<Producto>>  actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        try {
            Producto productoEditar = this.servicio.buscarProductoPorId(id);
            if(productoEditar!=null){
                productoEditar.setDescripcion(producto.getDescripcion());
                productoEditar.setPrecio(producto.getPrecio());
                productoEditar.setExistencia(producto.getExistencia());
                this.servicio.guardarProducto(productoEditar);
        
                ApiResponse<Producto> response = new ApiResponse<>();
                response.setData(productoEditar);
                response.setSuccess(true);
                return ResponseEntity.ok(response);
            }else{
                throw new RecursoNoEncontradoExcepcion("No se encontro el producto con el ID " + id);
            }
        } catch (Exception e) {
            logger.error("Error al editar producto", e);
            ApiResponse<Producto> response = new ApiResponse<>();
            response.setError("No se pudo editar el producto.");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<ApiResponse<Boolean>> eliminarProducto(@PathVariable int id){
        try {
            Producto productoEliminar = this.servicio.buscarProductoPorId(id);
            if(productoEliminar!=null){
                this.servicio.eliminarProductoPorId(id);
                ApiResponse<Boolean> response = new ApiResponse<>();
                response.setData(true);
                response.setSuccess(true);
                return ResponseEntity.ok(response);
            }else{
                throw new RecursoNoEncontradoExcepcion("No se encontro el producto con el ID " + id);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar producto", e);
            ApiResponse<Boolean> response = new ApiResponse<>();
            response.setData(false);
            response.setError("No se pudo eliminar el producto.");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }
}
