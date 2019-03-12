package br.com.entequerido.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Quadra;

public interface QuadraRepository extends MongoRepository<Quadra, String>{
	List<Quadra> findByNome(@Param("nome") String nome);
}
