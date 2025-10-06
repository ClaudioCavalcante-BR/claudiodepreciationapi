package br.edu.infnet.mono.model.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TruckRequestDTO {
	
	
	  // Marca (texto)
    @NotNull(message = "Nome da marca é obrigatório.")
    @Size(min = 3, max = 60, message = "Nome da marca deve ter entre 3 e 60 caracteres.")
    private String marca;

    // Modelo (texto)
    @NotNull(message = "Nome do modelo é obrigatório.")
    @Size(min = 3, max = 60, message = "Nome do modelo deve ter entre 3 e 60 caracteres.")
    private String modelo;

    // Ano do modelo (AAAA)
    @NotBlank(message = "Ano do modelo é obrigatório.")
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$", message = "Ano do modelo inválido. Use AAAA a partir de 1900.")
    private String anoModelo;

    // Categoria (nome)
    @NotNull(message = "Nome da categoria é obrigatório.")
    @Size(min = 3, max = 60, message = "Nome da categoria deve ter entre 3 e 60 caracteres.")
    private String categoryName;

    // Categoria (código interno - inteiro)
    @Min(value = 1, message = "Código da categoria deve ser positivo.")
    private int categoryCode;

    // Taxa (%)
    @DecimalMin(value = "0.0", inclusive = true, message = "Taxa deve ser maior ou igual a 0%.")
    @DecimalMax(value = "100.0", inclusive = true, message = "Taxa deve ser menor ou igual a 100%.")
    private double taxRate;

    // Ativo
    private boolean active;

    // Descrição
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres.")
    private String description;

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
