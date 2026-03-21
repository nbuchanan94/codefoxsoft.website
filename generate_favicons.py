from PIL import Image
import os

def generate_favicons(source_file):
    try:
        if not os.path.exists(source_file):
            print(f"Source {source_file} not found")
            return

        img = Image.open(source_file)
        if img.mode != 'RGBA':
            img = img.convert('RGBA')

        # Favicon sizes
        icon_sizes = [(16, 16), (32, 32), (48, 48)]
        img.save(os.path.join(os.path.dirname(source_file), 'favicon.ico'), sizes=icon_sizes)
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
            output_path = os.path.join(os.path.dirname(source_file), filename)
            resized_img.save(output_path)
            print(f"Generated {output_path}")

    except Exception as e:
        print(f"Error generating favicons: {e}")

if __name__ == "__main__":
    generate_favicons("resImg/logo/codefox-logo.png")
