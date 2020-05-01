package br.com.tokiomarine.domain;

public class ClienteResponse {

	private Integer retorno;
	private String Mensagem;

	public ClienteResponse() {

	}

	public ClienteResponse(Integer retorno, String mensagem) {
		super();
		this.retorno = retorno;
		Mensagem = mensagem;
	}

	public Integer getRetorno() {
		return retorno;
	}

	public void setRetorno(Integer retorno) {
		this.retorno = retorno;
	}

	public String getMensagem() {
		return Mensagem;
	}

	public void setMensagem(String mensagem) {
		Mensagem = mensagem;
	}

}
