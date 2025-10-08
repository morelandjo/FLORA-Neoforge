import os
import shutil

base_path = "src/main/resources/assets/flora/textures"

# Rename all files to lowercase
for root, dirs, files in os.walk(base_path):
    for file in files:
        if file != file.lower():
            old_path = os.path.join(root, file)
            new_path = os.path.join(root, file.lower())
            print(f"Renaming {old_path} -> {new_path}")
            os.rename(old_path, new_path)

print("All textures renamed to lowercase!")
