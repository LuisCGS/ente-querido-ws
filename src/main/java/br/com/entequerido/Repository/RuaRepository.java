package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Rua;

public interface RuaRepository extends MongoRepository<Rua, String>{
	List<Rua> findByNomeOrCodigo(@Param(value="nome") String nome, @Param(value="codigo") String codigo);
	List<Rua> findRuaByQuadraNomeLikeIgnoreCase(@Param(value = "nome") String nomeQuadra, Sort sort);
	Page<Rua> findRuaByQuadraNomeLikeIgnoreCase(@Param(value="nome") String nome, Pageable pageable);
	
	List<Rua> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Sort sort);
	Page<Rua> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Pageable pageable);
	
	long countByQuadraNomeIgnoreCase(@Param(value="nome") String nomeQuadra);
}
