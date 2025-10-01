package br.edu.infnet.claudiodepreciationapi.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParallelumTruckDetail {
	
	@JsonProperty("TipoVeiculo")
	private Integer tipoVeiculo;     // 3 = Caminhão
	
	@JsonProperty("Valor")
    private String valor;            // Valor de referência (pode vir como "R$ ...")
    
	@JsonProperty("Marca")
    private String marca;            // Nome da marca
    
	@JsonProperty("Modelo")
    private String modelo;           // Nome do modelo
    
	@JsonProperty("AnoModelo")
    private Integer anoModelo;       // Ano do modelo (ex.: 2023)
    
	@JsonProperty("Combustivel")
    private String combustivel;      // Nome do combustível (Diesel, Flex, etc.)
    
	@JsonProperty("SiglaCombustivel")
    private String siglaCombustivel; // Sigla do combustível (D, G, etc.)
    
	@JsonProperty("CodigoFipe")
    private String codigoFipe;       // Código FIPE (ex.: "509330-9")
    
	@JsonProperty("MesReferencia")
    private String mesReferencia;    // Mês de referência (ex.: "setembro de 2025")
    
    //Retorno da Parallelum (descoberta → marca, modelo, ano ⇒ CodigoFipe).
    
    
    
    
	public Integer getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(Integer tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
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
	public String getCombustivel() {
		return combustivel;
	}
	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}
	public String getSiglaCombustivel() {
		return siglaCombustivel;
	}
	public void setSiglaCombustivel(String siglaCombustivel) {
		this.siglaCombustivel = siglaCombustivel;
	}
	public String getCodigoFipe() {
		return codigoFipe;
	}
	public void setCodigoFipe(String codigoFipe) {
		this.codigoFipe = codigoFipe;
	}
	public String getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
    
    
}
