
import os

target_file = r"c:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website\javaapps.html"
# Broader terms to find the entries
search_terms = [
    "DecimalConverter",
    "Multiples", 
    "JavaMath", 
    "MathGui",
    "NameLength", 
    "Ascii",
    "Rectangle"
]

def main():
    if not os.path.exists(target_file):
        print("File not found.")
        return

    with open(target_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    for i, line in enumerate(lines):
        line_num = i + 1
        line_lower = line.lower()
        
        # Only check lines with 'img' or 'src' to reduce noise, or just all references that look like the program name
        if 'resimg' in line_lower or 'src=' in line_lower or 'img' in line_lower:
             for term in search_terms:
                if term.lower() in line_lower:
                    print(f"Line {line_num}: {line.strip()}")
