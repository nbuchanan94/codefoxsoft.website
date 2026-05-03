import re

filepath = 'test.html'
with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Add overflow-x: hidden to body CSS
content = content.replace(
    '''        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
        }''',
    '''        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            overflow-x: hidden;
        }'''
)

# 2. Remove the CSS transitions from animation classes
content = re.sub(
    r'transition: opacity 0\.6s ease-out, transform 0\.6s ease-out;\s*will-change: opacity, transform;',
    '',
    content
)
content = re.sub(
    r'transition: opacity 0\.5s ease-out, transform 0\.5s ease-out;',
    '',
    content
)

# 3. Add .glass-panel to CSS, replacing .hero-content-box rules
glass_css = '''        .glass-panel {
            background-color: rgba(31, 41, 55, 0.3);
            backdrop-filter: blur(8px);
            -webkit-backdrop-filter: blur(8px);
            border: 1px solid rgba(255, 255, 255, 0.05);
        }
        @media (max-width: 768px) {
            .glass-panel {
                background-color: rgba(31, 41, 55, 0.1) !important;
                backdrop-filter: none !important;
                -webkit-backdrop-filter: none !important;
                border: none;
            }
            .glass-panel h1,
            .glass-panel p,
            .glass-panel h3 {
                text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.8), 0 0 8px rgba(0, 0, 0, 0.5);
            }
        }'''
content = re.sub(
    r'/\* Hero Content Box - Base styles for desktop \*/.*?-webkit-backdrop-filter: none !important;\s*\}',
    glass_css,
    content,
    flags=re.DOTALL
)

# Remove the text-shadow rule from old .hero-content-box since it's now in .glass-panel
content = re.sub(
    r'\.hero-content-box h1,\s*\.hero-content-box p \{\s*/\* Reduce text-shadow.*?\s*\}',
    '',
    content,
    flags=re.DOTALL
)

# 4. Remove section-angled-top and section-angled-bottom from CSS
content = re.sub(
    r'/\* Unique section shapes using clip-path.*?\s*\}\s*\}\s*',
    '',
    content,
    flags=re.DOTALL
)

# 5. Remove `<div class="overflow-x-hidden w-full relative">` and its closing tag
content = content.replace('    <!-- All sections are now within a main container -->\n    <div class="overflow-x-hidden w-full relative">\n', '')
content = content.replace('        </section>\n    </div>\n\n    <!-- Footer -->', '        </section>\n\n    <!-- Footer -->')

# 6. Apply .glass-panel to .hero-content-box HTML
content = content.replace('class="hero-content-box hero-anim-in', 'class="glass-panel hero-anim-in')

# 7. Add gradient to hero video
content = content.replace(
    '<div class="absolute inset-0 bg-gray-900 bg-opacity-40"></div>',
    '<div class="absolute inset-0 bg-gray-900 bg-opacity-40"></div>\n                <div class="absolute bottom-0 left-0 w-full h-32 bg-gradient-to-b from-transparent to-[#0c0d11]"></div>'
)

# 8. Clean up section tags: remove bg-gray-* and bg-opacity-* and section-angled-*
content = re.sub(
    r'<section id="(.*?)" class="py-20 .*? fade-in .*?">',
    r'<section id="\1" class="py-20 fade-in">',
    content
)
content = re.sub(
    r'<section id="(.*?)" class="py-20 .*? section-angled-.*?">',
    r'<section id="\1" class="py-20">',
    content
)

# 9. Replace bg-gray-* with glass-panel in cards and forms
content = content.replace('class="bg-gray-700 p-8', 'class="glass-panel p-8')
content = content.replace('class="bg-gray-800 rounded-xl', 'class="glass-panel rounded-xl')
content = content.replace('class="max-w-xl mx-auto bg-gray-700 p-8', 'class="max-w-xl mx-auto glass-panel p-8')

# 10. Update footer to use glass-panel
content = content.replace('class="bg-gray-900 bg-opacity-70 backdrop-blur-sm py-10', 'class="glass-panel py-10')

# 11. Optional: Make sure inputs in contact form are slightly transparent
content = content.replace('bg-gray-600 border border-gray-500', 'bg-gray-800 bg-opacity-50 border border-gray-600')

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print("test.html successfully updated.")
