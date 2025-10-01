package br.edu.infnet.claudiodepreciationapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.claudiodepreciationapi.model.domain.AssetItem;
import br.edu.infnet.claudiodepreciationapi.model.domain.AssetRegistration;
import br.edu.infnet.claudiodepreciationapi.model.domain.DepreciationExecution;
import br.edu.infnet.claudiodepreciationapi.model.domain.RegistrationType;
import br.edu.infnet.claudiodepreciationapi.model.service.DepreciationExecutionService;

class DepreciationServiceTest {
	
	private DepreciationExecutionService depreciationExecutionService;
	
	@BeforeEach
	void setUp() {
		depreciationExecutionService = new DepreciationExecutionService();
		
	}
	
	// Metodos auxiliares privados criado para evitar repeticao de codigo 
    private AssetItem item(String code, String name, String value, int quantity) {
        AssetRegistration ar = new AssetRegistration(code, name, new BigDecimal(value), RegistrationType.ACQUISITION);
        AssetItem it = new AssetItem();
        it.setAssetRegistration(ar);
        it.setQuantity(quantity);
        return it;
    }

    private DepreciationExecution execVazio() {
        DepreciationExecution de = new DepreciationExecution();
        de.setResponsibleUser("tester");
        de.setItems(Collections.emptyList());
        return de;
    }

    private DepreciationExecution execComItens(AssetItem... itens) {
        DepreciationExecution de = new DepreciationExecution();
        de.setResponsibleUser("tester");
        de.setItems(Arrays.asList(itens));
        return de;
    }
	
    @Test
    @DisplayName("calculateDepreciationAssetTotal: retorna ZERO para execução nula e para lista vazia")
    void deveRetornarZero_paraExecucaoNulaOuListaVazia() {
        // Dado & Quando
        BigDecimal totalNulo = depreciationExecutionService.calculateDepreciationAssetTotal(null);
        BigDecimal totalVazio = depreciationExecutionService.calculateDepreciationAssetTotal(execVazio());

        // Então
        assertEquals(BigDecimal.ZERO, totalNulo, "Execução nula → total ZERO");
        assertEquals(BigDecimal.ZERO, totalVazio, "Sem itens → total ZERO");
    }

    @Test
    @DisplayName("calculateDepreciationAssetTotal: soma correta de múltiplos AssetItem válidos")
    void deveSomarCorretamenteMultiplosItens() {
        
    	// Dado: (100.00 * 2) + (50.50 * 3) = 200.00 + 151.50 = 351.50
        AssetItem i1 = item("11112222-AA", "Caminhão", "100.00", 2);
        AssetItem i2 = item("33334444-BB", "Empilhadeira", "50.50", 3);
        DepreciationExecution de = execComItens(i1, i2);

        // Quando
        BigDecimal total = depreciationExecutionService.calculateDepreciationAssetTotal(de);

        // Então
        assertEquals(new BigDecimal("351.50"), total);
    }

    @Test
    @DisplayName("aplicarExtraordinaryAdjustments: valida % nulo/negativo/>100% e aplica corretamente quando válido")
    void deveValidarEAjustarExtraordinaryAdjustments() {
        
    	// Dado: total = 100.00
        AssetItem i1 = item("55556666-CC", "Carreta", "100.00", 1);
        DepreciationExecution de = execComItens(i1);

        //Quando & Entao: percentuais inválidos
        assertThrows(IllegalArgumentException.class,
                () -> depreciationExecutionService.aplicarExtraordinaryAdjustments(de, null));
        assertThrows(IllegalArgumentException.class,
                () -> depreciationExecutionService.aplicarExtraordinaryAdjustments(de, new BigDecimal("-0.10")));
        assertThrows(IllegalArgumentException.class,
                () -> depreciationExecutionService.aplicarExtraordinaryAdjustments(de, new BigDecimal("1.10")));

        // Quando & Entao:percentual válido: 10% → 100.00 - 10% = 90.00
        BigDecimal ajustado = depreciationExecutionService.aplicarExtraordinaryAdjustments(de, new BigDecimal("0.10"));
        assertEquals(new BigDecimal("90.00"), ajustado);
    }	
    @Test
    @DisplayName("validateDepreciationExecution: deve retornar false para execução inválida (null, sem responsável ou sem itens)")
    void validateDeveRetornarFalse_quandoExecucaoInvalida() {
        DepreciationExecutionService s = new DepreciationExecutionService();

        // Dado:null
        assertEquals(false, s.validateDepreciationExecution(null));

        // Quando: sem responsável
        DepreciationExecution de1 = new DepreciationExecution();
        de1.setResponsibleUser(null);
        de1.setItems(Collections.emptyList());
        assertEquals(false, s.validateDepreciationExecution(de1));

        // Entao: com responsável mas sem itens
        DepreciationExecution de2 = new DepreciationExecution();
        de2.setResponsibleUser("tester");
        de2.setItems(Collections.emptyList());
        assertEquals(false, s.validateDepreciationExecution(de2));
    }

    @Test
    @DisplayName("validateDepreciationExecution: deve retornar true quando houver responsável e ativos")
    void validateDeveRetornarTrue_quandoExecucaoValida() {
        DepreciationExecutionService s = new DepreciationExecutionService();
        //Dado:
        AssetRegistration ar = new AssetRegistration("11112222-AA","Caminhão", new BigDecimal("10.00"), RegistrationType.ACQUISITION);
        AssetItem it = new AssetItem();
        it.setAssetRegistration(ar);
        it.setQuantity(1);
        
        //Quando:
        DepreciationExecution de = new DepreciationExecution();
        de.setResponsibleUser("tester");
        de.setItems(Arrays.asList(it));
        
        //Entao:
        assertEquals(true, s.validateDepreciationExecution(de));
    }
}
