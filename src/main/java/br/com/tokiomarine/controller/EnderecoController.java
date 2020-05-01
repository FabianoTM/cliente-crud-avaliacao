package br.com.tokiomarine.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.business.EnderecoService;
import br.com.tokiomarine.domain.ClienteResponse;
import br.com.tokiomarine.domain.Endereco;
import br.com.tokiomarine.domain.Erro;
import br.com.tokiomarine.exception.BusinessException;

@RestController
@RequestMapping("enderecos")
public class EnderecoController {

	int TAMANHO_PAGINACAO = 10;

	@Autowired
	private EnderecoService enderecoService;

	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Endereco endereco) {
		try {
			validarRequest(endereco);
			enderecoService.incluir(endereco);
			return new ResponseEntity<ClienteResponse>(
					new ClienteResponse(200, "Endereço do cliente cadastrado com sucesso!"), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Erro>(new Erro(e, 400), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<?> listarEnderecoCliente(@PathVariable("clienteId") Long clienteId) {
		try {
			return new ResponseEntity<List<Endereco>>(enderecoService.obterEnderecoCliente(clienteId), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Erro>(new Erro(e, 400), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{enderecoId}")
	public ResponseEntity<?> excluir(@PathParam("enderecoId") Long enderecoId) {
		try {
			enderecoService.excluir(enderecoId);
			return new ResponseEntity<ClienteResponse>(new ClienteResponse(200, "Endereco removido com sucesso!"),
					HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Erro>(new Erro(e, 400), HttpStatus.BAD_REQUEST);
		}
	}

	private boolean validarRequest(Endereco endereco) throws BusinessException {
		if (endereco.getCep() == null) {
			throw new BusinessException("É obrigatorio o preencimento do CEP!");
		}
		if (!endereco.getCep().replace("-", "").matches("\\d{8}")) {
			throw new BusinessException("O cep informado está inválido! Ex.: 012345-010");
		}
		if (endereco.getIdCliente() == null || endereco.getIdCliente().equals(0)) {
			throw new BusinessException("É obrigatorio o informar o id do cliente a ser vinculado!");
		}
		return true;
	}
}
