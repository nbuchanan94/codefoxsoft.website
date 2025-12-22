
import os

def find_orphans(directory):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith(".html"):
                path = os.path.join(root, file)
                with open(path, 'r', encoding='utf-8') as f:
                    lines = f.readlines()
                
                for i, line in enumerate(lines):
                    stripped = line.strip()
                    if stripped.startswith('content="') or stripped.startswith("content='"):
                        # Check previous non-empty line
                        prev_idx = i - 1
                        while prev_idx >= 0 and not lines[prev_idx].strip():
                            prev_idx -= 1
                        
                        if prev_idx >= 0:
                            prev_line = lines[prev_idx].strip()
                            if prev_line.endswith(">"):
                                print(f"Orphan found in {path} at line {i+1}:")
                                print(f"  Prev: {prev_line}")
                                print(f"  Curr: {stripped[:50]}...")
                                print("-" * 20)

find_orphans(r"c:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website")
