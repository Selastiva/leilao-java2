package br.com.senac.leiloes.controller;

import br.com.senac.leiloes.dto.*;import br.com.senac.leiloes.model.*;import br.com.senac.leiloes.service.LeilaoService;
import jakarta.validation.Valid;import java.util.List;import java.util.Map;
import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/produtos")
public class ProdutoController {
 private final LeilaoService service; public ProdutoController(LeilaoService service){this.service=service;}
 @GetMapping public List<Produto> listar(){return service.listar();}
 @GetMapping("/{id}") public Produto buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping public Produto cadastrar(@Valid @RequestBody ProdutoRequest request){return service.cadastrar(request);}
 @GetMapping("/{id}/lances") public List<Lance> lances(@PathVariable Long id){return service.listarLances(id);}
 @PostMapping("/{id}/lances") @ResponseStatus(HttpStatus.CREATED) public Lance lance(@PathVariable Long id,@Valid @RequestBody LanceRequest request){return service.ofertar(id,request);}
 @PutMapping("/{id}/venda") public Produto vender(@PathVariable Long id){return service.vender(id);}
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,String> erro(RuntimeException ex){return Map.of("erro",ex.getMessage());}
}
