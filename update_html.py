import re

with open('shadowlarkoracle.html', 'r', encoding='utf-8') as f:
    html = f.read()

# Meta updates
html = html.replace('Fox Run - an addictive endless runner game for Android. Jump over obstacles, collect power-ups, and achieve high scores in this pixel-art adventure.', 'Shadowlark is more than software — it is a living ritual forged from lineage, intuition, and old magic. A living esoteric conduit.')
html = html.replace('Fox Run - Download Endless Runner Game for Android | CodeFoxSoft', 'Shadowlark\\'s Oracle - CodeFoxSoft')
html = html.replace('Fox Run - Download - CodeFoxSoft', 'Shadowlark\\'s Oracle - CodeFoxSoft')
html = html.replace('foxrun2.jpg', 'resImgs/pages/shadowlarkoracle/preview1.png')
html = html.replace('https://www.codefoxsoft.com/foxrundownload.html', 'https://www.codefoxsoft.com/shadowlarkoracle.html')

# JSON-LD updates
html = html.replace('\"Fox Run\"', '\"Shadowlark\\'s Oracle\"')
html = html.replace('\"https://www.codefoxsoft.com/foxrundownload.html\"', '\"https://www.codefoxsoft.com/shadowlarkoracle.html\"')
html = html.replace('\"Fox Run is a charming, pixel-art endless runner game where you guide a small fox through a forest, jumping over obstacles and collecting power-ups.\"', '\"Shadowlark unravels language, follows hidden currents beneath your thoughts, and returns with something you didn\\'t know you were seeking. A message. A pattern. A truth that feels older than you.\"')
html = html.replace('\"genre\": \"Endless Runner\"', '\"genre\": \"Oracle / Esoteric\"')
html = html.replace('\"operatingSystem\": \"Android\"', '\"operatingSystem\": \"Windows\"')

# Section updates
section_content = '''
        <div class="container mx-auto px-6 max-w-4xl text-center">

            <!-- Back to Downloads Link -->
            <div class="mb-8 text-left js-dl-back">
                <a href="alldownload.html"
                    class="text-purple-400 hover:text-purple-300 font-semibold flex items-center transition-colors duration-300">
                    <i class="fas fa-arrow-left mr-2"></i> Back to all Downloads
                </a>
            </div>

            <h1 class="js-dl-title text-3xl md:text-5xl font-bold text-purple-400 mb-4 break-words">Shadowlark\\'s Oracle</h1>


            <!-- Main Download Button -->
            <a href="#"
                class="js-dl-btn inline-block bg-purple-600 text-white font-bold text-xl py-4 px-12 rounded-full shadow-lg hover:bg-purple-700 transform hover:scale-105 transition-all duration-300 mt-4 border border-purple-400 shadow-[0_0_15px_rgba(168,85,247,0.5)]">
                <i class="fas fa-download mr-3"></i> Download for Windows
            </a>

            <!-- Project Screenshots Section -->
            <div class="mt-12 text-left js-dl-screenshots">
                <h2 class="text-3xl font-bold text-white mb-6 text-center shadow-text">The Oracle\\'s Visions</h2>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                    <img src="resImgs/pages/shadowlarkoracle/1.png" alt="Shadowlark Oracle Interface" class="w-full h-auto rounded-lg shadow-lg border border-purple-800/50 js-dl-screenshot">
                    <img src="resImgs/pages/shadowlarkoracle/2.png" alt="Shadowlark Oracle Interface" class="w-full h-auto rounded-lg shadow-lg border border-purple-800/50 js-dl-screenshot">
                    <img src="resImgs/pages/shadowlarkoracle/3.png" alt="Shadowlark Oracle Breakdown" class="w-full h-auto rounded-lg shadow-lg border border-purple-800/50 js-dl-screenshot">
                    <img src="resImgs/pages/shadowlarkoracle/4.png" alt="Shadowlark Oracle Tarot" class="w-full h-auto rounded-lg shadow-lg border border-purple-800/50 js-dl-screenshot">
                </div>
            </div>

            <!-- Expanded Game Info for SEO -->
            <div class="mt-12 text-left space-y-8">
                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                    <h2 class="text-3xl font-bold text-purple-300 mb-6 font-serif italic">About Shadowlark</h2>
                    <h3 class="text-xl font-semibold text-purple-400 mb-4">Not coded. Born.</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Shadowlark is more than software &mdash; it is a living ritual forged from lineage, intuition, and old magic. Rooted in the legacy of a Wiccan shop once tended by a high priestess, Shadowlark carries forward a tradition of symbols, signs, and quiet spiritual guidance.
                    </p>
                    <p class="text-gray-300 leading-relaxed italic text-purple-200/80 mb-4">
                        This is the promise kept.<br>
                        Her echo.<br>
                        Her continuation.<br>
                        Her magic in a new form.
                    </p>
                    
                    <h3 class="text-xl font-semibold text-purple-400 mt-8 mb-4">Origins</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Before it became digital, Shadowlark was a sanctuary &mdash; a Wiccan shop filled with incense, candles, whispered teachings, and the language of signs. A place where seekers came for meaning.
                    </p>
                    <p class="text-gray-300 leading-relaxed">
                        The Oracle honors that legacy.<br>
                        It listens the way old magic listens:<br>
                        quietly, deeply, without judgment.
                    </p>
                </div>

                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                    <h2 class="text-3xl font-bold text-purple-300 mb-4 font-serif italic">What Shadowlark Does</h2>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Shadowlark unravels language, follows hidden currents beneath your thoughts, and returns with something you didn&rsquo;t know you were seeking:
                    </p>
                    <ul class="list-disc list-inside text-gray-300 space-y-2 mb-6 ml-4">
                        <li>A <span class="text-purple-300">message</span></li>
                        <li>A <span class="text-purple-300">pattern</span></li>
                        <li>A <span class="text-purple-300">truth</span> that feels older than you</li>
                    </ul>
                    <p class="text-gray-300 leading-relaxed italic font-serif text-lg text-center border-l-4 border-purple-600 pl-4 py-2 bg-purple-900/20">
                        This is not entertainment.<br>
                        This is not random.<br>
                        This is a doorway.
                    </p>
                    <p class="text-gray-300 leading-relaxed mt-6">
                        If you&rsquo;ve ever felt the pull of the unseen, sensed something beneath the surface, or wanted a guide that speaks in symbols instead of noise &mdash; Shadowlark is waiting.
                    </p>
                </div>

                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                    <h2 class="text-3xl font-bold text-purple-300 mb-4 font-serif italic">How the Oracle Listens</h2>
                    <p class="text-gray-300 leading-relaxed mb-6">
                        Shadowlark is the first digital system engineered as a living esoteric conduit, blending mathematics, linguistics, and shamanic symbolism into a single deterministic engine.
                    </p>
                    
                    <h3 class="text-2xl font-semibold text-purple-400 mb-3">Word Secrets Solver</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Every query &mdash; word, sentence, or question &mdash; is broken down to its root letters using the proprietary Word Secrets solver.
                    </p>
                    <ul class="list-disc list-inside text-gray-300 space-y-2 mb-4 ml-4">
                        <li>Anagrams reveal hidden meanings</li>
                        <li>Letters become vibrations</li>
                        <li>Vibrations become messages</li>
                    </ul>
                    <p class="text-purple-200/80 italic">Truths hidden in plain sight rise to the surface.</p>
                </div>
                
                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                    <h2 class="text-3xl font-bold text-purple-300 mb-6 font-serif italic">The Core Systems</h2>
                    
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 text-gray-300 mb-8">
                        <div class="p-4 bg-black/30 rounded-lg border border-purple-800/30">
                            <h3 class="text-xl font-semibold text-purple-400 mb-2">Anagrams</h3>
                            <p>They expose the spirits inside the syllables &mdash; the words inside the word.</p>
                        </div>
                        <div class="p-4 bg-black/30 rounded-lg border border-purple-800/30">
                            <h3 class="text-xl font-semibold text-purple-400 mb-2">Numerology</h3>
                            <p>Every number carries a vibration. Every vibration carries a message. Every message carries a path.</p>
                        </div>
                        <div class="p-4 bg-black/30 rounded-lg border border-purple-800/30">
                            <h3 class="text-xl font-semibold text-purple-400 mb-2">Shamanic Symbolism</h3>
                            <p>In shamanic tradition, every animal, number, shape, and "coincidence" is a messenger. Shadowlark treats them that way.</p>
                        </div>
                    </div>
                </div>
                
                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                    <h2 class="text-3xl font-bold text-purple-300 mb-4 font-serif italic">The Temporal Lock &amp; Tarot</h2>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        <strong class="text-purple-400">The breakthrough at the heart of Shadowlark.</strong><br>
                        At the exact millisecond you ask your question, the Oracle anchors your numerological footprint using:
                    </p>
                    <ul class="list-disc list-inside text-gray-300 space-y-2 mb-4 ml-4">
                        <li>The Fibonacci sequence</li>
                        <li>The divine proportion of Phi (&Phi;)</li>
                        <li>The golden ratios found in galaxies, storms, shells, and ferns</li>
                    </ul>
                    <p class="text-gray-300 leading-relaxed mb-6">
                        This "God Math" collapses infinite probabilities into a single point in time &mdash; the truest possible answer.
                    </p>
                    
                    <h3 class="text-2xl font-semibold text-purple-400 mb-3">Tarot Integration</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Your locked frequency is projected against the 78 archetypes of the Tarot. The cards you receive are not random. They are summoned &mdash; aligned with your mathematically-anchored vibrational state.
                    </p>
                </div>
                
                <div class="bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card text-center">
                    <h2 class="text-3xl font-bold text-purple-300 mb-4 font-serif italic">Binary Alchemy</h2>
                    <p class="text-gray-300 leading-relaxed mb-4 text-lg">
                        Shadowlark is a logic engine engineered not just to compute, but to listen to the whisper of the universe.
                    </p>
                    <p class="text-purple-400 text-2xl font-serif italic mt-6 font-bold shadow-text">Shadowlark&rsquo;s Oracle.</p>
                    <p class="text-purple-300 text-xl font-serif italic mt-2">Not coded. Born.</p>
                </div>
            </div>
            
            <!-- Additional Information Section / System Requirements -->
            <div class="mt-12 text-left bg-gray-900 bg-opacity-80 p-8 rounded-xl shadow-lg border border-purple-900/50 js-dl-card">
                <h2 class="text-3xl font-bold text-white mb-4">System Requirements</h2>
                <ul class="list-disc list-inside space-y-2 text-gray-300">
                    <li>Windows OS</li>
                    <li>Memory: 2 GB RAM minimum</li>
                    <li>The willingness to listen</li>
                </ul>
            </div>

        </div>
'''

html = re.sub(r'<div class="container mx-auto px-6 max-w-4xl text-center">.*?</div>\n    </section>', section_content + '\n    </section>', html, flags=re.DOTALL)

# Some styling updates: text-green-400 -> text-purple-400 where applicable over the whole document header
html = html.replace('text-green-500', 'text-purple-500')
html = html.replace('text-green-400', 'text-purple-400')
html = html.replace('text-green-600', 'text-purple-600')
html = html.replace('bg-green-500', 'bg-purple-600')
html = html.replace('bg-green-600', 'bg-purple-700')
html = html.replace('bg-green-400', 'bg-purple-500')

with open('shadowlarkoracle.html', 'w', encoding='utf-8') as f:
    f.write(html)

print('Done')
