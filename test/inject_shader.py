abc_path = "C:\\Users\\psych\\Documents\\codefoxsoftsite\\codefoxsoft.website\\test\\abc.html"

with open(abc_path, "r", encoding="utf-8") as f:
    abc = f.read()

# Fix the stripped rawPercent
stripped = "                    const activeTracks = clip.tracks.length;"
fixed = "                    const rawPercent = Math.round((boundOk / totalTracks) * 100);\n                    const activeTracks = clip.tracks.length;"
abc = abc.replace(stripped, fixed)

# Replace the material creation
old_mat = """        function createThemeMaterials(t) {
            return {
                build: new THREE.MeshStandardMaterial({
                    color: t.wallBase,
                    roughness: 0.2,
                    metalness: 0.8
                }),
                road: new THREE.MeshStandardMaterial({ color: t.floorBase, roughness: 0.9, metalness: 0.2 })
            };
        }"""

new_mat = """        function createThemeMaterials(t) {
            const wallMat = new THREE.MeshStandardMaterial({
                color: t.wallBase,
                roughness: 0.2,
                metalness: 0.8
            });

            // Procedural Grid & Noise Shader Injection
            wallMat.onBeforeCompile = (shader) => {
                shader.uniforms.uThemeColor = { value: new THREE.Color(t.fog) };
                
                shader.vertexShader = `
                    varying vec3 vWorldPosition;
                    ` + shader.vertexShader.replace(
                    `#include <worldpos_vertex>`,
                    `#include <worldpos_vertex>
                    vWorldPosition = (modelMatrix * vec4(position, 1.0)).xyz;`
                );

                shader.fragmentShader = `
                    uniform vec3 uThemeColor;
                    varying vec3 vWorldPosition;
                    
                    // Simple pseudo-random hash
                    float hash(vec2 p) { return fract(1e4 * sin(17.0 * p.x + p.y * 0.1) * (0.1 + abs(sin(p.y * 13.0 + p.x)))); }
                    
                    ` + shader.fragmentShader.replace(
                    `#include <dithering_fragment>`,
                    `#include <dithering_fragment>
                    
                    // Procedural Grid Lines
                    vec2 gridPos = vWorldPosition.xz / 200.0;
                    vec2 gridPosVert = vec2(vWorldPosition.x + vWorldPosition.z, vWorldPosition.y) / 200.0;
                    
                    vec2 grid = abs(fract(gridPos - 0.5) - 0.5) / fwidth(gridPos);
                    vec2 gridV = abs(fract(gridPosVert - 0.5) - 0.5) / fwidth(gridPosVert);
                    
                    float line = min(min(grid.x, grid.y), min(gridV.x, gridV.y));
                    
                    // Glitchy block noise
                    vec2 blockPos = floor(vWorldPosition.xy / 400.0 + vWorldPosition.z / 400.0);
                    float blockNoise = hash(blockPos);
                    
                    if (line < 1.0 || (blockNoise > 0.85 && blockNoise < 0.87)) {
                        gl_FragColor = vec4(uThemeColor * 1.5, 1.0); // Neon glow lines & blocks
                    }
                    `
                );
            };

            return {
                build: wallMat,
                road: new THREE.MeshStandardMaterial({ color: t.floorBase, roughness: 0.9, metalness: 0.2 })
            };
        }"""

abc = abc.replace(old_mat, new_mat)

with open(abc_path, "w", encoding="utf-8") as f:
    f.write(abc)

print("Shader injected and syntax repaired.")
