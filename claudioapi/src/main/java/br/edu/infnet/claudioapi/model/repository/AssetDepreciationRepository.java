package br.edu.infnet.claudioapi.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.infnet.claudioapi.model.domain.AssetDepreciation;
import br.edu.infnet.claudioapi.model.domain.AssetDepreciationType;

public interface AssetDepreciationRepository extends JpaRepository<AssetDepreciation, Integer> {
	
    Optional<AssetDepreciation> findByAssetCode(String assetCode);
    
    List<AssetDepreciation> findByDepreciationPeriod(String depreciationPeriod);
    List<AssetDepreciation> findByDepreciationRateYear(double depreciationRateYear);
    List<AssetDepreciation> findByFullyDepreciated(boolean fullyDepreciated);
    List<AssetDepreciation> findByValueDepreciatedPeriod(String valueDepreciatedPeriod);
    List<AssetDepreciation> findByDepreciationType(AssetDepreciationType depreciationType);
}
