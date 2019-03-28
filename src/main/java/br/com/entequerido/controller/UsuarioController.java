package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Usuario;
import br.com.entequerido.repository.UsuarioRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Metodos responsavel por salvar uma {@link Usuario} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 10:13 </i>
	 * @param usuario : {@link Usuario}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Usuario usuario) throws ValidacaoException, GenericoException {
		try {
			Usuario usuarioConsulta = usuarioRepository.findByLoginIgnoreCase(usuario.getLogin());
			
			if(Util.isNotNull(usuarioConsulta)
					&& !usuario.equals(usuarioConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.USUARIO, 
						Parametros.USUARIO_LOGIN), Caminhos.USUARIO);
			}
			
			return Util.montarRetornoResponseEntity(usuarioRepository.save(usuario));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.USUARIO);
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista ou paginas de {@link Usuario} por nome podendo ser ordenado e/ou paginado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:40 </i>
	 * @param nome : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link ResponseEntity}
	 * @throws GenericoException 
	 */
	@GetMapping
	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String login, String ordem, Integer pagina,
			Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.USUARIO);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(usuarioRepository.findByLoginLikeIgnoreCase(login, new Sort(ordemSort, Parametros.USUARIO_LOGIN)));
			} else {
				return Util.montarRetornoResponseEntity(usuarioRepository.findByLoginLikeIgnoreCase(login, PageRequest.of(pagina, tamanho, ordemSort, Parametros.USUARIO_LOGIN)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.USUARIO);
		}
	}
	
	/**
	 * Metodo responsavel por excluir uma {@link Usuario} a partir do codigo 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 10:19 </i>
	 * @param codigo : {@link String}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	@DeleteMapping
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Usuario> usuario = usuarioRepository.findById(codigo);
			
			if(!usuario.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.USUARIO), Caminhos.USUARIO);
			}
			
			usuarioRepository.delete(usuario.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.USUARIO);
		}
	}
}
