package dan.ms.tp.mspedidos.controller;

import java.util.List;
import java.util.Optional;

import dan.ms.tp.mspedidos.modelo.EstadoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dan.ms.tp.mspedidos.dao.PedidoRepository;
import dan.ms.tp.mspedidos.modelo.Pedido;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    
    @Autowired PedidoRepository repo;

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido){
        return ResponseEntity.ok().body(repo.save(pedido));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> buscar(){
        return ResponseEntity.ok().body(repo.findAll());
    }

    @GetMapping("/obtenerPorId")
    public ResponseEntity<Optional<Pedido>> buscar(@RequestBody String pedidoId){
        return ResponseEntity.ok().body(repo.findById(pedidoId));
    }
    @PutMapping("/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@RequestBody String pedidoId){
        Optional<Pedido> optionalPedido = repo.findById(pedidoId);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            // Realizar la lógica para cancelar el pedido aquí
            // Por ejemplo, puedes cambiar el estado del pedido a "CANCELADO"
            pedido.setEstado(EstadoPedido.CANCELADO); // ver como se settea un nuevo estado
            // Guardar los cambios en la base de datos
            pedido = repo.save(pedido);
            return ResponseEntity.ok().body(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
