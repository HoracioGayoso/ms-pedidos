package dan.ms.tp.mspedidos.controller;


import java.util.List;
import java.util.Optional;

import dan.ms.tp.mspedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dan.ms.tp.mspedidos.dao.PedidoRepository;
import dan.ms.tp.mspedidos.modelo.Pedido;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    
    @Autowired PedidoRepository repo;
    @Autowired
    PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Pedido pedido,@RequestHeader("Authorization") String token){
        //System.out.println(token);
        try{
        return ResponseEntity.ok().body(pedidoService.createPedido(pedido,token));}
        catch(Exception e){
            System.out.println("Error papu" +e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> buscar(){
        return ResponseEntity.ok().body(repo.findAll());
    }

    @GetMapping("/obtenerPorId")
    public ResponseEntity<Optional<Pedido>> buscar(@RequestBody String pedidoId){
        return ResponseEntity.ok().body(repo.findById(pedidoId));
    }
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable String id) {
        try {
            Pedido pedidoCancelado = pedidoService.cancelPedido(id);
            return ResponseEntity.ok().body(pedidoCancelado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
