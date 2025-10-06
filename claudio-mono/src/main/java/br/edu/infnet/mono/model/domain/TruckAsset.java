package br.edu.infnet.mono.model.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "truck")
public class TruckAsset {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer marcaId;
    private Integer modeloId;
    private String  anoCodigo;  

    private String codigoFipe;
    private String marca;
    private String modelo;
    private Integer ano;

    @Column(precision = 18, scale = 2)
    private BigDecimal preco;

	public static Object builder() {
		
		return "Builder";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	public Integer getModeloId() {
		return modeloId;
	}

	public void setModeloId(Integer modeloId) {
		this.modeloId = modeloId;
	}

	public String getAnoCodigo() {
		return anoCodigo;
	}

	public void setAnoCodigo(String anoCodigo) {
		this.anoCodigo = anoCodigo;
	}

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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
