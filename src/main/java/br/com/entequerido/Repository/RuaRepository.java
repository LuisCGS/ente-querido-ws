package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Rua;
import br.com.entequerido.model.Quadra;

public interface RuaRepository extends MongoRepository<Rua, String>{
	List<Rua> findByQuadraLikeIgnoreCase(@Param(value = "quadra") Quadra quadra);
	List<Rua> findByRuaLikeIgnoreCase(@Param(value="rua") String rua);
}
