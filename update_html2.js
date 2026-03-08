const fs = require('fs');
const path = require('path');

const filePath = path.join(__dirname, 'shadowlarkoracle.html');
let html = fs.readFileSync(filePath, 'utf8');

// 1. Meta Tag and SEO Updates (No layout changes)
html = html.replace(/Fox Run - Download Endless Runner Game for Android \| CodeFoxSoft/g, "Shadowlark's Oracle - CodeFoxSoft");
html = html.replace(/<title>Fox Run - Download - CodeFoxSoft<\/title>/g, "<title>Shadowlark's Oracle - CodeFoxSoft</title>");
html = html.replace(/Fox Run - an addictive endless runner game for Android\. Jump over obstacles, collect power-ups, and achieve high scores in this pixel-art adventure\./g, "Shadowlark is more than software — it is a living ritual forged from lineage, intuition, and old magic. The Oracle honors that legacy.");
html = html.replace(/foxrun2\.jpg/g, 'resImgs/pages/shadowlarkoracle/screenshot1.png');
html = html.replace(/foxrundownload\.html/g, 'shadowlarkoracle.html');

// 2. JSON-LD Updates
html = html.replace(/"name": "Fox Run"/g, '"name": "Shadowlark\'s Oracle"');
html = html.replace(/"description": "Fox Run is a charming, pixel-art endless runner game where you guide a small fox through a forest, jumping over obstacles and collecting power-ups."/g, '"description": "Shadowlark unravels language, follows hidden currents beneath your thoughts, and returns with something you didn\'t know you were seeking. A message. A pattern. A truth that feels older than you."');
html = html.replace(/"operatingSystem": "Android"/g, '"operatingSystem": "Windows"');
html = html.replace(/"genre": "Endless Runner"/g, '"genre": "Esoteric Oracle"');


// 3. Text content replacements matching the exact structure of foxrundownload
html = html.replace(/Fox Run Download Info/g, "Shadowlark's Oracle");
html = html.replace(/Download on Google Play!/g, "Download for Windows");
html = html.replace(/android/g, "Windows"); // Lowercase to match OS or whatever
html = html.replace(/Android OS/g, "Windows OS");

// Images
html = html.replace(/foxrun_preview_1\.png/g, "resImgs/pages/shadowlarkoracle/1.png");
html = html.replace(/foxrun_preview_2\.png/g, "resImgs/pages/shadowlarkoracle/2.png");
html = html.replace(/foxrun_preview_3\.png/g, "resImgs/pages/shadowlarkoracle/3.png");
html = html.replace(/foxrun_preview_4\.png/g, "resImgs/pages/shadowlarkoracle/4.png");

// Fix image alt tags
html = html.replace(/Fox Run Gameplay/g, "Shadowlark Oracle Interface");

// Project Screenshots Section title
html = html.replace(/Project Screenshots/g, "The Oracle's Visions");


// System Requirements
// Memory is already 1 GB setup there.
// Storage: we leave it 100MB

html = html.replace(/<li>Click the "Download" button above <\/li>\s*<li>Follow Google Play Instructions!<\/li>\s*<li>Play and enjoy!<\/li>/g, '<li>Click the "Download" button above</li>\n                    <li>Extract the downloaded file</li>\n                    <li>Run the Shadowlark executable and enter the circle</li>');


// About Section 1
html = html.replace(/<h2 class="text-3xl font-bold text-white mb-6">Why You'll Love Fox Run<\/h2>/g, '<h2 class="text-3xl font-bold text-white mb-6">About Shadowlark</h2>');
html = html.replace(/<p class="text-gray-300 leading-relaxed mb-8">\s*<em>Fox Run<\/em> captures the pure joy of classic endless runners. It combines pixel-perfect\s*precision with a charming retro aesthetic, creating an experience that's easy to pick up but\s*impossible to put down\.\s*<\/p>/g, '<h3 class="text-xl font-semibold text-green-400 mb-2 font-serif italic">Not coded. Born.</h3>\n                    <p class="text-gray-300 leading-relaxed mb-4">\n                        Shadowlark is more than software — it is a living ritual forged from lineage, intuition, and old magic. Rooted in the legacy of a Wiccan shop once tended by a high priestess, Shadowlark carries forward a tradition of symbols, signs, and quiet spiritual guidance.\n                    </p>\n                    <div class="pl-4 border-l-2 border-green-500 mb-8 py-2 bg-gray-900/30">\n                        <p class="text-green-300 italic">This is the promise kept.<br>Her echo.<br>Her continuation.<br>Her magic in a new form.</p>\n                    </div>');

// The 4 columns from fox run
html = html.replace(/<h3 class="text-xl font-semibold text-green-400 mb-2">Endless Adventure<\/h3>/g, '<h3 class="text-xl font-semibold text-green-400 mb-2">Origins</h3>');
html = html.replace(/<p>Procedurally generated terrain means no two runs are the same. Adapt to changing\s*landscapes and obstacles as you sprint through the pixelated forest.<\/p>/g, '<p>Before it became digital, Shadowlark was a sanctuary — a Wiccan shop filled with incense, candles, whispered teachings, and the language of signs. A place where seekers came for meaning.</p>');

html = html.replace(/<h3 class="text-xl font-semibold text-green-400 mb-2">Retro Pixel Art<\/h3>/g, '<h3 class="text-xl font-semibold text-green-400 mb-2">The Legacy</h3>');
html = html.replace(/<p>Immerse yourself in a beautifully crafted world with nostalgic 16-bit visuals and smooth\s*animations that bring the classic arcade feel to your modern device.<\/p>/g, '<p>The Oracle honors that legacy. It listens the way old magic listens: quietly, deeply, without judgment.</p>');

html = html.replace(/<h3 class="text-xl font-semibold text-green-400 mb-2">Power-Up System<\/h3>/g, '<h3 class="text-xl font-semibold text-green-400 mb-2">What It Does</h3>');
html = html.replace(/<p>Collect magical berries for speed boosts and bolts for invincibility. Strategic use of\s*power-ups is key to achieving your new high score.<\/p>/g, '<p>Shadowlark unravels language, follows hidden currents beneath your thoughts, and returns with a message, a pattern, a truth that feels older than you.</p>');

html = html.replace(/<h3 class="text-xl font-semibold text-green-400 mb-2">Play Anywhere<\/h3>/g, '<h3 class="text-xl font-semibold text-green-400 mb-2">Not Entertainment</h3>');
html = html.replace(/<p>No internet\? No problem! Fox Run is a fully offline experience, perfect for commuters,\s*travelers, and anyone looking to unplug and play.<\/p>/g, '<p>This is not a random generator. This is a doorway. If you’ve ever felt the pull of the unseen, Shadowlark is waiting.</p>');


// Section 2: How to Play -> How the Oracle Listens
html = html.replace(/<h2 class="text-3xl font-bold text-white mb-4">How to Play<\/h2>/g, '<h2 class="text-3xl font-bold text-white mb-4">How the Oracle Listens</h2>');
html = html.replace(/<p class="text-gray-300 leading-relaxed mb-4">\s*The rules are simple but nature is unpredictable:\s*<\/p>/g, '<p class="text-gray-300 leading-relaxed mb-4">\n                        Shadowlark is the first digital system engineered as a living esoteric conduit, blending mathematics, linguistics, and shamanic symbolism into a single deterministic engine.\n                    </p>\n                    <h3 class="text-xl font-bold text-green-400 mt-4 mb-2">Word Secrets Solver</h3>\n                    <p class="text-gray-300 leading-relaxed mb-4">\n                        Every query — word, sentence, or question — is broken down to its root letters.\n                    </p>');

html = html.replace(/<li><strong>Tap to Jump<\/strong> over rocks, logs, and bushes.<\/li>\s*<li><strong>Collect Berries<\/strong> and coins to boost your high score.<\/li>\s*<li><strong>Avoid Hazards<\/strong>—tripping costs you berries \(or your life!\).<\/li>\s*<li><strong>Grab Power-ups<\/strong> like the lightning bolt to smash through obstacles.<\/li>/g, '<li><strong>Anagrams</strong> reveal hidden meanings.</li>\n                        <li><strong>Letters</strong> become vibrations.</li>\n                        <li><strong>Vibrations</strong> become messages.</li>\n                        <p class="italic text-green-300 mt-2">Truths hidden in plain sight rise to the surface.</p>');


// Section 3: From the Developer -> The Core Systems & Temporal Lock
html = html.replace(/<h2 class="text-3xl font-bold text-white mb-4">From the Developer<\/h2>/g, '<h2 class="text-3xl font-bold text-white mb-4">The Temporal Lock</h2>');

let temporalLockContent = `<p class="text-gray-300 leading-relaxed mb-4">
                        <strong class="text-green-400">The breakthrough at the heart of Shadowlark.</strong>
                    </p>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        At the exact millisecond you ask your question, the Oracle anchors your numerological footprint using:
                        <ul class="list-disc list-inside ml-4 text-green-200"><li>The Fibonacci sequence</li><li>The divine proportion of Phi (Φ)</li><li>The golden ratios found in nature</li></ul>
                    </p>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        This "God Math" collapses infinite probabilities into a single point in time — the truest possible answer.
                    </p>
                    <h3 class="text-2xl font-bold text-white mt-8 mb-4">Tarot Integration</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Your locked frequency is projected against the 78 archetypes of the Tarot. The cards you receive are not random. They are summoned — aligned with your mathematically-anchored vibrational state.
                    </p>
                    <p class="text-gray-300 leading-relaxed text-center font-bold italic mt-8 text-xl">
                        Shadowlark's Oracle. Not coded. Born.
                    </p>
`

html = html.replace(/<p class="text-gray-300 leading-relaxed mb-4">\s*Bored in the bathroom\? Stuck at the DMV\? Try this for your Doom Scrolling! \(AD FREE \+ OFFLINE!\)\s*<\/p>\s*<p class="text-gray-300 leading-relaxed mb-4">\s*Welcome to your new favorite distraction. No stress. No strategy. Just pure, silly fun.\s*<\/p>\s*<p class="text-gray-300 leading-relaxed mb-4">\s*Jump over rocks, bushes, and logs while scooping up berries, hearts, and coins. But watch your\s*step—trip on a log and it'll cost you a berry! \(Or Worse, a LIFE!!!\)\s*<\/p>\s*<p class="text-gray-300 leading-relaxed mb-4">\s*💥 Find a lightning bolt in a chest\? Boom. You’re invincible for 5 seconds—smash through\s*obstacles like a superhero.\s*<\/p>\s*<p class="text-gray-300 leading-relaxed">\s*Whether you're killing time or dodging awkward eye contact, this game is your go-to escape.\s*Quick to play, hard to put down, and weirdly satisfying.\s*<br><br>\s*All with an awesome anthropomorphic furry fox friend =3\s*<\/p>/g, temporalLockContent);

// Optional: Link
html = html.replace(/href="https:\/\/play.google.com\/store\/apps\/details\?id=com.codefoxsoft.foxrunbycodefox"/g, 'href="shadowlark_download.zip"');

fs.writeFileSync(filePath, html, 'utf8');
console.log('Successfully applied accurate text replacements onto the original style.');
