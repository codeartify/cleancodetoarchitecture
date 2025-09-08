# Complicated Boolean Expression

## Explanation
* Complicated boolean expressions involve the combination of various logical operations into a single expression. 
* This makes it difficult to read and understand, especially when negations are nested or logical values are encoded into method or variable names.

## Example

Consider these complex boolean expressions in Java:

```java
if (x == y && k == z || xIsNotLessThan3 != false && k != z) {
    // ...
}

if (element instanceof Rect || element instanceof Curve && !(element instanceof Line)) {
// ...
}
```
## Reason
* The sequence in which logical operators are evaluated is not known by everyone. 
* Expressions involving negations or double negations can be hard to comprehend. 
* This could potentially lead to bugs, especially in corner cases.

## Solutions
* Introduce explaining parentheses to clarify the sequence of operations.
* Extract parts of the expressions that belong together into separate variables.
* Extract parts of the expressions that belong together into methods (similar to Deeply-Nested Control Flow). 
* Eliminate negations and double negations. Instead, use "positively" formulated names with semantic meaning. For example, instead of !node.hasNoChildren(), you could use !node.isLeaf() or node.hasChildren(). 
* Try to simplify the expression by applying De Morgan's laws:
```java
!a && !b = !(a || b)
!a || !b = !(a && b)
```

## Cause
* Such smells typically occur when expressions grow with new requirements.

## Exceptions
* Complex boolean expressions might be necessary in technical domains where complex equations are developed by domain experts, and the equations in the code must match those in the domain. 
* It might be justifiable when the business insists on using a predefined equation. However, such cases should be challenged if possible.
