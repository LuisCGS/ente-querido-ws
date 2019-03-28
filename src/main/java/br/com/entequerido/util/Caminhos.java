package br.com.entequerido.util;

public final class Caminhos {
	/* Caminhos de chamada para a controller de rua */
	public static final String RUA = "/rua";
	public static final String BUSCAR_RUA_NOME = "/buscarRuaPorNome";
	public static final String BUSCAR_RUA_POR_CODIGO_QUADRA = "/buscarRuaPorCodigoQuadra";
	public static final String BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA = "/buscarQuantidadeRuaPorCodigoDeQuadra";
	
	/* Caminhos de chamada para a controller de Quadra */
	public static final String QUADRA = "/quadra";
	
	/* Caminhos de chamada para a controller de Tumulo */
	public static final String TUMULO = "/tumulo";
	
	/* Caminhos de chamada para a controller de Cidade */
	public static final String CIDADE = "/cidade";
	public static final String SALVAR_CIDADE = "/salvarCidade";
	public static final String BUSCAR_QUANTIDADE_CIDADE_POR_NOME = "/buscarQuantidadeCidadePorNome";
	
	/* Caminhos de chamada para a controller de Cemiterio */
	public static final String CEMITERIO = "/cemiterio";
	public static final String BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE = "/buscarQuantidadeCemiterioPorCodigoDeCidade";
	public static final String BUSCAR_CEMITERIO_POR_CODIGO_DE_CIDADE = "/buscarCemiterioPorCodigoDeCidade";
	
	/* Caminhos de chamada para a controller de Pessoa */
	public static final String PESSOA = "/pessoa";
	
	/* Caminhos de chamada para a controller de Usuario */
	public static final String USUARIO = "/usuario";
	
}
