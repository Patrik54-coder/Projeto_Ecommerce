package com.Ecommerce.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Ecommerce.model.Produto;
import com.Ecommerce.repository.ProdutoRepository;

@Service
public class ProdutoService {
private @Autowired ProdutoRepository repositoryProduto;
	
	// Validar Lista de Produtos por Nome
	public List<Produto> pegarPorTitulo(String titulo){
		return repositoryProduto.findAllByTituloContainingIgnoreCase(titulo);
	}
}
