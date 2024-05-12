package dan.ms.tp.mspedidos.service;

import dan.ms.tp.mspedidos.dao.PedidoRepository;
import dan.ms.tp.mspedidos.modelo.Cliente;
import dan.ms.tp.mspedidos.modelo.EstadoPedido;
import dan.ms.tp.mspedidos.modelo.HistorialEstado;
import dan.ms.tp.mspedidos.modelo.Pedido;
import dan.ms.tp.mspedidos.modelo.PedidoDetalle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    PedidoRepository repo;

    @Override
    public Pedido getPedido(String id) throws Exception {
        return null;
    }

    @Override
    public List<Pedido> getPedidosByClienteOrDate(String razonSocial, Instant fromDate, Instant toDate) {
        if(fromDate == null) fromDate = Instant.ofEpochMilli(Long.MIN_VALUE);
        if(toDate == null) toDate = Instant.ofEpochMilli(Long.MAX_VALUE);

        return repo.findByClienteFecha(razonSocial, fromDate, toDate);
    }

    @Override
    public Pedido cancelPedido(String id) throws Exception {
        Optional<Pedido> optionalPedido = repo.findById(id);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            EstadoPedido ultimoEstado = pedido.getEstados().get(pedido.getEstados().size() - 1).getEstado();

            if (ultimoEstado == EstadoPedido.RECHAZADO || ultimoEstado == EstadoPedido.CANCELADO
                    || ultimoEstado == EstadoPedido.EN_DISTRIBUCION || ultimoEstado == EstadoPedido.ENTREGADO) {
                throw new Exception("No se puede cancelar el pedido porque ya se encuentra en un estado final.");
            }
            HistorialEstado estado = new HistorialEstado(EstadoPedido.CANCELADO, Instant.now(), "", "Pedido cancelado");

            pedido.getEstados().add(estado); // ver como se settea un nuevo estado
            // Guardar los cambios en la base de datos
            pedido = repo.save(pedido);
            return pedido;
        } else {
            return null;
        }
    }
    @Override
    public Pedido createPedido(Pedido pedido, String token) throws Exception {
        //TODO: Desglosar todo en funciones
        //Chequeos preliminares
        Cliente cliente;
        try{
        cliente = getClienteByPedido(pedido,token);}
        catch(Exception e){
            System.out.println("Fallo cliente");
            throw new Exception("Can't get cliente");
        }
        if(pedido.getNumeroPedido() == null){
            throw new Exception("Pedido no tiene numero de pedido");
        }
        else if(pedido.getCliente()==null){
            throw new Exception("Pedido no tiene cliente");
        }
        else if (pedido.getDetallePedido()==null){
            throw new Exception("Pedido no tiene detalle pedido");
        }

        HistorialEstado historialEstado = new HistorialEstado();
        historialEstado.setUserEstado(pedido.getUser());
        historialEstado.setFechaEstado(Instant.now());
        historialEstado.setEstado(EstadoPedido.RECHAZADO);
        Double montoTotal=0d;

        for (PedidoDetalle detalle : pedido.getDetallePedido()) {
                // Agregar chequeos necesarios
                if(detalle.getProducto().getStock()==0){
                    throw new Exception("No hay stock del producto "  + detalle.getProducto().getNombre());
                }
                
                //montoTotal += detalle.getProducto().getPrecio()*detalle.getCantidad()*(100-detalle.getDescuento());
                montoTotal +=50;
                historialEstado.setEstado(EstadoPedido.SIN_STOCK);
        }
        
        /*
        cliente.getMontoMaximo() 
        if montoTotal > cliente.getMontoMaximo 
        historialEstado.setEstado(EstadoPedido.RECHAZADO);
        */

        historialEstado.setEstado(EstadoPedido.RECIBIDO);
        pedido.setFecha(Instant.now());
        pedido.setTotal(montoTotal);
        pedido.setCliente(cliente);
        //refactor esto de estado
        pedido.addEstado(historialEstado);
        repo.save(pedido);
        return pedido;
    }

    private Cliente getClienteByPedido(Pedido pedido, String token) {
        // TODO Auto-generated method stub
        Cliente respuesta;
        RestTemplate clienteTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<Cliente> respuestaCompleta = clienteTemplate.exchange(
          "http://localhost:8080/usuarios/api/cliente/idCliente?id="+pedido.getCliente().getId(),
          HttpMethod.GET, entity,
          Cliente.class);
        System.out.println("La respuesta" + respuestaCompleta.getStatusCode());
        respuesta = respuestaCompleta.getBody();
        return respuesta;
    }



}
