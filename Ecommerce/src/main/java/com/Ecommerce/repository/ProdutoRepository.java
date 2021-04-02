package com.Ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Ecommerce.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	public List<Produto> findAllByTituloContainingIgnoreCase(String titulo);

	@Query(value = "SELECT * FROM tb_produto WHERE preco >= 10.0",nativeQuery = true)
	public  List<Produto> finByPreco(@Param("preco")Float preco);
	
}
