# Duplicate and Reduce
0. A structure (e.g. class, function, loop, system) with multiple callers or caller groups, which each are only interested in 1 of the 2 or more responsibilities the structure has.

<img src="../slides/imgs/dr1.png" width="500">

1. For each caller or caller group of a structure, create an own copy

<img src="../slides/imgs/dr2.png" width="500">

2. Redirect each caller or caller group to their respective copy of the structure.

<img src="../slides/imgs/dr3.png" width="500">

3. Reduce the copy to only work on the single responsibility the caller or caller group is interested in.

<img src="../slides/imgs/dr4.png" width="500">

4. (Optional) if it makes sense, extract the initially shared part into an own structure and call it from the other structures.

<img src="../slides/imgs/dr5.png" width="500">

This algorithm is both extremely simple yet scales from function to system level easily.
