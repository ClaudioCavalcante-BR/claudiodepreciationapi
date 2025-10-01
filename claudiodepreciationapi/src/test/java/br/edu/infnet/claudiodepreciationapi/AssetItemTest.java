package br.edu.infnet.claudiodepreciationapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.claudiodepreciationapi.model.domain.AssetItem;
import br.edu.infnet.claudiodepreciationapi.model.domain.AssetRegistration;
import br.edu.infnet.claudiodepreciationapi.model.domain.RegistrationType;

public class AssetItemTest {
	
	
	private AssetRegistration assetRegistration;
	
	@BeforeEach
	void setUp() {
		
		
	}
	
	@Test
	@DisplayName("Deve realizar o calculo do subtotal para o ativo valido.")
	void deveCalculateSubtotalDepreciationValue_quandoAssetItemValido(){
		
		// Dado: Um ativo de DepreciatioExecution com AssetRegistration e quantity validos
		
		String assetCode = "44445555-BB";
		String assetName = "MAX SPEED RODOVIARIA 2525";
		RegistrationType registrationType  = RegistrationType.ACQUISITION;
		BigDecimal acquisitionValue =  new BigDecimal("450000.00");
		int quantity = 1;
		
		assetRegistration = new AssetRegistration(assetCode, assetName, acquisitionValue, registrationType);
		
		AssetItem assetItem = new AssetItem();
		assetItem.setAssetRegistration(assetRegistration);
		assetItem.setQuantity(quantity);
		
		
		BigDecimal subTotalExpected = new BigDecimal("450000.00");
		
		// Quando:chamar o metodo calcularSubtotalDepreciationValue
		BigDecimal subTotalCalculate = assetItem.calculateSubtotalDepreciationValue();
		
		
		// Entao: o resultado do subTotal sera o valor esperado
		   // assertEquals testa um cara com outro e aparece um mensagem 
		assertEquals(subTotalExpected, subTotalCalculate, "O subtotal deve ser 450000.00 para os itens");
		
		
		assertEquals(assetCode, assetRegistration.getAssetCode(), "O Código utilizado na criacao do ativo deve ser 44445555-BB");
		assertEquals(assetName, assetRegistration.getAssetName(), "O nome utilizado na criacao do ativo deve ser MAX SPEED RODOVIARIA 2525");
		assertEquals(acquisitionValue, assetRegistration.getAcquisitionValue(), "O valor utilizado na criacao do ativo deve ser 450000.00");
		assertEquals(registrationType, assetRegistration.getRegistrationType(), "O tipo de registro utilizado na criacao do ativo deve ACQUISITION");
		assertEquals(quantity, assetItem.getQuantity(), "A quantidade utilizada na criacao do ativo deve ser 1");
		
		assertNotNull(assetItem.getAssetRegistration());
	}
	
	
	
	@Test
	@DisplayName("Deve retornar zero quando a quantidade for zerada.")
	void deveRetornarDepreciationZero_quandoQuantityForZero(){
		
		// Dado: Um AssetItem com AssetRegistration e quantity válidos
		
	    
	    AssetItem assetItem = new AssetItem();
	    assetItem.setQuantity(0);
	    
	    BigDecimal subTotalExpected = BigDecimal.ZERO;
		
		// Quando:chamar o metodo calcularSubtotalDepreciationValue
		BigDecimal subTotalCalculate = assetItem.calculateSubtotalDepreciationValue();
		
		
		// Entao: o resultado do subTotal sera o valor esperado
		   
		assertEquals(subTotalExpected, subTotalCalculate, "O subtotal deve ser zero quando a quantidade for zero");	
	}
	
	@Test
	@DisplayName("Deve lançar IllegalArgumentException quando a quantidade for negativa.")
	void deveLancarExcecao_quandoQuantityForNegativa() {
	    
	    // Dado: Um AssetItem com AssetRegistration  e quantity negativo
    
	    AssetItem assetItem = new AssetItem();
	    assetItem.setQuantity(-1);

	    
	    
	    // Quando e Então: chamar o método deve lançar IllegalArgumentException
	    
	 	assertThrows(IllegalArgumentException.class,assetItem::calculateSubtotalDepreciationValue,
	 	        "Quantidade negativa deve lançar IllegalArgumentException");
	}

	@Test
	@DisplayName("Deve retornar zero quando o registro do ativo (AssetRegistration) for nulo.")
	void deveRetornarZero_quandoAssetRegistrationForNulo() {
	    
	    // Dado: Um AssetItem com AssetRegistration e quantity validos
    
		
	    AssetItem assetItem = new AssetItem();
	    assetItem.setAssetRegistration(null);
	    assetItem.setQuantity(1);

	    BigDecimal subTotalExpected = BigDecimal.ZERO;
	    
	    // Quando:chamar o metodo calcularSubtotalDepreciationValue
	 	BigDecimal subTotalCalculate = assetItem.calculateSubtotalDepreciationValue();
	    
	    
	    //Então: chamar o método deve lançar IllegalArgumentException
	 	assertEquals(subTotalExpected, subTotalCalculate, "O subtotal deve ser zero quando o registro do ativo for nulo");
	}
	
	@Test
	@DisplayName("Deve retornar zero quando o valor do ativo (AssetRegistration) estiver nulo.")
	void deveRetornarZero_quandoValorAssetRegistrationForNulo() {
	    
	    // Dado: Um AssetItem com AssetRegistration e quantity validos
		assetRegistration = new AssetRegistration("44445555-BB", "MAX SPEED RODOVIARIA 2525", null,RegistrationType.ACQUISITION);
		
	    AssetItem assetItem = new AssetItem();
	    assetItem.setAssetRegistration(assetRegistration);
	    assetItem.setQuantity(1);

	    BigDecimal subTotalExpected = BigDecimal.ZERO;
	    
	    // Quando:chamar o metodo calcularSubtotalDepreciationValue
	 	BigDecimal subTotalCalculate = assetItem.calculateSubtotalDepreciationValue();
	    
	    
	    //Então: chamar o método deve lançar IllegalArgumentException
	 	assertEquals(subTotalExpected, subTotalCalculate, "O subtotal deve ser zero quando o valor do ativo estiver nulo");
	}
	
	@Test
	@DisplayName("Deve calcular corretamente com quantity muito grande (Integer.MAX_VALUE).")
	void deveCalcularComQuantityMuitoGrande() {
	    // Dado: acquisitionValue = 1.00 e quantity = Integer.MAX_VALUE
	    AssetRegistration ar = new AssetRegistration(
	        "44445555-BB", "REBOQUE", new BigDecimal("1.00"), RegistrationType.ACQUISITION
	    );
	    AssetItem item = new AssetItem();
	    item.setAssetRegistration(ar);
	    item.setQuantity(Integer.MAX_VALUE);

	    // Quando: calcular subtotal
	    BigDecimal resultado = item.calculateSubtotalDepreciationValue();

	    // Então: 1.00 * 2147483647 = 2147483647.00
	    BigDecimal esperado = new BigDecimal(Integer.toString(Integer.MAX_VALUE)).setScale(2);
	    assertEquals(esperado, resultado, "O subtotal deve ser 2147483647.00");
	}
	
	@Test
	@DisplayName("Deve manter o valor com três casas quando não há arredondamento explícito.")
	void deveManterTresCasasSemArredondamentoExplicito() {
	    
		// Dado: acquisitionValue = 1000.555 e quantity = 1
	    AssetRegistration ar = new AssetRegistration(
	        "44445555-BB", "CARRETA", new BigDecimal("1000.555"), RegistrationType.ACQUISITION
	    );
	    AssetItem item = new AssetItem();
	    item.setAssetRegistration(ar);
	    item.setQuantity(1);

	    // Quando: calcular subtotal
	    BigDecimal resultado = item.calculateSubtotalDepreciationValue();

	    // Então: sem setScale/rounding no método, o retorno é 1000.555
	    assertEquals(new BigDecimal("1000.555"), resultado,
	        "Sem arredondamento explícito no método, o subtotal permanece 1000.555");
	}

}
