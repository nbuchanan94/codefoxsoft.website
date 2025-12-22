import glob

def update_meta_tags():
    # Targeted replacements for meta tags and schema
    replacements = {
        "nbuchanan94": "codefoxsoft",
        "codefoxsoft.website": "codefoxsoft.com",
        "https://github.com/nbuchanan94": "https://github.com/codefoxsoft"
    }
    
    files = glob.glob("*.html")
    
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            original_content = content
            
            # Apply replacements globally
            for old, new in replacements.items():
                content = content.replace(old, new)
            
            if content != original_content:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.write(content)
                print(f"Updated {file_path}")
            else:
                pass

        except Exception as e:
            print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    update_meta_tags()
