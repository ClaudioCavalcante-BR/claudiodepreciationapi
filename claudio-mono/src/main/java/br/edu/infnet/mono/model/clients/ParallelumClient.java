package br.edu.infnet.mono.model.clients;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonProperty;

@FeignClient(name = "parallelum", url = "${parallelum.url}")
public interface ParallelumClient {
	
	// LISTA DE MARCAS
    @GetMapping("/caminhoes/marcas")
    List<Brand> listarMarcas();

    // LISTA DE MODELOS POR MARCA
    // A API retorna um objeto com o array "modelos"
    @GetMapping("/caminhoes/marcas/{marcaId}/modelos")
    ModelsResponse listarModelos(@PathVariable("marcaId") Integer marcaId);

    // LISTA DE ANOS POR MODELO
    @GetMapping("/caminhoes/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<Year> listarAnos(@PathVariable("marcaId") Integer marcaId,
                          @PathVariable("modeloId") Integer modeloId);

    // DETALHE (FIPE) POR ANO — resposta tipada
    @GetMapping("/caminhoes/marcas/{marcaId}/modelos/{modeloId}/anos/{anoCodigo}")
    VehicleDetail obterDetalheAno(@PathVariable("marcaId") Integer marcaId,
                                  @PathVariable("modeloId") Integer modeloId,
                                  @PathVariable("anoCodigo") String anoCodigo);

    
    class Brand {
        private String codigo;
        private String nome;

        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    class Model {
        private String codigo;
        private String nome;

        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    class Year {
        // Ex.: codigo = "2023-3", nome = "2023 Diesel"
        private String codigo;
        private String nome;

        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    // Wrapper para /modelos (a API retorna {"modelos":[...]})
    class ModelsResponse {
        private java.util.List<Model> modelos;

        public List<Model> getModelos() { return modelos; }
        public void setModelos(List<Model> modelos) { this.modelos = modelos; }
    }

    // Mapeamento do detalhe FIPE (campos em PT-BR)
    class VehicleDetail {

        @JsonProperty("Valor")
        private String valor;

        @JsonProperty("Marca")
        private String marca;

        @JsonProperty("Modelo")
        private String modelo;

        @JsonProperty("AnoModelo")
        private Integer anoModelo;

        @JsonProperty("Combustivel")
        private String combustivel;

        @JsonProperty("CodigoFipe")
        private String codigoFipe;

        @JsonProperty("MesReferencia")
        private String mesReferencia;

        @JsonProperty("TipoVeiculo")
        private Integer tipoVeiculo;

        @JsonProperty("SiglaCombustivel")
        private String siglaCombustivel;

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

		public Integer getTipoVeiculo() {
			return tipoVeiculo;
		}

		public void setTipoVeiculo(Integer tipoVeiculo) {
			this.tipoVeiculo = tipoVeiculo;
		}

		public String getSiglaCombustivel() {
			return siglaCombustivel;
		}

		public void setSiglaCombustivel(String siglaCombustivel) {
			this.siglaCombustivel = siglaCombustivel;
		}
 }

}