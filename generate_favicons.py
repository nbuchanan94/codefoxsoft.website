from PIL import Image
import os

def generate_favicons(source_file):
    try:
        img = Image.open(source_file)
        
        # Ensure RGBA for transparency
        if img.mode != 'RGBA':
            img = img.convert('RGBA')

        # Favicon sizes
        icon_sizes = [(16, 16), (32, 32), (48, 48)]
        img.save('favicon.ico', sizes=icon_sizes)
        print("Generated favicon.ico")

        # PNG Favicons
        sizes = {
            'favicon-16x16.png': (16, 16),
            'favicon-32x32.png': (32, 32),
            'apple-touch-icon.png': (180, 180),
            'android-chrome-192x192.png': (192, 192),
            'android-chrome-512x512.png': (512, 512)
        }

        for filename, size in sizes.items():
            resized_img = img.resize(size, Image.Resampling.LANCZOS)
            resized_img.save(filename)
            print(f"Generated {filename}")

    except Exception as e:
        print(f"Error generating favicons: {e}")

if __name__ == "__main__":
    source = "logo600x400.png"
    if os.path.exists(source):
        generate_favicons(source)
    else:
        # Fallback to other logo if 600x400 doesn't exist, though listed in directory
        if os.path.exists("logo.png"):
             generate_favicons("logo.png")
        else:
             print("Source logo not found!")
