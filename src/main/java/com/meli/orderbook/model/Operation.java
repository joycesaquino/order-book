package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.meli.orderbook.enums.OperationType;
import com.meli.orderbook.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "operations")
public class Operation {

    @DynamoDBRangeKey
    private Id id;
    private UUID requestId;
    private String hash;

    private BigDecimal value;
    private int quantity;
    private String user;

    @DynamoDBTypeConvertedEnum
    private Status status;

    @DynamoDBHashKey
    @DynamoDBTypeConvertedEnum
    private OperationType type;
    private Audit audit;

}
