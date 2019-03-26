package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Tumulo;
import br.com.entequerido.util.Parametros;

public interface TumuloRepository extends MongoRepository<Tumulo, String> {
	Tumulo findByCodigoOrNome(@Param(value=Parametros.TUMULO_CODIGO) String codigo, @Param(value=Parametros.TUMULO_NOME) String nome);
	
	long countByRuaCodigo(@Param(value=Parametros.RUA_CODIGO) String codigo);
	
	List<Tumulo> findByNomeLikeIgnoreCase(@Param(value=Parametros.TUMULO_NOME) String nome, Sort sort);
	Page<Tumulo> findByNomeLikeIgnoreCase(@Param(value=Parametros.TUMULO_NOME) String nome, Pageable pageable);
}
