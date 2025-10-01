package br.edu.infnet.claudioapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.claudioapi.model.domain.AssetDepreciation;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetInvalidException;
import br.edu.infnet.claudioapi.model.repository.AssetDepreciationRepository;
import jakarta.transaction.Transactional;

@Service
public class AssetDepreciationService implements CrudService<AssetDepreciation, Integer>{

	
	private final AssetDepreciationRepository assetDepreciationRepository;
	
	public AssetDepreciationService(AssetDepreciationRepository assetDepreciationRepository) {
		this.assetDepreciationRepository = assetDepreciationRepository;
	
	}
	
	private void validate(AssetDepreciation assetdepreciation) {
			if(assetdepreciation == null) {
				throw new IllegalArgumentException("O periodo nao pode ser nulo!");
			}
			
			if(assetdepreciation.getDepreciationPeriod() == null ||assetdepreciation.getDepreciationPeriod().trim().isEmpty()) {
				throw new AssetInvalidException("A inclusao do periodo em anos ë um item obrigatório!");
			}
		
	}

	@Override
	@Transactional
	public AssetDepreciation include(AssetDepreciation assetdepreciation) {
		validate(assetdepreciation);
		
		if(assetdepreciation.getId() != null && assetdepreciation.getId() > 0) {
			throw new IllegalArgumentException("Um novo ano repetido nao é para ser incluido!");
		}
		
		return assetDepreciationRepository.save(assetdepreciation);
	
	}

	@Override
	@Transactional
	public AssetDepreciation change(Integer id, AssetDepreciation assetdepreciation) {
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteracao nao pode nulo/zero!");
		}
		
		validate(assetdepreciation);
		
		obtainPutId(id);
		
		assetdepreciation.setId(id);

	    return assetDepreciationRepository.save(assetdepreciation);
	}

	@Override
	public AssetDepreciation obtainPutId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		AssetDepreciation assetdepreciation = obtainPutId(id);
		
		assetDepreciationRepository.delete(assetdepreciation);
		
	}

	@Override
	public List<AssetDepreciation> obtainList() {
		return assetDepreciationRepository.findAll();
	}
	
}
