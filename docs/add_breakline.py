import sys

def add_br_to_lines(text):
    lines = text.split('\n')
    modified_lines = [line + ' <br>' if line.strip() else line for line in lines]
    return '\n'.join(modified_lines)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python add_br_to_markdown.py input.md output.md")
        sys.exit(1)
    
    input_file = sys.argv[1]
    output_file = sys.argv[2]
    
    with open(input_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    modified_content = add_br_to_lines(content)
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(modified_content)
    
    print(f"Processed {input_file} and saved to {output_file}")