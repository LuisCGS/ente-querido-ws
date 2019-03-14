package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Pessoa;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {
	List<Pessoa> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Sort sort);
	Page<Pessoa> findByNomeLikeIgnoreCase(@Param(value="nome") String nome, Pageable pageable);
	
	long countByTumuloCodigo(@Param(value="codigo") String codigoTumulo);
	long countByTumuloRuaCodigoOrNome(@Param(value="codigo") String codigo, @Param(value="nome") String nome);
	long countByTumuloRuaQuadraCodigoOrNome(@Param(value="codigo") String codigo, @Param(value="nome") String nome);
	long countByTumuloCemiterioCodigoOrNome(@Param(value="codigo") String codigo, @Param(value="nome") String nome);
	long countByTumuloCemiterioCidadeCodigoOrNome(@Param(value="codigo") String codigo, @Param(value="nome") String nome);
}
