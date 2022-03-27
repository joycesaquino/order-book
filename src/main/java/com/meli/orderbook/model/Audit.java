package com.meli.orderbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@DynamoDBDocument
public class Audit {

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String updatedBy;
}
