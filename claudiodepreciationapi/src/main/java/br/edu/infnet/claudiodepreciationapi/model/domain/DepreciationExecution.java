package br.edu.infnet.claudiodepreciationapi.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepreciationExecution {
	
	private String executionCode; //Código único da execução de depreciação.
	private LocalDateTime executionDate; // Data e hora da execução do cálculo de depreciação.
	private String responsibleUser; // Usuário responsável pela execução do processo.
	private DepreciationPosition executionStatus; // Fase atual da depreciação nesta execução.
	private List<AssetItem> items; // Lista de ativos que estão sendo depreciados nesta execução.
	
	public DepreciationExecution() {
		
		this.setExecutionCode(UUID.randomUUID().toString());
		this.setExecutionDate(LocalDateTime.now());
		this.setResponsibleUser(null);
		this.setExecutionStatus(DepreciationPosition.NOT_DEPRECIATED);
		this.setItems(new ArrayList<AssetItem>());
		
	}

	public String getExecutionCode() {
		return executionCode;
	}

	public void setExecutionCode(String executionCode) {
		this.executionCode = executionCode;
	}

	public LocalDateTime getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDateTime executionDate) {
		this.executionDate = executionDate;
	}

	public String getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public DepreciationPosition getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(DepreciationPosition executionStatus) {
		this.executionStatus = executionStatus;
	}

	public List<AssetItem> getItems() {
		return items;
	}

	public void setItems(List<AssetItem> items) {
		this.items = items;
	}

	
}
