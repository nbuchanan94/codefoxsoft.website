import glob
import re

def fix_keywords_final():
    # The target authorized keyword line
    correct_tag = '<meta name="keywords" content="CodeFoxSoft, Indie Game Developer, Web Developer, App Development, AI Solutions, Nicholas Larkin Buchanan, Codefox soft">'
    
    files = glob.glob("*.html")
    
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()

            original_content = content
            
            # Step 1: Remove potentially malformed or old keywords blocks using regex
            # matches: <meta name="keywords" [anything until >] including newlines
            # This handles the case in alldownload.html where it spans multiple lines
            # and the case in blog.html where the FIRST valid one exists (we'll replace it to be sure)
            
            # Regex explanation:
            # <meta\s+name="keywords"  -> Match start
            # [\s\S]*?                 -> Match anything non-greedy (including newlines)
            # >                        -> Match closing bracket
            
            # We replace ALL occurrences with the correct tag
            pattern = r'<meta\s+name="keywords"[\s\S]*?>'
            content = re.sub(pattern, correct_tag, content)
            
            # Step 2: Clean up orphaned content attributes (like in blog.html)
            # These look like: content="CodeFoxSoft blog... ... ... ">
            # We look for lines starting with whitespace + content="...
            # Note: This is dangerous if we match valid content attributes inside other tags.
            # But valid attributes usually follow a tag name or other attribute on the/previous line.
            # The orphan usually sits on its own line(s).
            
            # Let's try to remove specific known bad artifacts identified in grep
            # 1. Double content line in blog.html (and potentially others)
            # It starts with content="CodeFoxSoft...
            
            # Regex for orphan content line(s) ending with ">"
            # We match `content="...` that is NOT preceded by `<meta` on the same line (roughly)
            # A safer way: scan line by line.
            
            lines = content.split('\n')
            new_lines = []
            skip_buffer = False
            
            for i, line in enumerate(lines):
                stripped = line.strip()
                
                # Check for the specific "old" keyword list starting with content=
                # Known signatures from audit:
                # content="CodeFoxSoft blog, ...
                # content="Fox Run, Fox Run download...
                # content="Space Pong...
                # content="CodeFoxSoft downloads...
                
                # If we see a line starting with content=" and it contains known old keywords, kill it.
                if stripped.startswith('content="') and (
                    "CodeFoxSoft blog" in stripped or 
                    "Fox Run" in stripped or 
                    "Space Pong" in stripped or 
                    "CodeFoxSoft downloads" in stripped
                ):
                    print(f"Removing orphan artifact in {file_path}: {stripped[:50]}...")
                    # If it doesn't end with ">", we might need to skip subsequent lines too?
                    # In alldownload.html, it spans lines.
                    if not stripped.endswith('>'):
                        skip_buffer = True
                    continue
                
                if skip_buffer:
                    # Continue skipping until we hit the closing ">"
                    if stripped.endswith('>'):
                        skip_buffer = False
                    continue
                    
                new_lines.append(line)
            
            final_content = '\n'.join(new_lines)
            
            # Additional cleanup for empty lines generated
            final_content = re.sub(r'\n\s*\n\s*\n', '\n\n', final_content)

            if final_content != original_content:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.write(final_content)
                print(f"Fixed {file_path}")
            else:
                pass # No changes needed

        except Exception as e:
            print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    fix_keywords_final()
