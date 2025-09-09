# Are we done with Engineering?

----
# What could be improved?

----
# Move functions closer to types!
* Also sometimes called "extract/wrap/move" or "emerging software design algorithm"

----
# Emerging Software Design Algorithm (simplified)
* Extract some logic as method / function 
* Wrap state in new class / type 
* Move extracted method into newly created class / closer to type

----
# Emerging Software Design Algorithm (extended)
* Separate responsibilities within the method (we'll do that later, too)
* Extract private method 
* Remove direct access to non required fields of the old class 
* Wrap target state with domain-specific class 
* Move method to the target class as public 
* Simplify parameters to remove unwanted dependencies

----
# What code smells to look for?
* Data Class
* Feature Envy (class accesses data of another class to perform some action)

----
# Exercise Data Class & Feature Envy

> Exercise branch: **3-primitive-obsession**
>
> Solution branch: **4-feature-envy** 

Move methods and logic closer to their types!
What makes sense from a domain perspective?

## Possible Refactorings
- Move method

### Run tests after each reasonably small refactoring step!
