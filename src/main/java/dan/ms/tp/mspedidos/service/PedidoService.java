package dan.ms.tp.mspedidos.service;

import dan.ms.tp.mspedidos.modelo.Pedido;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface PedidoService {

    Pedido getPedido(String id) throws Exception;

    List<Pedido> getPedidosByClienteOrDate(String razonSocial, Instant fromDate, Instant toDate);

    //Pedido createPedido(PedidoDtoForCreation p) throws Exception;

    Pedido cancelPedido(String id) throws Exception;
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay= 10000))
    public Pedido createPedido(Pedido pedido,String Token) throws Exception;

}