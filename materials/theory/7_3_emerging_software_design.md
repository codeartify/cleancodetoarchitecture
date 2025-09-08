# Emerging Software Design Algorithm
Extension of **extract, wrap, move**.
1. Separate responsibilities within the method (e.g. using Loop Split or Duplicate and Reduce)
2. **Extract private method / function**
3. Remove direct access to non-required fields of the old class or types
4. **Wrap target state with domain-specific class or type**
5. **Move method to the target class as public or as a function in a file close to the domain type**
6. Simplify parameters to remove unwanted dependencies
