# Getting Started 

- What does the following code do?
- <a href="https://github.com/codeartify/cleancodetoarchitecture/blob/main/src/main/java/com/codeartify/underengineered/Controller.java" target="_blank">Code</a>

----
# What could be improved?

----
# The method is too long, has too many branches, and complicated boolean expressions.
 
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

----
# Useful Shortcuts (see shortcuts.md)

| Action                        | IntelliJ (mac) | IntelliJ (win) | VS Code (mac)                     | VS Code (win)                    | Eclipse (mac)          | Eclipse (win)          |
|-------------------------------|----------------|---------------|-----------------------------------|----------------------------------|------------------------|------------------------|
| Rename                        | ⇧ + F6        | ⇧ + F6        | F2                                | F2                                | ⌘ + ⌥ + R              | ⇧ + Alt + R            |
| Extract variable              | ⌘ + ⌥ + V     | Ctrl + Alt + V| ⌘ +.                              | Ctrl +.                           | ⌘ + ⌥ + L              | ⇧ + Alt + L            |
| Extract method                | ⌘ + ⌥ + M     | Ctrl + Alt + M| ⌘ +.                              | Ctrl +.                           | ⌘ + ⌥ + M              | ⇧ + Alt + M            |
| Extract constant              | ⌘ + ⌥ + C     | Ctrl + Alt + C| ⌘ +.                              | Ctrl +.                           | see refactoring window | see refactoring window |
| Extract field                 | ⌘ + ⌥ + F     | Ctrl + Alt + F| ⌘ +.                              | Ctrl +.                           | see refactoring window | see refactoring window |
| Extract parameter             | ⌘ + ⌥ + P     | Ctrl + Alt + P| ⌘ +.                              | Ctrl +.                           | —                      | —                      |
| Inline                        | ⌘ + ⌥ + N     | Ctrl + Alt + N| ⌘ +.                              | Ctrl +.                           | ⌘ + ⌥ + I              | ⇧ + Alt + I            |
| Refactoring window / menu     | Ctrl + T | Ctrl + Alt + ⇧ + T| ⌃ + ⇧ + R (Refactor)              | Ctrl + ⇧ + R (Refactor)           | ⌘ + ⌥ + T              | Alt + ⇧ + T            |
| Quickfixes                    | ⌥ + Enter     | Alt + Enter   | ⌘ +.                              | Ctrl +.                           | ⌘ + 1                  | Ctrl + 1               |
