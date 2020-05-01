package br.com.tokiomarine.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.business.ClienteService;
import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.domain.ClienteResponse;
import br.com.tokiomarine.domain.Erro;
import br.com.tokiomarine.exception.BusinessException;

@RestController
@RequestMapping("clientes")
public class ClienteController {

	int TAMANHO_PAGINACAO = 10;

	@Autowired
	private ClienteService clienteService;

	@GetMapping()
	public Iterable<Cliente> listarClientes(@RequestParam("pagina") int pagina) {
		Pageable paginacao = PageRequest.of(pagina, TAMANHO_PAGINACAO);
		return clienteService.listar(paginacao);
	}

	@PostMapping
	public Cliente inlcuir(@RequestBody Cliente cliente) {
		return clienteService.incluir(cliente);
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<?> incluir(@PathVariable("clienteId") Long clienteId) {
		try {
			return new ResponseEntity<Cliente>(clienteService.obterClientePorId(clienteId), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Erro>(new Erro(e, 400), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<?> excluir(@PathParam("clienteId") Long clienteId) {
		try {
			clienteService.excluir(clienteId);
			return new ResponseEntity<ClienteResponse>(new ClienteResponse(200, "Cliente removido com sucesso!"),
					HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Erro>(new Erro(e, 400), HttpStatus.BAD_REQUEST);
		}
	}
}
