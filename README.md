# URLShortener

Uma aplicação web moderna para encurtamento de URLs, construída com Spring Boot. Converta URLs longas em identificadores curtos, únicos e memoráveis com expiração automática.

## 🎯 Visão Geral

URLShortener é um serviço RESTful que fornece encurtamento de URLs com segurança, validação rigorosa e expiração automática. Utiliza a biblioteca Hashids para gerar hashes curtos, não sequenciais e imprevisvéis, garantindo URLs únicas e seguras.

## ✨ Funcionalidades

- **Encurtamento Inteligente:** Converte URLs longas em hashes curtos e únicos usando Hashids
- **Validação Robusta:** Valida o formato e o esquema (HTTP/HTTPS) das URLs
- **Expiração Automática:** URLs encurtadas expiram automaticamente após 1 hora
- **API RESTful:** Interface simples para integração com outras aplicações
- **Interface Web:** Frontend intuitivo para usuários finais
- **CORS Habilitado:** Suporta requisições de diferentes origens
- **Logging Detalhado:** Registra todas as operações para auditoria

## 🛠️ Tecnologias

| Componente | Versão |
|-----------|--------|
| Java | 21 |
| Spring Boot | 3.5.7 |
| Gradle | (wrapper) |
| Hashids | 1.2.0 |
| MongoDB | Latest |
| Docker & Compose | Latest |
| Thymeleaf | 3.1.x |

**Nota:** MongoDB está configurado no `docker-compose.yml`, mas a integração está atualmente comentada no `build.gradle`. O projeto usa armazenamento em memória com `ConcurrentHashMap`.

## 📋 Pré-requisitos

- **Java Development Kit (JDK) 21+**
- **Docker & Docker Compose**
- **Git** (para clonar o repositório)

## 🚀 Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone https://github.com/joaoMiguelSousaOliveira/shortener.git
cd urlshortener
```
### 2. Configurar as variáveis do ambiente
```bash
MONGODB_USERNAME=admin
MONGODB_PASSWORD=seu_senha_segura
```
### 3. Iniciar MongoDB
```bash
docker compose -f docker/docker-compose.yml up -d
```
O MongoDB estará disponível em localhost:27017.
### 4. Configurar `application.properties`
```
spring.application.name=urlshortener
server.port=8081
hashids.salt=A-R-aNd0m-PhR4s3-With_Symbol5_AnD-NuMb3r5_T0_M4k3_It_Str0nG_G_G
```