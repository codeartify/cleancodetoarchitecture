# Parallel Change
**Steps for transitioning from an old to a new structure:**
1. **Preparation:** Create and test the new structure (such as a class or type) that you plan to introduce.
2. **Initialization:** Create an instance of the new structure alongside the existing one.
3. **Writing to New Structure:** Gradually incorporate write operations to the new structure, concurrent with write operations to the old one.
4. **Reading from New Structure:** Gradually switch read operations from the old structure to the new one. Ensure to run tests after each modification.
5. **Removing Old Write Operations:** Gradually eliminate write operations to the old structure. Remember to run tests after each change.
6. **Removal of Old Structure:** Finally, eliminate the old structure.
