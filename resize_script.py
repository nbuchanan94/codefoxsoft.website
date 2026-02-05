from PIL import Image

def resize_images():
    # Load images
    img1 = Image.open("furryrave1.png")
    img2 = Image.open("furryrave2.jpg")
    
    print(f"Original sizes: img1={img1.size}, img2={img2.size}")
    
    # Target dimensions from img1
    target_w, target_h = img1.size
    
    # Calculate scale for img2 to match height of img1
    # We want to match height first, then pad width?
    # Or match which one?
    # img1 is 1140x642 (1.77)
    # img2 is 586x440 (1.33)
    
    # Scaling img2 to height 642
    scale = target_h / img2.height
    new_w = int(img2.width * scale)
    new_h = target_h
    
    print(f"Resizing img2 to {new_w}x{new_h}")
    img2_resized = img2.resize((new_w, new_h), Image.Resampling.LANCZOS)
    
    # Create background container
    # Site bg color is #0c0d11 -> (12, 13, 17)
    bg_color = (12, 13, 17)
    new_img2 = Image.new("RGB", (target_w, target_h), bg_color)
    
    # Paste centered
    x_offset = (target_w - new_w) // 2
    new_img2.paste(img2_resized, (x_offset, 0))
    
    output_name = "furryrave2_fixed.png"
    new_img2.save(output_name)
    print(f"Saved {output_name} with size {new_img2.size}")

if __name__ == "__main__":
    resize_images()
