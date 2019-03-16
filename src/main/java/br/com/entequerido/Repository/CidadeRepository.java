package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Cidade;
import br.com.entequerido.util.Parametros;

public interface CidadeRepository extends MongoRepository<Cidade, String> {
	Cidade findByCodigoOrNome(@Param(value=Parametros.CIDADE_CODIGO) String codigo, @Param(value=Parametros.CIDADE_NOME) String nome);
	
	List<Cidade> findByNomeLikeIgnoreCase(@Param(value=Parametros.CIDADE_NOME) String nome, Sort sort);
	Page<Cidade> findByNomeLikeIgnoreCase(@Param(value=Parametros.CIDADE_NOME) String nome, Pageable pageable);
}
