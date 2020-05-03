bayes-dota
==========

This is the [task](TASK.md).

## Added Dependencies
1. The first dependency that I added was the `org.modelmapper`, to save me the time and tedious work of
mapping my entities to my dto manually.
2. The second dependency was `org.mockito` in order to be able to write tests for the service implementation.
I needed it to mock my repositories, since I didn't want to set up a test database.

## Design Pattern
For the ingestion of the data I used the chain of responsibility pattern. This way we decouple our service 
from the ingestion of the data. Also for the future if we need to extend the handlers our service does not need to be changed.

## Database model
For my models `Damage`, `Kill` and `Spell` I used compound keys, that would uniquely identify 
a row in my table. That would also prohibit people from trying to enter a new entry for one that already 
exists with these compound keys. 