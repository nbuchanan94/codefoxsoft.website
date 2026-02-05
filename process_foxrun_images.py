from PIL import Image
import os
import shutil

def process_images():
    # Source paths (adjust based on user metadata)
    base_upload_path = r"C:/Users/psych/.gemini/antigravity/brain/53325f7f-c267-44ba-83e5-ef56559be778/"
    uploaded_files = [
        "uploaded_media_0_1770288773162.png",
        "uploaded_media_1_1770288773162.png",
        "uploaded_media_2_1770288773162.png",
        "uploaded_media_3_1770288773162.png"
    ]
    
    target_dir = r"c:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website"
    
    # Target dimensions (matching furryrave size for consistency)
    target_w = 1140
    target_h = 642
    bg_color = (12, 13, 17) # #0c0d11
    
    output_files = []
    
    for i, filename in enumerate(uploaded_files):
        src_path = os.path.join(base_upload_path, filename)
        if not os.path.exists(src_path):
            print(f"File not found: {src_path}")
            continue
            
        img = Image.open(src_path)
        print(f"Processing {filename} (Size: {img.size})")
        
        # Stretch to fit target dimensions directly (ignoring aspect ratio)
        print(f"Stretching {filename} to {target_w}x{target_h}")
        canvas = img.resize((target_w, target_h), Image.Resampling.LANCZOS)
        
        output_filename = f"foxrun_preview_{i+1}.png"
        output_path = os.path.join(target_dir, output_filename)
        
        canvas.save(output_path)
        print(f"Saved {output_path}")
        output_files.append(output_filename)

if __name__ == "__main__":
    process_images()
