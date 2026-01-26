import glob
import os

def fix_all():
    new_links = """    <link rel="apple-touch-icon" sizes="180x180" href="apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="favicon-16x16.png">
    <link rel="manifest" href="manifest.json">
"""
    old_icon = '<link rel="icon" href="logo.png" type="image/png">'
    
    footer_old = "2026 CodeFoxSoft"
    footer_new = '<span id="year"></span> CodeFoxSoft'
    
    schema_old = '"logo": "https://www.codefoxsoft.com/assets/logo.png"'
    schema_new = '"logo": "https://www.codefoxsoft.com/logo.png"'

    script_check = "document.getElementById('year')"
    script_code = """    <script>
        document.getElementById('year').textContent = new Date().getFullYear();
    </script>
</body>"""

    new_keywords_content = 'content="CodeFoxSoft, Indie Game Developer, Web Developer, App Development, AI Solutions, Nick Larkin, Codefox soft"'

    files = glob.glob("*.html")
    for file_path in files:
        if file_path.startswith("yandex"): continue
        
        with open(file_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        new_lines = []
        modified = False
        
        for line in lines:
            # 1. Update Icon Links (string match in line)
            if old_icon in line:
                # We need to preserve indentation if possible or just replace
                # simpler to just append the block if it's the specific line
                # But 'line' has the content.
                line = line.replace(old_icon, new_links)
                modified = True
            
            # 2. Update Footer
            if footer_old in line:
                line = line.replace(footer_old, footer_new)
                modified = True

            # 3. Update Schema
            if schema_old in line:
                line = line.replace(schema_old, schema_new)
                modified = True

            # 4. Update Keywords (Robust indentation preservation)
            if '<meta name="keywords"' in line:
                indent = line[:line.find('<')]
                line = f'{indent}<meta name="keywords"\n{indent}    {new_keywords_content}>\n'
                modified = True

            new_lines.append(line)
        
        # 5. Add Script if needed
        content = "".join(new_lines)
        if footer_new in content and script_check not in content:
            if "</body>" in content:
                content = content.replace("</body>", script_code)
                modified = True

        if modified:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Updated {file_path}")
        else:
            print(f"No changes for {file_path}")

    # Fix yandex file
    yandex_content = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Yandex Verification</title>
</head>
<body>
    <h1>Yandex Verification</h1>
    <p>Verification Code: 52319b172d8b1527</p>
</body>
</html>"""
    yandex_files = glob.glob("yandex*.html")
    for yf in yandex_files:
        with open(yf, 'w', encoding='utf-8') as f:
            f.write(yandex_content)
        print(f"Fixed structure of {yf}")

if __name__ == "__main__":
    fix_all()
