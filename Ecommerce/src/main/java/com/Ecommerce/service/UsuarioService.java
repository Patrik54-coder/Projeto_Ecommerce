package com.Ecommerce.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import com.Ecommerce.model.Produto;
import com.Ecommerce.model.Usuario;
import com.Ecommerce.model.UsuarioLogin;
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCriptografada = encoder.encode(novoUsuario.getSenha());
		novoUsuario.setSenha(senhaCriptografada);
		return Optional.ofNullable(repositoryUsuario.save(novoUsuario));
	}
	/**
	 * Logar um usuario existente para acessar o sistema 
	 * @return retorna o usuario caso os parametros estejam corretos
	 */
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> usuarioLogin){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuarioPresente = repositoryUsuario.findByUsuario(usuarioLogin.get().getUsuario());

		if(usuarioPresente.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioPresente.get().getSenha())) {
				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogin.get().setToken(authHeader);				
				usuarioLogin.get().setNome(usuarioPresente.get().getNome());
				usuarioLogin.get().setSenha(usuarioPresente.get().getSenha());

				return usuarioLogin;
			}
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
	
	public Optional<Produto> inserirNoCarrinho(Long idUsuario, Produto produto) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		if(usuarioExistente.isPresent()) {
			produto.setUsuario(usuarioExistente.get());		
			return Optional.ofNullable(repositoryProduto.save(produto));
		}
		return Optional.empty();
	}
	
	public Optional<Usuario> removerDoCarrinho(Long idUsuario, Long idProduto) {
		Optional<Produto> produto = repositoryProduto.findById(idProduto);
		Optional<Usuario> usuario = repositoryUsuario.findById(idUsuario);

		if (produto.isPresent() && usuario.isPresent()) {

			usuario.get().getMinhasCompras().remove(produto.get());
			return Optional.ofNullable(repositoryUsuario.save(usuario.get()));
		} else {
			return Optional.empty();
		}

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