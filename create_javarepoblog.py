
import os

def create_blog_page():
    base_path = r"c:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website"
    privacy_file = os.path.join(base_path, "privacy.html")
    img_path = os.path.join(base_path, "javarepoblog.html")

    with open(privacy_file, "r", encoding="utf-8") as f:
        content = f.read()

    # Define the start and end markers for the content to replace
    # We want to replace the content inside the main card
    start_marker = '<div\n                class="bg-gray-800 bg-opacity-90 backdrop-blur-sm p-8 md:p-12 rounded-xl shadow-lg max-w-4xl mx-auto text-left">'
    
    # We'll look for the H1 tag which follows the start marker div
    h1_marker = '<h1 class="text-4xl md:text-5xl font-extrabold text-green-400 mb-6 text-center">Privacy Policy</h1>'
    
    # The content ends before the footer
    footer_marker = '<!-- Footer -->'
    
    # Locate the insertion point
    try:
        start_idx = content.index(start_marker) + len(start_marker)
        # Find the end of the div content. It's hard to find the matching closing div purely by string search without counting.
        # However, we know it ends before the section closes, and the section closes before the footer.
        # The section is <section class="py-20 pt-32 z-10 relative">
        # It ends with </section> before formatting footer.
        
        # Proper way: split the file into 3 parts: Header, Old Body, Footer.
        # Header ends after the opening div tag of the card.
        # Footer starts at the closing div tag of the card.
        
        # Let's find the card opening and the footer start, and assume the card closing div is the last </div> before </section> before <!-- Footer -->.
        
        section_end_idx = content.index('</section>', content.index('<!-- Main Content Area -->'))
        footer_idx = content.index('<!-- Footer -->')
        
        # The card closing div is likely the one before </div > (container) before </section>
        # Let's just find the exact string to replace.
        # We replace everything from <h1...Privacy Policy</h1> down to the last characters before the closing layout divs.
        
        # Let's assume we replace the whole <section ... > ... </section> block to be safe, creating a fresh section for the blog.
        section_start_str = '<!-- Main Content Area -->'
        section_start_idx = content.index(section_start_str)
        
        section_end_str = '<!-- Footer -->'
        section_end_idx = content.index(section_end_str)
        
        # Extract header (up to Main Content Area) and Footer (from Footer onwards)
        header_content = content[:section_start_idx]
        footer_content = content[section_end_idx:]
        
        # New Main Content
        new_body = """<!-- Main Content Area -->
    <section class="py-20 pt-32 z-10 relative">
        <div class="container mx-auto px-6">
            <div class="bg-gray-800 bg-opacity-90 backdrop-blur-sm p-8 md:p-12 rounded-xl shadow-lg max-w-4xl mx-auto text-left">
                
                <!-- Back Link -->
                <div class="mb-8">
                    <a href="blog.html" class="text-green-400 hover:text-green-500 font-semibold flex items-center">
                        <i class="fas fa-arrow-left mr-2"></i> Back to Blog
                    </a>
                </div>

                <article>
                    <h1 class="text-3xl md:text-5xl font-extrabold text-white mb-4 leading-tight break-words">The Fossil Record of a Soul: Why I Built an Archive of Echoes</h1>
                    <p class="text-gray-400 text-lg mb-6">
                        By <span class="text-green-400">Nick Larkin</span>
                    </p>

                    <div class="prose prose-invert max-w-none text-gray-300 leading-relaxed">
                        <p class="mb-4">
                            In the age of Artificial Intelligence, where code can be generated in seconds and applications are born from prompts, there is a dangerous temptation to forget where we came from. We are told that the future belongs to the machine. But I believe the future belongs to those who know the machine’s heartbeat—because they are the ones who built it, line by line, loop by loop, mistake by glorious mistake.
                        </p>
                        <p class="mb-4">
                            Today, I am opening the vault. I am publishing the <a href="javaapps.html" class="text-green-400 font-semibold hover:underline">CodeFoxSoft Java Portfolio</a>, a comprehensive archive of the projects that shaped my life as a developer.
                        </p>
                        <p class="mb-4">
                            This isn't just a portfolio. It is a fossil record of my evolution.
                        </p>

                        <h2 class="text-2xl font-bold text-white mb-3 mt-8">From QBasic to the Galaxy</h2>
                        <p class="mb-4">
                            My journey didn’t begin with a startup pitch or a university degree. It began in the 6th grade, staring at a QBasic terminal, realizing that if I typed the right words, I could make the screen obey my imagination. That realization—that to create is to be free—became the defining obsession of my life.
                        </p>
                        <p class="mb-4">
                            For nearly two decades, while others were playing games, I was reverse-engineering them. While others were using tools, I was writing my own. This new page is a living gallery of that obsession, set against a backdrop of drifting stars—a visual metaphor for a universe built from code.
                        </p>

                        <h2 class="text-2xl font-bold text-white mb-3 mt-8">The Heavy Hitters: Pillars of the Archive</h2>
                        <p class="mb-4">
                            When you scroll through this archive, you will see dozens of experiments. But if you look closely, you will find the giants—the projects where I stopped being a student and became an engineer. These are the true heavy hitters.
                        </p>

                        <div class="space-y-6 mb-8">
                            <div>
                                <h3 class="text-xl font-bold text-green-300 mb-2">1. Pong 2011: The Birth of Physics</h3>
                                <p class="mb-2">Before I released apps on the Play Store, I cut my teeth on Pong 2011. It wasn't just about bouncing a ball; it was about understanding the invisible forces of a digital world. Writing Pong.java taught me how to simulate gravity, calculate vectors, and handle collision detection in real-time. It was the moment I realized that a blank screen wasn't empty—it was a canvas waiting for physics.</p>
                            </div>
                            
                            <div>
                                <h3 class="text-xl font-bold text-green-300 mb-2">2. Binary Converter: Speaking the Machine's Tongue</h3>
                                <p class="mb-2">To truly master the machine, you must speak its language. The Binary Converter (found in the archive as DecimalConverterGUI.java) was my deep dive into the raw data that powers our reality. By building a tool that seamlessly translates between Decimal, Binary, Octal, and Hexadecimal, I wasn't just doing math; I was learning the dialect of the processor itself.</p>
                            </div>

                            <div>
                                <h3 class="text-xl font-bold text-green-300 mb-2">3. KeyGenerator: Chaos into Order</h3>
                                <p class="mb-2">Perhaps the most complex of them all is the KeyGenerator.java. This wasn't a simple random number script. It was an algorithmic beast that calculated massive Fibonacci numbers and converted subsets of them into formatted Hexadecimal strings. It was an exercise in turning mathematical chaos into structured, secure order—a philosophy that still drives CodeFoxSoft today.</p>
                            </div>
                        </div>

                        <h2 class="text-2xl font-bold text-white mb-3 mt-8">Why This Matters Now</h2>
                        <p class="mb-4">
                            At CodeFoxSoft, our mission is to prove that programmers aren't obsolete in the age of AI—we are the leaders shaping its future. But to lead the future, you must understand the foundation.
                        </p>
                        <p class="mb-4">
                            I didn't jump straight to building custom AI solutions. I earned my understanding of logic by writing sorting algorithms by hand and debugging thread synchronization. I learned the structure of data by writing these converters and generators.
                        </p>
                        <p class="mb-4 font-semibold text-green-300">
                            This archive is proof that expertise isn't downloaded; it is forged.
                        </p>

                        <h2 class="text-2xl font-bold text-white mb-3 mt-8">An Invitation to the Archive</h2>
                        <p class="mb-4">
                            I invite you to visit the new <a href="javaapps.html" class="text-green-400 font-semibold hover:underline">Java Apps</a>.
                        </p>
                        <p class="mb-4">
                            Don't just look at the code. Look at the intent. Look at the "pranks" like BSOD.java that show a teenager’s sense of humor. Look at the BabySitterPayCalc.java that shows a young entrepreneur trying to solve real-world problems.
                        </p>
                        <p class="mb-4">
                            This is who I am. I am a shaper. I am a builder. And this is my foundation.
                        </p>
                        <p class="text-xl font-bold text-white mt-8 italic">
                            Come see where the Fox learned to run.
                        </p>
                    </div>
                </article>
            </div>
        </div>
    </section>

"""
        
        # Also update Title and Meta tags in header
        # <title>Privacy Policy - CodeFoxSoft</title>
        header_content = header_content.replace('<title>Privacy Policy - CodeFoxSoft</title>', '<title>The Fossil Record of a Soul - CodeFoxSoft Blog</title>')
        header_content = header_content.replace('content="Privacy Policy - CodeFoxSoft | GDPR & CCPA Compliant"', 'content="The Fossil Record of a Soul - CodeFoxSoft Blog"')
        header_content = header_content.replace('content="Comprehensive privacy policy covering GDPR, CCPA, and data protection for CodeFoxSoft."', 'content="Nick Larkin reflects on his journey from QBasic to modern AI, introducing the new CodeFoxSoft Java Portfolio archive."')
        
        # Combine
        full_html = header_content + new_body + footer_content
        
        with open(img_path, "w", encoding="utf-8") as f:
            f.write(full_html)
            
        print("Successfully created javarepoblog.html")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    create_blog_page()
