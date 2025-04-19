package com.beautysalon.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String uri = "mongodb+srv://" +
                EnvConfig.get("MONGO_USERNAME") + ":" +
                EnvConfig.get("MONGO_PASSWORD") + "@" +
                EnvConfig.get("MONGO_CLUSTER") + "/" +
                EnvConfig.get("MONGO_DB") +
                "?retryWrites=true&w=majority";

        return MongoClients.create(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), EnvConfig.get("MONGO_DB"));
    }
}
