package br.edu.infnet.claudioapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.claudioapi.model.domain.Address;
import br.edu.infnet.claudioapi.model.domain.AssetCategory;
import br.edu.infnet.claudioapi.model.service.AssetCategoryService;

@Order(1)
@Component
public class AssetCategoryLoader implements ApplicationRunner {
	
	
	private final AssetCategoryService assetCategoryService;
	
	public AssetCategoryLoader(AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		FileReader filea1 = new FileReader("asset.txt");
		BufferedReader vision1 = new BufferedReader(filea1);
		
		String linha = vision1.readLine();
		String[] campos = null;
		
		System.out.println("[AssetCategoryLoader] Iniciando carregamento de dados do ativo do arquivo...");
				
		while(linha != null) {
			
			campos = linha.split(";");
			
			Address address = new Address();
			address.setStreet(campos[9]);
			address.setNumber(campos[10]);
			address.setZipcode(campos[11]);
			address.setLocation(campos[12]);
			address.setNeighborhood(campos[13]);
			address.setState(campos[14]);
			address.setUf(campos[15]);
			address.setComplement(campos[16]);
			
			AssetCategory assetcategory = new AssetCategory();
			
			assetcategory.setAssetName(campos[0]);
			assetcategory.setAcquisitionYear(campos[1]);
			assetcategory.setAcquisitionValue(campos[2]);
			assetcategory.setAssetCode(campos[3]);
			
			assetcategory.setCategoryName(campos[4]);
			assetcategory.setCategoryCode(Integer.valueOf(campos[5]));
			assetcategory.setTaxRate(Double.valueOf(campos[6]));
			assetcategory.setActive(Boolean.valueOf(campos[7]));
			assetcategory.setDescription(campos[8]);
			
			assetcategory.setAddress(address);
			
			try {
				assetCategoryService.include(assetcategory);
				System.out.println("[Certo] O Equipamento " + assetcategory.getAssetName() + "  incluido com sucesso!");
				
			} catch (Exception e) {
				System.err.println(" [ERRO]Problema na inclusao do numero do CHASSI: " + assetcategory.getAssetName() + " : " +e.getMessage());
			}
			
			linha = vision1.readLine();
		}
		
		System.out.println("[AssetLoader] Carregamento concluido.");
	
		List<AssetCategory> assetcategorys = assetCategoryService.obtainList();
		System.out.println("----- Ativos Carregados ------");
		assetcategorys.forEach(System.out::println);
		System.out.println("--------------------------------");
		
		vision1.close();
	}
}
