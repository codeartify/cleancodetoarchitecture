# Data Class
### Explanation
- A Data Class is merely a data container without any associated logic.
### Reason
- when a Data Class is employed, the logic that operates on the data is often scattered across the application, leading to missing encapsulation and duplicated code.
### Solution
- logic should be extracted from Manager, Controller, Helper, and Service classes, and moved to the data class as methods.
### Origin
- comes from procedural programming
- as intermediary step from procedural code to a richer domain model with behavior on the classes or type.
### Exceptions
Exceptions include:
- Data Transfer Objects (DTOs) at system, layer or component boundaries
- types in functional programming 
- database entities
