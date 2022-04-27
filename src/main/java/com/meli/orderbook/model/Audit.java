package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.meli.orderbook.config.DynamoDb;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@DynamoDBDocument
public class Audit {

    @DynamoDBTypeConverted(converter = DynamoDb.LocalDateTimeConverter.class)
    private ZonedDateTime createdAt;

    @DynamoDBTypeConverted(converter = DynamoDb.LocalDateTimeConverter.class)
    private ZonedDateTime updatedAt;
    private String updatedBy;
}
