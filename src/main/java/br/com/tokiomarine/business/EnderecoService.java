package br.com.tokiomarine.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.domain.Cep;
import br.com.tokiomarine.domain.Endereco;
import br.com.tokiomarine.exception.BusinessException;
import br.com.tokiomarine.repository.CepRepository;
import br.com.tokiomarine.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	ClienteService clienteService;

	@Autowired
	CepRepository cepRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	public Endereco incluir(Endereco endereco) throws BusinessException {
		try {
			clienteService.obterClientePorId(endereco.getIdCliente());
		} catch (BusinessException e) {
			throw new BusinessException("Não foi possível incluir novo endereço: " + e.getMessage());
		}
		Cep dadosCep = cepRepository.obterDadosCep(endereco.getCep());
		endereco.setCep(dadosCep.getCep());
		endereco.setLogradouro(dadosCep.getLogradouro());
		endereco.setCidade(dadosCep.getLocalidade());
		endereco.setUf(dadosCep.getUf());
		return enderecoRepository.save(endereco);
	}

	public List<Endereco> obterEnderecoCliente(Long idCliente) throws BusinessException {
		try {
			clienteService.obterClientePorId(idCliente);
			return enderecoRepository.findAllByIdCliente(idCliente);
		} catch (BusinessException e) {
			throw new BusinessException("Não foi possível obter a lista de endereço: " + e.getMessage());
		}
	}

	public void excluir(Long enderecoId) throws BusinessException {
		try {
			obterEnderecoPorId(enderecoId);
		} catch (BusinessException e) {
			throw new BusinessException("Não foi possível excluir o endereço: " + e.getMessage());
		}
		enderecoRepository.deleteById(enderecoId);

	}

	public Endereco obterEnderecoPorId(Long enderecoId) throws BusinessException {
		Optional<Endereco> enderecoLocalizado = enderecoRepository.findById(enderecoId);
		if (enderecoLocalizado.isPresent()) {
			return enderecoLocalizado.get();
		} else {
			throw new BusinessException("O id do endereço não foi localizado na base.");
		}

	}

}
