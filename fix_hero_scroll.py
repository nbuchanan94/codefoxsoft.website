import re

filepath = 'test.html'
with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Update hero section to start video below menu and cover text
hero_old = '''        <!-- Hero Section -->
        <section class="relative min-h-[100dvh] flex items-end justify-center pb-12 sm:pb-32 text-right sm:text-center overflow-hidden">
            <!-- Hero Video Background -->
            <div class="absolute top-0 left-0 w-full h-[55vh] sm:h-full z-0">
                <video id="heroVideo1" class="absolute top-0 left-0 w-full h-full object-cover object-[15%_center] sm:object-center" muted playsinline preload="auto"></video>
                <video id="heroVideo2" class="absolute top-0 left-0 w-full h-full object-cover object-[15%_center] sm:object-center opacity-0" muted playsinline preload="auto"></video>
                <div class="absolute inset-0 bg-gray-900 bg-opacity-30"></div>
                <div class="absolute bottom-0 left-0 w-full h-32 sm:h-48 bg-gradient-to-b from-transparent to-[#0c0d11]"></div>
            </div>'''
hero_new = '''        <!-- Hero Section -->
        <section class="relative min-h-[100dvh] pt-20 sm:pt-0 flex items-end justify-center pb-12 sm:pb-32 text-right sm:text-center overflow-hidden">
            <!-- Hero Video Background -->
            <div class="absolute top-20 sm:top-0 left-0 w-full h-[calc(100vh-5rem)] sm:h-full z-0">
                <video id="heroVideo1" class="absolute top-0 left-0 w-full h-full object-cover object-[15%_center] sm:object-center" muted playsinline preload="auto"></video>
                <video id="heroVideo2" class="absolute top-0 left-0 w-full h-full object-cover object-[15%_center] sm:object-center opacity-0" muted playsinline preload="auto"></video>
                <div class="absolute inset-0 bg-gray-900 bg-opacity-30"></div>
                <div class="absolute bottom-0 left-0 w-full h-32 sm:h-48 bg-gradient-to-b from-transparent to-[#0c0d11]"></div>
            </div>'''
content = content.replace(hero_old, hero_new)

# 2. Rip out the glitchy anime.js scroll reveal and replace with pure CSS observer
script_pattern = r'// --- Unified Scroll Reveal Strategy ---.*?// Event Listeners from the galaxy code'

clean_observer = '''// --- Unified Scroll Reveal Strategy (Clean CSS) ---
            const faders = document.querySelectorAll('.fade-in, .slide-left, .slide-right, .project-card, .blog-card, .js-footer-reveal');
            const appearOptions = { threshold: 0.1, rootMargin: "0px 0px -50px 0px" };
            const appearOnScroll = new IntersectionObserver(function (entries, observer) {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('visible');
                        observer.unobserve(entry.target);
                    }
                });
            }, appearOptions);
            faders.forEach(fader => appearOnScroll.observe(fader));
            
            // Event Listeners from the galaxy code'''

content = re.sub(script_pattern, clean_observer, content, flags=re.DOTALL)

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content)

print("test.html successfully updated")
