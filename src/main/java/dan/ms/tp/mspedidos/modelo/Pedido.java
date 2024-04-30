package dan.ms.tp.mspedidos.modelo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "dan_pedidos")
public class Pedido {

    @Id
    private String id;
    private Instant fecha;
    private Integer numeroPedido;
    private String user;
    private String observaciones;
    private Cliente cliente;
    private List<PedidoDetalle> detallePedido;
    private List<HistorialEstado> estados;
    private Double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoDetalle> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<PedidoDetalle> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public List<HistorialEstado> getEstados() {
        return estados;
    }

    public void setEstados(List<HistorialEstado> estados) {
        this.estados = estados;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
