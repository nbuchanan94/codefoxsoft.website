import re

with open('shadowlarkoracle.html', 'r', encoding='utf-8') as f:
    html = f.read()

# 1. Links and basic metas
html = html.replace('Fox Run - Download Endless Runner Game for Android | CodeFoxSoft', 'Shadowlark\\'s Oracle - CodeFoxSoft')
html = html.replace('Fox Run - Download - CodeFoxSoft', 'Shadowlark\\'s Oracle - CodeFoxSoft')
html = html.replace('Fox Run Download Info', 'Shadowlark\\'s Oracle')
html = html.replace('https://play.google.com/store/apps/details?id=com.codefoxsoft.foxrunbycodefox', 'ShadowlarkSetup.exe')
html = html.replace('Download on Google Play!', 'Download for Windows')
html = html.replace('foxrundownload.html', 'shadowlarkoracle.html')
html = html.replace('foxrun2.jpg', 'resImg/pages/shadowlarkoracle/shadowlarkoracle0.png')
html = html.replace('\"name\": \"Fox Run\"', '\"name\": \"Shadowlark\\'s Oracle\"')
html = html.replace('Android OS', 'Windows OS')
html = html.replace('Memory: 1 GB RAM', 'Memory: 2 GB RAM')
html = html.replace('Follow Google Play Instructions!', 'Extract the downloaded file')
html = html.replace('Play and enjoy!', 'Run the Shadowlark executable and enter the circle')


# Image replacements
html = html.replace('foxrun_preview_1.png', 'resImg/pages/shadowlarkoracle/shadowlarkoracle0.png')
html = html.replace('foxrun_preview_2.png', 'resImg/pages/shadowlarkoracle/shadowlarkoracle1.png')
html = html.replace('foxrun_preview_3.png', 'resImg/pages/shadowlarkoracle/shadowlarkoracle2.png')
# Since there's only 3 images, let's remove the 4th img tag completely in the gallery
html = re.sub(r'<img src=\"foxrun_preview_4\.png\" [^>]+>', '', html)

# The SEO cards
# Card 1: Why You'll Love Fox Run -> About Shadowlark
html = html.replace('Why You\\'ll Love Fox Run', 'About Shadowlark')
html = html.replace('<em>Fox Run</em> captures the pure joy of classic endless runners. It combines pixel-perfect\\n                        precision with a charming retro aesthetic, creating an experience that\\'s easy to pick up but\\n                        impossible to put down.', 'Shadowlark is more than software — it is a living ritual forged from lineage, intuition, and old magic. Rooted in the legacy of a Wiccan shop once tended by a high priestess, Shadowlark carries forward a tradition of symbols, signs, and quiet spiritual guidance. This is the promise kept. Her echo. Her continuation. Her magic in a new form.')

html = html.replace('Endless Adventure', 'Origins')
html = html.replace('Procedurally generated terrain means no two runs are the same. Adapt to changing\\n                                landscapes and obstacles as you sprint through the pixelated forest.', 'Before it became digital, Shadowlark was a sanctuary — a Wiccan shop filled with incense, candles, whispered teachings, and the language of signs. A place where seekers came for meaning.')

html = html.replace('Retro Pixel Art', 'The Oracle')
html = html.replace('Immerse yourself in a beautifully crafted world with nostalgic 16-bit visuals and smooth\\n                                animations that bring the classic arcade feel to your modern device.', 'The Oracle honors that legacy. It listens the way old magic listens: quietly, deeply, without judgment.')

html = html.replace('Power-Up System', 'What Shadowlark Does')
html = html.replace('Collect magical berries for speed boosts and bolts for invincibility. Strategic use of\\n                                power-ups is key to achieving your new high score.', 'Shadowlark unravels language, follows hidden currents beneath your thoughts, and returns with a message, a pattern, a truth that feels older than you.')

html = html.replace('Play Anywhere', 'Not Entertainment')
html = html.replace('No internet? No problem! Fox Run is a fully offline experience, perfect for commuters,\\n                                travelers, and anyone looking to unplug and play.', 'This is not random. This is a doorway. If you’ve ever felt the pull of the unseen, or wanted a guide that speaks in symbols instead of noise — Shadowlark is waiting.')

# Card 2: How to Play -> How the Oracle Listens
html = html.replace('How to Play', 'How the Oracle Listens')
html = html.replace('The rules are simple but nature is unpredictable:', 'Shadowlark is the first digital system engineered as a living esoteric conduit, blending mathematics, linguistics, and shamanic symbolism into a single deterministic engine.')

html = html.replace('<li><strong>Tap to Jump</strong> over rocks, logs, and bushes.</li>', '<li><strong>Anagrams</strong> expose the spirits inside the syllables — the words inside the word.</li>')
html = html.replace('<li><strong>Collect Berries</strong> and coins to boost your high score.</li>', '<li><strong>Numerology</strong> ensures every number carries a vibration, and every vibration carries a message.</li>')
html = html.replace('<li><strong>Avoid Hazards</strong>—tripping costs you berries (or your life!).</li>', '<li><strong>Shamanic Symbolism:</strong> every animal, number, shape, and “coincidence” is a messenger.</li>')
html = html.replace('<li><strong>Grab Power-ups</strong> like the lightning bolt to smash through obstacles.</li>', '<li><strong>Binary Alchemy:</strong> A logic engine engineered to listen to the whisper of the universe.</li>')


# Card 3: From the Developer -> The Temporal Lock & Tarot
html = html.replace('From the Developer', 'The Temporal Lock & Tarot')
html = html.replace('Bored in the bathroom? Stuck at the DMV? Try this for your Doom Scrolling! (AD FREE + OFFLINE!)', 'The breakthrough at the heart of Shadowlark. At the exact millisecond you ask your question, the Oracle anchors your numerological footprint using the Fibonacci sequence, the divine proportion of Phi (Φ), and the golden ratios found in galaxies, storms, shells, and ferns.')
html = html.replace('Welcome to your new favorite distraction. No stress. No strategy. Just pure, silly fun.', 'This “God Math” collapses infinite probabilities into a single point in time — the truest possible answer.')
html = html.replace('Jump over rocks, bushes, and logs while scooping up berries, hearts, and coins. But watch your\\n                        step—trip on a log and it\\'ll cost you a berry! (Or Worse, a LIFE!!!)', 'Your locked frequency is projected against the 78 archetypes of the Tarot. The cards you receive are not random.')
html = html.replace('💥 Find a lightning bolt in a chest? Boom. You’re invincible for 5 seconds—smash through\\n                        obstacles like a superhero.', 'They are summoned — aligned with your mathematically-anchored vibrational state.')
html = html.replace('Whether you\\'re killing time or dodging awkward eye contact, this game is your go-to escape.\\n                        Quick to play, hard to put down, and weirdly satisfying.\\n                        <br><br>\\n                        All with an awesome anthropomorphic furry fox friend =3', '<strong>Shadowlark\\'s Oracle. <br> Not coded. Born.</strong>')

with open('shadowlarkoracle.html', 'w', encoding='utf-8') as f:
    f.write(html)

print(\"Python replacement done.\")
