package br.edu.infnet.claudiodepreciationapi.model.domain;

import java.math.BigDecimal;


public class AssetItem {
	
	
	private AssetRegistration assetRegistration; // Depreciacao do ativo
	private int quantity; //quantidade de ativos a depreciar
	
	public BigDecimal calculateSubtotalDepreciationValue(){	
		
		if (quantity < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        
        if (quantity == 0) {
            return BigDecimal.ZERO;
        }
        
        if (assetRegistration == null) {
            return BigDecimal.ZERO;
        }
		        
		if(quantity <= 0) {
			return BigDecimal.ZERO;
		}
		
		
		
		BigDecimal acquisitionValue = assetRegistration.getAcquisitionValue(); 
		if(acquisitionValue == null) {
			return BigDecimal.ZERO;
		}
		
		return acquisitionValue
				.multiply(new BigDecimal(quantity));
				//.setScale(2, RoundingMode.HALF_UP); // Para arrendodamento em duas casas decimais, visao de futuro 
		
	}
	
	public AssetRegistration getAssetRegistration() {
		return assetRegistration;
	}

	public void setAssetRegistration(AssetRegistration assetRegistration) {
		this.assetRegistration = assetRegistration;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
