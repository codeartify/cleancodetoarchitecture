# Are we done engineering?

----
# What else could be improved?

----
# Real Estate objects creation and search for containment are two different concerns...
And for better maintainability, they could be separated.
 
----
# Exercise: Concern Separation through Loop Splitting
> Exercise branch: **4-feature-envy**
> Solution branch: **5-loop-split**

- RealEstate objects could be fetched from a ```RealEstateRepository```.
- The simplest version could have a ```fetchAll():List<RealEstate>``` method.
- To do so, the loop over xCoords should be split into
  1. creation of RealEstate objects and 
  2. actual check for containment in the search.

----
## Refactoring: Loop Splitting
* Create a list of objects in the first loop 
* Iterate over it in all the following loops

We can split a loop with multiple responsibilities into multiple loops and extract methods for each with intention-revealing names.

