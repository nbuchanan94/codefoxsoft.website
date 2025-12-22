import os
import glob
from datetime import datetime

def update_html_files():
    # New head links
    new_links = """    <link rel="apple-touch-icon" sizes="180x180" href="apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="favicon-16x16.png">
    <link rel="manifest" href="manifest.json">
"""
    
    # Old icon line to replace (strip whitespace for flexible matching)
    old_icon_line_content = '<link rel="icon" href="logo.png" type="image/png">'
    
    # Footer replacement
    # We look for "2026 CodeFoxSoft"
    footer_old = "2026 CodeFoxSoft"
    footer_new = '<span id="year"></span> CodeFoxSoft'

    # Script details
    script_check = 'document.getElementById(\'year\')'
    script_content = """    <script>
        document.getElementById('year').textContent = new Date().getFullYear();
    </script>
</body>"""

    files = glob.glob("*.html")
    for file in files:
        if file.startswith("yandex"): continue
        
        with open(file, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        new_lines = []
        modified = False
        
        for line in lines:
            # Handle Icon Link
            if old_icon_line_content in line:
                # Preserve indentation if possible, but the block has its own indents
                new_lines.append(new_links)
                modified = True
                continue
            
            # Handle Footer
            if footer_old in line:
                line = line.replace(footer_old, footer_new)
                modified = True
            
            # Helper to append if not modified by checking blocks above
            new_lines.append(line)
        
        # Join new new_lines to handle the body script insertion on the string content
        content = "".join(new_lines)
        
        # Handle Script
        if script_check not in content:
            if "</body>" in content:
                content = content.replace("</body>", script_content)
                modified = True
        
        if modified:
            with open(file, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Updated {file}")
        else:
            print(f"No changes for {file}")

def update_sitemap():
    if not os.path.exists("sitemap.xml"):
        print("sitemap.xml not found")
        return

    today = datetime.now().strftime("%Y-%m-%d")
    
    with open("sitemap.xml", 'r', encoding='utf-8') as f:
        content = f.read()
    
    import re
    new_content = re.sub(r'<lastmod>.*?</lastmod>', f'<lastmod>{today}</lastmod>', content)
    
    if content != new_content:
        with open("sitemap.xml", 'w', encoding='utf-8') as f:
            f.write(new_content)
        print("Updated sitemap.xml")
    else:
        print("sitemap.xml already up to date")

if __name__ == "__main__":
    update_html_files()
    update_sitemap()
