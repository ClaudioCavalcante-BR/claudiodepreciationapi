package br.edu.infnet.claudioapi.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.infnet.claudioapi.model.clients.TruckFeignClient;
import br.edu.infnet.claudioapi.model.dto.TruckQueryResult;
import feign.FeignException;


@Service
public class TruckGatewayService {
	
	private final TruckFeignClient truckFeignClient;

    public TruckGatewayService(TruckFeignClient truckFeignClient) {
        this.truckFeignClient = truckFeignClient;
    }

    //public TruckQueryResult consultar(Integer marcaId, Integer modeloId, String anoCodigo) {
        //return truckFeignClient.obterConsultarTruck(marcaId, modeloId, anoCodigo);
        
        
    //}
    public TruckQueryResult consultar(Integer marcaId, Integer modeloId, String anoCodigo) {
        try {
            TruckQueryResult result = truckFeignClient.consultar(marcaId, modeloId, anoCodigo);
            if (result != null) {
                result.applyDefaults(); // se quiser manter aqui
            }
            return result;
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Combinação não encontrada no provedor (marca/modelo/ano)"
                );
            }
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Falha ao consultar serviço externo"
            );
        }
    }
}
