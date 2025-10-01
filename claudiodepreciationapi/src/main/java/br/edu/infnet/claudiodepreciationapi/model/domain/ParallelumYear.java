package br.edu.infnet.claudiodepreciationapi.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParallelumYear {
	public String codigo; // ex.: "2023-3"
    public String nome;   // ex.: "2023 Diesel"
    
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}
