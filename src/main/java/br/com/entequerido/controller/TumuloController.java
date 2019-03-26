package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.interfaces.ControllerInterface;
import br.com.entequerido.model.Rua;
import br.com.entequerido.model.Tumulo;
import br.com.entequerido.repository.PessoaRepository;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/tumulo")
public class TumuloController implements ControllerInterface {
	@Autowired
	private RuaRepository ruaRepository;
	
	@Autowired
	private TumuloRepository tumuloRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/**
	 * Metodo responsavel por salvar um {@link Tumulo} em uma determinada e obrigatoria {@link Rua} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 02:24 </i>
	 * @param tumulo : {@link Tumulo}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Tumulo tumulo) throws ValidacaoException, Exception{
		try {
			Rua rua = ruaRepository.findByCodigoOrNome(tumulo.getRua().getCodigo(), null);
			
			if(Util.isNull(rua)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Parametros.RUA_CODIGO, 
						Parametros.RUA), Caminhos.TUMULO);
			}
			
			tumulo.setRua(rua);
			return Util.montarRetornoResponseEntity(tumuloRepository.save(tumulo));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}
	
	/**
	 * Metodo responsavel por excluir um {@link Tumulo}
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 14:25 </i>
	 * @param codigo : {@link String}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Tumulo> tumulo = tumuloRepository.findById(codigo);
			
			if(!tumulo.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.TUMULO), Caminhos.TUMULO);
			}
			
			if(pessoaRepository.countByTumuloCodigoOrNome(tumulo.get().getCodigo(), null) > 0) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Parametros.PESSOA, Parametros.TUMULO), Caminhos.TUMULO);
			}
			
			tumuloRepository.delete(tumulo.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}

	/**
	 * Metodos responsavel por buscar as {@link Tumulo} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 * 
	 * <b> Ordem pode ser vazio Ex.: &ordem OU &ordem="" </b>
	 * <b> Pagina e tamanho pode ser vazio. Ex.: &pagina&tamanho </b>
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 14:26 </i>
	 * @param nome : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String nome, String ordem, Integer pagina,
			Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.TUMULO);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(tumuloRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.TUMULO_NOME)));
			} else {
				return Util.montarRetornoResponseEntity(tumuloRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.TUMULO_NOME)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.TUMULO);
		}
	}

}
