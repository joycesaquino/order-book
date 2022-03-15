package com.meli.orderbook.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDb {

    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${amazon.dynamo.endpoint}") String endpoint,
                                         @Value("${amazon.dynamo.region:sa-east-1}") String region) {
        return AmazonDynamoDBClient
                .builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
    }

    @Bean
    public DynamoDBMapperConfig buyConfigMapper(@Value("${amazon.dynamo.table.buy}") String buyTable) {
        return dynamoDBMapperConfig(buyTable);
    }

    @Bean
    public DynamoDBMapperConfig saleConfigMapper(@Value("${amazon.dynamo.table.sale}") String saleTable) {
        return dynamoDBMapperConfig(saleTable);
    }

    @Bean
    public DynamoDBMapper saleMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig saleConfigMapper) {
        return new DynamoDBMapper(amazonDynamoDB, saleConfigMapper);
    }

    @Bean
    public DynamoDBMapper buyMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig buyConfigMapper) {
        return new DynamoDBMapper(amazonDynamoDB, buyConfigMapper);
    }

    public DynamoDBMapperConfig dynamoDBMapperConfig(String tableName) {
        return DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
                .build();
    }


}
