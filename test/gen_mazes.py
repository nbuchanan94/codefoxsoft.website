
import random
import json

def generate_maze(width, height):
    grid = [[1 for _ in range(width)] for _ in range(height)]
    
    def walk(x, y):
        grid[y][x] = 0
        dirs = [(0, 2), (0, -2), (2, 0), (-2, 0)]
        random.shuffle(dirs)
        for dx, dy in dirs:
            nx, ny = x + dx, y + dy
            if 0 < nx < width - 1 and 0 < ny < height - 1 and grid[ny][nx] == 1:
                grid[y + dy // 2][x + dx // 2] = 0
                walk(nx, ny)
    
    walk(1, 1)
    return grid

levels = {}
for i in range(11, 21):
    size = 17 + (i - 11) * 2 # 17x17 to 35x35? No, let's keep it reasonable.
    # User said "twice the size". 9x9 -> 18x18. So 19x19 or 21x21.
    size = 19 if i < 15 else 21
    if i == 20: size = 25 # Final level mega maze
    
    grid = generate_maze(size, size)
    levels[i] = {
        "grid": grid,
        "start": {"x": 1, "z": 1},
        "goal": {"x": size - 2, "z": size - 2}
    }

with open('C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\mazes.json', 'w') as f:
    json.dump(levels, f, indent=4)
print("Saved to mazes.json")
