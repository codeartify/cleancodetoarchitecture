# Narrow Change 
1. Create an indirection (scaffolding) that is used for all occurrences of the code to change
   1. Methods: extract an algorithm or value into a new method
   2. Constructor: wrap constructor with creation method
   3. Variable: extract a temporary variable for a value or one line piece of code
2. Apply the changes within the new indirection layer
3. Inline indirection layer again (remove scaffolding)
