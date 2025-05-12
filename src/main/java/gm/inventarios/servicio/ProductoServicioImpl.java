package gm.inventarios.servicio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import gm.inventarios.modelos.Producto;
import gm.inventarios.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements IProductoServicio{

    private static final Logger logger = LoggerFactory.getLogger(ProductoServicioImpl.class);

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        try {
        return productoRepositorio.findAll();
    } catch (Exception e) {
        logger.error("Error en listarProductos()", e);
        throw new RuntimeException("Error al listar productos");
    }
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto) {
        return this.productoRepositorio.findById(idProducto).orElse(null);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return this.productoRepositorio.save(producto); //Crea y actualiza dependiendo del valor del id
    }

    @Override
    public void eliminarProductoPorId(Integer idProducto) {
        this.productoRepositorio.deleteById(idProducto);
    }
    
}
