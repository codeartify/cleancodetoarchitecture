# How to get started with engineering?

----
# Look for code smells!

----
# What code smells to look for?  
* Long function
* Deeply nested control flow
* Complicated boolean expression

----

# Exercise Long Function & Deeply-Nested Control Flow

> Exercise branch: **main**
>
> Solution: **1-deeply-nested-control-flow**


Remove the deeply-nested control flow in ```Controller::checkContainment```!

> Simplify code and remove duplications using:
>    - ```invert if```
>    - ```merge if```, and/or
>    - ```remove redundant else```

### Run tests after each reasonably small refactoring step!

----

# Exercise Complicated Boolean Expression

> Exercise branch: **1-deeply-nested-control-flow**
>
> Solution branch: **2-complicated-boolean-expression**

Simplify the boolean expression in ```Controller::checkContainment```!

> Use extract variable and extract method to remove duplications and introduce intention revealing abstractions.

### Run tests after each reasonably small refactoring step!
