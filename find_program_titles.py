
import os

target_file = r"c:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website\javaapps.html"
# Titles or text likely in the description/header
search_terms = [
    "Decimal Converter",
    "Multiples", 
    "Java Math", 
    "Name Length", 
    "Ascii Rectangle"
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
        
        for term in search_terms:
            if term.lower() in line_lower:
                print(f"Line {line_num}: {line.strip()}")

if __name__ == "__main__":
    main()
