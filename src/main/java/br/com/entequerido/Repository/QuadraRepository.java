package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Quadra;

public interface QuadraRepository extends MongoRepository<Quadra, String>{
	List<Quadra> findByNomeIgnoreCase(@Param(value="nome") String nome);
	List<Quadra> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Sort sort);
	Page<Quadra> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Pageable pageable);
}
