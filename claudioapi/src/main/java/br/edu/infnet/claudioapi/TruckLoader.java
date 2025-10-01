package br.edu.infnet.claudioapi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import br.edu.infnet.claudioapi.model.dto.TruckQueryResult;
import br.edu.infnet.claudioapi.model.service.TruckGatewayService;

@Component
public class TruckLoader implements ApplicationRunner {
	
	private final TruckGatewayService truckGatewayService;
	
	public TruckLoader(TruckGatewayService truckGatewayService) {
		this.truckGatewayService = truckGatewayService;
	}
	
	@Override
    public void run(ApplicationArguments args) {
        try {
            TruckQueryResult truckQueryResult = truckGatewayService.consultar(116, 10602, "2025-3");
            System.out.println("#API# " + truckQueryResult.getMarca()
                    + " " + truckQueryResult.getModelo()
                    + " " + truckQueryResult.getAnoModelo());
        } catch (Exception ex) {
            // NÃO propaga: evita derrubar o contexto na subida
            System.out.println("[TruckLoader] Aviso: integração indisponível no startup: " + ex.getMessage());
        }
    }

}
