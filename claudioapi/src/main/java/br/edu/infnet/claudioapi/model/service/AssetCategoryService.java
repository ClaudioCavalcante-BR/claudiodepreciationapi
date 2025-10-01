package br.edu.infnet.claudioapi.model.service;

import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import br.edu.infnet.claudioapi.model.domain.AssetCategory;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetInvalidException;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetNotFoundException;
import br.edu.infnet.claudioapi.model.repository.AssetCategoryRepository;

@Service
public class AssetCategoryService implements CrudService<AssetCategory, Integer> {
	
	private final AssetCategoryRepository assetRepository;
	
	public AssetCategoryService(AssetCategoryRepository assetRepository) {
		this.assetRepository = assetRepository;
	}
	
	private void validate(AssetCategory assetcategory) {
		if(assetcategory == null) {
			throw new IllegalArgumentException("O número do CHASSI nao pode ser nulo!");
		}
		
		if(assetcategory.getAssetCode() == null || assetcategory.getAssetCode().trim().isEmpty()) {
			throw new AssetInvalidException("A inclusao do CHASSI do equipamento é um item obrigatório!");
		}
	}
	
	@Override
	@Transactional	
	public AssetCategory include(AssetCategory assetcategory) {
	
		validate(assetcategory);
		if(assetcategory.getId() != null && assetcategory.getId() > 0) {
			throw new IllegalArgumentException("Um novo número do CHASSI nao pode ter um ID na inclusao!");
		}
		
		return assetRepository.save(assetcategory);
	}
	
	@Transactional
	public AssetCategory change(Integer id, AssetCategory assetcategory) {
		
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteracao nao pode nulo/zero!");
		}
		
		validate(assetcategory);
		obtainPutId(id);
		assetcategory.setId(id);
	    return assetRepository.save(assetcategory);
		
	}
		
	@Override
	@Transactional
	public void delete(Integer id) {

		AssetCategory assetcategory = obtainPutId(id);
		assetRepository.delete(assetcategory);
			
	}
	
	@Transactional
	public AssetCategory deactivate(Integer id) {
		
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para inativaca nao pode nulo/zero!");
		}
		
		 AssetCategory assetcategory = obtainPutId(id);
		 if(!assetcategory.isActive()) {
			 System.out.println("O Equipamento" + assetcategory.getAssetCode() + "já está inativo!");
			 return assetcategory;
		 }
		 
		 assetcategory.setActive(false);
		 return assetRepository.save(assetcategory);
		
	}
	
	@Override
	public List<AssetCategory> obtainList() {
			return assetRepository.findAll();
	}

	@Override
	public AssetCategory obtainPutId(Integer id) {
		if(id == null || id <= 0) {
			throw new IllegalArgumentException("O ID para a busca nao pode nulo/zero!");
		}		
				
		return assetRepository.findById(id).orElseThrow(() -> new AssetNotFoundException("O Equipamento com ID " + id + "nao foi econtrado!"));
	}

	public AssetCategory obtainPutCode(String assetCode) {
		
		return assetRepository.findByassetCode(assetCode).orElseThrow(() -> new AssetNotFoundException("O Numero do Patrimonio " + assetCode + "nao foi econtrado!"));
	}
}
