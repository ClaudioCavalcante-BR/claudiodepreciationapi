package br.edu.infnet.claudiodepreciationapi.model.domain;

public enum RegistrationType {
	
	NEW, //("Registro inicial de um ativo no sistema"),
    ACQUISITION, //("Registro de um ativo adquirido por compra"),
    DONATION, //("Registro proveniente de doacao ou recebimento sem custo"),
    TRANSFER, //("Registro resultante de transferência interna entre unidades/filiais."),
    IMPAIRMENT_TEST //("Registro de ativo após teste de recuperabilidade (impairment test)");
}
