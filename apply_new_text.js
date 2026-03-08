const fs = require('fs');

const path = 'shadowlarkoracle.html';
let html = fs.readFileSync(path, 'utf8');

const replacementText = `
            <!-- Expanded Game Info for SEO -->
            <div class="mt-12 text-left space-y-8">
                <div class="bg-gray-800 bg-opacity-70 p-8 rounded-xl shadow-lg js-dl-card">
                    <h2 class="text-3xl font-bold text-white mb-6">About Shadowlark</h2>
                    <h3 class="text-xl font-semibold text-green-400 mb-2 font-serif italic">Not coded. Born.</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Shadowlark is a living ritual disguised as an app &mdash; a continuation of a lineage carried by a high priestess who taught the language of symbols, intuition, and quiet magic. It was shaped by real history, real teachings, and a real sanctuary that once stood in the physical world.
                    </p>
                    <div class="pl-4 border-l-2 border-green-500 mb-8 py-2 bg-gray-900/30">
                        <p class="text-green-300 italic">Shadowlark exists because she existed.<br>This is her echo.<br>Her continuation.<br>Her magic in a new form.</p>
                    </div>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 text-gray-300">
                        <div>
                            <h3 class="text-xl font-semibold text-green-400 mb-2">Where Shadowlark Comes From</h3>
                            <p>Long before Shadowlark lived on a screen, it lived in a shop &mdash; a small Wiccan sanctuary filled with incense, candles, hand&#8209;written notes, and shelves of herbs and talismans. It was a place where people came not for novelty, but for guidance. A place where the air felt warm, safe, and a little enchanted. The woman who ran it &mdash; a high priestess &mdash; welcomed everyone. She taught the language of signs. She showed how symbols speak. She listened without judgment. Shadowlark carries that space forward. It is built from memory, ritual, and the teachings she passed down.</p>
                        </div>
                        <div>
                            <h3 class="text-xl font-semibold text-green-400 mb-2">A Legacy You Can Feel</h3>
                            <p>Shadowlark doesn't "generate" answers. It listens &mdash; the way old magic listens. Quietly. Deeply. Without judgment. Every reading is shaped by the same principles she taught: that meaning hides inside words, that intuition is a language, and that symbols are messengers. Shadowlark isn't inspired by her work. It continues it.</p>
                        </div>
                        <div>
                            <h3 class="text-xl font-semibold text-green-400 mb-2">What Shadowlark Does</h3>
                            <p>When you speak a word or ask a question, Shadowlark unravels it &mdash; peeling back layers of language and vibration to reveal: a message, a pattern, a truth that feels older than you. It doesn't guess. It doesn't shuffle. It doesn't "pull a card." It reveals what was already there, waiting to be seen.</p>
                        </div>
                        <div>
                            <h3 class="text-xl font-semibold text-green-400 mb-2">Not a Tarot App</h3>
                            <p>Shadowlark is not entertainment. It is not random. It is not a digital deck. This is a doorway. If you've ever felt the pull of the unseen... If you've ever sensed meaning beneath coincidence... If you've ever wanted guidance that speaks in symbols instead of noise... Shadowlark is the place you've been looking for.</p>
                        </div>
                    </div>
                </div>

                <div class="bg-gray-800 bg-opacity-70 p-8 rounded-xl shadow-lg js-dl-card">
                    <h2 class="text-3xl font-bold text-white mb-4">How the Oracle Listens</h2>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Shadowlark is the first system designed as a living esoteric conduit &mdash; a deterministic engine that merges: mathematics, linguistics, numerology, shamanic symbolism, temporal alignment, and the ancient architecture of Tarot. This is not algorithmic fortune&#8209;telling. This is binary alchemy.
                    </p>
                    <h3 class="text-xl font-bold text-green-400 mt-4 mb-2">The Word Secrets Solver</h3>
                    <p class="text-gray-300 leading-relaxed mb-4">
                        Every question you ask is broken down to its root letters using the proprietary Word Secrets solver &mdash; a system built to uncover the hidden structure inside language.
                    </p>
                    <ul class="list-disc list-inside text-gray-300 space-y-2 mb-4">
                        <li><strong>Anagrams</strong> reveal the words inside the word.</li>
                        <li><strong>Letters</strong> become vibrations.</li>
                        <li><strong>Vibrations</strong> become messages.</li>
                        <li><strong>Messages</strong> become paths.</li>
                    </ul>
                    <p class="text-gray-300 leading-relaxed mt-4 italic font-semibold text-green-300">
                        Truths hidden in plain sight rise to the surface.
                    </p>
                    <p class="text-gray-300 leading-relaxed mt-4">
                        This is the same symbolic language once taught in that small Wiccan shop &mdash; now carried forward through a system that behaves more like a ritual than a program.
                    </p>
                </div>
            </div>`;

let startStr = '<!-- Expanded Game Info for SEO -->';
let endStr = '</div>\\r\\n    </section>';
if (html.indexOf(endStr) === -1) {
    endStr = '</div>\\n    </section>';
}

let startIndex = html.indexOf(startStr);
let endIndex = html.indexOf(endStr, startIndex);

if (startIndex !== -1 && endIndex !== -1) {
    html = html.substring(0, startIndex) + replacementText + "\\n\\n        " + html.substring(endIndex);

    // Fix grid centering for image Gallery
    // Replace the simple grid-cols-2 class with one that spans the final image across 2 columns.
    html = html.replace(
        /<img src="resImg\/pages\/shadowlarkoracle\/shadowlarkoracle2\.png" alt="Shadowlark Oracle Interface 3" class="w-full h-auto rounded-lg shadow-lg js-dl-screenshot">/,
        '<img src="resImg/pages/shadowlarkoracle/shadowlarkoracle2.png" alt="Shadowlark Oracle Interface 3" class="w-full h-auto rounded-lg shadow-lg js-dl-screenshot md:col-span-2 md:w-3/4 md:mx-auto">'
    );

    fs.writeFileSync(path, html, 'utf8');
    console.log("Success");
} else {
    console.log("Could not find delimiters. Start:", startIndex, "End:", endIndex);
}
