# README_API_Feature_02

\##  Visão Geral\
Este projeto é composto por \*\*dois microsserviços Spring Boot\*\* que
se comunicam para prover consultas a dados de caminhões a partir de APIs
externas:\
\
- \*\*Projeto 2 -- claudiodepreciationapi (porta 8080)\*\*\
Responsável por \*\*integrar com APIs externas\*\* (Parallelum FIPE +
BrasilAPI), realizar orquestração, validação e expor endpoints REST
consolidados.\
\
- \*\*Projeto 1 -- claudioapi (porta 8081)\*\*\
Atua como \*\*consumidor/\*\* do Projeto 2, expondo endpoints
próprios para aplicações externas, aplicando regras adicionais de
negócio (normalizações com \`applyDefaults\`) e servindo como ponto de
entrada.\
\
\-\--\
\
\## Tecnologias e Anotações Utilizadas\
- \*\*Spring Boot 3.3.x\*\*\
- \*\*Spring Web\*\* (\`@RestController\`, \`@RequestMapping\`,
\`@GetMapping\`)\
- \*\*Spring Cloud OpenFeign\*\* (\`@FeignClient\`) -- consumo de APIs
externas (P2) e do Projeto 2 (P1).\
- \*\*Camada Service\*\* (\`@Service\`) para orquestração da lógica.\
- \*\*Tratamento de erros\*\* (\`@RestControllerAdvice\`,
\`@ExceptionHandler\`, \`ResponseStatusException\`)\
- \*\*DTOs e Domínios\*\* para mapear respostas JSON externas e
internas.\
- \*\*JPA/Hibernate\*\* no Projeto 2 (camada de persistência de
ativos).\
\
\-\--\
\
\## Estrutura de Classes Criadas\
\
\### Projeto 2 -- claudiodepreciationapi\
- \*\*Clients\*\*\
- \`ParallelumFeignClient\` → consulta marcas, modelos e anos na API
FIPE.\
- \`BrasilApiFeignClient\` → consulta preço pelo código FIPE.\
\
- \*\*DTOs / Domain\*\*\
- \`ParallelumBrand\`, \`ParallelumModel\`, \`ParallelumYear\`\
- \`TruckQueryResult\` → resposta consolidada (marca, modelo, ano,
valor, etc.)\
\
- \*\*Service\*\*\
- \`TruckFipeService\` → orquestra chamadas às APIs externas (FIPE +
BrasilAPI), aplica fallback e sugere anos alternativos.\
\
- \*\*Controller\*\*\
- \`TruckDiscoveryController\` → endpoints de descoberta (marcas,
modelos, anos).\
- \`TruckFipeController\` → endpoint consolidado para consulta
completa.\
\
- \*\*Exception Handling\*\*\
- \`ApiExceptionHandler\` → converte erros em JSON padronizado
\`{timestamp, status, error, message, path}\`.\
\
\-\--\
\
\### Projeto 1 -- claudioapi\
- \*\*Feign Client\*\*\
- \`TruckFeignClient\` → conecta-se ao \`claudiodepreciationapi\`.\
\
- \*\*Service\*\*\
- \`TruckGatewayService\` → chama o Projeto 2 via Feign e aplica regras
simples (\`applyDefaults\`).\
\
- \*\*Controller\*\*\
- \`TruckGatewayController\` → expõe endpoint REST
\`/api/trucks/{marcaId}/{modeloId}/{anoCodigo}\`.\
\
- \*\*Loader\*\*\
- \`TruckLoader\` → executa chamadas de teste no startup e imprime
resultados no console.\
\
- \*\*Exception Handling\*\*\
- \`GlobalExceptionHandler\` → padroniza erros em JSON.\
\
\-\--\
\
\##  Endpoints Consumíveis\
\
\### Projeto 2 -- claudiodepreciationapi (porta 8080)\
\*\*Consulta consolidada (encapsula FIPE + BrasilAPI)\*\*\
\`\`\`\
GET http://localhost:8080/fipe/trucks/{marcaId}/{modeloId}/{anoCodigo}\
Exemplo:\
http://localhost:8080/fipe/trucks/116/10602/2025-3\
\`\`\`\
\
\*\*Discovery (apoio para montar consultas)\*\*\
- Listar marcas\
\`\`\`\
GET http://localhost:8080/fipe/marcas\
\`\`\`\
- Listar modelos por marca\
\`\`\`\
GET http://localhost:8080/fipe/marcas/{marcaId}/modelos\
Ex.: http://localhost:8080/fipe/marcas/109/modelos\
\`\`\`\
- Listar anos disponíveis\
\`\`\`\
GET http://localhost:8080/fipe/marcas/{marcaId}/modelos/{modeloId}/anos\
Ex.: http://localhost:8080/fipe/marcas/109/modelos/9674/anos\
\`\`\`\
\
 O endpoint \`/fipe/trucks/{marcaId}/{modeloId}/{anoCodigo}\` já
encapsula toda a lógica de orquestração.\
\
\-\--\
\
\###  Projeto 1 -- claudioapi (porta 8081)\
\*\*Proxy/Facade para consumo externo\*\*\
\`\`\`\
GET http://localhost:8081/api/trucks/{marcaId}/{modeloId}/{anoCodigo}\
Exemplo:\
http://localhost:8081/api/trucks/116/10602/2025-3\
\`\`\`\
\
📌 Este endpoint chama o Projeto 2 internamente e aplica ajustes locais
(como \`applyDefaults\`).\
\
\-\--\
\
\## Testes Rápidos\
\
\### Via Browser\
- Projeto 2 (direto no gateway de dados externos):\
\`http://localhost:8080/fipe/trucks/116/10602/2025-3\`\
\
- Projeto 1 (proxy do Projeto 2):\
\`http://localhost:8081/api/trucks/116/10602/2025-3\`\
\
\### Via cURL\
\`\`\`bash\
\# Projeto 2\
curl -i \"http://localhost:8080/fipe/trucks/116/10602/2025-3\"\
\
\# Projeto 1\
curl -i \"http://localhost:8081/api/trucks/116/10602/2025-3\"\
\`\`\`\
\
\-\--\
\
\## Tratamento de Erros\
- \*\*404 NOT_FOUND\*\* → Marca/Modelo/Ano não encontrado no provedor
externo.\
- \*\*502 BAD_GATEWAY\*\* → Falha na comunicação com serviços externos.\
- \*\*400 BAD_REQUEST\*\* → Parâmetros inválidos.\
\
\-\--\
\
\## Execução\
1. Subir primeiro o \*\*Projeto 2 -- claudiodepreciationapi\*\* na porta
\*\*8080\*\*.\
2. Subir o \*\*Projeto 1 -- claudioapi\*\* na porta \*\*8081\*\*.\
3. Testar endpoints no navegador ou via cURL.\
\
\-\--\
\
 Esse README consolida \*\*Feature 2 -- Arquiteturas Avançadas de
Software com Microsserviços e Spring Framework\*\*, cobrindo:\
✅ Integração com APIs externas\
✅ Orquestração e fallback\
✅ Criação de DTOs e services\
✅ Exposição de endpoints REST\
✅ Consumo pelo Projeto 1 via Feign
