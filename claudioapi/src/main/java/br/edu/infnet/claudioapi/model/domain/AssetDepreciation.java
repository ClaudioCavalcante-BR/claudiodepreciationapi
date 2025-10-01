package br.edu.infnet.claudioapi.model.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class AssetDepreciation extends AssetRegistration {
	
	@NotNull(message = "Informar o ano de depreciacao do periodo.")
    @Size(min = 3, max = 60, message = "Nome da categoria deve ter entre 3 e 60 caracteres.")
	private String depreciationPeriod;  // Periodo em anos
	
	@NotNull(message = "Infromar a Taxa anual de depreciacäo.")
	private double depreciationRateYear; // Taxa (%)
	
	@NotNull(message = "O equipamento já foi depreciado?  sim/nao.")
	private boolean fullyDepreciated;  // Já depreciado?
	
	@NotNull(message = "Informar o valor da depreciacao acumulada é obrigatório.")
    private String valueDepreciatedPeriod; // exemplo "21222.00"    
    
	@NotNull(message = "O tipo de depreciacao é obrigatório.")
	@Enumerated(EnumType.STRING)
	private AssetDepreciationType depreciationType;
	    
	@Override
	public String toString() {
		return String.format(
				"AssetDepreciation{%s, depreciationPeriod=%s, depreciationRateYear=%.2f, fullyDepreciated=%s, valueDepreciatedPeriod=%s, depreciationType=%s}", 
				 super.toString(),depreciationPeriod, depreciationRateYear, fullyDepreciated ? "ativo" : "inativo", valueDepreciatedPeriod, depreciationType);
	} 
    
	@Override
	public String obtainVisa() {
		return "AssetDepreciation";
	}

	public String getDepreciationPeriod() {
		return depreciationPeriod;
	}

	public void setDepreciationPeriod(String depreciationPeriod) {
		this.depreciationPeriod = depreciationPeriod;
	}

	public double getDepreciationRateYear() {
		return depreciationRateYear;
	}

	public void setDepreciationRateYear(double depreciationRateYear) {
		this.depreciationRateYear = depreciationRateYear;
	}

	public boolean isFullyDepreciated() {
		return fullyDepreciated;
	}

	public void setFullyDepreciated(boolean fullyDepreciated) {
		this.fullyDepreciated = fullyDepreciated;
	}

	public String getValueDepreciatedPeriod() {
		return valueDepreciatedPeriod;
	}

	public void setValueDepreciatedPeriod(String valueDepreciatedPeriod) {
		this.valueDepreciatedPeriod = valueDepreciatedPeriod;
	}

	public AssetDepreciationType getDepreciationType() {
		return depreciationType;
	}

	public void setDepreciationType(AssetDepreciationType depreciationType) {
		this.depreciationType = depreciationType;
	}
}
