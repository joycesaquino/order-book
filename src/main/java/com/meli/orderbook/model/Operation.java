package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.meli.orderbook.enums.operation.Type;
import com.meli.orderbook.enums.operation.Status;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "order-book-operation")
public class Operation {

    @DynamoDBRangeKey
    @DynamoDBDelimited(attributeNames = {"userId", "requestId"})
    private Id id;

    private BigDecimal value;
    private int quantity;
    private Long userId;

    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexRangeKey(attributeName = "operationStatus", localSecondaryIndexName = "operationStatus-index")
    private Status operationStatus;

    @DynamoDBHashKey
    @DynamoDBTypeConvertedEnum
    private Type operationType;

    private Audit audit;

    private UUID requestId;
    private String hash;


    public String hash() {
        return DigestUtils.md5Hex(toString());
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", quantity=" + quantity +
                ", userId='" + userId +
                ", status=" + operationStatus +
                ", type=" + operationType +
                '}';
    }
}
