# Introduce Inheritance

## Context
Given a class with a method (or multiple methods) that handles multiple responsibilities, typically executed via a `switch-case` or `if-(elseif-)else` structure.

## Steps

### 1. Encapsulate Constructor
Encapsulate the class constructor with a static factory method.

```java
public static Parent create() {
    return new Parent();
}
```

### 2. Create Subclasses
For each responsibility of the parent's method, create an empty subclass. This subclass should have the same constructor signatures as the parent class and pass all parameters using `super(…)`.

```java
public class ChildResponsibility1 extends Parent {
    public ChildResponsibility1(Type parameter1, Type parameter2) {
        super(parameter1, parameter2);
    }
}
```

### 3. Instantiate Subclasses in Factory Method
In the static factory method, instantiate the appropriate subclass based on each case of the switch.

```java
public static Parent create(CaseType caseType) {
    switch(caseType) {
        case RESPONSIBILITY1:
            return new ChildResponsibility1();
        // ... other cases
    }
}
```

### 4. Abstract Parent Class
Declare the parent class as abstract.

```java
public abstract class Parent {
    // ...
}
```

### 5. Refactor Logic into Subclasses
Using the "Duplicate and Reduce" method, move the logic that contains the switch-case into each subclass. Simplify this logic so that each subclass only contains what is necessary for its responsibility. Declare the parent method as abstract.

```java
public abstract class Parent {
    public abstract void method();
}

public class ChildResponsibility1 extends Parent {
    @Override
    public void method() {
        // logic specific to Responsibility1
    }
}
```

#### 5.2. Turn abstract class into interface
If the parent class is abstract and has no additional methods, consider turning it into an interface.

```java
public interface Parent {
    void method();
}
```

### 6. Optional: Resolve Circular Dependencies
If the parent class knows about its subclasses (circular dependency), resolve this by moving the `create()` method to a separate Factory class.

```java
public class ParentFactory {
    public static Parent create(CaseType caseType) {
        // ... logic from the create method
    }
}
```
 
