package dan.ms.tp.mspedidos.service;

import dan.ms.tp.mspedidos.dao.PedidoRepository;
import dan.ms.tp.mspedidos.modelo.EstadoPedido;
import dan.ms.tp.mspedidos.modelo.HistorialEstado;
import dan.ms.tp.mspedidos.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
