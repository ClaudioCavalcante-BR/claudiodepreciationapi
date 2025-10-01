package br.edu.infnet.claudioapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.claudioapi.model.domain.AssetCategory;
import br.edu.infnet.claudioapi.model.domain.AssetItem;
import br.edu.infnet.claudioapi.model.domain.AssetItemType;
import br.edu.infnet.claudioapi.model.domain.exceptions.AssetNotFoundException;
import br.edu.infnet.claudioapi.model.service.AssetCategoryService;
import br.edu.infnet.claudioapi.model.service.AssetItemService;

@Order(2)
@Component
public class AssetItemLoader implements ApplicationRunner {
	
	private final AssetItemService assetItemService;
	private final AssetCategoryService assetCategoryService;
	
	public AssetItemLoader(AssetItemService assetItemService, AssetCategoryService assetCategoryService) {
		this.assetItemService = assetItemService;
		this.assetCategoryService = assetCategoryService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		FileReader filea2 = new FileReader("assetitems.txt");
		BufferedReader vision2 = new BufferedReader(filea2);
		
		String linha = vision2.readLine();
		String[] campos = null;
		
		System.out.println("[AssetItemLoader] Iniciando carregamento de itens do ativo do arquivo...");
				
		while(linha != null) {
			
			campos = linha.split(";");
			
			String acquiredTangibleAsset = campos[0];
			String groupHaritageName = campos[1];
			AssetItemType itemType = AssetItemType.valueOf(campos[2]);
			BigDecimal unitCost = new BigDecimal(campos[3]);
			String assetCodeAssetCategory = campos[4];
			
			AssetCategory responsible = null;
			
			try {
				
				responsible = assetCategoryService.obtainPutCode(assetCodeAssetCategory);
				if(responsible == null) {
					System.err.println(" [ERRO]O Numero do Patrimonio : " + assetCodeAssetCategory + " näo encontrado para item : " + acquiredTangibleAsset);
					linha = vision2.readLine();
					continue;
				}
				
			} catch (AssetNotFoundException e) {
				linha = vision2.readLine();
				continue;
			}
			
			AssetItem assetitem = new AssetItem();
			assetitem.setAcquiredTangibleAsset(acquiredTangibleAsset);
			assetitem.setGroupHaritageName(groupHaritageName);
			assetitem.setItemType(itemType);
			assetitem.setUnitCost(unitCost);
			
			assetitem.setAssetCategory(responsible);
			
			try {
				assetItemService.include(assetitem);
				System.out.println("[Certo] O Numero do Patrimonio " + assetitem.getAcquiredTangibleAsset() + "incluido com sucesso");
				
			} catch (Exception e) {
				System.err.println(" [ERRO]Problema na inclusao do numero do Patrimonio: " + assetitem.getAcquiredTangibleAsset() + " : " +e.getMessage());
			}
			
			linha = vision2.readLine();
		}
		
		System.out.println("[AssetItemLoader] Carregamento concluido.");
	
		List<AssetItem> assetitems = assetItemService.obtainList();
		System.out.println("----- Ativos Carregados ------");
		assetitems.forEach(System.out::println);
		System.out.println("--------------------------------");
		
		vision2.close();
	}
}
