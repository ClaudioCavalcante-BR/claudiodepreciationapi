package br.edu.infnet.mono.model.dto;

import java.math.BigDecimal;

import br.edu.infnet.mono.model.domain.TruckAsset;

public class TruckAssetResponseDTO {


    private Integer marcaId;
    private Integer modeloId;
    private String  anoCodigo;     
    private String  codigoFipe;    
    private String  marca;
    private String  modelo;
    private Integer ano;           
    private BigDecimal preco;      

    public TruckAssetResponseDTO() { }

    // Construtor a partir do domínio TruckAsset
    public TruckAssetResponseDTO(TruckAsset t) {
        if (t != null) {
            this.marcaId   = t.getMarcaId();
            this.modeloId  = t.getModeloId();
            this.anoCodigo = t.getAnoCodigo();
            this.codigoFipe = t.getCodigoFipe();
            this.marca     = t.getMarca();
            this.modelo    = t.getModelo();
            this.ano       = t.getAno();
            this.preco     = t.getPreco();
        }
    }

    public static TruckAssetResponseDTO from(TruckAsset t) {
        return new TruckAssetResponseDTO(t);
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
