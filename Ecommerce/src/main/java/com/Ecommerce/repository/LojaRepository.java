package com.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.model.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
	public List<Loja> findAllByNomeContainingIgnoreCase(String nome);
	
	

}
