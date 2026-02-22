abc_path = "C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\abc.html"
levels_path = "C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\new_levels.txt"

with open(abc_path, "r", encoding="utf-8") as f:
    abc_content = f.read()

with open(levels_path, "r", encoding="utf-8") as f:
    new_levels_content = f.read()

start_magic = "        const themes = {"
end_magic = "        };\n\n        function log("

start_idx = abc_content.find(start_magic)
end_idx = abc_content.find(end_magic)

if start_idx != -1 and end_idx != -1:
    new_abc = abc_content[:start_idx] + new_levels_content + "\n\n" + abc_content[end_idx:]
    with open(abc_path, "w", encoding="utf-8") as f:
        f.write(new_abc)
    print("INJECTED SUCCESSFULLY")
else:
    print("FAILED TO FIND BOUNDARIES")
