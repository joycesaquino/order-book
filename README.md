# Sale and Buy API for order book

This API implements a service that will be responsible to transactions about sale and buy in order book project

to create dynamoDb table in local stack

    aws dynamodb --endpoint-url=http://localhost:4566 create-table \
    --table-name offer-book-sale-table \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
        AttributeName=type,AttributeType=S \
    --key-schema \
        AttributeName=type,KeyType=HASH \
        AttributeName=id,KeyType=RANGE \
    --provisioned-throughput \
    ReadCapacityUnits=10,WriteCapacityUnits=5
