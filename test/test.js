
        import * as THREE from 'three';
        import { FBXLoader } from 'three/addons/loaders/FBXLoader.js';

        let scene, camera, renderer, mixer, clock, model, modelContainer, fixGroup, goalOrb, nikoLight;
        let currentLevel = 1, levelGroup, gameActive = false, startTime = 0;
        let actions = {}, currentAction, activeIdleKey = 'twerk';
        let animationScores = {}; // Tracks link success %
        let idleTimer = 0;
        let restingThreshold = 10 + Math.random() * 5; // New: Randomized resting duration
        let isReady = false;
        let velX = 0, velZ = 0; // New for movement inertia
        let isPaused = false;

        // Game Feel & Transition Variables
        let walls = [], roads = [], shards = [];
        let isTransitioning = false, transitionTime = 0, hasShifted = false;
        let shakeIntensity = 0;
        let camYaw = 0, camDistance = 800;
        let targetPitch = 18, targetDistance = 400; // New for lerping
        let floatingTexts = [], ghosts = []; // Juice controllers

        // Camera settings
        let camPitch = 18;
        camYaw = 0;
        shakeIntensity = 0;
        let score = 0;

        const keys = {}, cellSize = 1800;
        const mapOffset = 4000;

        let minimapCanvas, minimapCtx;

        const themes = {
            1: { name: "THE BRONX", sky: 0x050510, fog: 0x050510, fogDens: 0.00018, roadColor: '#1a1a1a', sidewalkColor: '#333', buildColor: '#15151a', win1: '#ffaa00', win2: '#00aaff', lightColor: 0xffaa00, ambient: 0.6 },
            2: { name: "NEON DISTRICT", sky: 0x000510, fog: 0x000510, fogDens: 0.00025, roadColor: '#0a0a15', sidewalkColor: '#111', buildColor: '#050510', win1: '#ff00ff', win2: '#00ffff', lightColor: 0x00ffff, ambient: 0.7 },
            3: { name: "THE CORE", sky: 0x100500, fog: 0x100500, fogDens: 0.0003, roadColor: '#150500', sidewalkColor: '#201010', buildColor: '#100000', win1: '#ff0000', win2: '#ffaa00', lightColor: 0xff0000, ambient: 0.9 },
            4: { name: "THE GRID", sky: 0x000a0a, fog: 0x000a0a, fogDens: 0.0002, roadColor: '#001a1a', sidewalkColor: '#003333', buildColor: '#001111', win1: '#00ffff', win2: '#ffffff', lightColor: 0x00ffff, ambient: 0.6 },
            5: { name: "VOID JUNCTION", sky: 0x0a000a, fog: 0x0a000a, fogDens: 0.00022, roadColor: '#1a001a', sidewalkColor: '#2a002a', buildColor: '#110011', win1: '#ff00ff', win2: '#ffaa00', lightColor: 0xff00ff, ambient: 0.5 },
            6: { name: "OVERDRIVE", sky: 0x000000, fog: 0x000000, fogDens: 0.00035, roadColor: '#111111', sidewalkColor: '#222222', buildColor: '#000000', win1: '#ff0000', win2: '#ffffff', lightColor: 0xffffff, ambient: 0.7 },
            7: { name: "THE ARCHIVE", sky: 0x010101, fog: 0x010101, fogDens: 0.00045, roadColor: '#050505', sidewalkColor: '#000', buildColor: '#111', win1: '#fff', win2: '#888', lightColor: 0xffffff, ambient: 1.2 },
            8: { name: "CHROME DESERT", sky: 0x201005, fog: 0x201005, fogDens: 0.00015, roadColor: '#2a1a0a', sidewalkColor: '#332211', buildColor: '#151515', win1: '#ffcc00', win2: '#ffffff', lightColor: 0xffaa00, ambient: 0.8 },
            9: { name: "GLITCH DOMAIN", sky: 0x050005, fog: 0x050005, fogDens: 0.00028, roadColor: '#0a000a', sidewalkColor: '#150015', buildColor: '#000', win1: '#ff00ff', win2: '#00ffff', lightColor: 0xff00ff, ambient: 1.0 },
            10: { name: "DIMENSION ZERO", sky: 0x000000, fog: 0x000000, fogDens: 0.0005, roadColor: '#000', sidewalkColor: '#111', buildColor: '#000', win1: '#ff0000', win2: '#00ff00', lightColor: 0xffffff, ambient: 0.4 },
            11: { name: "CYBER RUINS", sky: 0x100800, fog: 0x100800, fogDens: 0.00018, roadColor: '#1a0d00', sidewalkColor: '#2a1a00', buildColor: '#110800', win1: '#ffd700', win2: '#8b4513', lightColor: 0xffd700, ambient: 0.8 },
            12: { name: "NEON PRISON", sky: 0x001000, fog: 0x001000, fogDens: 0.00025, roadColor: '#000a00', sidewalkColor: '#001a00', buildColor: '#000500', win1: '#00ff00', win2: '#ffffff', lightColor: 0x00ff00, ambient: 0.6 },
            13: { name: "COBALT CATHEDRAL", sky: 0x000020, fog: 0x000020, fogDens: 0.00015, roadColor: '#00001a', sidewalkColor: '#000033', buildColor: '#000011', win1: '#00ffff', win2: '#ffffff', lightColor: 0x0000ff, ambient: 1.0 },
            14: { name: "EMERALD EXPANSE", sky: 0x002000, fog: 0x002000, fogDens: 0.00012, roadColor: '#001a00', sidewalkColor: '#002a00', buildColor: '#001100', win1: '#00ff00', win2: '#ffffff', lightColor: 0x00ff00, ambient: 0.8 },
            15: { name: "RUBY RESERVOIR", sky: 0x200000, fog: 0x200000, fogDens: 0.0002, roadColor: '#1a0000', sidewalkColor: '#2a0000', buildColor: '#110000', win1: '#ff0000', win2: '#ffffff', lightColor: 0xff0000, ambient: 0.7 },
            16: { name: "SAPPHIRE STATION", sky: 0x000030, fog: 0x000030, fogDens: 0.00022, roadColor: '#000a1a', sidewalkColor: '#111', buildColor: '#000015', win1: '#0077ff', win2: '#ffffff', lightColor: 0x0077ff, ambient: 0.6 },
            17: { name: "AMETHYST ABBEY", sky: 0x100020, fog: 0x100020, fogDens: 0.00018, roadColor: '#1a0d1a', sidewalkColor: '#2a1a2a', buildColor: '#110811', win1: '#cc00ff', win2: '#ffffff', lightColor: 0xcc00ff, ambient: 0.9 },
            18: { name: "OBSIDIAN OUTPOST", sky: 0x000000, fog: 0x000000, fogDens: 0.0003, roadColor: '#000', sidewalkColor: '#111', buildColor: '#000', win1: '#ff4400', win2: '#ffffff', lightColor: 0xff4400, ambient: 0.4 },
            19: { name: "QUARTZ QUARRY", sky: 0x202020, fog: 0x202020, fogDens: 0.0001, roadColor: '#1a1a1a', sidewalkColor: '#333', buildColor: '#151515', win1: '#ffffff', win2: '#888', lightColor: 0xffffff, ambient: 1.2 },
            20: { name: "THE ENDLESS", sky: 0x000000, fog: 0x000000, fogDens: 0.0005, roadColor: '#000', sidewalkColor: '#111', buildColor: '#000', win1: '#00ffcc', win2: '#ffffff', lightColor: 0x00ffcc, ambient: 0.5 }
        };

        const maps = {
            1: { grid: [[1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 5, z: 5 } },
            2: { grid: [[1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 5, z: 1 } },
            3: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 7, z: 1 } },
            4: { grid: [[1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 1, 0, 1], [1, 0, 1, 0, 0, 1, 0, 1], [1, 0, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 6, z: 6 } },
            5: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 0, 1, 0, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 1, z: 7 } },
            6: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 9, z: 1 } },
            7: { grid: [[1, 1, 1, 1, 1], [1, 0, 1, 0, 1], [1, 0, 1, 0, 1], [1, 0, 0, 0, 1], [1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 3, z: 3 } },
            8: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 7, z: 5 } },
            9: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 7, z: 1 } },
            10: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 9, z: 1 } },
            11: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 17, z: 17 } },
            12: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1], [1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 17, z: 17 } },
            13: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 17, z: 17 } },
            14: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1], [1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 17, z: 17 } },
            15: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1], [1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 19, z: 19 } },
            16: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1], [1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 19, z: 19 } },
            17: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 19, z: 19 } },
            18: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1], [1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1], [1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 19, z: 19 } },
            19: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 19, z: 19 } },
            20: { grid: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1], [1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1], [1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1], [1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1], [1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1], [1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1], [1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1], [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]], start: { x: 1, z: 1 }, goal: { x: 23, z: 23 } }
        };

        function log(msg, type = 'info') {
            const div = document.getElementById('debug-log');
            const span = document.createElement('div');
            span.className = type;
            span.innerHTML = `> ${msg}`;
            div.appendChild(span);
            div.scrollTop = div.scrollHeight;
        }

        function saveBestTime(lvl, time) {
            let records = JSON.parse(localStorage.getItem('niko_records') || '{}');
            if (!records[lvl] || time < records[lvl]) {
                records[lvl] = time;
                localStorage.setItem('niko_records', JSON.stringify(records));
            }
        }

        function formatTime(sec) {
            return `${Math.floor(sec / 60).toString().padStart(2, '0')}:${(sec % 60).toString().padStart(2, '0')}`;
        }

        function showVictory() {
            const records = JSON.parse(localStorage.getItem('niko_records') || '{}');
            const tbody = document.getElementById('best-tbody');
            tbody.innerHTML = '';
            for (let i = 1; i <= 20; i++) {
                const time = records[i] ? formatTime(records[i]) : "--:--";
                const label = themes[i] ? themes[i].name : `DIMENSION ${i}`;
                tbody.innerHTML += `<tr><td>DIMENSION ${i}: ${label}</td><td class="best-row">${time}</td></tr>`;
            }
            document.getElementById('victory-screen').style.display = 'flex';
        }

        function createThemeMaterials(theme) {
            const rC = document.createElement('canvas'); rC.width = 512; rC.height = 512;
            const rCtx = rC.getContext('2d');
            rCtx.fillStyle = theme.sidewalkColor; rCtx.fillRect(0, 0, 512, 512);
            rCtx.fillStyle = theme.roadColor; rCtx.fillRect(60, 0, 392, 512);
            rCtx.fillStyle = '#444'; for (let i = 0; i < 512; i += 64) rCtx.fillRect(250, i, 12, 32);

            const bC = document.createElement('canvas'); bC.width = 512; bC.height = 1024;
            const bCtx = bC.getContext('2d'); bCtx.fillStyle = theme.buildColor; bCtx.fillRect(0, 0, 512, 1024);
            const eC = document.createElement('canvas'); eC.width = 512; eC.height = 1024;
            const eCtx = eC.getContext('2d'); eCtx.fillStyle = '#000'; eCtx.fillRect(0, 0, 512, 1024);

            for (let y = 40; y < 1024; y += 120) {
                for (let x = 30; x < 512; x += 100) {
                    if (Math.random() > 0.4) {
                        const col = Math.random() > 0.5 ? theme.win1 : theme.win2;
                        bCtx.fillStyle = col; bCtx.globalAlpha = 0.15; bCtx.fillRect(x, y, 65, 45);
                        eCtx.fillStyle = col; eCtx.shadowBlur = 25; eCtx.shadowColor = col; eCtx.fillRect(x, y, 65, 45);
                    }
                }
            }
            return {
                road: new THREE.MeshStandardMaterial({ map: new THREE.CanvasTexture(rC) }),
                build: new THREE.MeshStandardMaterial({
                    map: new THREE.CanvasTexture(bC),
                    emissiveMap: new THREE.CanvasTexture(eC),
                    emissive: 0xffffff, emissiveIntensity: currentLevel === 3 ? 4.5 : 2.5
                }),
                lightColor: theme.lightColor
            };
        }

        function init() {
            clock = new THREE.Clock();
            scene = new THREE.Scene();

            camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 10, 60000);
            renderer = new THREE.WebGLRenderer({ antialias: true });
            renderer.setSize(window.innerWidth, window.innerHeight);
            document.body.appendChild(renderer.domElement);

            // Initialize Mini-map
            minimapCanvas = document.getElementById('minimap');
            minimapCtx = minimapCanvas.getContext('2d');
            minimapCanvas.width = 150;
            minimapCanvas.height = 150;

            const ambient = new THREE.AmbientLight(0xffffff, 0.5);
            scene.add(ambient);
            const sun = new THREE.DirectionalLight(0xffffff, 0.5);
            sun.position.set(2000, 4000, 1000);
            scene.add(sun);

            nikoLight = new THREE.PointLight(0xffffff, 100, 2000);
            scene.add(nikoLight);

            const grid = new THREE.GridHelper(20000, 20, 0x444444, 0x222222);
            scene.add(grid);

            levelGroup = new THREE.Group();
            scene.add(levelGroup);

            const startAction = () => {
                if (!isReady) return;
                // Allow start if not transitioning OR if we've already shifted (waiting for wall rise)
                if (!gameActive && (!isTransitioning || hasShifted)) {
                    gameActive = true;
                    startTime = Date.now();
                    // If we were transitioning, just finish it
                    if (isTransitioning) {
                        isTransitioning = false;
                        walls.forEach(w => w.position.y = 3000); // Snap walls to final pos
                    }
                }
                if (gameActive) {
                    
                }
            };

            window.addEventListener('keydown', (e) => {
                if (e.code === 'Escape') {
                    togglePause();
                    return;
                }
                if (isPaused) return;

                keys[e.code] = true;
                keys[e.key.toLowerCase()] = true; // Fallback for some non-standard keys

                startAction();

                if (e.code === 'KeyT') {
                    targetPitch = (targetPitch < 40 ? 85 : 18);
                    targetDistance = (targetPitch === 85 ? 4000 : 400);
                    log(`Birds-eye: ${targetPitch === 85 ? "ON" : "OFF"}`, "info");
                }
            });
            window.addEventListener('mousedown', startAction);
            window.addEventListener('keyup', (e) => {
                keys[e.code] = false;
                keys[e.key.toLowerCase()] = false;
            });

            buildLevel(currentLevel);
            loadAllAssets();
            animate();
        }

        async function loadFile(loader, key, path) {
            return new Promise((resolve) => {
                loader.load(path,
                    (fbx) => { resolve({ key, fbx }); },
                    undefined,
                    (err) => { resolve({ key, error: true }); }
                );
            });
        }

        async function loadAllAssets() {
            const fbxLoader = new FBXLoader();
            const baseUrl = ''; // Use local files

            log("Loading main model (twerk.fbx)...", "info");
            const modelResult = await loadFile(fbxLoader, 'main', baseUrl + 'twerk.fbx');

            if (!modelResult.error && modelResult.fbx) {
                model = modelResult.fbx;

                // 1. Scene -> modelContainer (moves/turns)
                modelContainer = new THREE.Group();
                scene.add(modelContainer);

                // 2. modelContainer -> fixGroup (handles standing the prone character up)
                fixGroup = new THREE.Group();
                modelContainer.add(fixGroup);

                // 3. fixGroup -> model (handles animations)
                fixGroup.add(model);

                mixer = new THREE.AnimationMixer(model);

                // NOSE ON FLOOR FIX: Stand him up inside the fixGroup
                // If PI/2 was upside down UNDER floor, then -PI/2 is right-side up ABOVE floor.
                fixGroup.rotation.x = -Math.PI / 2;

                // NO MODEL ROTATION HERE: Child rotation relative to X-rotated parent causes "rolling"
                model.rotation.set(0, 0, 0);

                // HEIGHT FIX: Ensure feet are on the floor (Y=0)
                const box = new THREE.Box3().setFromObject(model);
                const size = box.getSize(new THREE.Vector3());
                // Scale to 80 units tall (user request for one notch smaller)
                model.scale.setScalar(80 / size.y);

                // Compute box again after scale to get precise offset
                const scaledBox = new THREE.Box3().setFromObject(model);
                // Lift so min Y (feet) is at floor level (Y=0)
                model.position.y = -scaledBox.min.y;

                // OPTIMIZED MATERIAL HANDLING: Preserve FBX materials/textures
                model.traverse(node => {
                    if (node.isMesh) {
                        node.material.side = THREE.DoubleSide;
                        node.material.depthWrite = true;
                        if (node.material.map) node.material.map.anisotropy = 16;
                    }
                });

                // BONE DIAGNOSTIC: Log all bone names to help with binding
                const boneNames = [];
                model.traverse(n => { if (n.isBone) boneNames.push(n.name); });
                log(`SKELETON READY: Found ${boneNames.length} bones. (e.g. ${boneNames.slice(0, 3).join(', ')})`, "info");

                log("Main FBX loaded, stood upright, and height shifted.", "success");
            } else {
                log("Failed to load local FBX, using fallback box", "error");
                modelContainer = new THREE.Group();
                scene.add(modelContainer);
                model = new THREE.Mesh(new THREE.BoxGeometry(100, 240, 100), new THREE.MeshStandardMaterial({ color: 0x00ffcc }));
                modelContainer.add(model);
                mixer = new THREE.AnimationMixer(model);
            }

            const files = [
                { key: 'walk', path: baseUrl + 'catwalk.fbx' },
                { key: 'resting', path: baseUrl + 'resting.fbx' },
                { key: 'twerk', path: baseUrl + 'twerk.fbx' },
                { key: 'run', path: baseUrl + 'catwalk.fbx' }
            ];

            let maxIdleScore = -1;
            const idlePriority = { 'resting': 1, 'twerk': 2 };

            log("Loading animations from local files...", "info");
            const results = await Promise.all(files.map(f => loadFile(fbxLoader, f.key, f.path)));
            document.getElementById('load-bar').style.width = "100%";

            // Map bones for retargeting
            const nodeMap = new Map();
            const stand = (s) => s.toLowerCase()
                .replace(/[^a-z0-9]/g, '') // Strip symbols first
                .replace(/^mixamorig|^ccbase|^armature|^rl|^bip01/, '')
                .replace(/^left|^l/, 'l').replace(/^right|^r/, 'r')
                .replace(/hand(?=[a-z])/g, '')
                .replace(/foot(?=[a-z])/g, '')
                .replace(/toe(?=[a-z])/g, '')
                .replace(/joint/g, '')
                .replace(/hips?/, 'hip')
                .replace(/spine[s]?/, 'spine')
                .replace(/arm[s]?/, 'arm')
                .replace(/leg[s]?/, 'leg')
                .replace(/([a-z])0?(\d)$/, '$10$2') // Normalize spine1/spine01 -> spine01
                .replace(/[^a-z0-9]/g, '');

            model.traverse(node => {
                if (node.isBone) {
                    const sName = stand(node.name);
                    nodeMap.set(node.name.toLowerCase(), node.name);
                    nodeMap.set(node.name.toLowerCase().replace(/[^a-z0-9]/g, ''), node.name);
                    if (sName.length > 2) nodeMap.set(sName, node.name);
                }
            });

            const boneAliases = {
                'hip': ['pelvis', 'waist', 'root'],
                'spine': ['chest', 'spine01', 'spine02', 'waist'],
                'chest': ['spine01', 'spine02', 'spine03'],
                'spine01': ['spine', 'chest'], 'spine02': ['spine01', 'spine', 'chest'],
                'neck': ['head', 'neck01'], 'head': ['neck01', 'neck'],
                'clavicle': ['shoulder', 'clv'], 'shoulder': ['clavicle', 'upperarm'],
                'arm': ['upperarm', 'bicep'], 'upperarm': ['arm', 'bicep'],
                'forearm': ['elbow', 'lowerarm'], 'hand': ['wrist'],
                'thigh': ['upperleg', 'upleg', 'highleg'], 'upleg': ['thigh', 'upperleg'],
                'calf': ['knee', 'lowerleg'], 'leg': ['calf', 'lowerleg'],
                'foot': ['ankle', 'ball', 'toe01'], 'ankle': ['foot'], 'wrist': ['hand'],
                'ball': ['toe', 'toe01', 'toebase'], 'toe': ['ball', 'toe01'],
                'larm': ['lupperarm', 'larm'], 'rarm': ['rupperarm', 'rarm'],
                // Finger/Toe bridging
                'thumb': ['thmb'], 'index': ['indx'], 'middle': ['mid'], 'ring': ['rng'], 'pinky': ['pnk', 'little'],
                'thmb': ['thumb'], 'indx': ['index'], 'mid': ['middle'], 'rng': ['ring'], 'pnk': ['pinky', 'little'],
                'bigtoe': ['toe1'], 'toe': ['bigtoe']
            };

            results.forEach(res => {
                if (!res.error && res.fbx && res.fbx.animations.length > 0) {
                    // PICK THE BEST CLIP (AccuRig often has poses at index 0, real anim at 1)
                    let bestClip = res.fbx.animations[0];
                    if (res.fbx.animations.length > 1) {
                        res.fbx.animations.forEach(c => {
                            if (c.duration > bestClip.duration) bestClip = c;
                        });
                        log(`  CLIPS [${res.key}]: Found ${res.fbx.animations.length}. Picking longest: ${Math.round(bestClip.duration)}s`, "info");
                    }

                    const clip = bestClip.clone();
                    clip.name = res.key;

                    let boundOk = 0;
                    let totalTracks = clip.tracks.length;

                    clip.tracks.forEach(track => {
                        const trackNameParts = track.name.split('.');
                        const property = trackNameParts.pop();
                        let targetBoneName = null;

                        // Hyper-Robust Matching with L/R preservation
                        for (const p of trackNameParts) {
                            let raw = p.toLowerCase().replace(/mixamorig[|_:]|cc[|_:]base[|_:]|rl[|_:]/g, '');
                            // Standardize L/R
                            raw = raw.replace(/^left/, 'l').replace(/^right/, 'r');
                            const clean = raw.replace(/[^a-z0-9]/g, '');
                            const core = clean.replace(/mixamo|armature|ccbase|rl/g, '');

                            if (nodeMap.has(clean)) targetBoneName = nodeMap.get(clean);
                            else if (nodeMap.has(core)) targetBoneName = nodeMap.get(core);

                            if (!targetBoneName) {
                                for (let aliasKey in boneAliases) {
                                    if (clean.includes(aliasKey) || core.includes(aliasKey)) {
                                        for (let alt of boneAliases[aliasKey]) {
                                            const side = clean.startsWith('l') ? 'l' : (clean.startsWith('r') ? 'r' : '');
                                            const searchKey = side + alt;
                                            if (nodeMap.has(searchKey)) {
                                                targetBoneName = nodeMap.get(searchKey);
                                                break;
                                            }
                                        }
                                    }
                                    if (targetBoneName) break;
                                }
                            }

                            if (!targetBoneName) {
                                for (let [key, val] of nodeMap) {
                                    if (key.length >= 4 && (clean.includes(key) || key.includes(clean))) {
                                        targetBoneName = val;
                                        break;
                                    }
                                }
                            }
                            if (targetBoneName) break;
                        }

                        if (targetBoneName) {
                            track.name = targetBoneName + "." + property;
                            if (model.getObjectByName(targetBoneName)) boundOk++;
                        }
                    });

                    // NOSE ON FLOOR FIX:
                    // Filter out tracks that target the root object to prevent them overriding model.rotation.x
                    clip.tracks = clip.tracks.filter(track => {
                        const parts = track.name.split('.');
                        if (parts.length === 1) return false;
                        const boneName = parts[0];
                        // Relaxed: Only filter out model root, allow everything else
                        if (boneName === model.name) return false;

                        // Positional tracks restricted to core hub bones
                        if (track.name.toLowerCase().includes('.position')) {
                            const low = track.name.toLowerCase();
                            return low.includes('hips') || low.includes('pelvis') || low.includes('waist') || low.includes('root');
                        }
                        return true;
                    });

                    const action = mixer.clipAction(clip);
                    if (res.key === 'twerk') action.setLoop(THREE.LoopOnce);
                    else action.setLoop(THREE.LoopRepeat);

                    // Animation Speeds
                    if (res.key === 'walk') action.setEffectiveTimeScale(3.0);
                    if (res.key === 'run') action.setEffectiveTimeScale(4.5);

                    actions[res.key] = action;
                    const percent = Math.round((boundOk / totalTracks) * 100);
                    animationScores[res.key] = percent;

                    // Update Diagnostic Overlay
                    const overlay = document.getElementById('anim-overlay');
                    const item = document.createElement('div');
                    item.className = 'anim-item';
                    const color = percent > 60 ? '#00ffcc' : (percent > 20 ? '#ffcc00' : '#ff4444');
                    item.innerHTML = `<span>${res.key}</span><span class="anim-pct" style="color:${color}">${percent}%</span>`;
                    overlay.appendChild(item);

                    log(`Linked ${res.key}: ${percent}% bound (${boundOk}/${totalTracks} tracks)`, percent > 50 ? "success" : "error");

                    if (res.key === 'resting') {
                        const sample = clip.tracks.slice(0, 8).map(t => {
                            const b = t.name.split('.')[0];
                            return b.length > 10 ? '...' + b.slice(-8) : b;
                        }).join(' | ');
                        log(`  AUDIT [resting]: ${sample}`, "info");
                    }

                    // Smart Idle Picker: Pick resting as primary if bound
                    if (res.key === 'resting') {
                        const score = percent;
                        if (score > maxIdleScore && percent > 15) {
                            maxIdleScore = score;
                            activeIdleKey = res.key;
                        }
                    }

                    if (percent < 30 && totalTracks > 0) {
                        const rawTracks = res.fbx.animations[0].tracks.slice(0, 3).map(t => t.name).join(' | ');
                        log(`  DIAG: ${res.key} failed. Source tracks sample: ${rawTracks}`, "error");
                    }
                } else {
                    log(`Skipped ${res.key}: clip not found or empty`, "error");
                }
            });

            resetNiko();
            // Force start best idle action
            if (actions[activeIdleKey]) {
                fadeToAction(activeIdleKey, 0);
                log(`Best Idle System: Using '${activeIdleKey}'`, "success");
            } else if (actions['twerk']) {
                activeIdleKey = 'twerk';
                fadeToAction('twerk', 0);
                log("Best Idle System: Falling back to 'twerk'", "warning");
            }

            isReady = true;
            if(document.getElementById('status-text')) document.getElementById('status-text').innerText = "SYSTEMS ONLINE"; if(document.getElementById('overlay-status-text')) document.getElementById('overlay-status-text').innerText = "SYSTEMS ONLINE"; document.getElementById('btn-play').style.opacity = 1; document.getElementById('btn-play').style.pointerEvents = 'auto';
            
        }

        function fadeToAction(name, duration = 0.3) {
            if (!actions[name]) return;
            const prev = currentAction;
            currentAction = actions[name];
            if (prev !== currentAction) {
                if (prev) prev.fadeOut(duration);
                currentAction.reset().fadeIn(duration).play();
            }
        }

        function buildLevel(num) {
            while (levelGroup.children.length > 0) levelGroup.remove(levelGroup.children[0]);
            shards = [];
            walls = [];
            roads = [];

            const theme = themes[num];
            const mapData = maps[num];
            const mats = createThemeMaterials(theme);

            scene.background = new THREE.Color(theme.sky);
            scene.fog = new THREE.FogExp2(theme.fog, theme.fogDens);

            const ambLight = scene.children.find(l => l instanceof THREE.AmbientLight);
            if (ambLight) ambLight.intensity = theme.ambient;
            if (nikoLight) nikoLight.color.set(theme.lightColor);

            for (let z = 0; z < mapData.grid.length; z++) {
                for (let x = 0; x < mapData.grid[z].length; x++) {
                    const px = x * cellSize - mapOffset, pz = z * cellSize - mapOffset;
                    if (mapData.grid[z][x] === 1) {
                        const b = new THREE.Mesh(new THREE.BoxGeometry(cellSize, 6000, cellSize), mats.build);
                        b.position.set(px, 3000, pz);
                        levelGroup.add(b);
                        walls.push(b);
                        if (isTransitioning) b.position.y = -3000;
                    } else {
                        const r = new THREE.Mesh(new THREE.PlaneGeometry(cellSize, cellSize), mats.road);
                        r.rotation.x = -Math.PI / 2; r.position.set(px, 0, pz);
                        levelGroup.add(r);
                        roads.push(r);
                        if (Math.random() > 0.7 && (x !== mapData.start.x || z !== mapData.start.z)) {
                            spawnShard(px, pz, theme.lightColor);
                        }
                    }
                }
            }

            goalOrb = new THREE.Mesh(new THREE.SphereGeometry(250), new THREE.MeshStandardMaterial({ color: 0xffcc00, emissive: 0xffcc00, emissiveIntensity: 5 }));
            goalOrb.position.set(mapData.goal.x * cellSize - mapOffset, 300, mapData.goal.z * cellSize - mapOffset);
            levelGroup.add(goalOrb);

            document.getElementById('lvl-name').innerText = `LEVEL ${num}: ${theme.name}`;
            document.getElementById('intro-title').innerText = theme.name;
        }

        function spawnShard(x, z, color) {
            const shard = new THREE.Mesh(new THREE.OctahedronGeometry(80), new THREE.MeshStandardMaterial({ color: color, emissive: color, emissiveIntensity: 2, wireframe: true }));
            shard.position.set(x, 150, z);
            shards.push(shard);
            levelGroup.add(shard);
        }

        function drawMinimap() {
            if (!minimapCtx || !isReady) return;
            const ctx = minimapCtx;
            const grid = maps[currentLevel].grid;
            const cw = minimapCanvas.width, ch = minimapCanvas.height;
            const rows = grid.length, cols = grid[0].length;
            const cellW = cw / cols, cellH = ch / rows;

            ctx.clearRect(0, 0, cw, ch);

            // Draw Background (Grid)
            for (let r = 0; r < rows; r++) {
                for (let c = 0; c < cols; c++) {
                    ctx.fillStyle = grid[r][c] === 1 ? 'rgba(0, 255, 204, 0.15)' : 'rgba(0, 0, 0, 0.3)';
                    ctx.fillRect(c * cellW, r * cellH, cellW - 1, cellH - 1);
                }
            }

            // Draw Shards
            ctx.fillStyle = '#00ffcc';
            shards.forEach(s => {
                const sx = (s.position.x + mapOffset) / cellSize;
                const sz = (s.position.z + mapOffset) / cellSize;
                ctx.beginPath();
                ctx.arc(sx * cellW + cellW / 2, sz * cellH + cellH / 2, 2, 0, Math.PI * 2);
                ctx.fill();
            });

            // Draw Goal
            const gx = maps[currentLevel].goal.x, gz = maps[currentLevel].goal.z;
            ctx.fillStyle = '#ffcc00';
            ctx.shadowBlur = 10; ctx.shadowColor = '#ffcc00';
            ctx.fillRect(gx * cellW + 2, gz * cellH + 2, cellW - 4, cellH - 4);
            ctx.shadowBlur = 0;

            // Draw Player
            const px = (modelContainer.position.x + mapOffset) / cellSize;
            const pz = (modelContainer.position.z + mapOffset) / cellSize;
            ctx.fillStyle = '#fff';
            ctx.shadowBlur = 15; ctx.shadowColor = '#fff';
            ctx.beginPath();
            ctx.arc(px * cellW + cellW / 2, pz * cellH + cellH / 2, 4, 0, Math.PI * 2);
            ctx.fill();
            ctx.shadowBlur = 0;

            // Player directional indicator
            ctx.strokeStyle = '#fff';
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.moveTo(px * cellW + cellW / 2, pz * cellH + cellH / 2);
            ctx.lineTo(
                px * cellW + cellW / 2 + Math.sin(modelContainer.rotation.y) * 10,
                pz * cellH + cellH / 2 + Math.cos(modelContainer.rotation.y) * 10
            );
            ctx.stroke();
        }

        function startLevelTransition() {
            if (isTransitioning) return;
            isTransitioning = true;
            transitionTime = 0;
            hasShifted = false;
            gameActive = false;
            log("LEVEL COMPLETE! Dimension shifting...", "success");
        }

        function resetNiko() {
            const start = maps[currentLevel].start;
            modelContainer.position.set(start.x * cellSize - mapOffset, 0, start.z * cellSize - mapOffset);
            modelContainer.rotation.y = 0;
            camYaw = 0;
            velX = 0; velZ = 0;
            idleTimer = 0;
            startTime = Date.now();
        }

        function createFloatingText(text, pos, color = '#ffffff') {
            const canvas = document.createElement('canvas');
            canvas.width = 256; canvas.height = 128;
            const ctx = canvas.getContext('2d');
            ctx.font = 'bold 80px Outfit, sans-serif';
            ctx.fillStyle = color;
            ctx.textAlign = 'center';
            ctx.fillText(text, 128, 80);
            const tex = new THREE.CanvasTexture(canvas);
            const mat = new THREE.SpriteMaterial({ map: tex, transparent: true });
            const sprite = new THREE.Sprite(mat);
            sprite.scale.set(400, 200, 1);
            sprite.position.copy(pos);
            sprite.position.y += 100;
            scene.add(sprite);
            floatingTexts.push({ obj: sprite, life: 1.0 });
        }

        function spawnGhost() {
            if (!model) return;
            const ghost = model.clone();
            ghost.traverse(child => {
                if (child.isMesh) {
                    child.material = new THREE.MeshBasicMaterial({
                        color: themes[currentLevel].lightColor || 0x00ffff,
                        transparent: true,
                        opacity: 0.4
                    });
                }
            });
            ghost.position.copy(modelContainer.position);
            ghost.rotation.copy(modelContainer.rotation);
            scene.add(ghost);
            ghosts.push({ obj: ghost, life: 0.5 });
        }

        function togglePause() {
            if (!gameActive && !isPaused) return;
            isPaused = !isPaused;
            document.getElementById('pause-overlay').style.display = isPaused ? 'flex' : 'none';
            log(isPaused ? "Game Paused" : "Game Resumed", "info");
        }

        function triggerHitEffect() {
            const el = document.getElementById('chromatic-hit');
            el.style.opacity = '1';
            setTimeout(() => { el.style.opacity = '0'; }, 150);
            shakeIntensity = 25;
        }

        
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


        function animate() {
            requestAnimationFrame(animate);
            if (isPaused) return;
            const delta = clock.getDelta();
            if (mixer) mixer.update(delta);

            // Camera Controls (Universal)
            if (isReady) {
                if (keys['ArrowLeft']) camYaw += 0.05;
                if (keys['ArrowRight']) camYaw -= 0.05;
                if (keys['ArrowUp']) targetDistance = Math.max(200, targetDistance - 50);
                if (keys['ArrowDown']) targetDistance = Math.min(6000, targetDistance + 50);
            }

            if (gameActive && isReady) {
                const elapsed = Math.floor((Date.now() - startTime) / 1000);
                document.getElementById('timer').innerText = formatTime(elapsed);

                // Smooth Lerp Camera
                camPitch = THREE.MathUtils.lerp(camPitch, targetPitch, 0.08);
                camDistance = THREE.MathUtils.lerp(camDistance, targetDistance, 0.08);

                let inputX = (keys['KeyD'] ? 1 : 0) - (keys['KeyA'] ? 1 : 0);
                let inputZ = (keys['KeyS'] ? 1 : 0) - (keys['KeyW'] ? 1 : 0);

                const isSprinting = keys['ShiftLeft'] || keys['ShiftRight'] || keys['shift'];
                const spd = isSprinting ? 75 : 40;

                if (inputX !== 0 || inputZ !== 0) {
                    idleTimer = 0;

                    if (isSprinting) {
                        shakeIntensity = Math.max(shakeIntensity, 3);
                        camera.fov = THREE.MathUtils.lerp(camera.fov, 75, 0.1);
                        if (Date.now() % 100 < 32) spawnGhost(); // Speed trails
                    } else {
                        camera.fov = THREE.MathUtils.lerp(camera.fov, 60, 0.1);
                    }
                    camera.updateProjectionMatrix();

                    const angle = Math.atan2(inputX, inputZ) + camYaw;

                    const targetVelX = Math.sin(angle) * spd;
                    const targetVelZ = Math.cos(angle) * spd;
                    velX = THREE.MathUtils.lerp(velX, targetVelX, 0.2);
                    velZ = THREE.MathUtils.lerp(velZ, targetVelZ, 0.2);

                    const targetRotation = angle;
                    const currentRotation = modelContainer.rotation.y;
                    let diff = targetRotation - currentRotation;
                    while (diff < -Math.PI) diff += Math.PI * 2;
                    while (diff > Math.PI) diff -= Math.PI * 2;
                    modelContainer.rotation.y += diff * 0.15;

                    if (isSprinting) fadeToAction('run');
                    else fadeToAction('walk');
                } else {
                    // Level-specific friction (Chrome Slide in level 8)
                    const friction = (currentLevel === 8) ? 0.04 : 0.2;
                    velX = THREE.MathUtils.lerp(velX, 0, friction);
                    velZ = THREE.MathUtils.lerp(velZ, 0, friction);
                    if (Math.abs(velX) < 2) velX = 0;
                    if (Math.abs(velZ) < 2) velZ = 0;

                    idleTimer += delta;
                    let targetIdle = activeIdleKey;

                    // SEQUENCE LOGIC: Randomized 10-15s resting -> twerk
                    if (idleTimer < restingThreshold) {
                        if (actions['resting'] && animationScores['resting'] > 20) targetIdle = 'resting';
                        else targetIdle = 'twerk';
                    } else {
                        targetIdle = actions['twerk'] ? 'twerk' : activeIdleKey;
                    }

                    fadeToAction(targetIdle);

                    if (targetIdle === 'twerk' && actions['twerk'] && actions['twerk'].time >= actions['twerk'].getClip().duration - 0.1) {
                        idleTimer = 0;
                        restingThreshold = 10 + Math.random() * 5; // RE-RANDOMIZE for next cycle
                    }
                }

                // Apply unified velocity movement & Collision (Inertia)
                if (Math.abs(velX) > 0.1 || Math.abs(velZ) > 0.1) {
                    const nX = modelContainer.position.x + velX;
                    const nZ = modelContainer.position.z + velZ;
                    const gx = Math.round((nX + mapOffset) / cellSize);
                    const gz = Math.round((nZ + mapOffset) / cellSize);
                    const currentGrid = maps[currentLevel].grid;

                    if (gz >= 0 && gz < currentGrid.length && gx >= 0 && gx < currentGrid[0].length) {
                        if (currentGrid[gz][gx] === 0) {
                            modelContainer.position.x = nX;
                            modelContainer.position.z = nZ;
                        } else {
                            // Wall hit: Stop velocity & Trigger Juice
                            if (Math.abs(velX) > 5 || Math.abs(velZ) > 5) triggerHitEffect();
                            velX = 0; velZ = 0;
                        }
                    }
                }

                // Update Ghosts & Floating Text
                for (let i = ghosts.length - 1; i >= 0; i--) {
                    ghosts[i].life -= delta;
                    ghosts[i].obj.traverse(c => { if (c.isMesh) c.material.opacity = ghosts[i].life * 0.8; });
                    if (ghosts[i].life <= 0) {
                        scene.remove(ghosts[i].obj);
                        ghosts.splice(i, 1);
                    }
                }
                for (let i = floatingTexts.length - 1; i >= 0; i--) {
                    floatingTexts[i].life -= delta;
                    floatingTexts[i].obj.position.y += 2;
                    floatingTexts[i].obj.material.opacity = floatingTexts[i].life;
                    if (floatingTexts[i].life <= 0) {
                        scene.remove(floatingTexts[i].obj);
                        floatingTexts.splice(i, 1);
                    }
                }

                if (nikoLight) nikoLight.position.set(modelContainer.position.x, modelContainer.position.y + 300, modelContainer.position.z);

                for (let i = shards.length - 1; i >= 0; i--) {
                    const s = shards[i];
                    s.rotation.y += 0.04;
                    s.position.y = 150 + Math.sin(Date.now() * 0.003 + i) * 50;

                    const dist = modelContainer.position.distanceTo(s.position);
                    // Magnetism
                    if (dist < 800) {
                        s.position.lerp(modelContainer.position, 0.15);
                    }

                    if (dist < 250) {
                        score += 100;
                        document.getElementById('score').innerText = `SHARDS: ${score}`;
                        createFloatingText("+100", s.position, themes[currentLevel].win1);
                        levelGroup.remove(s);
                        shards.splice(i, 1);
                        shakeIntensity = 15;
                    }
                }

                if (modelContainer.position.distanceTo(goalOrb.position) < 450) {
                    saveBestTime(currentLevel, elapsed);
                    startLevelTransition();
                }

                shakeIntensity *= 0.9;
            }

            // --- HAZARD: MOVING WALLS (LEVELS 8-10) ---
            if (gameActive && currentLevel >= 8 && !isTransitioning && !isPaused) {
                const time = Date.now() * 0.002;
                walls.forEach((w, idx) => {
                    // Only move every 3rd wall to keep paths somewhat predictable
                    if (idx % 3 === 0) {
                        w.position.y = 3000 + Math.sin(time + idx) * 1500;
                    }
                });
            }

            // --- LEVEL TRANSITION ANIMATION (MELTING WALLS) ---
            if (isTransitioning) {
                transitionTime += delta;

                if (transitionTime < 1.0) {
                    // Phase 1: Walls sink
                    const p = transitionTime;
                    walls.forEach(w => {
                        w.position.y = 3000 - (p * 6000);
                    });
                } else if (!hasShifted) {
                    // Phase 2: Load next level (ONCE)
                    if (currentLevel < 20) {
                        currentLevel++;
                        buildLevel(currentLevel);
                        resetNiko();
                        fadeToAction('resting');
                        document.getElementById('intro-overlay').classList.remove('hidden');
                        document.getElementById('status-text').innerText = "DIMENSION SHIFTED";
                    } else {
                        showVictory();
                        isTransitioning = false;
                        gameActive = false;
                    }
                    hasShifted = true;
                } else if (transitionTime < 2.5) {
                    // Phase 3: New walls rise with SPRING
                    const p = (transitionTime - 1.0) / 1.5; // normalized 0 to 1
                    const elastic = 1 - Math.pow(1 - p, 4) * Math.cos(p * Math.PI * 4);
                    const targetY = 3000;
                    const startY = -3000;
                    walls.forEach(w => {
                        w.position.y = startY + (targetY - startY) * elastic;
                    });
                } else {
                    isTransitioning = false;
                    // gameActive = true; // REMOVED: Wait for user click to start
                    log("Dimension shift complete. Ready to start.", "info");
                }
            }

            // --- UNIVERSAL CAMERA & OBJECT UPDATES ---
            const sX = (Math.random() - 0.5) * shakeIntensity;
            const sY = (Math.random() - 0.5) * shakeIntensity;
            const focus = modelContainer.position.clone().add(new THREE.Vector3(0, 150, 0));
            const pr = THREE.MathUtils.degToRad(camPitch);
            const tPos = new THREE.Vector3(
                focus.x + camDistance * Math.sin(camYaw) * Math.cos(pr) + sX,
                focus.y + camDistance * Math.sin(pr) + sY,
                focus.z + camDistance * Math.cos(camYaw) * Math.cos(pr)
            );
            camera.position.lerp(tPos, 0.2);
            camera.lookAt(focus);

            if (goalOrb) {
                goalOrb.position.y = 300 + Math.sin(clock.getElapsedTime() * 2) * 40;
                const sc = 1 + Math.sin(clock.getElapsedTime() * 4) * 0.15;
                goalOrb.scale.set(sc, sc, sc);
            }

            drawMinimap();

            renderer.render(scene, camera);
        }
        init();
    
