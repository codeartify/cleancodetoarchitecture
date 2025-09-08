# Long Method / Function 
### Explanation
- a Long Method / Function is difficult to scan, read, understand or name accurately.

### Example
- too limited space :)

### Reason
- The method might be so lengthy that it requires scrolling or places a cognitive load on the reader who has to remember various parts of the method to fully comprehend it.
- The method often violates the Single Responsibility Principle (SRP) and the "as it tries to perform several unrelated but entangled tasks at once. This typically leads to non-descriptive or general names that do not encompass all the responsibilities of the method.

### Solutions
- Create a consolidated method with a single level of abstraction by segregating several sub-methods with meaningful names that reflect their functions.
- Comments in the code often suggest potential segments for segregation.
- The process of extracting parts of the method can provide benefits such as facilitating the transfer of parts to other/new classes, enabling unit testing, and promoting reuse.

### Origins
- Over time, existing methods were continuously extended to accommodate new requirements without adding new abstractions.
- The absence of proper understanding of object-oriented design often leads to procedural programming on basic data structures.

### Exceptions
- Long Methods / Functions containing simple, direct logic without any branching (for example, mappings or protocol filling) are generally acceptable.
