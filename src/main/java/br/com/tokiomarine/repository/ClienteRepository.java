package br.com.tokiomarine.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.domain.Cliente;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
	
	List<Cliente> findAllByOrderByNomeAsc();
	
	
}
