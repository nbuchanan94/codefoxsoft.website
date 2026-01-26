import glob
import re

def fix_keywords_safe():
    # The correct single-line tag
    correct_keywords = '<meta name="keywords" content="CodeFoxSoft, Indie Game Developer, Web Developer, App Development, AI Solutions, Nick Larkin, Codefox soft">'
    
    files = glob.glob("*.html")
    
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()

            original_content = content
            
            # 1. SPECIFIC FIX FOR blog.html (and duplicates)
            # Remove the visible orphan text occurring AFTER a valid tag
            # We look for a closing > followed by whitespace and then content="CodeFoxSoft blog...
            # and spanning until the next >
            
            # Pattern for the orphan "double content" block seen in blog.html
            # It starts with 'content="CodeFoxSoft' appearing at the start of a line
            # followed by lines until '>"'
            
            # Using basic string replacement for safety first
            if 'content="CodeFoxSoft blog, game development blog' in content:
                # We want to remove this specific known bad string block
                # regex to capture it: content="CodeFoxSoft blog[\s\S]*?">
                content = re.sub(r'content="CodeFoxSoft blog, game development blog[\s\S]*?">', '', content)

            # 2. GENERAL FIX FOR OLD KEYWORDS
            # Replace any EXISTING <meta name="keywords" ... > block with the correct one.
            # This handles alldownload.html's multi-line valid tag.
            
            # Regex: <meta name="keywords" ... >
            # We use a lookahead to ensure we don't eat the next tag if things are messy,
            # but standard greedy match until > is usually fine for meta tags.
            content = re.sub(r'<meta\s+name="keywords"[\s\S]*?>', correct_keywords, content)

            # 3. CLEAN UP empty lines created by removals
            content = re.sub(r'\n\s*\n\s*\n', '\n\n', content)

            if content != original_content:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.write(content)
                print(f"Fixed {file_path}")

        except Exception as e:
            print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    fix_keywords_safe()
