package br.edu.infnet.mono.model.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "asset_category")
public class AssetCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Código do ativo é obrigatório.")
	@Pattern(regexp = "^\\d{8}-[A-Z]{2}$", message = "Código do ativo inválido. Use o formato XXXXXXXX-XX (8 dígitos, hífen e 2 letras maiúsculas).")
	private String assetCode;       
	
	@NotBlank(message = "Informe o nome do ativo.")
    @Size(min = 3, max = 120, message = "Nome do ativo deve ter entre 3 e 120 caracteres.")
	private String assetName;

    @NotBlank(message = "Ano de aquisição é obrigatório.")
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$", 
    message = "Ano de aquisição inválido. Use AAAA a partir de 1900.")
    private String acquisitionYear;

    @NotBlank(message = "Informe o valor de aquisição.")
    @DecimalMin(value = "0.01", message = "Valor de aquisição deve ser maior que zero.")
	private String acquisitionValue;

	@NotNull(message = "Nome da Marca ë obrigatório.")
    @Size(min = 3, max = 60, message = "Nome da marca deve ter entre 3 e 60 caracteres.")//Marca
	private String marca;
	
	@NotNull(message = "Nome do modelo é obrigatório.")
    @Size(min = 3, max = 60, message = "Nome do modelo deve ter entre 3 e 60 caracteres.")//Modelo
	private String modelo;
		
	@NotBlank(message = "Ano do modeo é obrigatório.")
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$", 
    message = "Ano do modelo inválido. Use AAAA a partir de 1900.")// AnoModelo
	private String anoModelo;
	
	
    @Size(min = 3, max = 60, message = "Nome da categoria deve ter entre 3 e 60 caracteres.")
	private String categoryName;
	
    @NotNull(message = "Código da categoria é obrigatório.")
    @Min(value = 1, message = "Código da categoria deve ser positivo.")
    private int categoryCode;
    
    @NotNull(message = "Taxa é obrigatória.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Taxa deve ser maior 0%.")
    @DecimalMax(value = "100.0", inclusive = true, message = "Taxa deve ser menor 100%.")
    private double taxRate; 
    
    @NotNull(message = "Categoria deve estar ativa/inativa definida.")
    private boolean active; 
    
    @Size(max = 255, message = "Descrição da categoria não pode exceder 255 caracteres.")
    private String description;     
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
    private Address address;
    
    @jakarta.persistence.Transient
    @jakarta.persistence.Column(name = "fipe_code", length = 20, nullable = true)
    private String fipeCode;
    
    @OneToMany(mappedBy = "assetCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AssetItem> assetitems = new ArrayList<AssetItem>();
  
	@Override
	public String toString() {
		return String.format("AssetCategory{%s, categoryName=%s, categoryCode=%d, taxRate=%.2f, active=%s, description=%s, %s}", 
				 super.toString(),categoryName, categoryCode, taxRate, active ? "ativo" : "inativo", description, address);
	}	
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<AssetItem> getAssetitems() {
		return assetitems;
	}

	public void setAssetitems(List<AssetItem> assetitems) {
		this.assetitems = assetitems;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(String anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getAcquisitionYear() {
		return acquisitionYear;
	}


	public void setAcquisitionYear(String acquisitionYear) {
		this.acquisitionYear = acquisitionYear;
	}


	public String getAcquisitionValue() {
		return acquisitionValue;
	}


	public void setAcquisitionValue(String acquisitionValue) {
		this.acquisitionValue = acquisitionValue;
	}


	public String getFipeCode() {
		return fipeCode;
	}


	public void setFipeCode(String fipeCode) {
		this.fipeCode = fipeCode;
	}

}
