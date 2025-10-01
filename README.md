# 📌 Feature 2 — Integração e Orquestração

## 🔎 Visão Geral
Este projeto é composto por **dois microsserviços Spring Boot** que se comunicam para prover consultas a dados de caminhões a partir de **APIs externas**.

- 🚛 **Projeto 2 — claudiodepreciationapi (porta 8080)**  
  Responsável por **integrar com APIs externas** (Parallelum FIPE + BrasilAPI), realizar orquestração, validações e expor endpoints REST consolidados.

- ⚙️ **Projeto 1 — claudioapi (porta 8081)**  
  Atua como **consumidor** do Projeto 2, expondo endpoints próprios para aplicações externas, aplicando regras adicionais de negócio (ex.: normalizações com `applyDefaults`) e servindo como ponto de entrada.

---

## 🛠 Tecnologias e Anotações Utilizadas

- **Spring Boot 3.3.x**
- **Spring Web**  
  Anotações: `@RestController`, `@RequestMapping`, `@GetMapping`
- **Spring Cloud OpenFeign**  
  Uso de `@FeignClient` para consumo de APIs externas (FIPE e BrasilAPI) e comunicação entre Projetos.
- **Camada Service**  
  Anotação: `@Service` para orquestração da lógica.
- **Tratamento de Erros**  
  Anotações: `@RestControllerAdvice`, `@ExceptionHandler`, `ResponseStatusException`
- **DTOs e Domínios**  
  Para mapear respostas JSON externas e internas.
- **JPA/Hibernate**  
  No Projeto 2 (persistência de ativos).

---

## 📂 Estrutura dos Projetos

```
Desenvolvimento_Avancado_com_Spring_e_Microservicos_25E3-25E3/
 ├── claudioapi/                 # Projeto 1 — consumidor (porta 8081)
 ├── claudiodepreciationapi/     # Projeto 2 — orquestrador (porta 8080)
 └── README.md                   # Este documento (Feature 2)
```

---

## 🌐 Endpoints Principais

### Projeto 2 — claudiodepreciationapi
- `GET /fipe/marcas` → lista todas as marcas de caminhões (Parallelum API)  
- `GET /fipe/marcas/{id}/modelos` → retorna modelos de uma marca  
- `GET /fipe/marcas/{id}/modelos/{modeloId}/anos` → retorna anos disponíveis  
- `GET /fipe/trucks/{marcaId}/{modeloId}/{anoCodigo}` → retorna preço consolidado (FIPE + BrasilAPI)

### Projeto 1 — claudioapi
- `GET /api/trucks/{marcaId}/{modeloId}/{anoCodigo}` → consulta final consolidada, consumindo Projeto 2, com aplicação de regras adicionais de negócio.

---

## ⚡ Exemplo de Orquestração com Feign

```java
@FeignClient(name = "parallelum", url = "https://parallelum.com.br/fipe/api/v1/caminhoes")
public interface ParallelumFeignClient {

    @GetMapping("/marcas")
    List<ParallelumBrand> obterMarcas();

    @GetMapping("/marcas/{id}/modelos")
    ParallelumModelsResponse obterModelos(@PathVariable("id") int id);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<ParallelumYear> obterAnos(@PathVariable("marcaId") int marcaId,
                                   @PathVariable("modeloId") int modeloId);
}
```

---

## ✅ Conclusão
A **Feature 2** entrega a **integração e orquestração** entre os dois microsserviços, garantindo:  
- Consumo de dados externos (FIPE e BrasilAPI)  
- Exposição de endpoints REST consolidados  
- Aplicação de regras de negócio no microsserviço consumidor (Projeto 1)  
- Tratamento de erros e uso de boas práticas com Spring Boot + OpenFeign  

📌 Essa implementação pode ser validada via **Postman** ou diretamente no navegador em `http://localhost:8080` (Projeto 2) e `http://localhost:8081` (Projeto 1).
