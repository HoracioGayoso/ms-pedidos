package dan.ms.tp.mspedidos.modelo;

import java.time.Instant;

import lombok.Data;

@Data
public class HistorialEstado {
    private EstadoPedido estado;
    private Instant fechaEstado;
    private String userEstado;
    private String detalle;

    public HistorialEstado(EstadoPedido estado, Instant fechaEstado, String userEstado, String detalle) {
        this.estado = estado;
        this.fechaEstado = fechaEstado;
        this.userEstado = userEstado;
        this.detalle = detalle;
    }

    public HistorialEstado() {
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Instant getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Instant fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public String getUserEstado() {
        return userEstado;
    }

    public void setUserEstado(String userEstado) {
        this.userEstado = userEstado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
