package com.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.Ecommerce.model.Loja;
import com.Ecommerce.repository.LojaRepository;

import io.swagger.annotations.ApiOperation;


public class LojaController {
	@Autowired
	private LojaRepository repository;
	
	@GetMapping
	@ApiOperation(value="Busca e retorna todas as lojas cadastradas")
	public ResponseEntity<List<Loja>> GetAll(){
	return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	@ApiOperation(value="Busca e retorna a loja pelo id")
	public ResponseEntity<Loja> GetById(@PathVariable long id){
	return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ApiOperation(value="Realiza o cadastro da loja")
	public ResponseEntity<Loja> post (@RequestBody Loja nome){
	return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(nome));
	}
	@PutMapping
	@ApiOperation(value="Modifica os dados da loja")
	public ResponseEntity<Loja> put (@RequestBody Loja nome){
	return ResponseEntity.status(HttpStatus.OK).body(repository.save(nome));
	}
	@DeleteMapping("/{id}")
	@ApiOperation(value="Deleta a loja pelo id")
	public void delete(@PathVariable Long id){
	repository.deleteById(id);
	}
}
