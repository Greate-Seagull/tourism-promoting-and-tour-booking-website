package com.uit.tourism_article_management.media.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GridFSConfig {
    @Value("${spring.data.mongo.uri}")
    private String mongoUri;
    @Value("${spring.data.mongo.database}")
    private String databaseName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(databaseName);
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoDatabase database) {
        var bucket = GridFSBuckets.create(database);
        database.getCollection("fs.files").createIndex(
                new Document("metadata.checksum", 1),
                new IndexOptions().background(true)
        );
        return bucket;
    }
}
