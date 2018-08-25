# Entity Voting application 

Capture votes and display the results for a set of entities. The
entites could be videos (e.g.  youtube), pages or URLs (e.g.
facebook), candidates (democratic election), etc.

To build the jar file, execute this: ./mvnw clean package

To launch the application, run this command:  java -jar target/disney-interview-coding-challenge-0.0.1-SNAPSHOT.jar

To run unit tests: ./mvnw test

- List all of the entities, sorted by creation date:

    GET https://localhost:8080/entities
    
- View the total vote count for a given object

    GET http://localhost:8080/entities/{id}/votes 
    
- Vote an entity up

    PUT http://localhost:8080/entities/{id}/upvote
    
- Vote an entity down

    PUT http://localhost:8080/entities/{id}/downvote
       
- Create an entity

    POST http://localhost:8080/entities
    
    with payload:
    
    {
        "type": "youtube videos",
        "name": "youtube video C",
        "upvotes": 10,
        "downvotes": 20
    }
    
    
