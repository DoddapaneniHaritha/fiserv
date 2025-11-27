
# FI REST Endpoint with Kafka Integration

Spring Boot application exposing `/fi/events` endpoint to accept validated events and publish them to a Kafka topic.

```

## Example Request
```bash
curl -X POST http://localhost:8080/fi/events   -H "Content-Type: application/json"   -d '{
    "eventId":"123",
    "eventType":"PAYMENT",
    "fiId":"FI_001"
  }'
```

## Kafka Setup
Ensure Kafka is running locally or provide bootstrap servers via `KAFKA_BOOTSTRAP_SERVERS`. Default topic: `fi-events`.
