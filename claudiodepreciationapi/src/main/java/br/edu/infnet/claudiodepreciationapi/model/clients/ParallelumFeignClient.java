package br.edu.infnet.claudiodepreciationapi.model.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.infnet.claudiodepreciationapi.model.domain.ParallelumBrand;
import br.edu.infnet.claudiodepreciationapi.model.domain.ParallelumModelsResponse;
import br.edu.infnet.claudiodepreciationapi.model.domain.ParallelumTruckDetail;
import br.edu.infnet.claudiodepreciationapi.model.domain.ParallelumYear;


@FeignClient(name = "parallelum", url = "${api.parallelum.url}/fipe/api/v1/caminhoes")
public interface ParallelumFeignClient {
	
	
	// ******** Consultas no processo do projeto  ********
	
	@GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos/{anoCodigo}")
    ParallelumTruckDetail obterDetalhe(@PathVariable Integer marcaId,
                                       @PathVariable Integer modeloId,
                                       @PathVariable String anoCodigo);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<ParallelumYear> obterAnos(@PathVariable Integer marcaId,
                                   @PathVariable Integer modeloId);
    
    
    // ***** Consultas adicionais para identificar as marcas e modelos *******
    
    @GetMapping("/marcas")
    List<ParallelumBrand> obterMarcas();

    @GetMapping("/marcas/{marcaId}/modelos")
    ParallelumModelsResponse obterModelos(@PathVariable Integer marcaId);

}
