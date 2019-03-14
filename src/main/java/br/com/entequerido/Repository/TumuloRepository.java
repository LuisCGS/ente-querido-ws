package br.com.entequerido.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Tumulo;

public interface TumuloRepository extends MongoRepository<Tumulo, String> {
	long countByRuaCodigo(@Param(value="codigo") String codigo);
}
