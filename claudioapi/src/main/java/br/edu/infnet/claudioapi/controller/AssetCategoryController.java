package br.edu.infnet.claudioapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.claudioapi.model.domain.AssetCategory;
import br.edu.infnet.claudioapi.model.service.AssetCategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assetCategorys")
public class AssetCategoryController {
	
	private final AssetCategoryService assetCategoryService;
	
	public AssetCategoryController(AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}
	
	@PostMapping
	public ResponseEntity<AssetCategory> include(@Valid @RequestBody AssetCategory assetcategory) {
		AssetCategory newassetCategory = assetCategoryService.include(assetcategory);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newassetCategory);	
				
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AssetCategory> change(@PathVariable Integer id, @RequestBody AssetCategory assetcategory) {
		AssetCategory assetcategoryChanged = assetCategoryService.change(id, assetcategory);
		
		return ResponseEntity.ok(assetcategoryChanged);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		assetCategoryService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{id}/deactivate")
	public ResponseEntity<AssetCategory> deactivate(@PathVariable Integer id) {
		AssetCategory assetcategory = assetCategoryService.deactivate(id);
		
		return ResponseEntity.ok(assetcategory);
	}

	@GetMapping
	public ResponseEntity<List<AssetCategory>> obtainList(){
		
		List<AssetCategory> list =  assetCategoryService.obtainList();
		
		if(list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssetCategory> obtainPutId(@PathVariable Integer id) {
		
		AssetCategory assetcategory = assetCategoryService.obtainPutId(id);
		
		return ResponseEntity.ok(assetcategory);
	}
}
