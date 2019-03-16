package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Rua;
import br.com.entequerido.util.Parametros;

public interface RuaRepository extends MongoRepository<Rua, String>{
	Rua findByNomeOrCodigo(@Param(value=Parametros.RUA_NOME) String nome, @Param(value=Parametros.RUA_CODIGO) String codigo);
	List<Rua> findRuaByQuadraNomeLikeIgnoreCase(@Param(value = Parametros.RUA_NOME) String nomeQuadra, Sort sort);
	Page<Rua> findRuaByQuadraNomeLikeIgnoreCase(@Param(value=Parametros.RUA_NOME) String nome, Pageable pageable);
	
	List<Rua> findByNomeLikeIgnoreCase(@Param(value=Parametros.RUA_NOME) String nome, Sort sort);
	Page<Rua> findByNomeLikeIgnoreCase(@Param(value=Parametros.RUA_NOME) String nome, Pageable pageable);
	
	long countByQuadraCodigoOrNomeIgnoreCase(@Param(value=Parametros.QUADRA_CODIGO) String codigoQuadra, @Param(value=Parametros.QUADRA_NOME) String nomeQuadra);
}
