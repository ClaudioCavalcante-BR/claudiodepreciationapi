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

## 📦 Estrutura de Classes Criadas

### Projeto 2 — claudiodepreciationapi
- **Clients**
  - `ParallelumFeignClient` → consulta marcas, modelos e anos na API FIPE.  
  - `BrasilApiFeignClient` → consulta preço pelo código FIPE.  

- **DTOs / Domain**
  - `ParallelumBrand`, `ParallelumModel`, `ParallelumYear`  
  - `TruckQueryResult` → resposta consolidada (marca, modelo, ano, valor, etc.)  

- **Service**
  - `TruckFipeService` → orquestra chamadas às APIs externas (FIPE + BrasilAPI), aplica fallback e sugere anos alternativos.  

- **Controller**
  - `TruckDiscoveryController` → endpoints de descoberta (marcas, modelos, anos).  
  - `TruckFipeController` → endpoint consolidado para consulta completa.  

- **Exception Handling**
  - `ApiExceptionHandler` → converte erros em JSON padronizado `{timestamp, status, error, message, path}`.  

---

### Projeto 1 — claudioapi
- **Feign Client**
  - `TruckFeignClient` → conecta-se ao `claudiodepreciationapi`.  

- **Service**
  - `TruckGatewayService` → chama o Projeto 2 via Feign e aplica regras simples (`applyDefaults`).  

- **Controller**
  - `TruckGatewayController` → expõe endpoint REST `/api/trucks/{marcaId}/{modeloId}/{anoCodigo}`.  

- **Loader**
  - `TruckLoader` → executa chamadas de teste no startup e imprime resultados no console.  

- **Exception Handling**
  - `GlobalExceptionHandler` → padroniza erros em JSON.  

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

## 🌍 Endpoints Consumíveis

### Projeto 2 — claudiodepreciationapi (porta 8080)

**Consulta consolidada (encapsula FIPE + BrasilAPI):**
```
GET http://localhost:8080/fipe/trucks/{marcaId}/{modeloId}/{anoCodigo}
Exemplo:
http://localhost:8080/fipe/trucks/116/10602/2025-3
```

**Discovery (apoio para montar consultas):**
- Listar marcas:
```
GET http://localhost:8080/fipe/marcas
```

- Listar modelos por marca:
```
GET http://localhost:8080/fipe/marcas/{marcaId}/modelos
Ex.: http://localhost:8080/fipe/marcas/109/modelos
```

- Listar anos disponíveis:
```
GET http://localhost:8080/fipe/marcas/{marcaId}/modelos/{modeloId}/anos
Ex.: http://localhost:8080/fipe/marcas/109/modelos/9674/anos
```

O endpoint `/fipe/trucks/{marcaId}/{modeloId}/{anoCodigo}` já encapsula toda a lógica de orquestração.

---

### Projeto 1 — claudioapi (porta 8081)

**Proxy/Facade para consumo externo:**
```
GET http://localhost:8081/api/trucks/{marcaId}/{modeloId}/{anoCodigo}
Exemplo:
http://localhost:8081/api/trucks/116/10602/2025-3
```

📌 Este endpoint chama o Projeto 2 internamente e aplica ajustes locais (como `applyDefaults`).

---

## 🧪 Testes Rápidos

### Via Browser
- Projeto 2 (direto no gateway de dados externos):  
  `http://localhost:8080/fipe/trucks/116/10602/2025-3`

- Projeto 1 (proxy do Projeto 2):  
  `http://localhost:8081/api/trucks/116/10602/2025-3`

### Via cURL
```bash
# Projeto 2
curl -i "http://localhost:8080/fipe/trucks/116/10602/2025-3"

# Projeto 1
curl -i "http://localhost:8081/api/trucks/116/10602/2025-3"
```

---

## 🚨 Tratamento de Erros
- **404 NOT_FOUND** → Marca/Modelo/Ano não encontrado no provedor externo.  
- **502 BAD_GATEWAY** → Falha na comunicação com serviços externos.  
- **400 BAD_REQUEST** → Parâmetros inválidos.  

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
