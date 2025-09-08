# Are we done with Engineering?

----
# What could be improved?

----
# Types are missing!

----
# What kind of types?

----
# Domain-Specific!
* What could be domain-specific types in the Controller?

----
# What code smells to look for?
* Long Parameter List
* Primitive Obsession

# Exercise Long Parameter List, Primitive Obsession

> Exercise branch: **2-complicated-boolean-expression**
>
> Solution branch: **3-primitive-obsession** 

Introduce wrapper classes for primitive types in ```Controller```.

----

## Possible abstractions
* Location(x, y)
* RealEstate / Property (id, Location)
* RealEstateSearch(Location, SearchRadius)

## Possible Refactorings
* Extract Parameter
* Introduce Parameter Object

### Run tests after each reasonably small refactoring step!

----
