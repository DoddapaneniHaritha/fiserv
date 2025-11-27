
# FI REST Endpoint with Kafka Integration

Spring Boot application exposing `/fi/events` endpoint to accept validated events and publish them to a Kafka topic.

## Build & Run
```bash
mvn clean package
java -jar target/fi-rest-endpoint-kafka-0.0.1-SNAPSHOT.jar
```

## Configuration
Environment variables:
```
KAFKA_BOOTSTRAP_SERVERS, FI_KAFKA_TOPIC, FI_BASE_URL, FI_API_KEY
```

## Example Request
```bash
curl -X POST http://localhost:8080/fi/events   -H "Content-Type: application/json"   -d '{
    "eventId":"123",
    "eventType":"PAYMENT",
    "fiId":"FI001",
    "payload":{"amount":100}
  }'
```

## Kafka Setup
Ensure Kafka is running locally or provide bootstrap servers via `KAFKA_BOOTSTRAP_SERVERS`. Default topic: `fi-events`.
