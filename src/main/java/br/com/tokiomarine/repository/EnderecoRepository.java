package br.com.tokiomarine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.domain.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

	@Query("SELECT e FROM Endereco e WHERE e.idCliente = :idCliente")
	List<Endereco> findAllByIdCliente(Long idCliente);

}
