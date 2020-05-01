package br.com.tokiomarine.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Erro {

	private Integer codigo;
	private String mensagem;

	public Erro(Exception e, Integer codigo) {
		this.mensagem = e.getMessage();
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getMensagem() {
		return mensagem;
	}
}