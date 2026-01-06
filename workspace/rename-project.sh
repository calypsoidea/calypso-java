#!/bin/bash
# Safe project rename script

OLD_NAME="js-ts-hardhat-project"
NEW_NAME="js-hardhat-project"

# 1. Rename the folder
mv "$OLD_NAME" "$NEW_NAME"

# 2. Update package.json name field
if [ -f "$NEW_NAME/package.json" ]; then
  sed -i "s/\"name\": \"$OLD_NAME\"/\"name\": \"$NEW_NAME\"/" "$NEW_NAME/package.json"
fi

# 3. Clean Hardhat artifacts + cache
rm -rf "$NEW_NAME/artifacts" "$NEW_NAME/cache"

echo "✅ Project renamed from $OLD_NAME to $NEW_NAME"
echo "➡️  package.json updated"
echo "➡️  artifacts/ and cache/ cleaned"

