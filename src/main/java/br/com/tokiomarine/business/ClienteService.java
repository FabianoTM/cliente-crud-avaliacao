package br.com.tokiomarine.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.exception.BusinessException;
import br.com.tokiomarine.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Iterable<Cliente> listar(Pageable paginacao) {
		return clienteRepository.findAll(paginacao);
	}

	public Cliente incluir(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId) throws BusinessException {
		try {
			obterClientePorId(clienteId);
		} catch (BusinessException e) {
			throw new BusinessException("Não foi possível excluir o cliente: " + e.getMessage());
		}
		clienteRepository.deleteById(clienteId);
	}

	public Cliente obterClientePorId(Long clienteId) throws BusinessException {
		Optional<Cliente> clienteLocalizado = clienteRepository.findById(clienteId);
		if (clienteLocalizado.isPresent()) {
			return clienteLocalizado.get();
		} else {
			throw new BusinessException("O id do cliente não foi localizado na base.");
		}

	}

}
