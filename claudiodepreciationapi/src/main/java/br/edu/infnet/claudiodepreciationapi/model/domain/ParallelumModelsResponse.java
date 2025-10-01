package br.edu.infnet.claudiodepreciationapi.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** @JsonIgnoreProperties(ignoreUnknown = true) 
 * espelha o JSON da Parallelum:
 * { "modelos": [ {codigo,nome}... ], "anos": [ {codigo,nome}... ] }
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParallelumModelsResponse {
	
	private List<ParallelumModel> modelos;
	private List<ParallelumYear> anos; // Jä existe no projeto - ParallelumYear
	
	public List<ParallelumModel> getModelos() {
		return modelos;
	}
	public void setModelos(List<ParallelumModel> modelos) {
		this.modelos = modelos;
	}
	public List<ParallelumYear> getAnos() {
		return anos;
	}
	public void setAnos(List<ParallelumYear> anos) {
		this.anos = anos;
	}

}
