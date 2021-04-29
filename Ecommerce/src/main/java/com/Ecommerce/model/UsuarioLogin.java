package com.Ecommerce.model;


public class UsuarioLogin {
	
	private String nome;
	private String Usuario;
	private String senha;
	private String token;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String Usuario) {
		this.Usuario = Usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
