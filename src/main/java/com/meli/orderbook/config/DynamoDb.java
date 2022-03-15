package com.meli.orderbook.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDb {

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
      return  AmazonDynamoDBClient.builder().build();
    }

}
