package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.util.Parametros;

public interface CemiterioRepository extends MongoRepository<Cemiterio, String> {
	Cemiterio findByCodigoOrNome(@Param(value=Parametros.CEMITERIO_CODIGO) String codigo, @Param(value=Parametros.CEMITERIO_NOME) String nome);
	Cemiterio findByNomeIgnoreCaseAndCidadeCodigo(@Param(value=Parametros.CEMITERIO_NOME) String nome, @Param(value=Parametros.CIDADE_CODIGO) String codigo);
	
	List<Cemiterio> findByNomeLikeIgnoreCase(@Param(value=Parametros.CEMITERIO_NOME) String nome, Sort sort);
	Page<Cemiterio> findByNomeLikeIgnoreCase(@Param(value=Parametros.CEMITERIO_NOME) String nome, Pageable pageable);
	
	
	List<Cemiterio> findByCidadeCodigo(@Param(value=Parametros.CIDADE_CODIGO) String codigo);
	long countByCidadeCodigoOrNome(@Param(value=Parametros.CIDADE_CODIGO) String codigo, @Param(value=Parametros.CEMITERIO_NOME) String nome);
}
