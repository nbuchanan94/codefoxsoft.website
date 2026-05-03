import re
content = open('test.html', 'r', encoding='utf-8').read()
print('w-full mx:', re.findall(r'class=[^>]*w-full[^>]*mx-[^>]*', content))
print('mx w-full:', re.findall(r'class=[^>]*mx-[^>]*w-full[^>]*', content))
