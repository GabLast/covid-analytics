# Covid Analytics BackEnd Service Project

### Tech Stack

1. Java v21
2. Spring Boot v3.5.9
3. Spring Security
4. Spring Data
5. H2 Database (Development profile)
6. MySQL (Production profile) (Default Port: 3306 | Default Schema Name: covid-analytics)
7. Redis (Extra - Caching for optimization on one of the dashboard datasource's)
8. JUnit / Mockito for tests

### Check list

1. Dataset Loading (ETL) Flow from the following sources:
- JSON URL
- JSON Object
- CSV
2. Authentication
3. Security Management

### TODO / Stack to test

1. Use Kafka to load the data and notify the UI about the loaded dataset
- Could send the whole request to this service then this one could produce the message for the topic.
- Another service could consume this message and do the data load. This service would then produce a message to be consumed by the UI.
- The UI would consume the notification message to know when the data has been loaded.