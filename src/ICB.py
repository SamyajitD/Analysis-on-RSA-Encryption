# Import modules
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import base64

# Define key and message
key = b'C&F)H@McQfTjWnZr'  # 16 bytes key for AES-128
message = "Hello, world!"

# Encrypt message
cipher = AES.new(key, AES.MODE_CBC)  # Create cipher object
# Pad and encrypt message
ct_bytes = cipher.encrypt(pad(message.encode(), AES.block_size))
iv = base64.b64encode(cipher.iv).decode()  # Encode iv to base64
ct = base64.b64encode(ct_bytes).decode()  # Encode ciphertext to base64
print("Encrypted message:", iv, ct)

# Decrypt message
# Create cipher object with same key and iv
cipher = AES.new(key, AES.MODE_CBC, iv=base64.b64decode(iv))
pt = unpad(cipher.decrypt(base64.b64decode(ct)),
           AES.block_size)  # Decrypt and unpad ciphertext
print("Decrypted message:", pt.decode())
