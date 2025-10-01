package br.edu.infnet.claudiodepreciationapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.claudiodepreciationapi.model.domain.TruckQueryResult;
import br.edu.infnet.claudiodepreciationapi.model.service.TruckFipeService;


@RestController
@RequestMapping("/fipe/trucks")
public class TruckFipeController {
	
	private final TruckFipeService truckFipeService;
	
	public TruckFipeController(TruckFipeService truckFipeService) {
		this.truckFipeService = truckFipeService;
	}
	
	@GetMapping("/{marcaId}/{modeloId}/{anoCodigo}")
    public ResponseEntity<TruckQueryResult> obterDadosMarcaModeloAno (
    		@PathVariable Integer marcaId,
            @PathVariable Integer modeloId,
            @PathVariable String anoCodigo) {
		
		TruckQueryResult result = truckFipeService.consultarPorMarcaModeloAno(marcaId, modeloId, anoCodigo);
		
        return ResponseEntity.ok(result);
    }

}
