package com.Ecommerce.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Ecommerce.model.Produto;
import com.Ecommerce.model.Usuario;
import com.Ecommerce.model.UsuarioLogin;
import com.Ecommerce.repository.UsuarioRepository;
import com.Ecommerce.service.UsuarioService;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/Usuario")
@CrossOrigin("*")
public class UsuarioController {
	
	private @Autowired UsuarioRepository repository;
	private @Autowired UsuarioService serviceUsuario;
	
	@PostMapping("/cadastrar")
	@ApiOperation(value="Cadastra um novo cliente")
	public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody Usuario novoUsuario){
		Optional<Usuario> dto = serviceUsuario.cadastrarUsuario(novoUsuario);
		return !dto.isEmpty() ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
	}
	@PostMapping("/cadastrarProduto/{id_Usuario}")
	@ApiOperation(value="Cliente cadastra um novo produto")
	public ResponseEntity<?> cadastrarProduto(
			@Valid @RequestBody Produto novoProduto,
			@PathVariable(value= "id_Usuario")Long idUsuario){
		Optional<Usuario> dto = Optional.ofNullable(serviceUsuario.cadastrarProduto(novoProduto,idUsuario));
		return !dto.isEmpty() ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/logar")
	@ApiOperation(value="Realiza o login do cliente")
	public ResponseEntity<UsuarioLogin> logar(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		return serviceUsuario.logar(usuarioLogin)
				.map(usuario -> ResponseEntity.ok(usuario))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	
	@PostMapping("/produto/compra/{id_Produto}/{id_Usuario}")
	@ApiOperation(value="Realiza uma compra")
	public ResponseEntity<?> novaCompra(
			@PathVariable(value = "id_Produto") Long idProduto,
			@PathVariable(value = "id_Usuario") Long idUsuario){
		Usuario compra = serviceUsuario.comprarProduto(idUsuario, idProduto);
		if(compra == null) {
			return new ResponseEntity<String>("Produto ou Usuario invalido", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Usuario>(compra, HttpStatus.CREATED);
	}
	
	@GetMapping("/usuario/produto")
	@ApiOperation(value="Busca e retorna o usuario e seus produtos")
	public ResponseEntity<List<Usuario>> findAllUsuarioByProduto(@RequestParam(defaultValue = "") String titulo){
		return new ResponseEntity<List<Usuario>>(repository.findAllUsuarioByProdutoTitulo(titulo), HttpStatus.OK);
	}
	
	@DeleteMapping("/produto/delete/{id_Produto}/{id_Usuario}")
	@ApiOperation(value="Deleta o produto cadastrado")
	public ResponseEntity<Object> removerProduto(
			@PathVariable(value = "id_Produto") Long idProduto,
			@PathVariable(value = "id_Usuario") Long idUsuario){
		Usuario retorno = serviceUsuario.deletarProduto(idProduto, idUsuario);
		if(retorno == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não Autorizado");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(retorno);
	}
}