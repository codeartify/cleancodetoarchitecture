# Data Clump
### Explanation
- Data Clump is a subcategory of Primitive Obsession, occurring when several interrelated primitive data types are combined as fields or parameters in various code locations.
### Example
- A Circle class having separate int fields for centerX and centerY.
### Reason
- D.R.Y. violations likely
- micro duplications due to missing high-level class
- domain language hidden in procedural code
### Solution
- Data clumps should be encapsulated within an own class / type. 
- Then extract and shift the logic operating on the data either into the new class (OOP) or closer to the type as own functions (FP).
### Origin
- Originates from procedural programming
- lack of design knowledge and skills
- absence of shared language with users/domain experts
- extending code without modifying the design
### Exceptions
- Possibly flat db entries.
