package br.edu.infnet.mono.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.infnet.mono.model.domain.TruckAsset;


public interface TruckRepository extends JpaRepository<TruckAsset, Long>  {
	
	  	List<TruckAsset> findByMarcaId(Integer marcaId);

	    List<TruckAsset> findByMarcaIdAndModeloId(Integer marcaId, Integer modeloId);

	    Optional<TruckAsset> findFirstByMarcaIdAndModeloIdAndAnoCodigo(Integer marcaId, Integer modeloId, String anoCodigo);

	    Optional<TruckAsset> findByCodigoFipe(String codigoFipe);

}
