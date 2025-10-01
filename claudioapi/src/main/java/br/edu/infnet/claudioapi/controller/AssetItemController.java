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

import br.edu.infnet.claudioapi.model.domain.AssetItem;
import br.edu.infnet.claudioapi.model.service.AssetItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assetItemCategorys")
public class AssetItemController {
	
	private final AssetItemService assetItemService;
	
	public AssetItemController(AssetItemService assetItemService) {
		this.assetItemService = assetItemService;
	}
	
	@PostMapping
	public ResponseEntity<AssetItem> include(@Valid @RequestBody AssetItem assetitem) {
		AssetItem newassetItem = assetItemService.include(assetitem);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newassetItem);	
				
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AssetItem> change(@PathVariable Integer id, @RequestBody AssetItem assetitem) {
		AssetItem assetitemChanged = assetItemService.change(id, assetitem);
		
		return ResponseEntity.ok(assetitemChanged);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		assetItemService.delete(id);
		
		return ResponseEntity.noContent().build();
	}


	@GetMapping
	public ResponseEntity<List<AssetItem>> obtainList(){
		
		List<AssetItem> list =  assetItemService.obtainList();
		
		if(list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssetItem> obtainPutId(@PathVariable Integer id) {
		
		AssetItem assetitem = assetItemService.obtainPutId(id);
		
		return ResponseEntity.ok(assetitem);
	}
}
