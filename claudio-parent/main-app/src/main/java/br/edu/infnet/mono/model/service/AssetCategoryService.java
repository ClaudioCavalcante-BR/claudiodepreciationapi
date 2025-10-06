package br.edu.infnet.mono.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.infnet.mono.model.domain.AssetCategory;
import br.edu.infnet.mono.model.repository.AssetCategoryRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetCategoryService {
	
	 private final AssetCategoryRepository assetCategoryRepository;

	    public AssetCategoryService(AssetCategoryRepository assetCategoryRepository) {
	        this.assetCategoryRepository = assetCategoryRepository;
	    }

	    // CREATE
	    @Transactional
	    public AssetCategory incluir(AssetCategory assetNova) {
	        if (assetNova == null) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria obrigatória.");
	        }

	        // validações relativos a unicidade
	        if (assetCategoryRepository.existsByCategoryCode(assetNova.getCategoryCode())) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código da categoria já cadastrado.");
	        }
	        //if (assetNova.getCategoryName() != null
	               // && assetCategoryRepository.existsByCategoryNameIgnoreCase(assetNova.getCategoryName())) {
	            //throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome da categoria já cadastrado.");
	        //}

	        // persistencia
	        return assetCategoryRepository.save(assetNova);
	    }

	    //  Read - list
	    @Transactional(readOnly = true)
	    public List<AssetCategory> obterLista() {
	        return assetCategoryRepository.findAll();
	    }

	    // Read - one
	    @Transactional(readOnly = true)
	    public AssetCategory obterPorId(Long id) {
	        return assetCategoryRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));
	    }

	    //  UPDATE 
	    @Transactional
	    public AssetCategory alterar(Long id, AssetCategory assetAtualizada) {
	        AssetCategory assetExistente = obterPorId(id);

	        // checar a unicidade
	        if (assetAtualizada.getCategoryCode() != assetExistente.getCategoryCode()
	                && assetCategoryRepository.existsByCategoryCode(assetAtualizada.getCategoryCode())) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código da categoria já cadastrado.");
	        }

	        // se alterar nome, checa unicidade (case-insensitive)
	        //if (assetAtualizada.getCategoryName() != null
	              //  && !assetAtualizada.getCategoryName().equalsIgnoreCase(assetExistente.getCategoryName())
	               // && assetCategoryRepository.existsByCategoryNameIgnoreCase(assetAtualizada.getCategoryName())) {
	           // throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome da categoria já cadastrado.");
	        //}

	        // atualiza somente com campos que já tem na entidade
	        assetExistente.setMarca(assetAtualizada.getMarca());
	        assetExistente.setModelo(assetAtualizada.getModelo());
	        assetExistente.setAnoModelo(assetAtualizada.getAnoModelo());

	        assetExistente.setCategoryName(assetAtualizada.getCategoryName());
	        assetExistente.setCategoryCode(assetAtualizada.getCategoryCode());
	        assetExistente.setTaxRate(assetAtualizada.getTaxRate());
	        assetExistente.setActive(assetAtualizada.isActive());
	        assetExistente.setDescription(assetAtualizada.getDescription());

	        assetExistente.setAddress(assetAtualizada.getAddress());
	        assetExistente.setAssetitems(assetAtualizada.getAssetitems());

	        return assetCategoryRepository.save(assetExistente);
	    }

	    //  Delete
	    @Transactional
	    public void excluir(Long id) {
	        if (!assetCategoryRepository.existsById(id)) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada.");
	        }
	        assetCategoryRepository.deleteById(id);
	    }

	    // Desativa
	    @Transactional
	    public void desativar(Long id) {
	        AssetCategory category = obterPorId(id);
	        if (!category.isActive()) return;
	        category .setActive(false);
	        assetCategoryRepository.save(category);
	    }
	    
	    //Ativa
	    @Transactional
	    public void ativar(Long id) {
	        AssetCategory category = obterPorId(id);
	        if (category.isActive()) return;
	        category.setActive(true);
	        assetCategoryRepository.save(category);
	    }

}
