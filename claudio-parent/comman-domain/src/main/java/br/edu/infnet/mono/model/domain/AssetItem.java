package br.edu.infnet.mono.model.domain;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_ativo")
public class AssetItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;				

    private String acquiredTangibleAsset;

    private String groupHaritageName;
	
    @NotNull(message = "O custo unitário é obrigatório.")
    @DecimalMin(value = "0.01", message = "O custo unitário deve ser maior que zero.")
    @Digits(integer = 20, fraction = 2, message = "O valor do ativo deve ter no maximo 20 digitos inteiros e 2 decimais ")
	private BigDecimal unitCost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetcategory_id", nullable = false)
    private AssetCategory assetCategory;
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGroupHaritageName() {
		return groupHaritageName;
	}

	public void setGroupHaritageName(String groupHaritageName) {
		this.groupHaritageName = groupHaritageName;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getAcquiredTangibleAsset() {
		return acquiredTangibleAsset;
	}

	public void setAcquiredTangibleAsset(String acquiredTangibleAsset) {
		this.acquiredTangibleAsset = acquiredTangibleAsset;
	}

	public AssetCategory getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}
}
