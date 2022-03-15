package com.meli.orderbook.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.stereotype.Component;

@Component
public class QueryFactory {

    public DynamoDBSaveExpression saveWith(String hash) {
        var expression = new DynamoDBSaveExpression();
        expression.withExpectedEntry("hash",
                new ExpectedAttributeValue(new AttributeValue(hash)).withComparisonOperator(ComparisonOperator.NE));
        return expression;
    }

}
