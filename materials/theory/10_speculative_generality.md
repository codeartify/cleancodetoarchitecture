# Speculative Generality

## Explanation
Abstractions or configurations in the code that are never used.

## Examples
- Interfaces with only one implementation (e.g., `IClassXYZ` and `ClassXYZ`, `ClassXYZ` and `ClassXYZImpl`).
- Abstract classes with only one subclass (note: this does not apply to concrete classes with only one subclass).
- Flag parameters, which are only ever called with certain values (e.g., always true, never false).
- Configuration file entries that remain consistent across environments (like development, test, and prod).
- Classes or methods solely used in test cases (exceptions are helper classes or methods specifically for tests).

## Reasons
- Contradicts the YAGNI (You ain’t gonna need it) and KISS (Keep It Simple, Stupid) principles.
- Goes against D.R.Y. (Don't Repeat Yourself).
- Introduces unnecessary complexity, leading to potential increases in adaptation and maintenance costs.
- Frequently, not all code pathways get adequately tested as they're based on hazy assumptions.

## Remedies
- Excise any unused parameters.
- Simplify and merge class hierarchies where it's feasible.
- Erase redundant configuration parameters across all environments.
- Shift from abstract naming conventions to more specific ones.

## Origins of the Issue
- The "just in case" mindset: adding features based on future speculation rather than current stakeholder needs.
- Misinterpretation of the principle: "Program against an interface, not an implementation" from the Gang of Four (GOF).
The principle advocates for utilizing the most generalized type in a hierarchy if it exists, not for crafting interfaces indiscriminately.
- An over-reliance on big upfront design / big design upfront (BUFD or BDUF).
- Excessively inventive developers.

## Exceptions: When Might It Be Acceptable?
- As a rule of thumb, avoid implementing unrequested features. Only bring in speculative features if their later introduction would be prohibitively costly.
- When distinguishing between the interface and the implementation for publicly available interfaces (like APIs) within frameworks.
- To enforce _separation of concerns_: single implementation interfaces that exist in different layers or components of an architecture (see e.g. Ports in Ports & Adapters / Hexagonal Architecture or Clean Architecture: 1 port may have only one implementation, but it is needed to define the contract the implementation has to fulfill).

### Example of Acceptable Use
In a well-structured layered architecture, suppose users can modify their address.
This info might pass through multiple layers. 
If designed correctly, upper layers can access lower ones but not vice versa. 
This means once an address is updated, its new data can't directly move up layers. 
A solution could be a lower layer defining an interface, with same-layer objects calling it and higher-layer objects implementing it (akin to Hooks, Listeners, or Observers). 
For instance, a `UserProfile` class in the presentation layer might implement an `AddressChangeListener` interface from the application layer. 
When a user's address updates, all registered `AddressChangeListeners` get a notification. 
The implementing classes then decide the next steps. 
This example promotes the **Separation of Concerns (SoC)** principle, 
even if there's only one implementation of the interface.



