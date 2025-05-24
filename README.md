# Sistema de Pedidos - Desafio Estágio Klok

Este projeto é uma solução para o desafio técnico proposto pela **Klok**, que consiste em refatorar e otimizar um serviço de pedidos com foco em clareza, legibilidade, manutenção e testes.

## 📌 Objetivo

Refatorar a lógica de negócios do serviço `PedidoService`, separando responsabilidades e garantindo maior organização, reutilização e testabilidade, utilizando **Spring Boot**.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- Maven

---

## ⚙️ Funcionalidades

- Criação de pedidos com cliente e itens associados
- Cálculo de total e total com desconto para clientes VIP
- Verificação de disponibilidade em estoque
- Definição de data de entrega
- Envio de notificação (simulado via terminal)
- Testes unitários cobrindo os cenários principais

---

## 🧱 Estrutura do Projeto

```
src/
├── controller/
│   └── PedidoController.java
├── dto/
│   ├── request/
│   └── response/
├── mapper/
│   ├── PedidoMapper.java
│   ├── ClienteMapper.java
│   └── ItemMapper.java
├── model/
│   ├── Pedido.java
│   ├── Cliente.java
│   ├── Item.java
│   └── ItemPedido.java
├── repository/
│   ├── ClienteRepository.java
│   ├── ItemRepository.java
│   └── PedidoRepository.java
├── service/
│   ├── PedidoService.java
│   ├── ItemService.java
│   └── helper/
│       ├── EstoqueService.java
│       ├── NotificacaoService.java
│       └── TotalService.java
├── test/
│    └── PedidoServiceTest.java
└── KlokPedidoApplication
```

---

## 📩 Endpoints

### Criar Pedido

```http
POST http://localhost:8080/pedidos/criar
```

#### Exemplo de Request Body

```json
{
  "clienteId": 1,
  "data": "2025-05-23",
  "itens": [
    {
      "itemId": 1,
      "quantidade": 3
    }
  ]
}
```

### Listar Pedidos

```http
GET http://localhost:8080/pedidos/listar
```

### Buscar Pedido

```http
GET http://localhost:8080/pedidos/{id}
```

### Deletar Pedido

```http
DELETE http://localhost:8080/pedidos/{id}
```

### Criar Item

```http
POST http://localhost:8080/itens
```

#### Exemplo de Request Body

```json
{
  "nome": "Camiseta",
  "preco": 59.90,
  "estoque": 10
}

```

### Buscar Item

```http
GET http://localhost:8080/api/itens/{id}
```

### Criar Cliente

```http
POST http://localhost:8080/api/clientes
```

#### Exemplo de Request Body

```json
{
  "nome": "João da Silva",
  "email": "joao@exemplo.com",
  "vip": true
}

```

### Buscar Cliente

```http
GET /api/pedidos/criar
```

📌 **Nota:** A notificação ao cliente é exibida no terminal da aplicação, conforme exigido no desafio. Exemplo de saída:

```
Enviando e-mail para joao@exemplo.com: Seu pedido será entregue em breve.
```

📌 **Nota:** Nunca crie o pedido antes de ter criado o cliente e o item anteriormente!

---

## 🧪 Testes

Os testes de unidade estão implementados para o `PedidoService`, utilizando **JUnit** e **Mockito**, validando os seguintes casos:

- Pedido de cliente VIP com itens em estoque
- Pedido de cliente VIP com itens fora de estoque
- Pedido de cliente não-VIP
- Múltiplos pedidos com diferentes condições

Para rodar os testes:

```bash
./mvnw test
```

---

## 📂 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/RenanGDias/estagio-klok
   cd nome-do-repo
   ```

2. Execute o projeto com o Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o Postman ou outro cliente e execute os endpoints! Aproveite!
   

---

## ✅ Requisitos atendidos

- [x] Serviço de pedidos refatorado com boas práticas
- [x] Testes unitários implementados
- [x] Projeto disponibilizado em repositório Git
- [x] Mensagem de notificação exibida no terminal
- [x] Utilização de Spring Boot

---

## 🏫 OBSERVAÇÃO IMPORTANTE!

- Eu tenho muito orgulho de dizer que me especializei na área de desenvolvimento Java com Spring Boot + Java Persistency API + Bancos de Dados (H2-Console/PostgreSQL/MySQL) e hoje pude demonstrar minha capacidade de desenvolver um sistema completo desta forma! Mas não para por aí: desenvolvi também um curso completo de modo a apresentar um pouco mais do meu portfólio, minha trajetória e minhas conquistas! Você pode acessá-lo clicando no link abaixo (OBS: Caso note o design no nome de LucasGabriel, saiba que solicitei o Canva Pro dele para desenvolver este minicurso, mas é de autoria completa minha!):

[Minicurso Introdução ao Java Spring Boot](https://www.canva.com/design/DAGkEDuDoaI/x0xoQT1upXphpTM9ugbAcQ/edit?utm_content=DAGkEDuDoaI&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

https://www.canva.com/design/DAGj4R1LfHo/nPQd6y3gI2r9ZcgSdlPrBg/edit?utm_content=DAGj4R1LfHo&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

https://www.canva.com/design/DAGkLssY18A/_NVSDXoFCETmaUbOZJXKtQ/edit?utm_content=DAGkLssY18A&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

---

## 📧 Contato

Desenvolvido por **Renan Gondim Dias de Albuquerque**  
📫 Email: [renangdias18@gmail.com](mailto:renangdias18@gmail.com)  
💼 LinkedIn: [www.linkedin.com/in/renan-albuquerque-38661a284](https://www.linkedin.com/in/renan-albuquerque-38661a284)