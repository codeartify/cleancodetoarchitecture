# Primitive Obsession
### Explanation
- Primitive Obsession refers to overuse of primitive types and existing data structures, instead of employing high-level abstraction classes.
### Example
- Primitive types such as int, String, etc. and collections like List<...>, Set<...> are employed instead of domain-specific objects like a PhoneNumber. 
### Reason
- low type safety
- complex code 
- poor testability 
- lack of domain language in the code.
### Solution
- Encapsulate primitive state within useful domain-specific abstractions and shift associated logic into these classes.
### Origin
- procedural programming instead of OOP or FP
- lack of design knowledge and skills
- absence of a shared language between developers and stakeholders
### Exceptions
- It may be acceptable for data transfer without any logic.
