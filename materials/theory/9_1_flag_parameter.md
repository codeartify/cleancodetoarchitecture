# Flag Parameter

## Explanation

A method parameter that controls the behavior of the method or a constructor parameter that controls the behavior of the object.
> **Note**: Every Boolean parameter is a candidate.

## Examples

```java
//two different responsibilities:
if (flag) {
//...
} else {
//...
}

if (param != null) {    
//  ...
}
```

From a caller’s perspective, it's unclear what the flag means or does:

```java
File myFile = new File(true);
urlStrategy.getWeblogEntryURL(website, locale, entryAnchor, true);
doIt(null);
```

## Reason

- Can be used to simulate polymorphic behavior.
- Likely violation of SRP as the method does different things depending on the flag parameter’s value.
- Meaning not clear from the caller perspective.

## How to fix it?

1. If the flag evaluates to a Boolean: exchange it with an intention revealing variable, constant, or Enum.
    - Example: `new File(true)` → `new File(READ_ONLY)`
2. Split the method into two, each with a more specific name, and let the caller call each method directly.
    - Try to use the Elvis Operator Trick (?:) to simplify automatic refactoring.

```java
// before
public void print(boolean withNewLine) {
   if (withNewLine) {
       System.out.println("...");
   } else { 
       System.out.print("...");
   }
}

// extract method for each condition
public void print(boolean withNewLine) {
    if (withNewLine) {
        printWithNewLine();
    } else {
        printWithoutNewLine();
    }
}

// elvis trick
public void print(boolean withNewLine) {
    return withNewLine ? this.printWithNewLine() : this.printWithoutNewLine();
}

// after inline & clean ups
public void printWithNewLine() {
    System.out.println("...");
}

public void printWithoutNewLine() {
    System.out.print("...");
}
```

3. Wrap constructor flag by two static creation methods and make the constructor private.

```java
File readOnlyFile = File.createReadOnlyFile();
File writableFile = File.createWritableFile();
```

4. Exchange constructor flag by class hierarchy/refactor to Strategy Pattern.
    - (See Introduce inheritance in chapter Refactoring Techniques). This should be a last resort if all other methods failed to provide a satisfying solution.

## How does the smell arise?

- Additional functionality was hacked into existing code without thinking about expressive abstractions.

## Edge cases - When is it ok?

If the flag parameter is just passed through to a further class and the control flow is not branched based on the flag parameter (e.g. to store the flag as a value in a database).



