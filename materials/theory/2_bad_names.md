# Bad names 

### Explanation
* Incorrect, incomplete, or misguiding names of variables, methods, classes, constants, or packages.

### Example
* XYHelper, XYManager, XYData, XYTools, XYUtils.
* Generic terms like length, time. 
* Constant names like ZERO, ONE.
* Abbreviations like DTE, PSE, AE, DBC.
* Overly verbose identifiers like CustomerServiceManagementRepository, DatabaseConnectionManager.

### Reasons
* A name that doesn't accurately convey its intent or meaning can lead to misunderstandings. 
* This forces other developers to refer to documentation or, in a worst-case scenario, the entire codebase to comprehend the program. 
* If the name implies a function different from its actual purpose, it can inadvertently introduce bugs.
* The units of a numerical value may be ambiguous (for instance, 'time' could refer to seconds, milliseconds, or nanoseconds). 

### Solution (see below)
* Apply [Rename](https://refactoring.guru/rename-method) Refactoring technique.
* Break up classes, methods, or packages
* Introduce a new variable when an existing one is being reused
* Organize classes into appropriate packages or namespaces.
* Above all, avoid justifying poor code through documentation - rename it instead.

### Exceptions
* Never - always strive for clear, unambiguous naming conventions.

## Essentials of Good Naming Practices

> “Names in software are 90% of what makes software readable." - Robert C. Martin in Clean Code

> “There are many powerful refactorings, but “Rename Class” is the most powerful. It changes the way people see code and lets them notice possibilities that they might not have considered before.” - Michael C. Feathers in Working Effectively with Legacy Code

### Typical Naming Smells
- Use of abbreviations (except for common abbreviations and domain language)
- Generic names (e.g., Manager, Handler, Data, Container, Helper, Util, etc.)
- Including the type in the name
- Mixing languages (except for business domain - the Ubiquitous Language)
- Inconsistent usage
- Unclear metrics (Milliseconds? Seconds?)

### Characteristics of Good Names
- They describe the intention or purpose
- They are specific
- They document any side effects
- They are unique within their scope of usage (the larger the scope, the longer the name)
- They use common names (like design patterns or domain language)

### Naming Quality Check Guidelines
- The name should explicitly convey what the variable/method/class/package is intended for.
- Each variable/method/class/package should do only what its name suggests.
- Evaluate the quality of names from the caller's perspective (it always seems clear at declaration).
- Let others assess the quality of names (for example, in a code review).

### Additional Resources
* [Naming as a process](https://www.digdeeproots.com/articles/naming-process)
