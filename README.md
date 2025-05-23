# 📦 Klok Pedido - Desafio Técnico

Esta é a entrega do projeto desenvolvido para o desafio técnico da empresa **Klok**, com o objetivo de refatorar e otimizar o serviço de pedidos, melhorando sua legibilidade, manutenção e eficiência.

---

## ✅ Objetivo

Refatorar a classe `PedidoService`, mantendo a lógica de negócio clara, bem organizada e com testes unitários cobrindo os principais fluxos.

---

## 🧰 Tecnologias utilizadas

- Java 17+
- Spring Boot
- Maven
- JUnit 5
- Mockito
- VS Code / IntelliJ IDEA

---

## 🗂️ Estrutura do projeto

```
src/
├── main/
│   └── java/br/com/klok/pedidos/
│       ├── controller/            # Controllers REST
│       ├── dto/                   # (opcional) DTOs
│       ├── mapper/                # (opcional) mapeadores
│       ├── model/                 # Modelos: Pedido, Item, Cliente
│       ├── service/               # Lógica principal (PedidoService)
│       │   └── helper/            # Serviços auxiliares: estoque, total, notificação
│       └── KlokPedidoApplication  # Main Spring Boot
│
├── test/
│   └── java/br/com/klok/pedidos/
│       └── PedidoServiceTest      # Testes unitários com Mockito
```

---

## 🚀 Como rodar o projeto

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/klok-pedidos.git
cd klok-pedidos
```

2. Compile o projeto:

```bash
./mvnw clean install
```

3. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## 📬 Endpoint disponível

### POST `/api/pedidos`

Processa uma lista de pedidos e aplica as regras de negócio.

**Exemplo de corpo da requisição:**
Veja o arquivo [`pedido-exemplo.json`](./pedido-exemplo.json)

**Curl:**

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d @pedido-exemplo.json
```

---

## 🧪 Rodar os testes

```bash
./mvnw test
```

---

## Código fonte original em java

```

public class PedidoService {

    public void processarPedidos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            double total = 0;

            for (Item item : pedido.getItems()) {
                total += item.getPreco() * item.getQuantidade();
            }

            pedido.setTotal(total);

            if (pedido.getCliente().isVip()) {
                total *= 0.9;
            }

            pedido.setTotalComDesconto(total);

            boolean emEstoque = true;
            for (Item item : pedido.getItems()) {
                if (item.getQuantidade() > item.getEstoque()) {
                    emEstoque = false;
                    break;
                }
            }
            pedido.setEmEstoque(emEstoque);

            if (emEstoque) {
                pedido.setDataEntrega(LocalDate.now().plusDays(3));
            } else {
                pedido.setDataEntrega(null);
            }

            if (emEstoque) {
                enviarNotificacao(pedido.getCliente().getEmail(), "Seu pedido será entregue em breve.");
            } else {
                enviarNotificacao(pedido.getCliente().getEmail(), "Um ou mais itens do seu pedido estão fora de estoque.");
            }
        }
    }

    private void enviarNotificacao(String email, String mensagem) {
        System.out.println("Enviando e-mail para " + email + ": " + mensagem);
    }
}


```

## 🧠 Regras de negócio implementadas

- Cálculo do total do pedido com base em itens.
- Aplicação de desconto para clientes VIP.
- Verificação de estoque.
- Definição da data de entrega com base na disponibilidade.
- Envio de notificação por e-mail (simulado via `System.out`).

---

## 📬 Contato

Desenvolvido por [Renan Gondim Dias de Albuquerque] — [renangdias18@gmail.com]

---