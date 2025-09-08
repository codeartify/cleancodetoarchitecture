# Lazy Class

## Explanation
A class that does so little, its continued existence becomes questionable.

## Example
- Classes containing only a handful of constants or merely static helper functions.

## Reason
Such classes tend to clutter packages and directories, adding unnecessary overhead to the codebase.

## How to Address This?
- Migrate the methods or constants elsewhere, then consider deleting or inlining the class.
- If the class is part of an inheritance chain, contemplate collapsing the hierarchy to simplify.

## Origins of the Issue
- Overengineering, especially when influenced by a big upfront design approach.
- Remnants or artifacts left behind after prior refactorings.

## Exceptions: When Might It Be Acceptable?
- Empty interface implementations when working with external frameworks.
- Specific commands adhering to the command pattern.
- Marker interfaces that don't mandate any methods.



