package br.edu.infnet.claudioapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.claudioapi.model.domain.AssetCategory;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer>{
	
	Optional<AssetCategory> findByassetCode(String assetCode);

}
