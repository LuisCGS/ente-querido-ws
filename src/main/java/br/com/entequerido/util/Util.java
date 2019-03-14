package br.com.entequerido.util;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

public final class Util {
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
	
	/**
	 * Metodos responsavel por montar a mensagem de retorno de um erro de validacao
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 03:01 </i>
	 * @param mensagem : {@link String}
	 * @param caminho : {@link String}
	 * @return {@link String}
	 */
	public static String montarRetornoErro(String codigoMensagem, String caminho) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("timestamp", new Date().toString());
		map.put("status", Integer.toString(HttpStatus.BAD_REQUEST.value()));
		map.put("error", HttpStatus.BAD_REQUEST.name());
		map.put("message", bundleMensagens.getString(codigoMensagem));
		map.put("path", caminho);
		
		return new Gson().toJson(map);
	}
	
	/**
	 * Metodos responsavel por montar a mensagem de retorno de uma excecao causada por algo interno 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 03:32 </i>
	 * @param mensagem : {@link String}
	 * @param caminho : {@link String}
	 * @return {@link String}
	 */
	public static String montarRetornoErroException(String mensagem, String caminho) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("timestamp", new Date().toString());
		map.put("status", Integer.toString(HttpStatus.BAD_REQUEST.value()));
		map.put("error", HttpStatus.BAD_REQUEST.name());
		map.put("message", mensagem);
		map.put("path", caminho);
		
		return new Gson().toJson(map);
	}
	
	/**
	 * Metodos responsavel por retornar uma mensagem de sucesso! 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 01:24 </i>
	 * @param mensagem : {@link String} - codigo que esta no arquivo mensagens
	 * @param entidade : {@link String} - entidade que foi executada
	 * @return {@link String}
	 */
	public static String montarRetornoSucesso(String mensagem, String entidade) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("timestamp", new Date().toString());
		map.put("status", Integer.toString(HttpStatus.ACCEPTED.value()));
		map.put("message", bundleMensagens.getString(mensagem).replace("{0}", entidade));
		
		return new Gson().toJson(map);
	}
}
