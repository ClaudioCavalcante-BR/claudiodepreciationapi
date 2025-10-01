package br.edu.infnet.claudiodepreciationapi.model.domain;

public enum FuelType {
	
	DIESEL("Diesel"),
    GASOLINA("Gasolina"),
    ETANOL("Etanol"),
    FLEX("Flex"),
    ELETRICO("Elétrico"),
    DESCONHECIDO("Desconhecido");

    private final String descricao;

    FuelType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte texto vindo da API para o enum correspondente.
     */
    public static FuelType fromDescricao(String texto) {
        if (texto == null) return DESCONHECIDO;
        switch (texto.toLowerCase()) {
            case "diesel": return DIESEL;
            case "gasolina": return GASOLINA;
            case "etanol": return ETANOL;
            case "flex": return FLEX;
            case "elétrico": 
            case "eletrico": return ELETRICO;
            default: return DESCONHECIDO;
        }
    }

}
