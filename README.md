# Untitled

Status: Não iniciado

## Descrição do projeto

O Sistema de Transações com Spring Boot é uma aplicação desenvolvida para fins didáticos, utilizando a robustez do framework Spring Boot para fornecer uma arquitetura escalável e de fácil manutenção.

## Objetivo

Criar um sistema que oferecer funcionalidades para a gestão eficiente de transações, permitindo o registro de transações, consulta de histórico, processamento de transferências entre contas e geração de relatórios financeiros

**Árvore do Projeto:**

```jsx

├── src/main/java/br/com/tgid/TgidTestJavaDeveloper
│   │   │                   ├── DTOs
│   │   │                   │   ├── CompanyResponseDTO.java
│   │   │                   │   ├── CreateClientDTO.java
│   │   │                   │   ├── CreateCompanyDTO.java
│   │   │                   │   ├── CreateTransactionDTO.java
│   │   │                   │   ├── SuccessCompanyListResponse.java
│   │   │                   │   ├── SuccessOrFailureResponse.java
│   │   │                   │   └── TransactionResponseDTO.java
│   │   │                   ├── TgidTestJavaDeveloperApplication.java
│   │   │                   ├── controllers
│   │   │                   │   ├── ClientController.java
│   │   │                   │   ├── CompanyController.java
│   │   │                   │   └── TransactionController.java
│   │   │                   ├── exceptions
│   │   │                   │   ├── ClientServiceException.java
│   │   │                   │   ├── CompanyServiceException.java
│   │   │                   │   └── TransactionServiceException.java
│   │   │                   ├── models
│   │   │                   │   ├── Client.java
│   │   │                   │   ├── Company.java
│   │   │                   │   ├── Transaction.java
│   │   │                   │   ├── User.java
│   │   │                   │   └── UserType.java
│   │   │                   ├── repositories
│   │   │                   │   ├── ClientRepository.java
│   │   │                   │   ├── CompanyRepository.java
│   │   │                   │   └── TransactionRepository.java
│   │   │                   ├── services
│   │   │                   │   ├── ClientAlert.java
│   │   │                   │   ├── ClientService.java
│   │   │                   │   ├── CompanyService.java
│   │   │                   │   ├── CompanyWebhook.java
│   │   │                   │   ├── TransactionService.java
│   │   │                   │   └── clientImpl
│   │   │                   │       ├── ClientServiceImpl.java
│   │   │                   │       ├── CompanyServiceImpl.java
│   │   │                   │       ├── DummyClientAlertImpl.java
│   │   │                   │       ├── HttpCompanyWebhookImpl.java
│   │   │                   │       └── TransactionServiceImpl.java
│   │   │                   ├── transport
│   │   │                   └── utils
│   │   │                       ├── CNPJUtil.java
│   │   │                       └── CPFUtil.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── br
│               └── com
│                   └── tgid
│                       └── TgidTestJavaDeveloper
│                           ├── TgidTestJavaDeveloperApplicationTests.java
│                           ├── models
│                           │   ├── ClientTest.java
│                           │   └── UserTest.java
│                           ├── services
│                           │   └── clientImpl
│                           │       ├── ClientServiceImplTest.java
│                           │       ├── CompanyServiceImplTest.java
│                           │       └── TransactionServiceImplTest.java
│                           └── utils
│                               └── CNPJUtilTest.java

```

A estrutura do projeto pode ser resumida da seguinte forma:

- **DTOs (Data Transfer Objects):** Responsáveis por transferir dados entre as camadas da aplicação.
- **Controladores (Controllers):** Manipulam as requisições HTTP, direcionando o fluxo para os serviços apropriados.
- **Exceções (Exceptions):** Personalizadas para lidar com situações específicas de erro durante a execução da aplicação.
- **Modelos (Models):** Representam as entidades do domínio, como clientes, empresas, transações e usuários.
- **Repositórios (Repositories):** Lidam com a persistência e recuperação de dados no banco de dados.
- **Serviços (Services):** Implementam a lógica de negócios, incluindo operações relacionadas a clientes, empresas, webhooks e transações.
- **Transporte:** Possíveis componentes relacionados ao transporte de dados.
- **Utilitários (Utils):** Funções utilitárias para validação e manipulação de dados específicos, como CPFs e CNPJs.
- **Recursos (Resources):** Contém o arquivo de configuração da aplicação e diretórios para recursos estáticos e templates.
- **Testes:** Inclui testes unitários para garantir a robustez das funcionalidades implementadas.

A estrutura do projeto consiste na camada de controller onde eu estou recebendo os requests http por meio de tres grupos, /api/user /api/company /api/transaction e São repassados as informações de entradas por meio de DTOs para os serviços onde esta a lógica de negocio. Tudo isso seguindo as convenções e boas práticas do ecossistema Spring Boot.

Dentro da camada de DTO é onde eu estou concentrando os dados para poder trafegar para os services.

### **APIs e Endpoints:**

A API oferece endpoints RESTful para realizar operações como:

- **`POST/api/user/create` Cria** um cliente.

para criar um novo usuario e necessario colocar esse payload:

```jsx
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Fernando Souza",
  "cpf": "664.095.440-06",
  "email": "souza.fernando@example.com",
  "password": "ada",
  "phone": "+551234587891"
}' http://localhost:8080/api/user/create

response success: 

{
    "success": true,
    "msg": "Client created successfully"
}

response error cpf:

{
    "success": false,
    "msg": "invalid cpf format cpf: .095.440-06"
}

response error cpf already exists:

{
    "success": false,
    "msg": "Client with cpf '664.095.440-06 already exists"
} 
```

- **`POST/api/company/create` Existe duas rotas, uma** para criar uma company e outra para listagem das companies existentes.

A rota de criar uma nova company utiliza esse payload:

```jsx
curl -X POST -H "Content-Type: application/json" -d '{
  "cnpj": "36.451.132/0001-87",
  "fee": 0.2,
  "email": "vox@mixas.com",
  "phone": "+551234588890",
  "name": "tomaxbux",
  "password": "companyPassword123"
}' http://localhost:8080/api/company/create

response success:

{
    "success": true,
    "msg": "Company created successfully"
}
```

A rota de listagem de companies e feita com esse payload:

```jsx
curl http://localhost:8080/api/company/list

response success:

{
    "success": true,
    "msg": "all companies",
    "companies": [
        {
            "name": "americanas",
            "cnpj": "51501889000121"
        },
        {
            "name": "casa do norte",
            "cnpj": "36451132000187"
        }
    ]
}
```

- **`POST/api/transaction/create` Cria uma transação entre o cliente e a company.**

Por fim, temos a rota de create transection, que utiliza esse payload:

```jsx
curl -X POST -H "Content-Type: application/json" -d '{
  "clientCpf": "66409544006",
  "companyCnpj": "36451132000187",
  "amount": 100.50
}' http://localhost:8080/api/transaction/create

response success:

{
    "success": true,
    "meg": "Transaction processed successfully",
    "transaction": {
        "id": 16,
        "client": {
            "id": 3,
            "name": "Fernando Souza",
            "createdAt": "2024-02-04T23:19:01.24072",
            "updatedAt": "2024-02-04T23:19:01.240731",
            "userType": "CLIENT",
            "email": "souza.fernando@example.com",
            "phone": "+551234587891",
            "cpf": "66409544006"
        },
        "company": {
            "id": 4,
            "name": "casa do norte",
            "createdAt": "2024-02-05T05:11:28.019107",
            "updatedAt": "2024-02-05T08:39:21.295473",
            "userType": "COMPANY",
            "email": "comercial@casanorte.com",
            "phone": "+551234588890",
            "cnpj": "36451132000187",
            "balance": 1005.0000,
            "fee": 0.2
        },
        "amount": 80.4000,
        "rawAmount": 100.50,
        "feeAppliedOnThisOperation": 0.2,
        "timestamp": "2024-02-05T08:39:21.722202"
    }
}
```

---

<div align="center">Feito por <a href="[https://github.c](https://github.com/elidianaandrade)om/thmonteles">Thainá Monteles</a>.</div>

---