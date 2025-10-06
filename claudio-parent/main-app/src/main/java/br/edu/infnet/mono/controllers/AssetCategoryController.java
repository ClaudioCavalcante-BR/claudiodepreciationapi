package br.edu.infnet.mono.controllers;

import java.net.URI;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.infnet.mono.clients.ParallelumClient;
import br.edu.infnet.mono.clients.ParallelumClient.Brand;
import br.edu.infnet.mono.clients.ParallelumClient.ModelsResponse;
import br.edu.infnet.mono.clients.ParallelumClient.Year;
import br.edu.infnet.mono.clients.ParallelumClient.VehicleDetail;


import br.edu.infnet.mono.model.domain.AssetCategory;
import br.edu.infnet.mono.model.service.AssetCategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asset-categories")
public class AssetCategoryController {
	
	 private final AssetCategoryService assetCategoryService;
	 private final ParallelumClient parallelumClient;

	    public AssetCategoryController(AssetCategoryService assetCategoryService, ParallelumClient parallelumClient) {
	        this.assetCategoryService = assetCategoryService;
	        this.parallelumClient = parallelumClient;
	    }

	    // CREATE (201 + Location)
	    @PostMapping
	    public ResponseEntity<AssetCategory> incluir(@Valid @RequestBody AssetCategory nova) {
	        AssetCategory salva = assetCategoryService.incluir(nova);

	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(salva.getId())
	                .toUri();

	        return ResponseEntity.created(location).body(salva);
	    }

	    // READ - LIST
	    @GetMapping
	    public List<AssetCategory> listar() {
	        return assetCategoryService.obterLista();
	    }

	    // READ - ONE
	    @GetMapping("/{id}")
	    public AssetCategory obter(@PathVariable Long id) {
	        return assetCategoryService.obterPorId(id);
	    }

	    // UPDATE (PUT)
	    @PutMapping("/{id}")
	    public AssetCategory alterar(@PathVariable Long id, @Valid @RequestBody AssetCategory atualizada) {
	        return assetCategoryService.alterar(id, atualizada);
	    }

	    // DELETE (204 No Content)
	    @DeleteMapping("/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void excluir(@PathVariable Long id) {
	        assetCategoryService.excluir(id);
	    }

	    // PATCH - DESATIVAR (204)
	    @PatchMapping("/{id}/desativar")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void desativar(@PathVariable Long id) {
	        assetCategoryService.desativar(id);
	    }

	    // PATCH - ATIVAR (204)
	    @PatchMapping("/{id}/ativar")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void ativar(@PathVariable Long id) {
	        assetCategoryService.ativar(id);
	    }  
	    
	    
	    @GetMapping(value = "/{id}", params = {"marcaId","modeloId","ano"})
	    public AssetCategory obterComFipe(@PathVariable Long id,
	                               @org.springframework.web.bind.annotation.RequestParam(required = false) Integer marcaId,
	                               @org.springframework.web.bind.annotation.RequestParam(required = false) Integer modeloId,
	                               @org.springframework.web.bind.annotation.RequestParam(required = false) Integer ano) {
	    	AssetCategory ac = assetCategoryService.obterPorId(id);

	        if (marcaId != null && modeloId != null && ano != null) {
	            String anoCodigo = ano + "-3"; // diesel
	            ParallelumClient.VehicleDetail det =
	                    parallelumClient.obterDetalheAno(marcaId, modeloId, anoCodigo);
	            if (det != null && det.getCodigoFipe() != null) {
	                ac.setFipeCode(det.getCodigoFipe());
	            }
	        }
	        return ac;
	    }

	    
	    // GET /api/asset-categories/fipe/marcas
	    @GetMapping("/fipe/marcas")
	    public List<Brand> fipeListarMarcas() {
	        return parallelumClient.listarMarcas();
	    }

	    // GET /api/asset-categories/fipe/marcas/{marcaId}/modelos
	    @GetMapping("/fipe/marcas/{marcaId}/modelos")
	    public ModelsResponse fipeListarModelos(@PathVariable Integer marcaId) {
	        return parallelumClient.listarModelos(marcaId);
	    }

	    // GET /api/asset-categories/fipe/marcas/{marcaId}/modelos/{modeloId}/anos
	    @GetMapping("/fipe/marcas/{marcaId}/modelos/{modeloId}/anos")
	    public List<Year> fipeListarAnos(@PathVariable Integer marcaId,
	                                     @PathVariable Integer modeloId) {
	        return parallelumClient.listarAnos(marcaId, modeloId);
	    }

	    // GET /api/asset-categories/fipe/marcas/{marcaId}/modelos/{modeloId}/anos/{anoCodigo}
	    @GetMapping("/fipe/marcas/{marcaId}/modelos/{modeloId}/anos/{anoCodigo}")
	    public VehicleDetail fipeDetalhe(@PathVariable Integer marcaId,
	                                     @PathVariable Integer modeloId,
	                                     @PathVariable String anoCodigo) {
	        return parallelumClient.obterDetalheAno(marcaId, modeloId, anoCodigo);
	    }
	    
	 // Conveniência: detalhe por ANO sempre como diesel ("-3")
	    @GetMapping("/fipe/marcas/{marcaId}/modelos/{modeloId}/ano/{ano}")
	    public VehicleDetail fipeDetalheDiesel(@PathVariable Integer marcaId,
	                                           @PathVariable Integer modeloId,
	                                           @PathVariable int ano) {
	        return parallelumClient.obterDetalheAno(marcaId, modeloId, ano + "-3");
	    }
}