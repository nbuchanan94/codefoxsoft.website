
function generateMaze(width, height) {
    const grid = Array(height).fill(0).map(() => Array(width).fill(1));

    function walk(x, y) {
        grid[y][x] = 0;
        const dirs = [[0, 2], [0, -2], [2, 0], [-2, 0]].sort(() => Math.random() - 0.5);
        for (const [dx, dy] of dirs) {
            const nx = x + dx, ny = y + dy;
            if (nx > 0 && nx < width - 1 && ny > 0 && ny < height - 1 && grid[ny][nx] === 1) {
                grid[y + dy / 2][x + dx / 2] = 0;
                walk(nx, ny);
            }
        }
    }

    walk(1, 1);
    return grid;
}

const levels = {};
for (let i = 11; i <= 20; i++) {
    const size = 15 + (i - 11) * 2; // Incremental size
    const grid = generateMaze(size, size);
    levels[i] = {
        grid: grid,
        start: { x: 1, z: 1 },
        goal: { x: size - 2, z: size - 2 }
    };
}

console.log(JSON.stringify(levels, null, 4));
