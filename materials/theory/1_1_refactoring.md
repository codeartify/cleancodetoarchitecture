# Refactoring

## Definition
> Changing the internal structure of software without changing its external behaviour to make it easier to understand and cheaper to modify 

*Martin Fowler*

## Prerequesites
* Fast, reliable, automated (unit) tests!
* No tests, no refactoring!
* Code without tests: instant [legacy](https://www.amazon.de/Working-Effectively-Legacy-Robert-Martin/dp/0131177052)!

## How to refactor?
* In **baby-steps**!
* Split large refactorings into many **small steps**. 
* Keep code compiling and tests green after each refactoring step.
* **Run tests** after each reasonably small refactoring step!
* **Make steps smaller** if you run into red tests or non-compiling code!
* **Revert code refactoring** that led to non-compiling code or red tests after max. **30 secs** of thinking!
* **Commit often**! Atomic commits after each refactoring step!
* Keep **cognitive load** small at all times.
