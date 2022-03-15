package com.meli.orderbook.repository.sale;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.meli.orderbook.model.Operation;
import com.meli.orderbook.repository.QueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaleRepository {

    private final DynamoDBMapper saleMapper;
    private final QueryFactory query;

    public void save(Operation operation) {
        try {
            saleMapper.save(operation, query.saveWith(operation.getHash()));
        } catch (ConditionalCheckFailedException ignore) {
        }
    }

}
