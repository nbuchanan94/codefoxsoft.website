from PIL import Image

def resize_images():
    # Load images
    img_target = Image.open("pongplay.png") # The larger one to match
    img_to_fix = Image.open("p164.png")     # The smaller one to pad
    
    target_w, target_h = img_target.size
    print(f"Target size (pongplay.png): {target_w}x{target_h}")
    print(f"Original size (p164.png): {img_to_fix.size}")
    
    # Calculate scale to match height?
    # img_target: 1080x585 (1.84)
    # img_to_fix: 600x400 (1.5)
    
    # If we maximize height (585):
    scale_h = target_h / img_to_fix.height
    new_w_from_h = int(img_to_fix.width * scale_h)
    
    # If we maximize width (1080):
    scale_w = target_w / img_to_fix.width
    new_h_from_w = int(img_to_fix.height * scale_w)
    
    # Check which fits
    if new_w_from_h <= target_w:
        # Fits by height scaling
        new_w = new_w_from_h
        new_h = target_h
    else:
        # Must scale by width
        new_w = target_w
        new_h = new_h_from_w
        
    print(f"Resizing p164.png to {new_w}x{new_h}")
    img_resized = img_to_fix.resize((new_w, new_h), Image.Resampling.LANCZOS)
    
    # Create background container
    # Site bg color is #0c0d11 -> (12, 13, 17)
    # Or just use black/transparent? User liked the previous result which used site bg.
    bg_color = (12, 13, 17) 
    
    new_img = Image.new("RGB", (target_w, target_h), bg_color)
    
    # Paste centered
    x_offset = (target_w - new_w) // 2
    y_offset = (target_h - new_h) // 2
    
    new_img.paste(img_resized, (x_offset, y_offset))
    
    output_name = "p164_fixed.png"
    new_img.save(output_name)
    print(f"Saved {output_name} with size {new_img.size}")

if __name__ == "__main__":
    resize_images()
