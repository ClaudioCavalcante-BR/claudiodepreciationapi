package br.edu.infnet.claudioapi.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.claudioapi.model.domain.AssetItem;

@Repository
public interface AssetItemRepository extends JpaRepository<AssetItem, Integer>{
	
	List<AssetItem> findByUnitCostGreaterThan(BigDecimal unitCost);
	List<AssetItem> findByacquiredTangibleAssetContaining(String acquiredTangibleAsset);
	
}