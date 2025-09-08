## Temporary Field

### Explanation
A field (member variable) with only temporary validity.

### Reason
The constructor contract states that all fields are in a valid state once constructor execution completes. In case of a temporary field however, at least one field is not initialized after constructor execution. Thus, the lifetime of the field is different from the lifetime of its object. This creates code that is hard to understand and error prone as its never clear when the field is in a valid state. To be able to use the field, it first needs to be initialized by another call. Thus, the call to initialize the field is a precondition for the field to be usable later. However, developers should not need to care whether a field is in a valid state or not.

### How does the smell arise?
Misunderstanding of the purpose of fields in objects by using them as (hidden) side channels between methods to pass around data and to reduce the number of method parameters.

### How to fix it?
1. Extract the part in method (b) responsible for calculating the value of the temporary field into a new method (e) (Duplicate and Reduce refactoring may help).
   a. If it is a fast call, both methods (b) and (c) may individually call method (e). The temporary field may be pulled into the local scope of method (e).
   b. If it is a slow call, only method (a) calls (e) and passes the value to (b) and (c) as a parameter. The temporary field may be encapsulated within a new class that lazily initializes its value the first time it is called.

2. Extend the return value of (b). Instead of only returning a single value, it returns both, encapsulated in an object. This solution should only be applied if the created returned object has a semantic meaning (the values naturally occur together, e.g. street, house number, zip and location in an Address object, see Primitive Obsession and Data Clump smells) and is thus a valid domain object. It should not be a generic “tuple” (technical object) with no other purpose than to wrap two values.

**Example**
```java
private int numberOfContainingPoints; // temporary field

public int countContainingPoints(int[] xCords, int[] yCords) {
   this.numberOfContainingPoints = 0; // initialisation
   for (int i = 0; i < xCords.length; ++i) {
        this.contains(xCords[i], yCords[i]);
   }
   return this.numberOfContainingPoints;
}

public boolean contains(int x, int y) {
      // side effect: sets the temporary field and returns “contains”
      boolean result = (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) <= radius * radius;
      if (result == true) {
           this.numberOfContainingPoints++; // sets the temporary field
      }
      return result;
}
```

### Edge cases - When is it ok?
Technically spoken, lazy initialization of a field creates a temporary field, too. However, lazy initialization is a commonly used pattern and the access to the field is normally encapsulated by a getter, even from within the class itself (called self-encapsulation). Thus, it is usually not a problem in practice. However, the design is still not optimal and may have to be reconsidered at some point in the future.

