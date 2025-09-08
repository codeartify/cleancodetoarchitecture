#!/bin/bash
set -e

commit=01c9edfbe4c55ab2e545628fbc46dae04fbf4876
source_branch=main

git fetch --all

for branch in $(git branch -r | grep -v '\->' | sed 's|origin/||' | grep -v "^$source_branch$"); do
  echo "=== Working on $branch ==="
  git checkout $branch
  if git cherry-pick $commit; then
    git push origin $branch
  else
    echo "Conflict on $branch, skipping..."
    git cherry-pick --abort
  fi
done

git checkout $source_branch   # go back to your source branch
