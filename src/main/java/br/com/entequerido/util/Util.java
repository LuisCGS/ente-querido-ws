package br.com.entequerido.util;

import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

public class Util {
	public static final ResourceBundle bundleMensagens = ResourceBundle.getBundle("mensagens");
	public static final ResourceBundle bundleConfig = ResourceBundle.getBundle("configuracoes");
	public static final Gson gson = new Gson();
	
	/**
	 * Metodo responsavel por validar o atributo identificador do servidor {@link UUID}
	 *
	 * @author Luis Claudio Goncalves Sanches
	 * @since 18/08/2018 - 14:40
	 * @param uuid : {@link String}
	 * @return {@link Boolean}
	 */
	public static Boolean validarUUID(String uuid) {
		try {
			UUID.fromString(uuid);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Metodos responsavel por verificiar se o objeto parametrizado e nulo
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 12:52 </i>
	 * @param objeto : {@link Object}
	 * @return {@link Boolean}
	 */
	public static Boolean isNull(Object objeto) {
		return objeto == null;
	}
	
	/**
	 * Metodos responsavel por verificiar se o objeto parametrizado nao e nulo  
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 12:52 </i>
	 * @param objeto : {@link Object}
	 * @return {@link Boolean}
	 */
	public static Boolean isNotNull(Object objeto) {
		return Boolean.FALSE.equals(isNull(objeto));
	}
	
	/**
	 * Metodos responsavel por verificar se o texto esta branco
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 01:00 </i>
	 * @param texto : {@link String}
	 * @return {@link Boolean}
	 */
	public static Boolean isBlank(String texto) {
		return isNull(texto) || texto.trim().isEmpty();
	}
	
	/**
	 * Metodos responsavel por verificar se o texto nao esta branco 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 01:08 </i>
	 * @param texto
	 * @return
	 */
	public static Boolean isNotBlank(String texto) {
		return Boolean.FALSE.equals(isBlank(texto));
	}
	
	/**
	 * Método responsável por validar a conversão de uma {@link String} para {@link Double} 
	 *
	 * @author Luis Claudio Goncalves Sanches
	 * @since 18/08/2018 - 15:22
	 * @param numero : {@link String}
	 * @return {@link Boolean}
	 */
	public static Boolean validarConversaoDouble(String numero) {
		try {
			Double.parseDouble(numero);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Método responsável por formatar apenas 2 casas decimais do {@link Double} 
	 *
	 * @author Luis Claudio Goncalves Sanches
	 * @since 18/08/2018 - 18:46
	 * @param numero : {@link Double}
	 * @return {@link Double}
	 */
	public static Double formatarDecimais(Double numero) {
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		String numeroFormatado = decimalFormat.format(numero).replace(",", ".");
		
		return new Double(numeroFormatado);
	}
	
	/** Metodos responsavel por montar mensagem sobreescrevendo os parametros
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 11:24 </i>
	 * @param codigoMensagem : {@link String}
	 * @param parametros : {@link String}[]
	 * @return {@link String}
	 */
	public static String montarMensagemParametrizado(String codigoMensagem, String... parametros) {
		String mensagem = bundleMensagens.getString(codigoMensagem);
		
		for (int i = 0; i < parametros.length; i++) {
			mensagem = mensagem.replace(("{" + i + "}"), parametros[i]);
		}
		
		return mensagem;
	}
	
	public static final String montarPreAuthorizeHasAnyRole(String... perfis) {
		StringBuilder sb = new StringBuilder("hasAnyRole('");
		
		for (String string : perfis) {
			sb.append(string);
		}
		
		sb.append("')");
		return sb.toString();
	}
	
	/** Metodos responsavel por verificar a ordem informada pelo {@link RequestParam}
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 12:29 </i>
	 * @param ordem : {@link String}
	 * @return {@link Boolean}
	 */
	public static Boolean verificarOrdemParametrizado(String ordem) {
		return !ordem.equalsIgnoreCase(Direction.ASC.toString()) && !ordem.equalsIgnoreCase(Direction.DESC.toString());
	}
	
	/**
	 * Metodos responsavel por montar o retorno do response entity com o status de OK do {@link HttpStatus}, para haver reaproveitamento de codigo
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 22/03/2019 - 10:31 </i>
	 * @param objeto : {@link Object}
	 * @return {@link ResponseEntity}
	 */
	public static ResponseEntity<?> montarRetornoResponseEntity(Object objeto) {
		if(isNull(objeto)) {
			return null;
		}
		
		return new ResponseEntity<>(objeto, HttpStatus.OK);
	}
}
