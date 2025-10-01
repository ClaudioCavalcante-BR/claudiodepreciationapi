package br.edu.infnet.claudiodepreciationapi.model.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import br.edu.infnet.claudiodepreciationapi.model.domain.AssetItem;
import br.edu.infnet.claudiodepreciationapi.model.domain.DepreciationExecution;

public class DepreciationExecutionService {
	
	/**
	 * 
     * RFAAAA.Aa: Calcula o valor total de um Ativo(os) Depreciado (os).
     * 
     * Retorna BigDecimal.ZERO se o DepreciationExecute for nulo ou não tiver ativo.
     * 
     */	
	
	public BigDecimal calculateDepreciationAssetTotal(DepreciationExecution depreciationExecution) {
		
		if (Objects.isNull(depreciationExecution)) {
			return BigDecimal.ZERO;
		}
		
		if(Objects.isNull(depreciationExecution.getItems()) || depreciationExecution.getItems().isEmpty()){
			return BigDecimal.ZERO;
			
		}
		
		BigDecimal valueTotal = BigDecimal.ZERO;
		for (AssetItem items : depreciationExecution.getItems()) {
			valueTotal = valueTotal.add(items.calculateSubtotalDepreciationValue());
		}
		
		return valueTotal;
		
	}
	
	/**
	 * 
	 * RFAAAA.Ab: Aplica um ajuste extraordinário sobre o valor total da DepreciationExecution.
	 * 
	 * O ajuste é aplicado como percentual (ex: 0.10 = 10%).
	 * 
	 * Lança IllegalArgumentException para ajustes extraordinários inválidos.
	 * 
	 */
	
	public BigDecimal aplicarExtraordinaryAdjustments(DepreciationExecution depreciationExecution, BigDecimal extraordinaryAdjustments) {
        BigDecimal valueTotal = calculateDepreciationAssetTotal(depreciationExecution);

        if (Objects.isNull(extraordinaryAdjustments)) {
            throw new IllegalArgumentException("O ajuste extraordinario não pode ser nulo.");
        }
        if (extraordinaryAdjustments.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O ajuste extraordinario não pode ser negativo.");
        }
        if (extraordinaryAdjustments.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("O ajuste extraordinario não pode ser maior que 100%.");
        }

        BigDecimal valueAjustments = valueTotal.multiply(extraordinaryAdjustments);
        return valueTotal.subtract(valueAjustments).setScale(2, RoundingMode.HALF_UP);
    }

	/**
	 * 
     * RFAAAA.Ac: Valida a integridade básica de uma DepreciationExecution.
     * 
     * Retorna true se a depreciationExecution atende aos critérios mínimos de validade.
     * 
     */
	
	public boolean validateDepreciationExecution(DepreciationExecution depreciationExecution) {
		
		if (Objects.isNull(depreciationExecution)) {
			return false;
		}
		
		if (Objects.isNull(depreciationExecution.getResponsibleUser()) || depreciationExecution.getResponsibleUser().isEmpty()) {
			return false;
		}
		
		if (Objects.isNull(depreciationExecution.getItems()) || depreciationExecution.getItems().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	
}
