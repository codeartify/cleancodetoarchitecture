# Long Parameter List

### Explanation
A method or constructor that carries an extended parameter list, leading to complexity and reduced comprehension.

### Example
```
  public void replace(String fileName, 
                      String newCode, 
                      int startLine, 
                      int numberOfDeletedLines, 
                      int numberOfInsertedLines, 
                      ReplacementMode mode) {
                      ...
               }
```
### Issues
- Confusion over the purpose of parameters from a caller's point of view.
- Commonly violates Single Responsibility Principle (SRP), especially if flags or multiple large service classes are injected.
- Mistakes are likely when parameters share the same type due to potential mix-ups.
- As a general rule, aim for 3-4 parameters max. However, this isn't set in stone; it should make sense from a caller's perspective.

### Solutions
- Address Primitive Obsession and Data Clump smells by extracting parameter objects (initially creates a Data Class smell).
- Remove flags in parameter smells.
- Break down methods that violate SRP.
- Use the builder pattern to encapsulate constructors.

### Origin
- Incremental feature additions without altering the design.
- Tendency towards procedural programming over object-oriented programming.

### Exceptions
- It's acceptable in APIs that we use but do not control.
