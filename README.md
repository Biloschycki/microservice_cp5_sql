# Jogador API

API REST em **Java 17 + Spring Boot 4** com persistência via **Spring Data JPA /
Hibernate** em um banco **SQL Server**. Expõe o CRUD de duas entidades:
**Jogador** e **Restaurante** (pedidos de um restaurante), com documentação
Swagger/OpenAPI, profiles `dev`/`prd` e suporte a Docker.

---

## 📋 Pré-requisitos

- Java 17+
- Maven (ou o Maven Wrapper incluso, `mvnw`)
- Um SQL Server acessível (local, em Docker ou remoto)
- Docker (opcional, para subir o SQL Server ou rodar a API em container)

---

## 🗄️ 1. Banco de dados SQL Server

### Informações de conexão padrão

Se nenhuma variável de ambiente for definida, a API tenta se conectar com:

| Item          | Valor padrão  |
|---------------|---------------|
| Servidor      | `localhost`   |
| Porta         | `1433`        |
| Banco         | `api`         |
| Usuário       | `sa`          |
| Senha         | `1q2w3e4R@`   |
| Criptografia  | `encrypt=false`, `trustServerCertificate=true` |

URL JDBC resultante:

```text
jdbc:sqlserver://localhost:1433;databaseName=api;encrypt=false;trustServerCertificate=true
```

### Opção A — subir um SQL Server com Docker

```sh
docker run -d \
  --name sqlserver-jogador \
  -e "ACCEPT_EULA=Y" \
  -e "MSSQL_SA_PASSWORD=1q2w3e4R@" \
  -p 1433:1433 \
  mcr.microsoft.com/mssql/server:2022-latest
```

Aguarde alguns segundos até o SQL Server iniciar (`docker logs sqlserver-jogador`
deve exibir `SQL Server is now ready for client connections`).

### Opção B — usar um SQL Server já existente (local ou remoto)

Basta informar os dados de conexão pelas variáveis de ambiente descritas na
seção **2. Configuração da conexão com o SQL Server**.

### Criar o banco `api`

O SQL Server **não cria o database automaticamente** pela URL JDBC, então ele
precisa existir antes de a API subir. As tabelas são criadas pelo Hibernate
no profile `dev` (`ddl-auto=update`).

Com o container da opção A:

```sh
docker exec -it sqlserver-jogador /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "1q2w3e4R@" -C \
  -Q "IF DB_ID('api') IS NULL CREATE DATABASE api;"
```

Com um SQL Server instalado direto na máquina (opção B), pelo terminal
(PowerShell, CMD ou bash) usando o `sqlcmd`:

```sh
sqlcmd -S localhost -U sa -P "1q2w3e4R@" -Q "IF DB_ID('api') IS NULL CREATE DATABASE api;"
```

> Em versões novas do `sqlcmd` (ODBC 18), se aparecer erro de certificado,
> adicione a flag `-C` ao comando.

Ou, em qualquer cliente (SSMS, Azure Data Studio, DBeaver):

```sql
CREATE DATABASE api;
```

Alternativamente, o script `src/main/resources/migration-2026-08-17.sql` cria o
banco **e** as tabelas de uma vez (obrigatório para o profile `prd`):

```sh
# SQL Server instalado na máquina
sqlcmd -S localhost -U sa -P "1q2w3e4R@" -i src/main/resources/migration-2026-08-17.sql

# SQL Server em Docker (opção A)
docker exec -i sqlserver-jogador /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "1q2w3e4R@" -C < src/main/resources/migration-2026-08-17.sql
```

---

## ⚙️ 2. Configuração da conexão com o SQL Server

A conexão é lida de **variáveis de ambiente** (arquivo
`src/main/resources/application.properties`), então é possível apontar para
qualquer SQL Server sem alterar o código.

| Variável                 | Descrição                                   | Padrão (dev)  |
|--------------------------|---------------------------------------------|---------------|
| `DB_SERVER_URL`          | Host/IP do SQL Server                       | `localhost`   |
| `DB_SERVER_PORT`         | Porta do SQL Server                         | `1433`        |
| `DB_NAME`                | Nome do banco                               | `api`         |
| `DB_USER`                | Usuário                                     | `sa`          |
| `DB_PWD`                 | Senha                                       | `1q2w3e4R@`   |
| `DB_ENCRYPT`             | `true` para servidores que exigem TLS (ex.: Azure SQL) | `false` |
| `SPRING_PROFILES_ACTIVE` | Profile ativo (`dev` ou `prd`)              | `dev`         |

**Linux / macOS**

```sh
export DB_SERVER_URL=localhost
export DB_SERVER_PORT=1433
export DB_NAME=api
export DB_USER=sa
export DB_PWD='1q2w3e4R@'
```

**Windows PowerShell**

```powershell
$env:DB_SERVER_URL="localhost"
$env:DB_SERVER_PORT="1433"
$env:DB_NAME="api"
$env:DB_USER="sa"
$env:DB_PWD="1q2w3e4R@"
```

> Também é possível sobrescrever a URL inteira com
> `SPRING_DATASOURCE_URL=jdbc:sqlserver://...`, caso o banco disponibilizado
> exija parâmetros adicionais.

### Profiles

| Arquivo                      | Uso |
|------------------------------|-----|
| `application.properties`     | Configuração base: conexão SQL Server via variáveis de ambiente com valores padrão. Ativa o profile `dev` por padrão. |
| `application-dev.properties` | Desenvolvimento: `ddl-auto=update` (Hibernate cria/atualiza as tabelas) e `show-sql=true`. |
| `application-prd.properties` | Produção: todas as variáveis de banco são **obrigatórias**, `ddl-auto=none` (tabelas criadas pelo script de migration) e `show-sql=false`. |

---

## 🚀 3. Executando a aplicação

Com o banco `api` criado e as variáveis configuradas (ou usando os padrões):

```sh
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou com o Maven instalado: `mvn spring-boot:run`.

A aplicação sobe em `http://localhost:8080`. No log, a linha
`Started JogadorApplication` indica que a conexão com o SQL Server foi
estabelecida e as tabelas `jogadores` e `pedidos` foram criadas.

### Executando com Docker

```sh
docker build -t cp4-microservice-api1:1.0 .

docker run -d \
  --name jogador-api \
  -p 8080:8080 \
  -e DB_SERVER_URL=host.docker.internal \
  -e DB_SERVER_PORT=1433 \
  -e DB_NAME=api \
  -e DB_USER=sa \
  -e DB_PWD='1q2w3e4R@' \
  cp4-microservice-api1:1.0
```

> Dentro do container, `localhost` é o próprio container. Por isso
> `DB_SERVER_URL=host.docker.internal` aponta para o SQL Server rodando na
> máquina host. Em Linux, adicione `--add-host=host.docker.internal:host-gateway`.

---

## 🧪 4. Testando a API

### Swagger UI

Com a aplicação rodando, acesse **`http://localhost:8080/`** para visualizar e
testar todos os endpoints.

### Endpoints

**Jogadores** — `/api/v1/jogadores`

| Verbo  | Rota                     | Corpo                 | Resposta                          |
|--------|--------------------------|-----------------------|-----------------------------------|
| POST   | `/api/v1/jogadores`      | `JogadorRequestDTO`   | `201 Created` + `JogadorResponseDTO` |
| GET    | `/api/v1/jogadores`      | -                     | `200 OK` + lista                  |
| GET    | `/api/v1/jogadores/{id}` | -                     | `200 OK` ou `404 Not Found`       |
| PUT    | `/api/v1/jogadores/{id}` | `JogadorRequestDTO`   | `200 OK` ou `404 Not Found`       |
| DELETE | `/api/v1/jogadores/{id}` | -                     | `204 No Content` ou `404 Not Found` |

**Pedidos (Restaurante)** — `/api/v1/pedidos`

| Verbo  | Rota                    | Corpo                     | Resposta                          |
|--------|-------------------------|---------------------------|-----------------------------------|
| POST   | `/api/v1/pedidos`       | `RestauranteRequestDTO`   | `201 Created` + `RestauranteResponseDTO` |
| GET    | `/api/v1/pedidos`       | -                         | `200 OK` + lista                  |
| GET    | `/api/v1/pedidos/{id}`  | -                         | `200 OK` ou `404 Not Found`       |
| PUT    | `/api/v1/pedidos/{id}`  | `RestauranteRequestDTO`   | `200 OK` ou `404 Not Found`       |
| DELETE | `/api/v1/pedidos/{id}`  | -                         | `204 No Content` ou `404 Not Found` |

Campos inválidos retornam `400 Bad Request` com o detalhamento por campo.

### Roteiro de teste com cURL

```sh
# Criar um jogador
curl -i -X POST http://localhost:8080/api/v1/jogadores \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "nome": "Neymar", "time": "Santos", "numero": 10, "posicao": "Atacante"}'

# Listar jogadores
curl -i http://localhost:8080/api/v1/jogadores

# Buscar por id
curl -i http://localhost:8080/api/v1/jogadores/1

# Atualizar
curl -i -X PUT http://localhost:8080/api/v1/jogadores/1 \
  -H "Content-Type: application/json" \
  -d '{"nome": "Neymar Jr", "time": "Santos", "numero": 11, "posicao": "Ponta"}'

# Excluir
curl -i -X DELETE http://localhost:8080/api/v1/jogadores/1

# Criar um pedido
curl -i -X POST http://localhost:8080/api/v1/pedidos \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "nome": "Feijoada", "numero": 5, "quantidade": 2, "valor": 89.90}'

# Listar pedidos
curl -i http://localhost:8080/api/v1/pedidos
```

> As entidades não usam `@GeneratedValue`, então o `id` é informado pelo
> cliente no `POST`. No `PUT`, vale sempre o `id` da URL.

### Conferindo os dados direto no SQL Server

```sh
docker exec -it sqlserver-jogador /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "1q2w3e4R@" -C -d api \
  -Q "SELECT * FROM jogadores; SELECT * FROM pedidos;"
```

---

## 🏗️ Arquitetura do projeto

As Controllers não recebem nem retornam entidades JPA: toda entrada e saída
passa por DTOs.

```
Controller (DTO)  →  Service  →  Mapper (DTO <-> Entity)  →  Repository (JPA)  →  SQL Server
```

```
com.github.biloschycki.jogador
├── controller   # Endpoints REST, recebem/retornam DTOs
├── dto
│   ├── request  # JogadorRequestDTO, RestauranteRequestDTO (Bean Validation)
│   └── response # JogadorResponseDTO, RestauranteResponseDTO
├── mapper       # Conversão Entity <-> DTO
├── service      # Regras de negócio do CRUD
├── model        # Entidades JPA (Jogador -> jogadores, Restaurante -> pedidos)
├── repository   # Interfaces JpaRepository (Spring Data JPA)
└── exception    # ResourceNotFoundException + GlobalExceptionHandler (404/400)
```

### Tabelas

| Entidade      | Tabela      | Colunas |
|---------------|-------------|---------|
| `Jogador`     | `jogadores` | `id`, `nome_jogador`, `time_jogador`, `numero_jogador`, `posicao_jogador` |
| `Restaurante` | `pedidos`   | `id`, `nome_prato`, `numero_mesa`, `quantidade_prato`, `valor_pedido` |

---

## ⚠️ Possíveis problemas

- **`Cannot open database "api"`**: o banco não foi criado. Execute o
  `CREATE DATABASE api;` da seção 1.
- **`Login failed for user 'sa'`**: senha incorreta; confira `DB_PWD`.
- **`The TCP/IP connection to the host ... has failed`**: o SQL Server não está
  rodando, a porta está errada ou, em Docker, `DB_SERVER_URL` ainda é
  `localhost` (use `host.docker.internal`).
- **Erro de TLS/certificado em servidor remoto**: defina `DB_ENCRYPT=true`.
- **Profile `prd` não sobe**: todas as variáveis `DB_*` são obrigatórias nesse
  profile, e as tabelas precisam ter sido criadas pelo script de migration.
