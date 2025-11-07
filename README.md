# Encurtador de URL

Este é um serviço de encurtamento de URL construído com Spring Boot e MongoDB. Ele permite aos usuários converter URLs longas em URLs curtas e memoráveis, com expiração automática.

## Funcionalidades

*   **Encurtamento de URL:** Converte URLs longas em hashes curtos e exclusivos.
*   **Expiração Automática:** URLs encurtadas expiram automaticamente e são removidas após 1 hora.
*   **Geração de Hash Personalizada:** Usa Hashids para gerar hashes curtos, não sequenciais e exclusivos.
*   **API RESTful:** Fornece uma API simples para encurtar e redirecionar URLs.

## Tecnologias Utilizadas

*   Java 25
*   Spring Boot 3.5.7
*   Gradle
*   MongoDB
*   Docker e Docker Compose
*   Hashids

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

*   **Java Development Kit (JDK) 25**
*   **Gradle** (geralmente incluído com o wrapper do projeto)
*   **Docker** e **Docker Compose**

## Primeiros Passos

### 1. Iniciar MongoDB com Docker Compose

Este projeto usa MongoDB como seu banco de dados. Para iniciá-lo, navegue até a raiz do projeto e execute o seguinte comando:

```bash
docker compose -f docker/docker-compose.yml up -d
```

Este comando iniciará um contêiner MongoDB em segundo plano, expondo-o na porta `27017`.

### 2. Configuração da Aplicação

A aplicação Spring Boot se conecta ao MongoDB usando credenciais definidas em um arquivo `.env` e configurações em `src/main/resources/application.properties`.

Crie um arquivo `.env` no diretório `docker/` com suas credenciais MongoDB. Por exemplo:

```
MONGODB_USERNAME=seu_usuario
MONGODB_PASSWORD=sua_senha
```

Certifique-se de que `src/main/resources/application.properties` tenha as seguintes configurações para MongoDB e a porta do servidor:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=shortenerdb
spring.data.mongodb.username=${MONGODB_USERNAME}
spring.data.mongodb.password=${MONGODB_PASSWORD}
server.port=8081
hashids.salt=sua_chave_secreta_aqui # IMPORTANTE: Mude para uma chave secreta forte e única
```
**IMPORTANTE:** Para `hashids.salt`, substitua `sua_chave_secreta_aqui` por uma string secreta forte e única. Este "salt" é crucial para a segurança e exclusividade das suas URLs curtas geradas.

## Executando a Aplicação

Após configurar o MongoDB, você pode iniciar a aplicação Spring Boot. Navegue até a raiz do projeto e execute:

```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8081`.

## Endpoints da API

### 1. Encurtar URL

*   **POST /shorten-url**
    *   **Descrição:** Encurta uma URL longa.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "url": "https://www.example.com"
        }
        ```
    *   **Exemplo de Resposta (JSON):**
        ```json
        {
            "url": "http://localhost:8081/abcde"
        }
        ```

### 2. Redirecionar URL

*   **GET /{hash}**
    *   **Descrição:** Redireciona para a URL original associada ao hash fornecido.
    *   **Exemplo de Uso:** Acesse `http://localhost:8081/abcde` no seu navegador.

### 3. Testar Conexão

*   **GET /test**
    *   **Descrição:** Um endpoint simples para verificar se a aplicação está funcionando.
    *   **Exemplo de Resposta:** `O teste funcionou!`

## Como Usar

1.  **Iniciar MongoDB:**
    ```bash
    docker compose -f docker/docker-compose.yml up -d 
    ```

2.  **Iniciar a Aplicação Spring Boot:**
    ```bash
    ./gradlew bootRun
    ```
3.  **Encurtar uma URL:**
    Use `curl` para enviar uma requisição POST:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"url": "https://www.google.com"}' http://localhost:8081/shorten-url
    ```
    Você receberá uma resposta com a URL encurtada.

4.  **Acessar a URL Encurtada:**
    Copie a `url` da resposta e cole-a no seu navegador para ser redirecionado para a URL original.

5.  **Testar a Conexão (Opcional):**
    Abra seu navegador ou use `curl`:
    ```bash
    curl http://localhost:8081/test
    ```
    Você deverá ver a mensagem: `O teste funcionou!`