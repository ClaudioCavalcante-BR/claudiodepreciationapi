package br.edu.infnet.claudioapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.claudioapi.model.domain.AssetDepreciation;
import br.edu.infnet.claudioapi.model.domain.AssetDepreciationType;
import br.edu.infnet.claudioapi.model.service.AssetDepreciationService;

@Order(3)
@Component
public class AssetDepreciationLoader implements ApplicationRunner{
	
private final AssetDepreciationService assetDepreciationService;
	
	public AssetDepreciationLoader(AssetDepreciationService assetDepreciationService) {
		this.assetDepreciationService = assetDepreciationService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		FileReader filea3 = new FileReader("assetdepreciations.txt");
		BufferedReader vision3 = new BufferedReader(filea3);
		
		String linha = vision3.readLine();
		String[] campos = null;
		
		System.out.println("[AssetDepreciationLoader] Iniciando carregamento de dados do ativo do arquivo...");
				
		while(linha != null) {
			
			campos = linha.split(";");
			
			AssetDepreciation assetdepreciation = new AssetDepreciation();
			assetdepreciation.setDepreciationPeriod(campos[0]);
			assetdepreciation.setDepreciationRateYear(Double.valueOf(campos[1]));
			assetdepreciation.setFullyDepreciated(Boolean.valueOf(campos[2]));
			assetdepreciation.setValueDepreciatedPeriod(campos[3]);
			assetdepreciation.setDepreciationType(AssetDepreciationType.valueOf(campos[4]));
			assetdepreciation.setAssetCode(campos[5]);
			assetdepreciation.setAcquisitionValue(campos[6]);
			assetdepreciation.setAcquisitionYear(campos[7]);
			assetdepreciation.setAssetName(campos[8]);
			
			try {
				assetDepreciationService.include(assetdepreciation);
				System.out.println("[Visto] O Periodo de" + assetdepreciation.getDepreciationPeriod() + "  incluido com sucesso!");
				
			} catch (Exception e) {
				System.err.println(" [ERRO]Problema na inclusao do periodo: " + assetdepreciation.getDepreciationPeriod() + " : " +e.getMessage());
			}
			
			linha = vision3.readLine();
		}
		
		System.out.println("[AssetDepreciationLoader] Carregamento concluido.");
	
		List<AssetDepreciation> assetdepreciations = assetDepreciationService.obtainList();
		System.out.println("----- Depreciacao de Ativos Carregados ------");
		assetdepreciations.forEach(System.out::println);
		System.out.println("--------------------------------");
		
		vision3.close();	
	}
}
