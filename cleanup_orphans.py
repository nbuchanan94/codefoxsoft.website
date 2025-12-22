import glob

def cleanup_orphans():
    files = glob.glob("*.html")
    
    # Signatures of the orphaned lines (from alldownload.html, foxrundownload.html, etc.)
    # These lines are visibly breaking the page.
    bad_prefixes = [
        'content="CodeFoxSoft downloads',
        'content="Fox Run, Fox Run',
        'content="Space Pong, Space',
        'content="CodeFoxSoft blog',
        'content="CodeFoxSoft, Indie', # In case there's another variant
        'CodeFoxSoft games, download games', # Continuation lines
        'CodeFoxSoft apps, game download', 
        'download Fox Run, Android',
        'indie game, endless runner',
        'Space Pong Android, download',
        'game, classic game remake',
        'blog, Nicholas Larkin',
        'tips, tech articles',
        'blog, game development'
    ]
    
    # We will use valid containment check.
    # Lines 12-15 in alldownload.html look like:
    # 12: content="...
    # 13: CodeFoxSoft games, ...
    # 14: CodeFoxSoft apps...
    # 15: developer games">
    
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
            
            new_lines = []
            modified = False
            
            for line in lines:
                s = line.strip()
                
                # Check if this line matches our "bad" list
                is_bad = False
                
                # Check specific start patterns
                if s.startswith('content="'):
                    # Check if it contains the known bad keyword starts
                    if any(x in s for x in ["CodeFoxSoft downloads", "Fox Run, Fox Run", "Space Pong, Space"]):
                         is_bad = True
                
                # Check continuation lines (harder, but they usually contain specific phrases)
                # Or we can just look at the specific text reported
                if any(x in s for x in bad_prefixes):
                    # Double check we aren't killing valid lines
                    # Valid lines are usually inside <meta ...>
                    # But we replaced the meta tag with a single liner already!
                    # So ANY line containing "CodeFoxSoft downloads" (old keyword) is definitely garbage now.
                    if "<meta" not in s: 
                         is_bad = True
                
                # Special cleanup for the tail end of the garbage
                if s.endswith('developer games">') or s.endswith('Buchanan games">') or s.endswith('arcade game">'):
                    if "<meta" not in s:
                        is_bad = True

                if is_bad:
                    print(f"Removing orphan in {file_path}: {s[:50]}...")
                    modified = True
                    continue
                
                new_lines.append(line)
            
            if modified:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.writelines(new_lines)
                print(f"Cleaned {file_path}")

        except Exception as e:
            print(f"Error processing {file_path}: {e}")

if __name__ == "__main__":
    cleanup_orphans()
