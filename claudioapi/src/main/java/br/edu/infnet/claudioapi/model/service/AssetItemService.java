package br.edu.infnet.claudioapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.claudioapi.model.domain.AssetItem;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetInvalidException;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetNotFoundException;
import br.edu.infnet.claudioapi.model.repository.AssetItemRepository;
import jakarta.transaction.Transactional;

@Service
public class AssetItemService implements CrudService<AssetItem, Integer> {

	private final AssetItemRepository assetItemRepository;
	
	public AssetItemService(AssetItemRepository assetItemRepository) {
		this.assetItemRepository = assetItemRepository;
	}
	
	private void validate(AssetItem assetitem) {
		if(assetitem == null) {
			throw new IllegalArgumentException("O número do Patrimonio nao pode ser nulo!");
		}
		
		if(assetitem.getAcquiredTangibleAsset() == null || assetitem.getAcquiredTangibleAsset().trim().isEmpty()) {
			throw new AssetInvalidException("A inclusao do numnero do Patrimonio é um item obrigatório!");
		}
	}
		
	@Override
	@Transactional
	public AssetItem include(AssetItem assetitem) {
		
		validate(assetitem);
		
		if(assetitem.getId() != null && assetitem.getId() > 0) {
			throw new IllegalArgumentException("Um novo número do Patrimonio nao pode ter um ID na inclusao!");
		}
		
		return assetItemRepository.save(assetitem);
	
	}

	@Override
	@Transactional
	public AssetItem change(Integer id, AssetItem assetitem) {
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteracao nao pode nulo/zero!");
		}
		
		validate(assetitem);
		
		obtainPutId(id);
		
		assetitem.setId(id);

	    return assetItemRepository.save(assetitem);

	}

	@Override
	@Transactional
	public void delete(Integer id) {

		AssetItem assetitem = obtainPutId(id);
		
		assetItemRepository.delete(assetitem);
	
	}
	
	@Override
	public AssetItem obtainPutId(Integer id) {
		
		if(id == null || id <= 0) {
			throw new IllegalArgumentException("O ID para a busca nao pode nulo/zero!");
		}		
				
		return assetItemRepository.findById(id).orElseThrow(() -> new AssetNotFoundException("O Equipamento com ID " + id + "nao foi econtrado!"));
		
	}
	@Override
	public List<AssetItem> obtainList() {
		return assetItemRepository.findAll();
	}

}