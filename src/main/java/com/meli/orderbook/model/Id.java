package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.meli.orderbook.enums.operation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class Id {

    @DynamoDBTypeConvertedEnum
    private Status status;

    private Long userId;
    private UUID requestId;

}
