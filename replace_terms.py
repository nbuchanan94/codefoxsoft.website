import os
import glob

directory = r'c:\Users\psych\Documents\website\codefoxsoft.com'
html_files = glob.glob(os.path.join(directory, '*.html'))

replacements = {
    '"founder": {': '"creator": {',
    '"@type": "Organization"': '"@type": "Project"',
    'or "Company"': 'or "Project"',
    'other company.</small>': 'other entity.</small>',
    'Company News': 'Project News',
    'Company Launch': 'Project Launch',
    'company updates': 'project updates',
    'company, mission': 'project, mission',
    'a company built on': 'a creative initiative built on',
    'business purposes:': 'project purposes:',
    'business is as indicated': 'project is as indicated',
    'business looking to': 'creator or team looking to',
    'Your Name/Business Name': 'Your Name/Project Name',
    'Company Name': 'Project Name'
}

for filepath in html_files:
    if 'Copy' in filepath or 'OLD' in filepath:
        continue
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    new_content = content
    for old_str, new_str in replacements.items():
        new_content = new_content.replace(old_str, new_str)
        # Also handle lowercase variants where applicable
        if old_str == 'Company Launch':
            new_content = new_content.replace('company launch', 'project launch')
            new_content = new_content.replace('Company launch', 'Project launch')
            
    if new_content != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f'Updated {os.path.basename(filepath)}')

print('Done.')
