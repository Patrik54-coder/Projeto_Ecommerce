package com.Ecommerce.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Ecommerce.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findAllByUsuarioContainingIgnoreCase(String nome);

	public Optional<Usuario> findByUsuario(String usuario);
	
	@Query(value = ""
			+ "SELECT * FROM tb_usuario "
			+ "INNER JOIN tb_produto "
			+ "ON tb_usuario.id = tb_produto.usuario_id "
			+ "WHERE tb_produto.titulo LIKE CONCAT('%',:produto,'%')", nativeQuery = true)
	public List<Usuario> findAllUsuarioByProdutoTitulo(@Param("produto") String titulo);

}