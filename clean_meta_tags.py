import glob

def clean_malformed_tags():
    # The specific orphaned string pattern identified by the user
    # It starts with '        content="CodeFoxSoft blog...' and ends with '">'
    # It was likely appended after a valid tag
    
    files = glob.glob("*.html")
    
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
            
            new_lines = []
            modified = False
            
            for line in lines:
                stripped = line.strip()
                # Check for the specific malformed line that starts with content= but isn't inside a tag properly
                # based on the user's report: "content="CodeFoxSoft blog, game development blog..."
                if stripped.startswith('content="CodeFoxSoft blog') or \
                   stripped.startswith('content="CodeFoxSoft, Indie'):
                    # This line is likely the orphaned attribute. SKIP IT.
                    # However, we must be careful not to delete valid meta tags.
                    # Valid tag: <meta name="keywords" content="...">
                    # Invalid/Orphaned: content="..."> inside the text body or head but outside < >
                    
                    if not stripped.startswith('<meta'):
                        print(f"Removing malformed line in {file_path}: {stripped[:50]}...")
                        modified = True
                        continue
                
                # Also check for the double content case on the same line if it exists
                # e.g. <meta ...> content="...">
                if '">        content="' in line:
                     # This is a harder split, but let's see if we can just replace the bad part
                     # The bad part is usually the second content attribute
                     # User report says it's visible, so it's likely on its own line or outside the tag
                     pass

                new_lines.append(line)
            
            if modified:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.writelines(new_lines)
                print(f"Cleaned {file_path}")

        except Exception as e:
            print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    clean_malformed_tags()
