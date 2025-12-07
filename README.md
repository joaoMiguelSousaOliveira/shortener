# 🔗 URLShortener

Uma aplicação web moderna para encurtamento de URLs, construída com Spring Boot. Converta URLs longas em identificadores curtos, únicos e memoráveis com expiração automática.

---

## 📋 Visão Geral

**URLShortener** é um serviço RESTful completo que fornece encurtamento de URLs com segurança, validação rigorosa e expiração automática. A aplicação utiliza a biblioteca **Hashids** para gerar hashes curtos, não sequenciais e imprevisíveis, garantindo URLs únicas e seguras.

Desenvolvido como projeto acadêmico para a disciplina de **LPII** (Linguagem de Programação II) - BTI.

---

## ✨ Funcionalidades

- ✅ **Encurtamento Inteligente:** Converte URLs longas em hashes curtos e únicos usando Hashids
- ✅ **Validação Robusta:** Valida o formato e o esquema (HTTP/HTTPS) das URLs
- ✅ **Expiração Automática:** URLs encurtadas expiram automaticamente após 1 hora
- ✅ **API RESTful:** Interface simples e bem estruturada para integração com outras aplicações
- ✅ **Interface Web:** Frontend intuitivo e responsivo para usuários finais
- ✅ **CORS Habilitado:** Suporta requisições de diferentes origens
- ✅ **Logging Detalhado:** Registra todas as operações para auditoria e depuração
- ✅ **Redirecionamento Automático:** Redireciona para a URL original com status HTTP 302 (FOUND)
- ✅ **Tratamento de Erros:** Validações completas e respostas HTTP apropriadas

---

## 🛠️ Stack Tecnológico

| Componente | Versão |
|-----------|--------|
| **Java** | 21 (LTS) |
| **Spring Boot** | 3.5.7 |
| **Spring Data MongoDB** | Incluído no Spring Boot 3.5.7 |
| **Spring Web** | 3.5.7 |
| **Thymeleaf** | 3.1.x |
| **Hashids** | 1.2.0 |
| **MongoDB** | Última versão (Docker) |
| **Gradle** | Wrapper (8.x+) |
| **Docker & Compose** | Última versão |

---

## 📦 Estrutura do Projeto
```
urlshortener/
├── src/
│ ├── main/
│ │ ├── java/LPII/urlshortener/
│ │ │ ├── UrlshortenerApplication.java # Classe principal e configuração do Hashids
│ │ │ ├── controller/
│ │ │ │ ├── UrlController.java # Endpoints da API
│ │ │ │ ├── TestController.java # Endpoint de teste
│ │ │ │ ├── dto/
│ │ │ │ │ ├── ShortUrlRequest.java # Request DTO
│ │ │ │ │ └── ShortUrlResponse.java # Response DTO
│ │ │ │ ├── entities/
│ │ │ │ │ └── Url.java # Entidade MongoDB
│ │ │ │ └── repositories/
│ │ │ │ └── UrlRepository.java # Repository MongoDB
│ │ │ └── resources/
│ │ │ ├── application.properties # Configurações da aplicação
│ │ │ ├── templates/
│ │ │ │ └── index.html # Interface web
│ │ │ └── static/
│ │ │ ├── index.css # Estilos
│ │ │ └── script.js # Lógica frontend
│ │ └── test/
│ │ └── java/LPII/urlshortener/
│ │ └── UrlshortenerApplicationTests.java # Testes
│ └── docker/
│ └── docker-compose.yml # Configuração MongoDB
├── build.gradle # Dependências Gradle
├── settings.gradle # Configurações Gradle
├── gradlew & gradlew.bat # Gradle wrapper
└── README.md # Este arquivo
```
---

## 📋 Pré-requisitos

### Opção 1: Com Docker (Recomendado)
- **Docker & Docker Compose** instalados
- **Java Development Kit (JDK) 21+**
- **Git** (para clonar o repositório)

---

## 🚀 Instalação e Configuração

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/joaoMiguelSousaOliveira/shortener.git
cd urlshortener
```
### 2️⃣ Criar o arquivo `.env`

```bash
cd docker
cat > .env << EOF
MONGODB_USERNAME=admin
MONGODB_PASSWORD=sua_senha_segura_aqui
EOF
cd ..
```
### 3️⃣ Configurar `application.properties`
```bash
spring.application.name=urlshortener
server.port=8081

# Configuração MongoDB
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=shortenerdb
spring.data.mongodb.username=admin
spring.data.mongodb.password=sua_senha_segura_aqui

# Secret key do Hashids - Garante que os HashIDs sejam únicos e imprevisíveis
hashids.salt=A-R-aNd0m-PhR4s3-With_Symbol5_AnD-NuMb3r5_T0_M4k3_It_Str0nG_G_G

# Configuração do Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false

# Configuração dos recursos estáticos
spring.web.resources.static-locations=classpath:/templates/,classpath:/static/,classpath:/public/
spring.mvc.static-path-pattern=/static/**
```
### 4️⃣ Compilar e Executar
```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

## 🌐 Acessar a Aplicação
### Após iniciar o servidor:

* **`Interface Web`**: http://localhost:8081
* **`API de Encurtamento`**: `POST` http://localhost:8081/shorten-url
* **`Redirecionamento`**: `GET` http://localhost:8081/{hash}
* **`Teste`**: `GET` http://localhost:8081/test

## 📡 API REST
### 1. Encurtar URL
**Endpoint**: `POST /shorten-url`<br>
**Headers**:
```
Content-Type: application/json
```

**Resquest Body**:
```JSON
{
  "url": "https://www.exemplo.com/url/extensa/demasiadamente"
}
```

**Response (Sucesso - 200 OK)**:
```JSON
{
  "url": "http://localhost:8081/{hash}"
}
```

**Response (Erro - 400 Bad Request)**
* URL vazia ou inválida
* Esquema não é HTTP/HTTPS

**Observação**: O front-end trata e evita que urls com essas condições acionem alguma requisição para a nossa API

**Response (Erro - 500 Internal Server Error)**
* Erro ao conectar com MongoDB

### 2. Redireiciona para a URL Original
**Endpoint**: `GET /{hash}`

**Response (Erro - 302 Bad Request)**
* Redireciona para a URL original
* Define header `Location` com a URL original


**Response (Erro - 404 Not Found)**
* Hash não existe
* Url expirou (após 1 hora)

### 3. Endpoint de Teste
**Endpoint**: `GET /test`

**Response**
```
O teste funcionou!
```

## 🎨 Interface Web
A aplicação inclui uma interface web intuitiva e responsiva em `index.html`.

Recursos:
* ⚡ Validação em tempo real no frontend
* 🎯 Feedback visual com animações
* 📋 Botão para copiar URL encurtada

### Como usar
**Acesse**: http://localhost:8081

1. Cole uma URL longa no campo de entrada
2. Clique em "Encurtar"
3. Copie a URL encurtada
4. Compartilhe!

### 🔐 Segurança
* ✅ **Validação de URLs**: Apenas URLs com esquema HTTP/HTTPS são aceitas
* ✅ **CORS Habilitado**: Permite requisições de qualquer origem (configurável)
* ✅ **Hashids**: Utiliza salt criptográfico para gerar hashes imprevisíveis
* ✅ **Expiração Automática**: URLs expiram após 1 hora com TTL do MongoDB
* ✅ **Logging**: Todas as operações são registradas

### ⏰ Expiração de URLs
* **Tempo de Expiração**: 1 hora (3600 segundos)
* **Mecanismo**: TTL (Time To Live) do MongoDB
* **Comportamento**: URLs expiradas retornam 404 e são deletadas do banco

Quando uma ULR é encurtada:
```bash
LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);
```

## 👨‍💻 Autor
**João Miguel Sousa Oliveira** <br>
**Projeto acadêmico** - `Disciplina LPII` (Linguagem de Programação II) - **`BTI`**

## 📄 Licença
Este projeto é fornecido como está para fins educacionais.

**Última atualização**: 6 de dezembro de 2025 <br>
**Versão**: 0.0.1-SNAPSHOT