# Refactoring a Method with Flag Parameter

## Context
Given a method with dual responsibilities that uses a flag in an if-else statement to determine which part of the method to execute.

## Steps

### 1. Wrap Content in Separate Methods
Separate the content of both `if` and `else` parts into two distinct, public methods.

```java
public String getColorFormatted(boolean includeHexAndRGB) {
    if (includeHexAndRGB) {
        return getColorFormatted();
    } else {
        return getColorAsText();
    }
}

public String getColorFormatted() {
    return String.format("%s %s %s:%s:%s", colorAsText, colorAsHex, 
                         colorAsRGB_Red, colorAsRGB_Green, colorAsRGB_Blue);
}

public String getColorAsText() {
    return colorAsText;
}
```

### 2. Use Elvis Operator
Replace the `if-else` statement with the Elvis operator (?:).

```java
public String getColorFormatted(boolean includeHexAndRGB) {
    return includeHexAndRGB ? getColorFormatted() : getColorAsText();
}
```

### 3. Inline Outer Method
Inline the method where it was used.

```java
String formattedColor = true ? c.getColorFormatted() : c.getColorAsText();
```

### 4. Simplify Expression
Post inlining, simplify the expression to directly call the method.

```java
String formattedColor = c.getColorFormatted();
```



