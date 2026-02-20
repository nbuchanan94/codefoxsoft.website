
import json

with open('C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\mazes.json', 'r') as f:
    data = json.load(f)

with open('C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\maps_data.txt', 'w') as out:
    for k, v in data.items():
        out.write(f'            {k}: {{ grid: {v["grid"]}, start: {v["start"]}, goal: {v["goal"]} }},\n')
print("Formatted maps saved to maps_data.txt")
