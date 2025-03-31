# Order Book Challenge - Challenge PicPay
 Esse é um Desafio para o desafio Tecnico da Picpay , Que consistem em uma API para gerenciamento de ordens de compra e venda de ativos. A API permite criar, listar, atualizar e excluir ordens, além de validar a quantidade mínima de itens e buscar ordens por ID ou símbolo.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Docker**
- **JUnit 5** (Testes automatizados)
- **Mockito** (Testes unitários)
- **Lombok** (Simplificação do código)

---

## Configuração do Projeto

### Pré-requisitos

- **JDK 21**
- **Maven 3+**
- **Docker** (para rodar o banco de dados PostgreSQL)

## Como Rodar a Aplicação

### Passo 1: Subir o banco de dados com Docker
```sh
docker-compose up -d
```

### Passo 2: Compilar o projeto
```sh
mvn clean install
```

### Passo 3: Executar a aplicação
```sh
mvn spring-boot:run
```

---

## Endpoints

### Criar uma Ordem
**POST** `/orders`
```json
{
  "symbol": "GOOG",
  "quantity": 10,
  "price": 100.0,
  "side": "BUY"
}
```

### Buscar Ordem por ID
**GET** `/orders/{id}`

### Listar Todas as Ordens
**GET** `/orders`

### Listar Ordens por Símbolo
**GET** `/orders/symbol/{symbol}`

### Atualizar Ordem
**PUT** `/orders/{id}`
```json
{
  "symbol": "GOOG",
  "quantity": 15, # Alterando de 10 para 15
  "price": 105.00,
  "side": "SELL"
}
```

### Deletar Ordem
**DELETE** `/orders/{id}`

---

## Testes Automatizados


Para rodar os testes:
```sh
mvn test
```

---

## Estrutura do Projeto

```
📂 src
 ├── 📂 main
 │   ├── 📂 java/com/picpay/banking/interview
 │   │   ├── 📂 controller (Controladores REST)
 │   │   ├── 📂 domain (Modelos de Dados)
 │   │	 ├───── 📂 repository (Camada de Persistência)
 │   │   ├── 📂 dto (Objetos de Transferência de Dados)
 │   │   ├── 📂 exceptions (Tratamento de Erros)
 │   │   ├── 📂 service (Regras de Negócio)
 │   ├── 📂 resources (Configurações e Migrations)
 │
 ├── 📂 test (Testes Unitários)
```


