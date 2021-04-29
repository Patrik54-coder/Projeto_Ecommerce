package com.Ecommerce.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull @Size(min = 2) String nome;
	private @NotNull @Size(min = 2) String usuario;
	private @NotNull @Size(min = 2) String senha;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			  name = "inscricao", 
			  joinColumns = @JoinColumn(name = "usuario_id"), 
			  inverseJoinColumns = @JoinColumn(name = "loja_id"))
	@JsonIgnoreProperties("loja1")
	private Set<Loja> loja1 = new HashSet<>();

	@OneToMany(mappedBy = "usuarios" , cascade = CascadeType.ALL)
	@JsonIgnoreProperties("loja")
	private List<Loja> loja = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("usuario")
	private List<Produto> produto = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Loja> getLoja1() {
		return loja1;
	}

	public void setLoja1(Set<Loja> loja1) {
		this.loja1 = loja1;
	}

	public List<Loja> getLoja() {
		return loja;
	}

	public void setLoja(List<Loja> loja) {
		this.loja = loja;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public List<Produto> getMinhasCompras() {
		// TODO Auto-generated method stub
		return null;
	}

}
