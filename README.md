# order-book-challenge

**PicPay - Entrevista Java**

**1. Regras de Negócio - Criação de Order (Pedido)**
Implemente a funcionalidade para criação de pedidos (Order), garantindo que apenas pedidos com quantidade mínima de 2 itens sejam aceitos.

**Regras:**
- Ao tentar criar um pedido, verifique se a quantidade total de itens é maior ou igual a 2.
- Se a regra for atendida, o pedido deve ser criado normalmente.
- Caso contrário, a operação deve ser interrompida e uma mensagem de erro clara deve ser retornada ao usuário, informando que a quantidade mínima exigida não foi atingida.
Exemplo de Resposta de Erro:

```json
{
  "error": "INVALID_ORDER",
  "message": "O pedido deve conter pelo menos 2 itens."
}
```

**2. Testes unitários** 
- Existem testes unitários que estão quebrando na classe OrderBookServiceImplTest. Crie a implementação correta para 
resolver os problemas nos testes unitários
- Crie testes unitários para a criação de Order (Pedido) contemplando a regra descrita

 

