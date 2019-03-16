package br.com.entequerido.util;

public final class Caminhos {
	/* Caminhos de chamada para a controller de rua */
	public static final String WS_RUA = "/ws/rua";
	public static final String SALVAR_RUA = "/salvarRua";
	public static final String BUSCAR_RUA_NOME = "/buscarRuaPorNome";
	public static final String BUSCAR_RUA_QUADRA = "/buscarRuaPorQuadra";
	public static final String BUSCAR_RUA_POR_NOME_ORDENADO_E_OU_PAGINADO = "/buscarRuaPorNomeOrdenadoEOuPaginado";
	public static final String BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA = "/buscarQuantidadeRuaPorCodigoDeQuadra";
	public static final String EXCLUIR_RUA = "/excluirRua";
	
	/* Caminhos de chamada para a controller de Quadra */
	public static final String WS_QUADRA = "/ws/quadra";
	public static final String SALVAR_QUADRA = "/salvarQuadra";
	public static final String BUSCAR_QUADRA_NOME = "/buscarQuadraPorNome";
	
	/* Caminhos de chamada para a controller de Tumulo */
	public static final String WS_TUMULO = "/ws/tumulo";
	public static final String SALVAR_TUMULO = "/salvarTumulo";
	
	/* Caminhos de chamada para a controller de Cidade */
	public static final String WS_CIDADE = "/ws/cidade";
	public static final String SALVAR_CIDADE = "/salvarCidade";
	public static final String BUSCAR_QUANTIDADE_CIDADE_POR_NOME = "/buscarQuantidadeCidadePorNome";
	public static final String BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO = "/buscarCidadePorNomeOrdenadoEOuPaginado";
	
	/* Caminhos de chamada para a controller de Cemiterio */
	public static final String WS_CEMITERIO = "/ws/cemiterio";
	public static final String SALVAR_CEMITERIO = "/salvarCemiterio";
	public static final String BUSCAR_QUANTIDADE_CEMITERIO_POR_NOME_DE_CIDADE = "/buscarQuantidadeCemiterioPorNomeDeCidade";
	public static final String BUSCAR_CEMITERIO_POR_NOME_ORDENADO_E_OU_PAGINADO = "/buscarCemiterioPorNomeOrdenadoEOuPaginado";
}
