package br.edu.infnet.mono.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.mono.model.domain.AssetCategory;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {

    Optional<AssetCategory> findByCategoryCode(int categoryCode);
    
    boolean existsByCategoryCode(int categoryCode);

    boolean existsByCategoryNameIgnoreCase(String categoryName);
	
}
