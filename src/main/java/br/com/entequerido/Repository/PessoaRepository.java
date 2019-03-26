package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Pessoa;
import br.com.entequerido.util.Parametros;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {
	Pessoa findByNomeIgnoreCaseAndTumuloCodigo(@Param(value=Parametros.PESSOA_NOME) String nome, @Param(value=Parametros.TUMULO_CODIGO) String codigo);
	
	List<Pessoa> findByNomeLikeIgnoreCase(@Param(value=Parametros.PESSOA_NOME) String nome, Sort sort);
	Page<Pessoa> findByNomeLikeIgnoreCase(@Param(value=Parametros.PESSOA_NOME) String nome, Pageable pageable);
	
	long countByTumuloCodigoOrNome(@Param(value=Parametros.TUMULO_CODIGO) String codigo, @Param(value=Parametros.TUMULO_NOME) String nome);
	long countByTumuloRuaCodigoOrNome(@Param(value=Parametros.RUA_CODIGO) String codigo, @Param(value=Parametros.RUA_NOME) String nome);
	long countByTumuloRuaQuadraCodigoOrNome(@Param(value=Parametros.QUADRA_CODIGO) String codigo, @Param(value=Parametros.QUADRA_NOME) String nome);
	long countByTumuloRuaQuadraCemiterioCodigoOrNome(@Param(value=Parametros.CEMITERIO_CODIGO) String codigo, @Param(value=Parametros.CEMITERIO_NOME) String nome);
	long countByTumuloRuaQuadraCemiterioCidadeCodigoOrNome(@Param(value=Parametros.CIDADE_CODIGO) String codigo, @Param(value=Parametros.CIDADE_NOME) String nome);
}
