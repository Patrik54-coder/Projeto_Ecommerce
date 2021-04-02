package com.Ecommerce.controller;

import java.util.List;
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
import com.Ecommerce.repository.ProdutoRepository;
import com.Ecommerce.service.ProdutoService;



@RestController
@RequestMapping("/Produto")
@CrossOrigin("*")
public class ProdutoController {
	
	
	private @Autowired ProdutoRepository repository;
	private @Autowired ProdutoService services;
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Produto>> pegarPorTitulo(@RequestParam (defaultValue = "")String titulo){
		return new ResponseEntity<List<Produto>>(services.pegarPorTitulo(titulo), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("preco/{preco}")
	public ResponseEntity<List<Produto>> GetfindByPreco(@PathVariable Float preco) 
	{
		return ResponseEntity.ok(repository.finByPreco(preco));
	}
	@PostMapping
	public ResponseEntity<Produto> post (@RequestBody Produto produto){
	return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	@PutMapping
	public ResponseEntity<Produto> put (@RequestBody Produto produto){
	return ResponseEntity.status(HttpStatus.OK).body(repository.save(produto));
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
	repository.deleteById(id);
	}
	
	
}
