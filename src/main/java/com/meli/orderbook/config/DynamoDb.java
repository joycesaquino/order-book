package com.meli.orderbook.config;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
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
    public AmazonDynamoDB amazonDynamoDB(@Value("${amazon.dynamo.endpoint:}") String endpoint,
                                         @Value("${amazon.dynamo.region:sa-east-1}") String region,
                                         @Value("${amazon.dynamo.accessKey}") String accessKey,
                                         @Value("${amazon.dynamo.secretKey}") String secretKey) {

        var dynamoClientBuilder = AmazonDynamoDBClient.builder();
        dynamoClientBuilder
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)));

        if (endpoint.isBlank()) {
            dynamoClientBuilder.withRegion(Regions.fromName(region));
        } else {
            dynamoClientBuilder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region));
        }
        return dynamoClientBuilder.build();

    }

    @Bean
    public DynamoDBMapperConfig operationConfigMapper(@Value("${amazon.dynamo.table.operation}") String operationTable) {
        return dynamoDBMapperConfig(operationTable);
    }

    @Bean
    public DynamoDBMapper operationMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig operationMapper) {
        return new DynamoDBMapper(amazonDynamoDB, operationMapper);
    }

    public DynamoDBMapperConfig dynamoDBMapperConfig(String tableName) {
        return DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
                .build();
    }

}
