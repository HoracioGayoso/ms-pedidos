package dan.ms.tp.mspedidos.service;

import dan.ms.tp.mspedidos.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface PedidoService {

    Pedido getPedido(String id) throws Exception;

    List<Pedido> getPedidosByClienteOrDate(String razonSocial, Instant fromDate, Instant toDate);

    //Pedido createPedido(PedidoDtoForCreation p) throws Exception;

    Pedido cancelPedido(String id) throws Exception;
    public Pedido createPedido(Pedido pedido) throws Exception;

}