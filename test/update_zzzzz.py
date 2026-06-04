import re

abc_path = r'c:\Users\psych\Documents\website\codefoxsoft.com\test\abc2.html'
z_path = r'c:\Users\psych\Documents\website\codefoxsoft.com\test\zzzzz.html'

with open(abc_path, 'r', encoding='utf-8') as f:
    abc = f.read()
with open(z_path, 'r', encoding='utf-8') as f:
    z = f.read()

# 1. CSS
start_menu_css_match = re.search(r'/\* ===== START MENU ===== \*/.*?(?=</style>)', abc, re.DOTALL)
css_to_add = start_menu_css_match.group(0) if start_menu_css_match else ''

joystick_css = '''
        /* Joystick Controls */
        #joystick-zone {
            position: absolute;
            bottom: 20px;
            left: 20px;
            width: 150px;
            height: 150px;
            background: rgba(0, 255, 204, 0.1);
            border: 2px solid rgba(0, 255, 204, 0.3);
            border-radius: 50%;
            display: none; /* hidden by default */
            z-index: 200;
            touch-action: none;
        }
        #joystick-knob {
            position: absolute;
            top: 50%;
            left: 50%;
            width: 60px;
            height: 60px;
            margin-top: -30px;
            margin-left: -30px;
            background: rgba(0, 255, 204, 0.6);
            border-radius: 50%;
            box-shadow: 0 0 15px #00ffcc;
            pointer-events: none;
        }
        #touch-sprint-btn {
            position: absolute;
            bottom: 40px;
            right: 40px;
            width: 80px;
            height: 80px;
            background: rgba(255, 204, 0, 0.2);
            border: 2px solid rgba(255, 204, 0, 0.5);
            border-radius: 50%;
            display: none;
            z-index: 200;
            color: #ffcc00;
            text-align: center;
            line-height: 76px;
            font-weight: bold;
            font-family: sans-serif;
            touch-action: none;
            user-select: none;
        }
        #touch-sprint-btn.active {
            background: rgba(255, 204, 0, 0.6);
            box-shadow: 0 0 20px #ffcc00;
        }
'''
z = z.replace('</style>', css_to_add + '\n' + joystick_css + '\n    </style>')

# 2. HTML
start_menu_html_match = re.search(r'<!-- ===== START MENU ===== -->.*?<!-- ===== SETTINGS MENU ===== -->.*?</div>\s*</div>', abc, re.DOTALL)
html_to_add = start_menu_html_match.group(0) if start_menu_html_match else ''

html_to_add = html_to_add.replace(
    '<div class="setting-item" id="setting-difficulty" onclick="cycleDifficulty()">',
    '<div class="setting-item" id="setting-joystick" onclick="toggleJoystick()">'
)
html_to_add = html_to_add.replace(
    '<span class="setting-icon">⚔</span>',
    '<span class="setting-icon">🕹</span>'
)
html_to_add = html_to_add.replace(
    '<span class="setting-value" id="diff-display">NORMAL</span>',
    '<span class="setting-value" id="joy-display">OFF</span>'
)
html_to_add = html_to_add.replace(
    '<span class="setting-label">Difficulty</span>',
    '<span class="setting-label">Joystick</span>'
)

# Insert joystick HTML
joystick_html = '''
    <div id="joystick-zone"><div id="joystick-knob"></div></div>
    <div id="touch-sprint-btn">SPRINT</div>
'''

z = z.replace('<body>', '<body>\n' + html_to_add + '\n' + joystick_html)

# Remove old intro overlay
z = re.sub(r'<div id=\"intro-overlay\">.*?</div>', '', z, flags=re.DOTALL)

# 3. Bone Aliases
bone_aliases_match = re.search(r'const boneAliases = \{.*?\};', abc, re.DOTALL)
bone_aliases_new = bone_aliases_match.group(0) if bone_aliases_match else ''

z = re.sub(r'const boneAliases = \{.*?\};', bone_aliases_new, z, flags=re.DOTALL)

# Also port normalization from abc2 if possible
norm_abc_match = re.search(r'const stand = \(s\) =>.*?nodeMap\.set\(sName, node\.name\);\n                }\n            \}\);', abc, re.DOTALL)
norm_z_match = re.search(r'const nodeMap = new Map\(\);\n            model\.traverse\(node => \{.*?\n            \}\);', z, re.DOTALL)

if norm_abc_match and norm_z_match:
    z = z.replace(norm_z_match.group(0), 'const nodeMap = new Map();\n            ' + norm_abc_match.group(0))


# 4. JS Functions for menu and touch
js_logic = '''
        let menuTransitioning = false;
        let useJoystick = false;

        window.menuAction = function(action) {
            if (menuTransitioning) return;
            switch (action) {
                case 'play':
                    if (!isReady) return;
                    menuTransitioning = true;
                    document.getElementById('start-menu').classList.add('hidden');
                    setTimeout(() => {
                        gameActive = true;
                        if (!startTime) startTime = Date.now();
                        menuTransitioning = false;
                    }, 500);
                    break;
                case 'settings':
                    document.getElementById('start-menu').classList.add('hidden');
                    document.getElementById('settings-menu').classList.add('active');
                    break;
                case 'exit':
                    window.location.href = 'https://codefoxsoft.com';
                    break;
                case 'back':
                    document.getElementById('settings-menu').classList.remove('active');
                    document.getElementById('start-menu').classList.remove('hidden');
                    break;
            }
        };

        window.toggleJoystick = function() {
            useJoystick = !useJoystick;
            document.getElementById('joy-display').innerText = useJoystick ? 'ON' : 'OFF';
            document.getElementById('joystick-zone').style.display = useJoystick ? 'block' : 'none';
            document.getElementById('touch-sprint-btn').style.display = useJoystick ? 'block' : 'none';
        };

        window.updateVolume = function(val) {
            document.getElementById('vol-display').innerText = val + '%';
        };

        // TOUCH CONTROLS
        let touchStartX = 0;
        let touchStartY = 0;
        let swipeThreshold = 30;

        // Joystick vars
        const joyZone = document.getElementById('joystick-zone');
        const joyKnob = document.getElementById('joystick-knob');
        const sprintBtn = document.getElementById('touch-sprint-btn');
        let joyActive = false;
        let joyCX = 0, joyCY = 0;
        let joyTouchId = null;

        // Joystick Logic
        if(joyZone) {
            joyZone.addEventListener('touchstart', e => {
                e.preventDefault();
                const touch = e.changedTouches[0];
                joyTouchId = touch.identifier;
                joyActive = true;
                const rect = joyZone.getBoundingClientRect();
                joyCX = rect.left + rect.width / 2;
                joyCY = rect.top + rect.height / 2;
                updateJoystick(touch.clientX, touch.clientY);
            }, {passive: false});

            joyZone.addEventListener('touchmove', e => {
                e.preventDefault();
                if (!joyActive) return;
                for(let i=0; i<e.changedTouches.length; i++) {
                    if(e.changedTouches[i].identifier === joyTouchId) {
                        updateJoystick(e.changedTouches[i].clientX, e.changedTouches[i].clientY);
                    }
                }
            }, {passive: false});

            function endJoy(e) {
                for(let i=0; i<e.changedTouches.length; i++) {
                    if(e.changedTouches[i].identifier === joyTouchId) {
                        joyActive = false;
                        joyTouchId = null;
                        joyKnob.style.transform = `translate(0px, 0px)`;
                        keys['KeyW'] = false; keys['KeyS'] = false; keys['KeyA'] = false; keys['KeyD'] = false;
                    }
                }
            }
            joyZone.addEventListener('touchend', endJoy);
            joyZone.addEventListener('touchcancel', endJoy);

            function updateJoystick(tx, ty) {
                let dx = tx - joyCX;
                let dy = ty - joyCY;
                let dist = Math.hypot(dx, dy);
                let maxDist = 40;
                if (dist > maxDist) {
                    dx = (dx/dist)*maxDist;
                    dy = (dy/dist)*maxDist;
                }
                joyKnob.style.transform = `translate(${dx}px, ${dy}px)`;
                
                keys['KeyW'] = dy < -10;
                keys['KeyS'] = dy > 10;
                keys['KeyA'] = dx < -10;
                keys['KeyD'] = dx > 10;
            }

            sprintBtn.addEventListener('touchstart', e => { e.preventDefault(); sprintBtn.classList.add('active'); keys['ShiftLeft'] = true; });
            sprintBtn.addEventListener('touchend', e => { e.preventDefault(); sprintBtn.classList.remove('active'); keys['ShiftLeft'] = false; });
        }

        // Discrete Swipe Logic (only active if joystick is OFF)
        window.addEventListener('touchstart', e => {
            if (useJoystick || e.target.closest('.menu-buttons') || e.target.closest('#settings-menu')) return;
            touchStartX = e.touches[0].clientX;
            touchStartY = e.touches[0].clientY;
        }, {passive: false});

        window.addEventListener('touchmove', e => {
            if (useJoystick) return;
            if (!touchStartX || !touchStartY) return;
            let dx = e.touches[0].clientX - touchStartX;
            let dy = e.touches[0].clientY - touchStartY;

            if (Math.abs(dx) > swipeThreshold || Math.abs(dy) > swipeThreshold) {
                // Determine swipe direction
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        // Swipe Right -> Move Right
                        keys['KeyD'] = true; setTimeout(() => keys['KeyD'] = false, 500);
                    } else {
                        // Swipe Left -> Move Left
                        keys['KeyA'] = true; setTimeout(() => keys['KeyA'] = false, 500);
                    }
                } else {
                    if (dy > 0) {
                        // Swipe Down -> Backwards
                        keys['KeyS'] = true; setTimeout(() => keys['KeyS'] = false, 500);
                    } else {
                        // Swipe Up -> Forwards / Sprint
                        keys['KeyW'] = true; keys['ShiftLeft'] = true;
                        setTimeout(() => { keys['KeyW'] = false; keys['ShiftLeft'] = false; }, 1000);
                    }
                }
                touchStartX = 0;
                touchStartY = 0;
            }
        }, {passive: false});

        window.addEventListener('touchend', e => {
            if (useJoystick) return;
            touchStartX = 0;
            touchStartY = 0;
            keys['KeyW'] = false; keys['ShiftLeft'] = false;
            keys['KeyA'] = false; keys['KeyD'] = false; keys['KeyS'] = false;
        });

'''

z = z.replace('function animate() {', js_logic + '\n        function animate() {')

# Enable play button only when loaded
z = z.replace('id="btn-play"', 'id="btn-play" style="opacity:0.5; pointer-events:none;"')
z = z.replace("document.getElementById('status-text').innerText = \"SYSTEMS ONLINE\";", 
              "if(document.getElementById('status-text')) document.getElementById('status-text').innerText = \"SYSTEMS ONLINE\"; if(document.getElementById('overlay-status-text')) document.getElementById('overlay-status-text').innerText = \"SYSTEMS ONLINE\"; document.getElementById('btn-play').style.opacity = 1; document.getElementById('btn-play').style.pointerEvents = 'auto';")
z = z.replace("document.getElementById('click-prompt').style.display = \"block\";", "")

# We removed intro-overlay, so we need to remove references to it in startAction
z = z.replace("document.getElementById('intro-overlay').classList.add('hidden');", "")

with open(r'c:\Users\psych\Documents\website\codefoxsoft.com\test\zzzzz_new.html', 'w', encoding='utf-8') as f:
    f.write(z)
