package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.model.Rua;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;
import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Quadra;

@RestController
@RequestMapping("/rua")
public class RuaController {
	@Autowired
	private RuaRepository ruaRepository;
	
	@Autowired
	private QuadraRepository quadraRepository;
	
	@Autowired
	private TumuloRepository tumuloRepository;
	
	/**
	 * Metodos responsavel por salvar uma {@link Rua} sendo obrigatorio ja ter adicionado uma {@link Quadra} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 04:01 </i>
	 * @param rua : {@link Rua}
	 * @return {@link String}
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Rua rua) throws ValidacaoException, Exception{
		try {
			Quadra quadra = quadraRepository.findByCodigoOrNome(rua.getQuadra().getCodigo(), null);
			
			if(Util.isNull(quadra)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Parametros.QUADRA_CODIGO, 
						Parametros.QUADRA), Caminhos.RUA);
			}
			
			Rua ruaConsulta = ruaRepository.findByNomeIgnoreCaseAndQuadraCodigo(rua.getNome().trim(), quadra.getCodigo());
			
			if(Util.isNotNull(ruaConsulta)
					&& !rua.equals(ruaConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, 
						Parametros.RUA, Parametros.RUA_NOME), Caminhos.RUA);
			}
			
			rua.setQuadra(quadra);
			return Util.montarRetornoResponseEntity(ruaRepository.save(rua));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}
	
	/**
	 * Metodos responsavel por buscar as {@link Rua} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 * 
	 * <b> Ordem pode ser vazio Ex.: &ordem OU &ordem="" </b>
	 * <b> Pagina e tamanho pode ser vazio. Ex.: &pagina&tamanho </b>
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 11:23 </i>
	 * @param nomeRua : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link String}
	 * @throws GenericoException, ValidacaoException 
	 */
	@GetMapping
	public ResponseEntity<?> buscarRuaPorNomeOrdenadoEOuPaginado(@RequestParam String nome, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) throws GenericoException, ValidacaoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.RUA);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(ruaRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.RUA_NOME)));
			} else {
				return Util.montarRetornoResponseEntity(ruaRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.RUA_NOME)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}
	
	/**
	 * Metodos responsavel por obter a quantidade de ruas que existem para a quadra informada 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 12:35 </i>
	 * @param codigoQuadra : {@link String}
	 * @return {@link String}
	 * @throws ValidacaoException, GenericoException 
	 */
	@RequestMapping(value=Caminhos.BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA, method=RequestMethod.GET)
	public String buscarQuantidadeRuaPorQuadra(@RequestParam @NotBlank String codigoQuadra) throws ValidacaoException, GenericoException {
		try {
			if(Util.isNull(quadraRepository.findByCodigoOrNome(codigoQuadra, null))){
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.QUADRA), 
						Caminhos.RUA.concat(Caminhos.BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA));
			}
			
			return Long.toString(ruaRepository.countByQuadraCodigoOrNome(codigoQuadra, null));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.QUADRA.concat(Caminhos.BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA));
		}
	}
	
	/**
	 * Metodos responsavel por excluir uma {@link Rua} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 01:27 </i>
	 * @param codigo : {@link String}
	 * @throws GenericoException 
	 */
	@DeleteMapping
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Rua> rua = ruaRepository.findById(codigo);
			
			if(!rua.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.RUA), Caminhos.RUA);
			}
			
			if(tumuloRepository.countByRuaCodigo(rua.get().getCodigo()) > 0) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Parametros.TUMULO, Parametros.RUA), Caminhos.RUA);
			}
			
			ruaRepository.delete(rua.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}
	
}
