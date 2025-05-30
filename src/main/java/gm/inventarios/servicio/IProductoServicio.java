package gm.inventarios.servicio;

import java.util.List;

import gm.inventarios.modelos.Producto;

public interface IProductoServicio {
    
    List<Producto> listarProductos();

    Producto buscarProductoPorId(Integer idProducto);

    Producto guardarProducto(Producto producto);

    void eliminarProductoPorId(Integer idProducto);
}
