package com.meli.orderbook.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.meli.orderbook.model.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OperationRepository {

    private final DynamoDBMapper operationMapper;
    private final QueryFactory query;

    public void save(Operation operation) {

        try {
            operationMapper.save(operation, query.saveWith(operation.getHash()));
        } catch (ConditionalCheckFailedException ignore) {
        }
    }

}
