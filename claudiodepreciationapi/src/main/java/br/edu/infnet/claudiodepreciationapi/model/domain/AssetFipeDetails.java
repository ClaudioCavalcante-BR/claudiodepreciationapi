package br.edu.infnet.claudiodepreciationapi.model.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public class AssetFipeDetails {
	
	private String codigoFipe;
    private String marca;
    private String modelo;
    private Integer anoModelo;
    private FuelType combustivel;    // enum seu (abaixo)
    private BigDecimal valor;        // valor normalizado (sem "R$")
    private YearMonth mesReferencia; // ex.: 2025-09
    private LocalDate dataConsulta;  // quando foi obtido o preço
    
    
	public String getCodigoFipe() {
		return codigoFipe;
	}
	public void setCodigoFipe(String codigoFipe) {
		this.codigoFipe = codigoFipe;
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
	public Integer getAnoModelo() {
		return anoModelo;
	}
	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}
	public FuelType getCombustivel() {
		return combustivel;
	}
	public void setCombustivel(FuelType combustivel) {
		this.combustivel = combustivel;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public YearMonth getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(YearMonth mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	public LocalDate getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

}
