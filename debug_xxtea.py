#!/usr/bin/env python3
"""Debug XXTEA encryption/decryption for Cocos2d-x-lite engine."""

import struct
import sys
import gzip
import zlib
import hashlib

# XXTEA constants
DELTA = 0x9E3779B9
UINT32_MASK = 0xFFFFFFFF

def MX(z, y, total, key, p, e):
    """Standard XXTEA MX function with proper uint32 masking."""
    return (
        (((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^
        ((total ^ y) + (key[(p & 3) ^ e] ^ z))
    ) & UINT32_MASK

def bytes_to_uint32(data):
    """Convert bytes to list of uint32 (little-endian)."""
    # Pad to multiple of 4
    padded = data + b'\x00' * ((4 - len(data) % 4) % 4)
    return list(struct.unpack('<' + 'I' * (len(padded) // 4), padded))

def uint32_to_bytes(v):
    """Convert list of uint32 to bytes (little-endian)."""
    return struct.pack('<' + 'I' * len(v), *v)

def key_to_uint32(key_bytes):
    """Convert key bytes to 4 uint32 values (standard Cocos2d-x way)."""
    # Cocos2d-x copies up to 16 bytes of key, pads with zeros
    k = bytearray(16)
    copy_len = min(len(key_bytes), 16)
    k[:copy_len] = key_bytes[:copy_len]
    return list(struct.unpack('<IIII', bytes(k)))

def btea(v, n, k):
    """Standard XXTEA block TEA algorithm."""
    if n > 1:  # Encrypt
        rounds = 6 + 52 // n
        total = 0
        z = v[n - 1]
        for _ in range(rounds):
            total = (total + DELTA) & UINT32_MASK
            e = (total >> 2) & 3
            for p in range(n - 1):
                y = v[p + 1]
                v[p] = (v[p] + MX(z, y, total, k, p, e)) & UINT32_MASK
                z = v[p]
            y = v[0]
            v[n - 1] = (v[n - 1] + MX(z, y, total, k, n - 1, e)) & UINT32_MASK
            z = v[n - 1]
    elif n < -1:  # Decrypt
        n = -n
        rounds = 6 + 52 // n
        total = (rounds * DELTA) & UINT32_MASK
        y = v[0]
        for _ in range(rounds):
            e = (total >> 2) & 3
            for p in range(n - 1, 0, -1):
                z = v[p - 1]
                v[p] = (v[p] - MX(z, y, total, k, p, e)) & UINT32_MASK
                y = v[p]
            z = v[n - 1]
            v[0] = (v[0] - MX(z, y, total, k, 0, e)) & UINT32_MASK
            y = v[0]
            total = (total - DELTA) & UINT32_MASK
    return v

def xxtea_encrypt(data, key_bytes):
    """Encrypt data using XXTEA (Cocos2d-x style).

    The Cocos2d-x implementation:
    1. Prepends 4 bytes of original data length (uint32 LE)
    2. Pads to 4-byte alignment
    3. Encrypts using btea with positive n
    4. Returns the entire encrypted buffer
    """
    data_len = len(data)
    # Create buffer: 4 bytes (length) + data + padding
    padded_size = 4 + data_len
    if padded_size % 4 != 0:
        padded_size += 4 - (padded_size % 4)

    buf = bytearray(padded_size)
    # First 4 bytes: original length
    struct.pack_into('<I', buf, 0, data_len)
    # Copy data
    buf[4:4 + data_len] = data
    # Rest is zero-padded (already zero from bytearray)

    # Convert to uint32 array
    v = list(struct.unpack('<' + 'I' * (len(buf) // 4), bytes(buf)))

    # Convert key
    k = key_to_uint32(key_bytes)

    # Encrypt
    n = len(v)
    v = btea(v, n, k)

    return uint32_to_bytes(v)

def xxtea_decrypt(data, key_bytes):
    """Decrypt data using XXTEA (Cocos2d-x style).

    Returns the original data (without length prefix and padding).
    """
    if len(data) % 4 != 0:
        print(f"  WARNING: data length {len(data)} is not multiple of 4")
        return None

    # Convert to uint32 array
    v = list(struct.unpack('<' + 'I' * (len(data) // 4), data))

    # Convert key
    k = key_to_uint32(key_bytes)

    # Decrypt (negative n)
    n = len(v)
    v = btea(v, -n, k)

    # Get decrypted bytes
    decrypted = uint32_to_bytes(v)

    # First 4 bytes: original length
    orig_len = struct.unpack_from('<I', decrypted, 0)[0]

    # Validate length
    max_len = len(decrypted) - 4
    if orig_len > max_len:
        print(f"  WARNING: original length {orig_len} > max possible {max_len}")
        return None

    return bytes(decrypted[4:4 + orig_len])

def test_key_conversion():
    """Test key conversion."""
    key = b'f0c18725-6d0a-4c'
    print(f"Key bytes: {key.hex()}")
    print(f"Key length: {len(key)}")

    k = key_to_uint32(key)
    print(f"Key uint32: [{', '.join(f'0x{x:08x}' for x in k)}]")

    # Reconstruct key bytes from uint32
    reconstructed = struct.pack('<IIII', *k)
    print(f"Reconstructed: {reconstructed.hex()}")
    print()

def test_small():
    """Test with small known data."""
    key = b'f0c18725-6d0a-4c'
    original = b'Hello World! This is a test.'

    print(f"Original: {original}")
    print(f"Original hex: {original.hex()}")
    print(f"Original length: {len(original)}")

    encrypted = xxtea_encrypt(original, key)
    print(f"Encrypted ({len(encrypted)} bytes): {encrypted.hex()}")

    decrypted = xxtea_decrypt(encrypted, key)
    print(f"Decrypted: {decrypted}")

    if decrypted == original:
        print("ROUND-TRIP OK!")
    else:
        print("ROUND-TRIP FAILED!")
    print()

def test_with_file(jsc_path, key):
    """Test decrypt and re-encrypt round trip with a JSC file."""
    print(f"=== Testing {jsc_path} ===")

    with open(jsc_path, 'rb') as f:
        original_encrypted = f.read()

    print(f"Original encrypted size: {len(original_encrypted)} bytes")
    print(f"Original encrypted first 32 bytes: {original_encrypted[:32].hex()}")

    # Step 1: Decrypt
    decrypted = xxtea_decrypt(original_encrypted, key)
    if decrypted is None:
        print("DECRYPTION FAILED!")
        return

    print(f"Decrypted size: {len(decrypted)} bytes")
    print(f"Decrypted first 16 bytes: {decrypted[:16].hex()}")

    # Check if it's gzip
    if decrypted[:2] == b'\x1f\x8b':
        print("Format: gzip")
    else:
        print(f"Format: unknown (header: {decrypted[:4].hex()})")

    # Step 2: Re-encrypt
    re_encrypted = xxtea_encrypt(decrypted, key)
    print(f"Re-encrypted size: {len(re_encrypted)} bytes")
    print(f"Re-encrypted first 32 bytes: {re_encrypted[:32].hex()}")

    # Step 3: Decrypt again
    re_decrypted = xxtea_decrypt(re_encrypted, key)
    if re_decrypted is None:
        print("RE-DECRYPTION FAILED!")
        return

    # Step 4: Compare
    if decrypted == re_decrypted:
        print("PYTHON ROUND-TRIP: OK (decrypt→encrypt→decrypt produces same data)")
    else:
        print("PYTHON ROUND-TRIP: FAILED!")
        print(f"  Original decrypted: {decrypted[:32].hex()}")
        print(f"  Re-decrypted: {re_decrypted[:32].hex()}")

    # Step 5: Compare re-encrypted with original encrypted
    # Note: This might differ because gzip output can vary between runs
    # But we compare anyway
    if original_encrypted == re_encrypted:
        print("ENCRYPT OUTPUT MATCH: OK (bit-identical to original)")
    else:
        print("ENCRYPT OUTPUT DIFFERS from original (expected if gzip differs)")

        # Compare just the XXTEA portion by looking at v arrays
        # (not the raw bytes which include length prefix)
        orig_v = list(struct.unpack('<' + 'I' * (len(original_encrypted) // 4), original_encrypted))
        new_v = list(struct.unpack('<' + 'I' * (len(re_encrypted) // 4), re_encrypted))

        if orig_v == new_v:
            print("  But uint32 arrays are identical")
        else:
            diff_count = sum(1 for a, b in zip(orig_v, new_v) if a != b)
            print(f"  {diff_count} uint32 values differ")

    # Also try different key handling approaches
    print("\n--- Testing alternative key handling ---")
    return decrypted

if __name__ == '__main__':
    KEY = b'f0c18725-6d0a-4c'

    test_key_conversion()
    test_small()

    # Test with a small JSC file
    jsc_files = [
        '/Users/maofeifei/Desktop/ad_reverse/dulai/qygame-release-decompiled/resources/assets/src/settings.jsc',
    ]

    for jsc_path in jsc_files:
        try:
            test_with_file(jsc_path, KEY)
        except FileNotFoundError:
            print(f"File not found: {jsc_path}")
