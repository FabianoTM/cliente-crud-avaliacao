package br.com.tokiomarine.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.tokiomarine.domain.Cep;
import br.com.tokiomarine.exception.BusinessException;

@Repository
public class CepRepository {

	private String VIA_CEP_URL = "https://viacep.com.br/ws/";
	private String VIA_CEP_FORMAT = "/json/";

	RestTemplate restTemplate;

	public CepRepository() {
		restTemplate = new RestTemplate();
	}

	public Cep obterDadosCep(String cepBusca) throws BusinessException {
		cepBusca = cepBusca.replace("-", "");
		try {
			ResponseEntity<Cep> cep = restTemplate.getForEntity(VIA_CEP_URL.concat(cepBusca).concat(VIA_CEP_FORMAT),
					Cep.class);
			return cep.getBody();
		} catch (Exception e) {
			throw new BusinessException("Ocorreu um erro ao localizar o cep informado.");
		}
	}

}
