# HubSpot Integration API

Este projeto tem como objetivo demonstrar a integração com a API do HubSpot utilizando OAuth 2.0 (Authorization Code Flow), com a implementação de endpoints para geração de URL de autorização, troca de código por token, criação de contatos e recebimento de webhooks.

- Projeto publicado em :https://hubspot-api-968271651730.us-central1.run.app/swagger-ui/index.html
- Para realizar o processo de autenticação siga os passos em: **Descrição do fluxo:**
- O arquivo com a descrição problema está em : https://github.com/uczak/meetime/blob/main/src/main/resources/static/doc.pdf?raw=true
## 📌 Objetivo

Avaliar habilidades técnicas em:

- Implementação de API REST com Java Spring Boot.
- Integração com API externa (HubSpot).
- Fluxo de autenticação OAuth 2.0.
- Gerenciamento de webhooks.
- Boas práticas de segurança, organização de código e tratamento de erros.

## 🛠️ Melhorias Futuras
- A API Webhook não esta utilizando os eventos que recebe, apenas grava logs. Poderimos implementar 
um monitoramento para identificar o volume de contatos criados em um périodo X.
- Em primeira melhoria poderiamos implemtar um Redis para armazenar o access_token.
Para uma implementação mais solida podemos adicionar uma camada de identificação 
do Client. A API atual não faz distinção de requisiçoes, e desta forma não consegue
identificar qual o client que fez a primeira requisição e se autenticou. Poderiamos
armazenar o access_token por User em um Banco não relacional, desta 
forma saberiamos se o client já está autenticado.
 

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot 2.7.11
- Spring Web
- Spring Cache (para armazenamento de access_token)
- gradle
- Lombok

## 🧩 Funcionalidades

| Endpoint                       | Descrição                                                                    |
|--------------------------------|------------------------------------------------------------------------------|
| `GET  /auth/authorize-url`     | Gera e retorna a URL de autorização para iniciar o fluxo OAuth com o HubSpot |
| `GET  /auth/callback?code=...` | Processa o código de autorização e troca pelo access_token                   |
| `POST /contacts`               | Cria um contato no CRM HubSpot                                               |
| `GET  /contacts?count=...`     | Bsuca o número de contatos não arquivados no CRM HubSpot                     |
| `POST /webhook/hubspot`        | Recebe eventos de criação de contatos via webhook do HubSpot                 |

---

## 🔐 Autenticação OAuth 2.0

A aplicação utiliza o fluxo **Authorization Code Flow**, conforme documentação oficial do HubSpot.

### 🔁 Fluxograma do Fluxo OAuth 2.0
 ![Fluxo OAuth](https://github.com/uczak/meetime/blob/main/src/main/resources/static/hubspot-oauth-flow.PNG?raw=true)

**Descrição do fluxo:**

1. A aplicação gera a URL de autorização (`/auth/url`). https://hubspot-api-968271651730.us-central1.run.app/oauth/authorize-url
2. O usuário é redirecionado ao HubSpot e autoriza o app.
3. O HubSpot envia o `code` ao endpoint de callback (`/auth/callback`).
4. A aplicação troca o `code` pelo `access_token`. O token é armazenado em cache para reutilização em chamadas à API do HubSpot.
5. O cliente já pode fazer requisições para o server, para a criação de 
contatos. Acesse o swagger para realizar a chamda de cadastro de contato 
ou utilize um client externo como Postman. Exemplo de requisição:
  ```bash
   curl -X 'POST' \
   'https://hubspot-api-968271651730.us-central1.run.app/contact' \
   -H 'accept: application/json' \
   -H 'Content-Type: application/json' \
   -d '{
   "email": "guilherme.uczak@gmail.com",
   "lastname": "Uczak",
   "firstname": "Guilherme"
   }'
   ```
6. O server autenticado faz a integração com o hubspot para criar o contato.
7. A API Webhook recebe a notificação do registro criado.
---

## 📬 Webhook

- Endpoint configurado para receber eventos do tipo `contact.creation`
- Processamento do evento e logging das informações.
- exemplo:
  ![Fluxo OAuth](https://github.com/uczak/meetime/blob/main/src/main/resources/static/webhook-event.PNG?raw=true)
---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Gradle
- Conta no HubSpot com um app registrado

### Configuração

Edite o arquivo `application.yml` como **DEV** para rodar locamente ou **HLG** para rodar em cloud.

---
### Executar testes localmente
- Execute o comando a seguir para rodar os testes unitários:
  ```bash
  ./gradlew test --info

---
### Executar local
- Limpa a lib gerada e executa um novo build:
  ```bash
  ./gradlew clean build
- Executa a aplicação local:
  ```bash
  ./gradlew bootRun
- Acesse o swagger da aplicação: http://localhost:8080/swagger-ui.html

---
### Executar em ambiente remoto - ☁️ Deploy no Google Cloud Platform (GCP)

Este projeto pode ser facilmente implantado no Google Cloud usando o serviço **Cloud Run** com container Docker.

### 📦 Etapas para Deploy via Cloud Run

- Criar uma conta no Google Cloud que já vem com alguns créditos por 90 dias.
- Criar um Projeto GCP.
- SDK do Google Cloud instalado.
- Billing ativado no projeto.
- Conceder acesso ao user:
- - Acessar o painel IAM
- - Editar a Role do User e adicionar as Roles **Cloud Builder Editor** e **Storage Admin**
- Na pasta raiz do projeto execute os seguintes comandos:
- Habilita as APIs:
  ```bash
  gcloud services enable run.googleapis.com
  gcloud services enable containerregistry.googleapis.com
- Autenticação com o mesmo user que concedeu as permissoes:
  ```bash
  gcloud auth login
- Lista os projetos:
  ```bash
  gcloud projects list
- Salve o [PROJECT_ID] do projeto criado.
- Configura a sessão atual para o projto:
  ```bash
  gcloud config set project [PROJECT_ID]

- defina o ambiente como hlg na variável:
  ```
  spring:
    profiles:
      active: hlg
- Execute o comando a seguir para  descobrir qual número do projeto criado:
  ```bash
  gcloud projects describe [PROJECT_ID]
- Salve o número e monte sua URL base:
-  - https://hubspot-api-[PROJECT_NUMBER].us-central1.run.app
- Altere o arquivo application-hlg.yml com a su URL base[BASE_URL]:
  ```properties
    hubspot:
      client-id: d90039b5-b654-4caa-8dad-c50d2bcd310b
      client-secret: ef84b9cc-ad72-4191-99ee-0e65ee75e50e
      redirect-uri: [BASE_URL]/oauth/callback
      server-url: [BASE_URL]
      url: https://api.hubapi.com
      scope: "oauth crm.objects.contacts.read crm.objects.contacts.write"
- Limpa a lib gerada e executa um novo build:
  ```bash
  ./gradlew clean build
- Cria uma imagem docker e envia para o Container Registry:
  ```bash
  gcloud builds submit --tag gcr.io/[PROJECT_ID]/hubspot-api
- Executa o deploy da aplicação no Cloud Run:
  ```bash
  gcloud run deploy hubspot-api \
  --image gcr.io/[PROJECT_ID]/hubspot-api \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
  ```
- Acesse https://hubspot-api-[PROJECT_NUMBER].us-central1.run.app/swagger-ui.html



