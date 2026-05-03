import re

filepath = 'test.html'
with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Change object-cover to object-contain for both video elements
content = content.replace('object-cover" muted playsinline preload="auto"></video>', 'object-contain" muted playsinline preload="auto"></video>')
content = content.replace('object-cover opacity-0" muted playsinline preload="auto"></video>', 'object-contain opacity-0" muted playsinline preload="auto"></video>')

# 2. Remove bg-[#0c0d11] from the video container
content = content.replace('<div class="absolute inset-0 z-0 bg-[#0c0d11]">', '<div class="absolute inset-0 z-0">')

# 3. Add colorful animated outline to .glass-panel and .hero-glass-panel
glass_css_replacement = '''        .glass-panel {
            background-color: rgba(31, 41, 55, 0.2);
            backdrop-filter: blur(8px);
            -webkit-backdrop-filter: blur(8px);
            border: 2px solid rgba(16, 185, 129, 0.6);
            box-shadow: 0 0 15px rgba(16, 185, 129, 0.2);
            animation: pulse-border 4s infinite alternate;
            transition: transform 0.3s ease-out, box-shadow 0.3s ease-out;
        }
        .glass-panel:hover {
            transform: scale(1.02);
            box-shadow: 0 0 25px rgba(16, 185, 129, 0.4);
        }
        @keyframes pulse-border {
            0% { border-color: rgba(16, 185, 129, 0.4); box-shadow: 0 0 10px rgba(16, 185, 129, 0.1); }
            50% { border-color: rgba(59, 130, 246, 0.6); box-shadow: 0 0 20px rgba(59, 130, 246, 0.3); }
            100% { border-color: rgba(139, 92, 246, 0.6); box-shadow: 0 0 20px rgba(139, 92, 246, 0.3); }
        }
        @media (max-width: 768px) {
            .glass-panel {
                background-color: rgba(31, 41, 55, 0.1) !important;
                backdrop-filter: none !important;
                -webkit-backdrop-filter: none !important;
                border: 2px solid rgba(16, 185, 129, 0.4) !important;
                animation: pulse-border 4s infinite alternate;
            }
            .glass-panel h1,
            .glass-panel p,
            .glass-panel h3 {
                text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.8), 0 0 8px rgba(0, 0, 0, 0.5);
            }
        }

        .hero-glass-panel {
            background-color: rgba(31, 41, 55, 0.15); 
            backdrop-filter: blur(4px); 
            -webkit-backdrop-filter: blur(4px);
            border: 2px solid rgba(16, 185, 129, 0.3);
            box-shadow: 0 0 20px rgba(16, 185, 129, 0.1);
            animation: pulse-border 4s infinite alternate;
        }
        @media (max-width: 768px) {
            .hero-glass-panel {
                background-color: rgba(31, 41, 55, 0.05) !important;
                backdrop-filter: none !important;
                -webkit-backdrop-filter: none !important;
                border: 2px solid rgba(16, 185, 129, 0.3) !important;
                animation: pulse-border 4s infinite alternate;
            }
            .hero-glass-panel h1,
            .hero-glass-panel p,
            .hero-glass-panel h3,
            .hero-glass-panel span {
                text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.9), 0 0 10px rgba(0, 0, 0, 0.7);
            }
        }'''
content = re.sub(
    r'\.glass-panel \{.*?\}\s*\}',
    glass_css_replacement,
    content,
    flags=re.DOTALL
)

# 4. Restore CSS transitions for animation classes
content = content.replace(
    '''        .fade-in {
            opacity: 0;
            transform: translateY(20px);
            
        }''',
    '''        .fade-in {
            opacity: 0;
            transform: translateY(20px);
            transition: opacity 0.6s ease-out, transform 0.6s ease-out;
            will-change: opacity, transform;
        }'''
)
content = content.replace(
    '''        .slide-left {
            opacity: 0;
            transform: translateX(-100px);
            
        }''',
    '''        .slide-left {
            opacity: 0;
            transform: translateX(-100px);
            transition: opacity 0.6s ease-out, transform 0.6s ease-out;
            will-change: opacity, transform;
        }'''
)
content = content.replace(
    '''        .slide-right {
            opacity: 0;
            transform: translateX(100px);
            
        }''',
    '''        .slide-right {
            opacity: 0;
            transform: translateX(100px);
            transition: opacity 0.6s ease-out, transform 0.6s ease-out;
            will-change: opacity, transform;
        }'''
)

# 5. Remove the anime.js unified scroll reveal block entirely
content = re.sub(
    r'// --- Unified Scroll Reveal Strategy ---.*?// \s*anime\.js:\s*Hero staggered reveal',
    '// --- anime.js: Hero staggered reveal',
    content,
    flags=re.DOTALL
)

# 6. Fix the IntersectionObserver so it only fires ONCE per element
observer_old = '''            const appearOnScroll = new IntersectionObserver(function (entries) {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('visible');
                    } else {
                        // Remove visible class when element leaves viewport to allow replay
                        entry.target.classList.remove('visible');
                    }
                });
            }, appearOptions);'''
observer_new = '''            const appearOnScroll = new IntersectionObserver(function (entries, observer) {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('visible');
                        observer.unobserve(entry.target); // Only animate once
                    }
                });
            }, appearOptions);'''
content = content.replace(observer_old, observer_new)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print("test.html successfully updated with mobile fixes, CSS transitions, and fun outlines.")
