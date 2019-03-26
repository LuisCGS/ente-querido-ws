package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Quadra;
import br.com.entequerido.util.Parametros;

public interface QuadraRepository extends MongoRepository<Quadra, String>{
	Quadra findByCodigoOrNome(@Param(value=Parametros.QUADRA_CODIGO) String codigo, @Param(value=Parametros.QUADRA_NOME) String nome);
	Quadra findByNomeIgnoreCaseAndCemiterioCodigo(@Param(value=Parametros.QUADRA_NOME) String nome, @Param(value=Parametros.CEMITERIO_CODIGO) String codigo);
	
	List<Quadra> findByNomeLikeIgnoreCase(@Param(value=Parametros.QUADRA_NOME) String nome, Sort sort);
	Page<Quadra> findByNomeLikeIgnoreCase(@Param(value=Parametros.QUADRA_NOME) String nome, Pageable pageable);
	
	long countByCemiterioCodigo(@Param(value=Parametros.CEMITERIO_CODIGO) String codigo);
}
