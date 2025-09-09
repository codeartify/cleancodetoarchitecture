# Moving Past Layers 
## Hexagonal-Architecture-Style Patterns
 
<a href="https://miro.com/app/board/uXjVLPACwgk=/?moveToWidget=3458764639806936884&cot=14" target="_blank">Miro</a>

----
# Exercise Hexagonal Architecture
> Exercise branch: **6-separation-of-concerns**
> Solution branch: **7-hexagonal-architecture**

- Move presentation & data access layers to an adapter package .
- Introduce interfaces for inbound and outbound ports in the application package.
- Let the repository implement the outbound port and the application service implement the inbound port.
- Only use simple data structures in port interfaces.

### Run tests after each reasonably small refactoring step!
