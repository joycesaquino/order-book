package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@DynamoDBDocument
public class Audit {

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String updatedBy;
}
