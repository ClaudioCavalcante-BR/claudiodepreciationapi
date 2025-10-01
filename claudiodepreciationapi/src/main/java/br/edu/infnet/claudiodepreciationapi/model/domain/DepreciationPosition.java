package br.edu.infnet.claudiodepreciationapi.model.domain;

public enum DepreciationPosition {
	
	NOT_DEPRECIATED,// ("Ativo ainda não iniciou o processo de depreciação."),
    IN_DEPRECIATION, //("Ativo está em processo de depreciação mensal ou periódico."),
    FULLY_DEPRECIATET, //("Ativo está totalmente depreciado, sem saldo contábil a depreciar."),
    IMPAIRMENT_ADJUSTED, //("Ativo passou por ajuste de valor (impairment), afetando a base de depreciação."),
    WRITTEN_OFF //("Ativo foi baixado ou retirado do patrimônio (venda, descarte, etc.).")

    
}
