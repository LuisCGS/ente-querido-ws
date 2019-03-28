package br.com.entequerido.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import br.com.entequerido.model.Usuario;
import br.com.entequerido.util.Parametros;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	Usuario findByLoginIgnoreCase(@Param(value=Parametros.USUARIO_LOGIN) String login);
	
	List<Usuario> findByLoginLikeIgnoreCase(@Param(value=Parametros.USUARIO_LOGIN) String login, Sort sort);
	Page<Usuario> findByLoginLikeIgnoreCase(@Param(value=Parametros.USUARIO_LOGIN) String login, Pageable pageable);
}
