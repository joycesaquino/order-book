# Order book API

This API implements a service that will be responsible to transactions about sale and buy in order book project


How to test ?

    - docker-compose up
    - Run OrderBookApplication

Request

    curl --location --request POST 'localhost:8081/operations' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "value": 1334.99,
    "quantity": 200,
    "userId": 2233998,
    "status": "IN_OFFER",
    "operationType": "SALE"
    }'

Expected response

    HTTP create 201

Validate in local database

    aws dynamodb scan --table-name order-book-operation --endpoint-url http://localhost:4566

