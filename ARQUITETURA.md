# Order book

O que preciso fazer ?

- Permitir que usuários coloquem ordens de compra e venda de Vibranium;
- Créditos e Débitos efetuados corretamente quando uma compra/venda for efetuada;
- Compra e Venda podem ser feita "em trade" Ex.: Mandei uma ordem de compra para quando o valor de vibranium estiver em x reais
- Apresentar uma solução escalável que aceite 5000 rps;
- Transações com rastreabilidade;
- Histórico de transações;


## Arquitertura
![MELI drawio (1)](https://user-images.githubusercontent.com/12643220/159602408-f73da6b6-38b8-4f63-bdac-2151d4966901.png)

### Banco de dados

- Escolhi o Dynamo considerando um banco auto-gerenciado o qual não preciso me preocupar com scale e disponibilidade, salvo a preocupação com as configurações que seriam feitas considerando WCU e LCU de acordo com o volume das requisições;
- Na modelagem usando o tipo do evento como hash key irá me permitir fazer uma busca otimizada de todas as transações do tipo que preciso, o Id concatenando userId e UUID irá garantir a unicidade dos registros, criei um indice secundário com tipo e status para usar de apoio na busca para as ordens disponíveis para negociação, as mesmas seriam ordenadas por data garantindo uma fila para o match entre compra e venda;
- O uso do Dynamo Stream vai permitir que a arquitetura seja estimulada a cada evento permitindo que as ordens que eventualmente não derem match e fiquem com status IN_TRADE em algum momento sejam concluídas;
- O uso da maquina de estados considerando a disponibilidade do registro de compra e venda irá garantir que compra e venda deem match com um único registro, garantindo assim a consistência no fluxo;
- Estou usando Update transacional do Dynamo para gerenciar concorreência entre a atualização dos registros;
- Caso um registro já tenha "race condition" na atualização do mesmo seria usado o ConditionalCheckFailed do banco como parâmetro para retentativa no match desse registro com outros possíveis.

### Order Book API

![Untitled Diagram drawio](https://user-images.githubusercontent.com/12643220/159604280-63e1cc95-41b9-4994-8a89-f4ca1764f955.png)


- API feita em Java com spring
- Transaction Id gerado a cada requisição que será propagado pela arquitetura para uso de trace distribuído.
- Apenas fluxo de escrita no Banco de dados

Dynamo Input

    {
        "value": 100.99,
        "quantity": 10,
        "userId": 334455,
        "operationType": "BUY"
    }


Dynamo Stream Output

    {
        "operationType": "BUY",
        "id": "334455|daa42855-7786-4a27-a9c6-8917dd26e2a7",
        "quantity": 10,
        "value": 100.99,
        "userId": 334455,
        "operationStatus": "IN_TRADE",
        "requestId": "daa42855-7786-4a27-a9c6-8917dd26e2a7",
        "hash": "871006e4a3b6f1234bbccecedc8e5f46",
        "audit": {
            "createdAt": "2022-03-18T15:43:51.456Z",
            "updatedBy": "API",
            "updatedAt": "2022-03-18T15:43:51.456Z"
        }
    }

### Match Engine

- Função serverless feita em Golang. Rsponsável pelo match entre compras e vendas de operações.
- A cada evento de compra recebido, todos as vendas com status IN_TRADE são ordendas por data e é feito processo de Match parcial ou total.
- Caso o processo de match não aconteca o evento é alterado para status IN_TRADE e com o update a arquitetura é reestimulada para que determinado evento passe novamente pelo processo de match até que seja encontrado uma compra para determinada venda e vice e versa.
- Caso o match aconteça o status do evento é alterado com um TTL para que a linha seja deletada, a ideia da deleção é para que banco não cresca infinitamente, principalmente considerando que teremos todo histórico dessas transações no S3 conforme desenhado na arquitetura.
- O event source mapping da função é o evento do dynamo stream.
- O output dessa função é uma lista de matchs com tipo de operação que será feita na conta de determinado userId, os matchs são enviados para uma fila SQS que posteriormente será consumida por uma função ou também poderia ser consumido pela própria API.
- Cada resultado de transação é enviada para o S3 afim de manter um histórico.


![img.png](img.png)

Input - Dynamo Db Event

        {
          "operationType": "BUY",
          "id": "334455|daa42855-7786-4a27-a9c6-8917dd26e2a7",
          "quantity": 10,
          "value": 100.99,
          "userId": 334455,
          "status": "IN_TRADE",
          "requestId": "daa42855-7786-4a27-a9c6-8917dd26e2a7",
          "hash": "871006e4a3b6f1234bbccecedc8e5f46",
          "audit": {
              "createdAt": "2022-03-18T15:43:51.456Z",
              "updatedBy": "API",
              "updatedAt": "2022-03-18T15:43:51.456Z"
          }
      }

Output - SQS Payload

    [
      {
          "value": 100.99,
          "quantity": 10,
          "userId": 334455,
          "operationType": "CREDIT",
          "requestId": "daa42855-7786-4a27-a9c6-8917dd26e2a7"
      },
      {
          "value": 100.99,
          "quantity": 10,
          "userId": 223344,
          "operationType": "DEBIT",
          "requestId": "daa42855-7786-4a27-a9c6-8917dd26e2a7"
      }
    ]

### Wallet API Integration

- Função serverless feita em Golang. 
- Responsável pelo integração com a API de wallet.
- Faz POST via HTTP com a API de wallet.
- Gerencia politica de retentativa com a API
- Caso falhe N vezes o registro vai para DLQ
- Em cenários de caos o tempo de retenção das mensagens na fila pode ser de até 7/14 dias para não perdermos os dados.
- Cada resultado de transação é enviada para o S3 afim de manter um histórico.

### Wallet API

      {
          "value": 100.99,
          "quantity": 10,
          "userId": 334455,
          "operationType": "CREDIT",
          "requestId": "daa42855-7786-4a27-a9c6-8917dd26e2a7"
      }
