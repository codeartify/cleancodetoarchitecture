# Are we done engineering?

----
# What else could be improved?

----
# Real Estate objects creation and search containment are two different concerns...
And for better maintainability, they should be separated.
 
----
# Exercise Concern Separation through Loop Splitting
> Exercise branch: **4-feature-envy**
> Solution branch: **5-loop-split**

RealEstate objects should be created and fetched from a RealEstateRepository::fetchAll() method.
This means that the loop over xCoords should be split into creation of RealEstate objects and actual check for containment in the search.

## Loop Splitting
* Create a list of objects in the first loop 
* Iterate over it in all the following loops and check for containment 

### Run tests after each reasonably small refactoring step!

----
