package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Cemiterio;

public interface CemiterioRepository extends MongoRepository<Cemiterio, String> {
	List<Cemiterio> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Sort sort);
	Page<Cemiterio> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Pageable pageable);
	
	long countByCidadeCodigoOrNomeIgnoreCase(@Param(value="codigo") String codigo, @Param(value="nome") String nome);
}
