# Are we done with Engineering?

----
# What could be improved?

----
# Move functions closer to types!
* Also sometimes called "extract/wrap/move" or "emerging software design algorithm"

# Emerging Software Design Algorithm (simplified)
1. Extract some logic as method / function
2. Wrap state in new class / type
3. Move extracted method into newly created class / closer to type

----
# Emerging Software Design Algorithm (extended)
1. Separate responsibilities within the method 
2. Extract private method
3. Remove direct access to non required fields of the old class
4. Wrap target state with domain-specific class
5. Move method to the target class as public
6. Simplify parameters to remove unwanted dependencies

----
# What code smells to look for?
* Data Class
* Feature Envy (class accesses data of another class to perform some action)

# Exercise Data Class & Feature Envy

> Exercise branch: **3-primitive-obsession**
>
> Solution branch: **4-feature-envy** 

Move methods and logic closer to their types!
What makes sense from a domain perspective?
----

## Possible Refactorings
* Move method

### Run tests after each reasonably small refactoring step!

----
