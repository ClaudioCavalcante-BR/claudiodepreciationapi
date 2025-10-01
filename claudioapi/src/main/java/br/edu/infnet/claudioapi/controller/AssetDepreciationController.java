package br.edu.infnet.claudioapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.claudioapi.model.domain.AssetDepreciation;
import br.edu.infnet.claudioapi.model.service.AssetDepreciationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assetDepreciations")
public class AssetDepreciationController {
	
private final AssetDepreciationService assetDepreciationService;
	
	public AssetDepreciationController(AssetDepreciationService assetDepreciationService) {
		this.assetDepreciationService = assetDepreciationService;
	}
	
	@PostMapping
	public ResponseEntity<AssetDepreciation> include(@Valid @RequestBody AssetDepreciation assetdepreciation) {
		AssetDepreciation newassetDepreciation = assetDepreciationService.include(assetdepreciation);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newassetDepreciation);	
				
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AssetDepreciation> change(@PathVariable Integer id, @RequestBody AssetDepreciation assetdepreciation) {
		AssetDepreciation assetdepreciationChanged = assetDepreciationService.change(id, assetdepreciation);
		
		return ResponseEntity.ok(assetdepreciationChanged);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		assetDepreciationService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<AssetDepreciation>> obtainList(){
		
		List<AssetDepreciation> list =  assetDepreciationService.obtainList();
		
		if(list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssetDepreciation> obtainPutId(@PathVariable Integer id) {
		
		AssetDepreciation assetdepreciation = assetDepreciationService.obtainPutId(id);
		
		return ResponseEntity.ok(assetdepreciation);
	}
}
