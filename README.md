# Feature 3 — Multi-módulos com `claudio-parent` e `claudio-mono`

## Objetivo
A Feature 3 reestrutura o projeto em multi-módulos Maven, mantendo a integração com a API FIPE (Parallelum) e a leitura dos dados internos do arquivo `asset.txt` em formato JSON.  
O foco é garantir separação de responsabilidades e organização modular entre domínio, integrações externas e aplicação principal.

---

## Relação entre `claudio-mono` e `claudio-parent`

### Estrutura de Projetos
No repositório existem dois projetos distintos (lado a lado, sem um dentro do outro):

- `claudio-mono/` → projeto original, single-module, criado anteriormente.  
- `claudio-parent/` → novo projeto multi-módulos, criado nesta Feature 3.

Estrutura do `claudio-parent`:
```
claudio-parent/
├── common-domain/        → Entidades JPA e enums
├── external-api/         → Feign Clients e DTOs externos (Parallelum FIPE / BrasilAPI)
└── main-app/             → Aplicação principal Spring Boot (controllers, services e DTOs internos)
```

Migração realizada: as classes do `claudio-mono` foram copiadas e redistribuídas nos módulos do `claudio-parent` respeitando responsabilidades.  
Projeto ativo para avaliação desta feature: `claudio-parent/main-app`.

---

## Banco de Dados H2

O projeto utiliza o banco de dados H2 em modo local, com os seguintes parâmetros no `application.properties`:

```properties
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:~/databaseClaudio
spring.datasource.username=sa
spring.datasource.password=
```

- O arquivo físico do banco é criado em: `~/databaseClaudio.mv.db`.  
- Pode ser acessado pelo navegador em:  
  `http://localhost:8080/h2-console`  
  (JDBC URL: `jdbc:h2:~/databaseClaudio`)

O H2 é usado para armazenar os dados do arquivo `asset.txt` e demais registros locais durante a execução.

---

## Execução
1. Build na raiz do parent:
   ```bash
   cd claudio-parent
   mvn clean install -DskipTests
   ```
2. Subir o módulo executável:
   ```bash
   cd main-app
   mvn spring-boot:run
   ```
3. A aplicação sobe em `http://localhost:8080`.

Para encerrar a execução no terminal: pressione **Ctrl + C**.

---

## Teste rápido no navegador (Browser)
Sem Postman; basta abrir as URLs:

- Listar todos os ativos (`asset.txt` → JSON):  
  `http://localhost:8080/api/asset-categories`

- Buscar ativo por ID:  
  `http://localhost:8080/api/asset-categories/5`

- Buscar ativo + Código FIPE “on the fly” (diesel = sufixo `-3`):  
  `http://localhost:8080/api/asset-categories/5?marcaId=109&modeloId=9674&ano=2023`

Catálogo FIPE (Parallelum):  
- Marcas:  
  `http://localhost:8080/api/asset-categories/fipe/marcas`
- Modelos da marca 109:  
  `http://localhost:8080/api/asset-categories/fipe/marcas/109/modelos`
- Anos do modelo 9674 (marca 109):  
  `http://localhost:8080/api/asset-categories/fipe/marcas/109/modelos/9674/anos`
- Detalhe 2023-3 (Diesel):  
  `http://localhost:8080/api/asset-categories/fipe/marcas/109/modelos/9674/anos/2023-3`

**Retorno esperado:**  
- Todos os dados do `asset.txt` são exibidos em JSON.  
- Informando `marcaId`, `modeloId` e `ano`, o Código FIPE é obtido da API Parallelum e incluído no mesmo JSON.  
- Não há persistência automática: o cálculo é feito em tempo real (“on the fly”).

---

## Observações
- Utilize apenas o `claudio-parent/main-app` para avaliar a Feature 3.  
- A validação foi planejada para navegador (browser) — Postman não é necessário.  
- Endpoints retornam JSON unificado (dados internos do `asset.txt` + dados externos FIPE).

---

## Estrutura de diretórios (resumo)
```
repo-root/
├── claudio-mono/                  # projeto original (referência)
└── claudio-parent/                # projeto multi-módulos da Feature 3
    ├── pom.xml                    # parent POM
    ├── common-domain/
    ├── external-api/
    └── main-app/
```

---

**Pronto.** Execute o `main-app` e valide pelo navegador usando as URLs acima.
