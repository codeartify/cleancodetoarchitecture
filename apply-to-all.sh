#!/bin/bash
set -e

commit=21aba6ed72fafca9b46e8caa73f9199cfdbd3c8f
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
