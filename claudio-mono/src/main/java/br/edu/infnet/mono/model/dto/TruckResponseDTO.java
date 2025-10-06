package br.edu.infnet.mono.model.dto;

import br.edu.infnet.mono.model.domain.AssetCategory;

public class TruckResponseDTO {

    private String marca;
    private String modelo;
    private String anoModelo;
    private String categoryName;
    private int    categoryCode;
    private double taxRate;
    private boolean active;
    private String description;

    public TruckResponseDTO() { }

    // Construtor ResponseDTO a partir do domínio)
    public TruckResponseDTO(AssetCategory cat) {
        if (cat != null) {
            this.marca = cat.getMarca();
            this.modelo = cat.getModelo();
            this.anoModelo = cat.getAnoModelo();
            this.categoryName = cat.getCategoryName();
            this.categoryCode = cat.getCategoryCode();
            this.taxRate = cat.getTaxRate();
            this.active = cat.isActive();
            this.description = cat.getDescription();
        }
    }

    public static TruckResponseDTO from(AssetCategory cat) {
        return new TruckResponseDTO(cat);
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

}
