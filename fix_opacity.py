import re
content = open('test.html', 'r', encoding='utf-8').read()
content = content.replace(' style="opacity: 0;"', '')
open('test.html', 'w', encoding='utf-8').write(content)
