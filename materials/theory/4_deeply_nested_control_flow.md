# Deeply-Nested Control Flow
 
## Explanation
Problems can occur with nested control flow structures, such as if/else, switch/case, loops, and try/catch/finally blocks.

## Example
```java
try {
    if (input != null) {
        for (int v = 0; v < input.length; v++) { 
            if (v == x) {
                z++; 
            }
        }
    }
}
```
* This code is difficult to comprehend, primarily because it mixes several levels of abstraction, violating the Single Level of Abstraction (SLoA) principle. 
* We aim for code that's easy to scan and read. 
* Higher-level abstractions (e.g. objects) are missing, making it hard to understand the code's intent. 
* This nesting also increases the code's cyclomatic complexity, making it error-prone and challenging to test.


## Solutions
1. **Early exit strategy / early returns / Guard Clause / Bouncer** - Use early return statements to lessen the depth of nested statements.
```java
public void doSth(FancyObject f) { 
    if (f == null) {
        return; 
    }
    f.execute();
}
```
2. **Remove redundante else clauses** - Especially with early returns and validation logic.
3. **Merge conditions** - Combine nested ifs into one if when conditions can be merged.
```java
// before

public void doSth(FancyObject f) {
    if (f != null) {
        if(f.canExecute()) {
            f.execute();
        }
    }
}

// after
public void doSth(FancyObject f) {
    if (f != null && f.canExecute()) {
        f.execute();
    }
}
```
4. **Composed method** - Extract sub-methods to create a method with only a single level of abstraction. This approach makes your code more readable and structured in a way that it reads like a newspaper: from headline to lead paragraph to body.

**Example of Composed Methods:**

```java
public void handleActionRequest(ActionRequest a) {
    if (hasExecutableActions(a)) {
        executeAll(a.getExecutableActions());
    }
}

private boolean hasExecutableActions(ActionRequest a) {
    return a != null && a.numberOfExecutableActions() > 0;
}

private void executeAll(List<Action> actions) {
    if (isEmpty(actions)) {
        return;
    }
    for (var action : actions) {
        execute(action);
    }
}

private boolean isEmpty(List<Action> actions) {
    return actions == null || actions.isEmpty();
}

private void execute(Action action) {
    actionExecutor.withConfigParams(action.getConfigParams()).execute(action);
}
```

## Cause
* Deeply-nested control flow structures usually arise from procedural code without the development of higher-level software design abstractions. 
* This can also occur when existing methods are continuously extended to accommodate new requirements.

## Exceptions
* Two levels of nesting may be bearable, three levels should always be refactored.
* Striving for a single level of abstraction is key to improving readability.
