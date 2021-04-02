package com.Ecommerce.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.model.Produto;
import com.Ecommerce.model.Usuario;
import com.Ecommerce.repository.ProdutoRepository;
import com.Ecommerce.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	private @Autowired UsuarioRepository repositoryUsuario;
	private @Autowired ProdutoRepository repositoryProduto;
	
	
	/**
	 * Registra no banco de dados um novo usuario para acessar o sistema caso não exista, retornando um optional com a entidade
	 * @return optional com usuario se os parametros estiverem devidamente escritos, caso contrario vazio(empty)
	 * @author patrik
	 */
	public Optional<Usuario> cadastrarUsuario(Usuario novoUsuario){
		
		Optional<Usuario> usuarioExistente = repositoryUsuario.findByUsuario(novoUsuario.getUsuario());
		if(usuarioExistente.isPresent()) {
		return Optional.empty();
		}
		Optional<Usuario> usuarioCadastrado = Optional.ofNullable(repositoryUsuario.save(novoUsuario));
		if(usuarioCadastrado.isPresent()) {
			return usuarioCadastrado;
		}else {
			return Optional.empty();
		}
	}
	/**
	 * Logar um usuario existente para acessar o sistema 
	 * @return retorna o usuario caso os parametros estejam corretos
	 */
	public Optional<Usuario> logar(Optional<Usuario> Usuario){
		Optional<Usuario> usuarioPresente = repositoryUsuario.findByUsuario(Usuario.get().getUsuario());

		if(usuarioPresente.isPresent()) {
				Usuario.get().getUsuario();
				Usuario.get().getSenha();				
				Usuario.get().setNome(usuarioPresente.get().getNome());
				Usuario.get().setSenha(usuarioPresente.get().getSenha());

				return Usuario;
		}
		return null;
	}
	//Comprar produto
	public Usuario comprarProduto(Long idUsuario, Long idProduto) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		Optional<Produto> produtoExistente = repositoryProduto.findById(idProduto);
		
		if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
			usuarioExistente.get().getProduto().add(produtoExistente.get());
			return repositoryUsuario.save(usuarioExistente.get());
		}
		return null;
	}
	public Usuario cadastrarProduto(Produto novoProduto, Long idUsuario) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		if(usuarioExistente.isPresent()) {
			novoProduto.setUsuario(usuarioExistente.get());
			repositoryProduto.save(novoProduto);
			return repositoryUsuario.findById(idUsuario).get();
		}
		return null;
	}
			
	// Remover um produto
		public Usuario deletarProduto(Long idProduto, Long idUsuario) {
			Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
			Optional<Produto> produtoExistente = repositoryProduto.findById(idProduto);
			
			if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
				if(usuarioExistente.get() == produtoExistente.get().getUsuario()) {
					produtoExistente.get().setUsuario(null);
					repositoryProduto.save(produtoExistente.get());
					repositoryProduto.deleteById(produtoExistente.get().getId());
					return repositoryUsuario.findById(usuarioExistente.get().getId()).get();
				}
			}
			return null;
		}
	
}