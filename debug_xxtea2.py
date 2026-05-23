#!/usr/bin/env python3
"""Test XXTEA with length at END (matching the xxtea C library behavior)."""

import struct
import sys
import zlib

DELTA = 0x9E3779B9
UINT32_MASK = 0xFFFFFFFF

def btea_encrypt(v, key_uint32):
    """Standard XXTEA encrypt with length at end."""
    n = len(v) - 1  # Last element is the length
    if n < 1:
        return v

    rounds = 6 + 52 // (n + 1)
    total = 0
    z = v[n]

    for _ in range(rounds):
        total = (total + DELTA) & UINT32_MASK
        e = (total >> 2) & 3

        for p in range(n):
            y = v[p + 1]
            mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^ ((total ^ y) + (key_uint32[(p & 3) ^ e] ^ z)))
            v[p] = (v[p] + mx) & UINT32_MASK
            z = v[p]

        y = v[0]
        mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^ ((total ^ y) + (key_uint32[(n & 3) ^ e] ^ z)))
        v[n] = (v[n] + mx) & UINT32_MASK
        z = v[n]

    return v

def btea_decrypt(v, key_uint32):
    """Standard XXTEA decrypt with length at end."""
    n = len(v) - 1  # Last element is the length
    if n < 1:
        return v

    rounds = 6 + 52 // (n + 1)
    total = (rounds * DELTA) & UINT32_MASK
    y = v[0]

    for _ in range(rounds):
        e = (total >> 2) & 3

        for p in range(n, 0, -1):
            z = v[p - 1]
            mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^ ((total ^ y) + (key_uint32[(p & 3) ^ e] ^ z)))
            v[p] = (v[p] - mx) & UINT32_MASK
            y = v[p]

        z = v[n]
        mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^ ((total ^ y) + (key_uint32[(0 & 3) ^ e] ^ z)))
        v[0] = (v[0] - mx) & UINT32_MASK
        y = v[0]

        total = (total - DELTA) & UINT32_MASK

    return v

def key_str_to_uint32(key_bytes):
    """Convert key string to 4 uint32 (little-endian). Pad/truncate to 16 bytes."""
    k = bytearray(16)
    copy_len = min(len(key_bytes), 16)
    k[:copy_len] = key_bytes[:copy_len]
    return list(struct.unpack('<IIII', bytes(k)))

def xxtea_encrypt(data, key_bytes):
    """Encrypt using XXTEA with length at END (matching xxtea C library).

    Layout: [data_bytes... (padded to 4 bytes)] [original_length as uint32]
    Total is N+1 uint32 elements, encrypted together.
    """
    data_len = len(data)

    # Calculate padded size for data (multiple of 4)
    padded_data_len = ((data_len + 3) // 4) * 4

    # Total uint32 elements: padded_data + 1 (for length)
    total_uint32 = padded_data_len // 4 + 1

    # Create buffer
    buf = bytearray(total_uint32 * 4)
    # Copy data
    buf[:data_len] = data
    # Padding bytes after data are already zero (from bytearray)
    # Last 4 bytes: original length
    struct.pack_into('<I', buf, total_uint32 * 4 - 4, data_len)

    # Convert to uint32
    v = list(struct.unpack('<' + 'I' * total_uint32, bytes(buf)))

    # Convert key
    k = key_str_to_uint32(key_bytes)

    # Encrypt
    v = btea_encrypt(v, k)

    return struct.pack('<' + 'I' * len(v), *v)

def xxtea_decrypt(data, key_bytes):
    """Decrypt using XXTEA with length at END (matching xxtea C library)."""
    if len(data) % 4 != 0:
        return None

    # Convert to uint32
    n_uint32 = len(data) // 4
    v = list(struct.unpack('<' + 'I' * n_uint32, data))

    # Convert key
    k = key_str_to_uint32(key_bytes)

    # Decrypt
    v = btea_decrypt(v, k)

    # Convert back to bytes
    decrypted = struct.pack('<' + 'I' * len(v), *v)

    # Get original length from last 4 bytes
    orig_len = struct.unpack_from('<I', decrypted, len(decrypted) - 4)[0]

    # Validate
    max_data_len = len(decrypted) - 4
    if orig_len > max_data_len:
        return None

    return bytes(decrypted[:orig_len])

# Let me also try with a simple key that the xxtea package uses
def try_key_variations():
    """Try different key interpretations."""
    key_str = 'f0c18725-6d0a-4c'
    print(f"Key string: {key_str}")
    print(f"Key string length: {len(key_str)}")
    print(f"Key string bytes: {key_str.encode().hex()}")

    # Variation 1: as-is ASCII
    k1 = key_str.encode()
    print(f"\nVariation 1 (ASCII): {k1.hex()}")

    # Variation 2: hex decode the key (skip dashes)
    hex_part = key_str.replace('-', '')
    k2 = bytes.fromhex(hex_part)
    print(f"Variation 2 (hex decoded): {k2.hex()} (length {len(k2)})")

    # Pad k2 to 16 bytes
    k2_padded = k2 + b'\x00' * (16 - len(k2))
    print(f"Variation 2 padded: {k2_padded.hex()}")

    # Variation 3: null-terminated
    k3 = key_str.encode() + b'\x00'
    print(f"Variation 3 (null-term): {k3.hex()}")

    # Variation 4: first 16 chars only
    k4 = key_str.encode()[:16]
    print(f"Variation 4 (first 16): {k4.hex()}")

    return [k1, k2_padded, k3, k4]

# Test with settings.jsc
jsc_path = '/Users/maofeifei/Desktop/ad_reverse/dulai/qygame-release-decompiled/resources/assets/src/settings.jsc'

with open(jsc_path, 'rb') as f:
    encrypted_data = f.read()

print(f"Encrypted file size: {len(encrypted_data)} bytes")
print(f"Encrypted first 32 bytes: {encrypted_data[:32].hex()}\n")

# Try with my implementation
keys = try_key_variations()

for i, key in enumerate(keys):
    print(f"\n--- Trying key variation {i+1}: {key.hex()} (len={len(key)}) ---")
    result = xxtea_decrypt(encrypted_data, key)
    if result is None:
        print("  FAILED: invalid length or decryption error")
    else:
        print(f"  Decrypted {len(result)} bytes")
        print(f"  First 32 bytes: {result[:32].hex()}")
        if result[:2] == b'\x1f\x8b':
            print("  FORMAT: gzip detected!")
            try:
                js = zlib.decompress(result, 31)
                print(f"  JS length: {len(js)}")
                print(js[:200].decode('utf-8', errors='replace'))
            except Exception as e:
                print(f"  Gzip decompress error: {e}")
                # Try raw deflate
                try:
                    js = zlib.decompress(result, -15)
                    print(f"  Raw deflate JS length: {len(js)}")
                    print(js[:200].decode('utf-8', errors='replace'))
                except Exception as e2:
                    print(f"  Raw deflate error: {e2}")
        else:
            print(f"  Not gzip, trying as text...")
            print(f"  First 100 chars: {result[:100]}")
