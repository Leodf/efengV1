# 🌱 Projeto - EfengV1 - Cidades ESGInteligentes

Este projeto tem como objetivo promover a gestão inteligente de cidades com foco em práticas ESG (Ambientais, Sociais e de Governança), utilizando uma arquitetura moderna, segura e escalável.

A automação de processos dentro de uma organização pode trazer eficiência, reduzir custos e melhorar a sustentabilidade. No contexto de ESG (Environmental, Social, and Governance), bancos de dados podem ser usados para otimizar processos que impactam positivamente o meio ambiente, a sociedade e a governança corporativa.

O Projeto em si visa fazer o monitoramento de dispositivos dentro de uma industria ao receber seus dados e utilizando de triggres e procedures dentro do banco de dados para poder ter consistencia dos dados e poder impactar na decisão de quem consome os dados.

## 🚀 Como executar localmente com Docker

Para rodar o projeto localmente com Docker e Docker Compose, siga os passos abaixo:

- Clone o repositório

  ```bash
  git clone https://github.com/leodf/efengV1.git
  cd efengV1
  ```

- Configure as variáveis de ambiente
- As variáveis já estão definidas no docker-compose.yml, mas você pode sobrescrevê-las com um .env se desejar.
- Execute o Docker Compose

  ```bash
  docker compose up --build
  ```

- Serviços disponíveis
  - API: [http://localhost:8080](http://localhost:8080)
  - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
  - phpMyAdmin: [http://localhost:8081](http://localhost:8080/swagger-ui.html)
- O serviço da API depende do banco de dados MySQL e só será iniciado após o banco estar saudável, graças ao depends_on com healthcheck.
- Para parar a aplicação:

  ```bash
  docker compose down --build
  ```

## ⚙️ Pipeline CI/CD

A automação do ciclo de vida da aplicação é feita com GitHub Actions, garantindo integração contínua (CI) e entrega contínua (CD) desde o commit até o deploy em produção no Azure.

### Gatilhos

- Push na branch master
- Execução manual via workflow_dispatch

### Etapas do pipeline

| Etapa   | Descrição                                                                |
| ------- | ------------------------------------------------------------------------ |
| Build   | Compila o projeto com Java 21 usando Maven                               |
| Testes  | Executa testes unitários com `mvn test`                                  |
| Release | Cria imagem Docker e publica no Docker Hub com tags `vX` e `latest`      |
| Deploy  | Realiza deploy da imagem para o Azure Web App via `azure/webapps-deploy` |

### Ferramentas utilizadas

- actions/checkout@v4
- setup-java@v4
- docker/login-action@v3
- docker/build-push-action@v6
- azure/webapps-deploy@v2

As credenciais são armazenadas em GitHub Secrets e o deploy é feito no slot production do app api-fiap-efengv1-dev.

## 📦 Containerização

A aplicação é empacotada em dois estágios usando Docker:

### Etapa de build

- Imagem: `maven:3.9.8-eclipse-temurin-21`
- Compilação com `./mvnw clean package`
- Geração do JAR em `/opt/app/target/app.jar`

### Etapa de runtime

- Imagem: eclipse-temurin:21-jre-alpine
- Copia do JAR gerado
- Profile definido via variável `PROFILE`
- Porta exposta: `8080`
- Inicialização:

  ```bash
  java -Dspring.profiles.active=$PROFILE -jar app.jar
  ```

### Docker Compose

Define três serviços:

| Serviço      | Descrição                                                                  |
| ------------ | -------------------------------------------------------------------------- |
| `db`         | Banco MySQL 8.0 com volume persistente e healthcheck                       |
| `phpmyadmin` | Interface web para o banco, acessível em `localhost:8081`                  |
| api          | Aplicação Java 21, com build automatizado e variáveis de ambiente definida |

## 🖼️ Prints do funcionamento

Inclua aqui evidências visuais do projeto em execução:

✅ Swagger:

![Swagger funcionando 1](/docs/swagger1.png)

![Swagger funcionando 2](/docs/swagger2.png)

:octocat: Github Actions:

![Pipe CI/CD](/docs/pipecicd.png)

🚀 Deploy no Azure

![Azure dash 1](/docs/azuredash1.png)

![Azure dash 2](/docs/azuredash2.png)

:whale: Docker Hub

![Docker Hub](/docs/dockerhub.png)

## 🛠️ Tecnologias utilizadas

| Categoria       | Tecnologias/Ferramentas            |
| --------------- | ---------------------------------- |
| Linguagem       | Java 21                            |
| Banco de Dados  | MySQL 8.0                          |
| Migrations      | Flyway                             |
| Autenticação    | JWT                                |
| Documentação    | Swagger (Springdoc OpenAPI)        |
| CI/CD           | GitHub Actions                     |
| Deploy          | Azure Web App                      |
| Containerização | Docker, Docker Compose             |
| Testes          | JUnit 5, Mockito, Spring Boot Test |

## 🔐 Segurança e Autenticação

A segurança é configurada com Spring Security, utilizando autenticação via JWT e controle de acesso por roles (`ADMIN`, `USER`).

### Estratégias adotadas

- Stateless (`SessionCreationPolicy.STATELESS`)
- Filtro JWT (`VerificarToken`)
- Criptografia com `BCryptPasswordEncoder`

### Endpoints públicos

- `/auth/login`, `/auth/register`
- `/swagger-ui.html`, `/v3/api-docs/**`

### Endpoints protegidos

| Recurso                | Método(s) Permitido(s) | Perfil Necessário |
| ---------------------- | ---------------------- | ----------------- |
| `/api/usuarios`        | GET, POST, PUT, DELETE | ADMIN             |
| `/api/limites/**`      | GET                    | ADMIN, USER       |
| `/api/dispositivos/**` | GET                    | ADMIN, USER       |
| `/api/sensores/**`     | GET                    | ADMIN, USER       |
| `/api/consumos/**`     | GET                    | ADMIN, USER       |
| `/api/alertas/**`      | GET                    | ADMIN, USER       |

## ⚙️ Configuração de Ambientes

A aplicação utiliza perfis distintos para desenvolvimento (`dev`) e produção (`prd`), definidos pela variável PROFILE.

### application-dev.properties

- Porta: `8080`
- SQL visível no console
- Stacktrace ativado
- Swagger habilitado
- Flyway ativo
- JWT configurado via `JWT.SECRET`

### application-prd.properties

- SQL oculto
- Stacktrace desativado
- Swagger habilitado
- Flyway ativo
- JWT configurado via `JWT.SECRET`

## 🧪 Testes Automatizados

Os testes são implementados com JUnit 5, Mockito e Spring Boot Test, focando nos controllers e simulando chamadas HTTP com MockMvc.

### Casos de teste

| Cenário de Teste                | Resultado Esperado   |
| ------------------------------- | -------------------- |
| Criar usuário com sucesso       | `201 Created` + JSON |
| Buscar usuário por ID existente | `200 OK` + JSON      |
| Listar todos os usuários        | `200 OK` + JSON      |
| Excluir usuário por ID          | `204 No Content`     |
| Buscar usuário inexistente      | `404 Not Found`      |

### Segurança nos testes

A classe `TestSecurityConfig` desativa autenticação e CSRF para facilitar os testes:

```java
http.csrf(csrf -> csrf.disable());
http.sessionManagement(session -> session.disable());
http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
```

Os testes são executados automaticamente no pipeline após o build.
