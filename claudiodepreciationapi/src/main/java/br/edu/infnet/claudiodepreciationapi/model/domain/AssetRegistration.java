package br.edu.infnet.claudiodepreciationapi.model.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

public class AssetRegistration {
	
	private String assetCode; // Codigo unico do ativo nao pode ter dupplicidade
	private String assetName; //Nome do ativo
	private BigDecimal acquisitionValue; // Valor de aquisicao do ativo
	private RegistrationType registrationType; // Tipo de Registro
	
	public AssetRegistration(String assetCode, String assetName, BigDecimal acquisitionValue,
			RegistrationType registrationType) {
		super();
		this.setAssetCode(assetCode);
		this.setAssetName(assetName);
		this.setAcquisitionValue(acquisitionValue);
		this.setRegistrationType(registrationType);
	}

	// regex ("\\d{8}-[A-Z]{2}") do assetCode (exemplo: 8 dígitos + hífen + 2 letras)
    private static final Pattern ASSET_CODE_PATTERN = Pattern.compile("\\d{8}-[A-Z]{2}");
    
    public AssetRegistration() {}

    public AssetRegistration(String assetCode) {
        this.assetCode = assetCode;
    }

    public boolean isAssetCodeValid() {
        return assetCode != null && ASSET_CODE_PATTERN.matcher(assetCode).matches();
    }

    public void validateRequired() {
        if (Objects.isNull(assetCode) || Objects.isNull(assetName)) {
            throw new IllegalStateException("AssetCode e AssetName são obrigatórios.");
        }
    }

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public BigDecimal getAcquisitionValue() {
		return acquisitionValue;
	}

	public void setAcquisitionValue(BigDecimal acquisitionValue) {
		this.acquisitionValue = acquisitionValue;
	}

	public RegistrationType getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(RegistrationType registrationType) {
		this.registrationType = registrationType;
	}

	public static Pattern getAssetCodePattern() {
		return ASSET_CODE_PATTERN;
	}
	
}
