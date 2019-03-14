package br.com.entequerido.util;

public final class Caminhos {
	/* Caminhos de chamada para a controller de rua */
	public static final String WS_RUA = "/ws/rua";
	public static final String SALVAR_RUA = "/salvarRua";
	public static final String BUSCAR_RUA_NOME = "/buscarRuaPorNome";
	public static final String BUSCAR_RUA_QUADRA = "/buscarRuaPorQuadra";
	public static final String BUSCAR_RUA_POR_NOME_ORDENADO_E_OU_PAGINADO = "/buscarRuaPorNomeOrdenadoEOuPaginado";
	public static final String BUSCAR_QUANTIDADE_RUA_POR_QUADRA = "/buscarQuantidadeRuaPorQuadra";
	public static final String EXCLUIR_RUA = "/excluirRua";
	
	/* Caminhos de chamada para a controller de Quadra */
	public static final String WS_QUADRA = "/ws/quadra";
	public static final String SALVAR_QUADRA = "/salvarQuadra";
	public static final String BUSCAR_QUADRA_NOME = "/buscarQuadraPorNome";
	
	/* Caminhos de chamada para a controller de Tumulo */
	public static final String WS_TUMULO = "/ws/tumulo";
	public static final String SALVAR_TUMULO = "/salvarTumulo";
}
