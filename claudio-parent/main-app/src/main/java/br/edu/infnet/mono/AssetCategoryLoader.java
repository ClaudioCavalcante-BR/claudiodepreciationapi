package br.edu.infnet.mono;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.edu.infnet.mono.model.domain.Address;
import br.edu.infnet.mono.model.domain.AssetCategory;
import br.edu.infnet.mono.model.service.AssetCategoryService;

@Component
public class AssetCategoryLoader implements ApplicationRunner {

    private final AssetCategoryService assetCategoryService;


    public AssetCategoryLoader(AssetCategoryService assetCategoryService) {
        this.assetCategoryService = assetCategoryService;
     
       
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n=== [AssetCategoryLoader] START ===");

        ClassPathResource cpr = new ClassPathResource("asset.txt");
        if (!cpr.exists()) {
            System.out.println("[AssetCategoryLoader] 'asset.txt' NÃO encontrado em src/main/resources. Loader ignorado.");
            System.out.println("=== [AssetCategoryLoader] DONE (ok=0, erros=0) ===\n");
            return;
        } else {
            System.out.println("[AssetCategoryLoader] Lendo de classpath: asset.txt");
        }

        int ok = 0, erro = 0, linha = 0;
        
        List<String> linhasDoArquivo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(cpr.getInputStream(), StandardCharsets.UTF_8))) {
            String raw;
            while ((raw = br.readLine()) != null) {
                System.out.println(raw); 

                linha++;
                
                String rawTrim = raw.trim();
                if (!rawTrim.isEmpty() && !rawTrim.startsWith("#") && !rawTrim.startsWith("//")) {
                    linhasDoArquivo.add(raw); // mantém exatamente como no arquivo
                }
                         
                String line = raw.trim();
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    System.out.printf("[AssetCategoryLoader] linha %d: ignorada (vazia/comentário)%n", linha);
                    continue;
                }

                String[] t = line.split(";", -1);
                for (int i = 0; i < t.length; i++) t[i] = t[i] != null ? t[i].trim() : null;

                try {
                    // ---- mapeamento exato do seu asset.txt ----
                    String modeloTexto   = get(t, 0);
                    String marcaIdStr    = get(t, 1);
                    String modeloIdStr   = get(t, 2);
                    String anoModelo     = get(t, 3);
                    String anoRef        = get(t, 4);   
                    String precoStr      = get(t, 5);   
                    String assetCodeStr  = get(t, 6);   
                    String categoryName  = get(t, 7);
                    String taxRateStr    = get(t, 8);
                    String activeStr     = get(t, 9);
                    String description   = get(t,10);

                   
                    String street        = get(t,11);
                    String number        = get(t,12);
                    String zipcode       = get(t,13);
                    String location      = get(t,14);
                    String neighborhood  = get(t,15);
                    String state         = get(t,16);
                    String uf            = get(t,17);
                    String complement    = get(t,18);

                   
                    Integer brandId      = parseIntOrNull(marcaIdStr);
                    Integer modelId      = parseIntOrNull(modeloIdStr);
                    double taxRate       = parseDouble(taxRateStr);
                    boolean active       = parseBool(activeStr);
                    String marcaNome = brandName(brandId);
                    

                    // categoryCode interno derivado de (brandId, modelId) para manter unicidade
                    int categoryCode;
                    if (brandId != null && modelId != null) {
                        categoryCode = brandId * 100000 + modelId; // ex.: 109*100000 + 9674 = 10906674
                    } else {
                        categoryCode = Math.abs((String.valueOf(modeloTexto) + String.valueOf(anoModelo)).hashCode());
                    }

      
                    AssetCategory classCategory = new AssetCategory();
                    classCategory.setMarca(marcaNome);
                    classCategory.setModelo(modeloTexto);                 
                    classCategory.setAnoModelo(anoModelo);
                    classCategory.setCategoryName(categoryName);
                    classCategory.setCategoryCode(categoryCode);          
                    classCategory.setTaxRate(taxRate);
                    classCategory.setActive(active);
                    classCategory.setDescription(description);

                    
                    classCategory.setAssetName(modeloTexto);
                    classCategory.setAcquisitionYear(anoRef);             
                    classCategory.setAcquisitionValue(precoStr);          
                    classCategory.setAssetCode(assetCodeStr);

                    // endereço
                    Address addr = new Address();
                    addr.setStreet(street);
                    addr.setNumber(number);
                    addr.setZipcode(zipcode);
                    addr.setLocation(location);
                    addr.setNeighborhood(neighborhood);
                    addr.setState(state);
                    addr.setUf(uf);
                    addr.setComplement(complement);
                    classCategory.setAddress(addr);
                    
                    String fipeFromFile = (t.length > 19) ? get(t, 19) : null;

                    if (fipeFromFile != null && !fipeFromFile.isBlank()) {
                        classCategory.setFipeCode(fipeFromFile);
                    } 

                    AssetCategory salvo = assetCategoryService.incluir(classCategory);
                    ok++;
                    System.out.printf("[AssetCategoryLoader] OK linha %d -> id=%d, categoryCode=%d, assetCode=%s, name=%s%n",
                            linha, salvo.getId(), salvo.getCategoryCode(), salvo.getAssetCode(), salvo.getCategoryName());
                } catch (Exception e) {
                    erro++;
                    System.out.printf("[AssetCategoryLoader] ERRO linha %d: %s%n", linha, e.getMessage());
                    System.out.printf("[AssetCategoryLoader]  Conteúdo: %s%n", line);
                }
            }
        }

        System.out.println("\n===== [AssetCategoryLoader] RELAÇÕES DO asset.txt (BLOCO ÚNICO) =====");
        linhasDoArquivo.forEach(System.out::println);
        System.out.println("===== [AssetCategoryLoader] FIM DO BLOCO =====\n");
        
        System.out.printf("=== [AssetCategoryLoader] DONE (ok=%d, erros=%d) ===%n%n", ok, erro);
    }

    // ===== helpers locais =====
    private static String get(String[] t, int idx) {
        return (idx >= 0 && idx < t.length)
                ? (t[idx] == null || t[idx].isBlank() ? null : t[idx].trim())
                : null;
    }

    private static Integer parseIntOrNull(String s) {
        try { return (s == null || s.isBlank()) ? null : Integer.valueOf(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    private static double parseDouble(String s) {
        if (s == null) throw new IllegalArgumentException("taxRate ausente");
        return Double.parseDouble(s.replace(",", "."));
    }

    private static boolean parseBool(String s) {
        if (s == null) return false;
        String v = s.trim().toLowerCase();
        return v.equals("true") || v.equals("1") || v.equals("sim") || v.equals("ativo") || v.equals("yes");
    }
    
    private static final java.util.Map<Integer, String> BRAND_NAMES =
            java.util.Map.of(
                109, "MERCEDES-BENZ",
                116, "VOLVO"
            );

    private static String brandName(Integer brandId) {
        if (brandId == null) return "DESCONHECIDA";
        return BRAND_NAMES.getOrDefault(brandId, "DESCONHECIDA");
    }
}