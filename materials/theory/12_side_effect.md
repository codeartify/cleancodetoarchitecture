## Side Effect

### Explanation

A method does more than its name implies. This is especially problematic when it involves unexpected state changes
inside an object or its collaborators: if a method that looks like a query changes states (which are not communicated
through its name), it effectively is a command method.

- **Query**: returns the same result no matter how many times it is called. Simplest form: getter.
- **Command**: executes an action and likely sets a state. Simplest form: setter.

**Example**

```java
public boolean validateForStore(SearchData searchData){
    if(!searchData.isValidForStore()){
        searchData.setErrorCode(ERROR_INVALID_INPUT_DATA);
        return false;
    }
    return true;
}
```

### Reason

It should be possible to invoke a query method for as many times as needed without changing any state. When a method
looks like a query but acts like a command, developers quickly lose trust in them and will start to investigate them
first before they use it, which reduces their productivity. Because the method acts as both query and command, it
violates SRP. Such methods can be hard to understand and test appropriately. Furthermore, refactoring may lead to
unexpected behaviour and can introduce bugs that are hard to find. Conclusively, a method avoids many of these issues if
it is either a query or a command, but not both.

### How to fix it?

1. Rename the method to reflect the side effect if it takes too much effort to resolve it immediately, and to warn other
   developers who may use it.
2. Split the method according to their responsibilities (into queries and commands). Duplicate and Reduce refactoring
   may help.
3. Rename the methods appropriately.
4. Let the caller invoke both methods explicitly.

### Edge cases - When is it ok?

Cross-cutting concerns like logging and caching (because they are not unexpected) and methods used in concurrent
programming (e.g. AtomicInteger::incrementAndGet()) that should be executed atomically to avoid race conditions. Also,
PUT, DELETE, and POST HTTP verbs alter a state and return a response, which is expected.

