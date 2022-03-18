package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.meli.orderbook.enums.operation.Type;
import com.meli.orderbook.enums.operation.Status;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;

@Data
@DynamoDBTable(tableName = "order-book-operation")
public class Operation {

    @DynamoDBRangeKey
    @DynamoDBDelimited(attributeNames = {"status", "userId", "requestId"})
    private Id id;

    private BigDecimal value;
    private int quantity;
    private Long userId;

    @DynamoDBTypeConvertedEnum
    private Status status;

    @DynamoDBHashKey
    @DynamoDBTypeConvertedEnum
    private Type type;

    private Audit audit;

    public String hash() {
        return DigestUtils.md5Hex(toString());
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", quantity=" + quantity +
                ", userId='" + userId +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
