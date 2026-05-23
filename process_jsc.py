#!/usr/bin/env python3
"""
Process JSC files for Cocos2d-x-lite engine.

The engine uses DIRECT XXTEA encryption (no length prefix) on gzip-compressed data.
Each .jsc file is: xxtea_encrypt(gzip_compress(js_code))

Key: f0c18725-6d0a-4c
"""

import struct
import gzip
import sys
import os

DELTA = 0x9E3779B9
UINT32_MASK = 0xFFFFFFFF


def to_uint32_array(data):
    """Convert bytes to list of uint32 (little-endian), padding to 4-byte alignment."""
    pad = (4 - len(data) % 4) % 4
    if pad:
        data = data + b'\x00' * pad
    n = len(data) // 4
    return list(struct.unpack('<' + 'I' * n, data))


def from_uint32_array(v):
    """Convert list of uint32 back to bytes."""
    return struct.pack('<' + 'I' * len(v), *v)


def key_to_uint32(key_bytes):
    """Convert key bytes to 4 uint32 values."""
    k = bytearray(16)
    copy_len = min(len(key_bytes), 16)
    k[:copy_len] = key_bytes[:copy_len]
    return list(struct.unpack('<IIII', bytes(k)))


def btea(v, n, k):
    """Direct XXTEA block TEA algorithm.

    Args:
        v: list of uint32 values
        n: positive = encrypt, negative = decrypt
        k: 4-element uint32 key array
    """
    if n > 1:  # Encrypt
        rounds = 6 + 52 // n
        total = 0
        z = v[n - 1]
        for _ in range(rounds):
            total = (total + DELTA) & UINT32_MASK
            e = (total >> 2) & 3
            for p in range(n - 1):
                y = v[p + 1]
                mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^
                      ((total ^ y) + (k[(p & 3) ^ e] ^ z)))
                v[p] = (v[p] + mx) & UINT32_MASK
                z = v[p]
            y = v[0]
            mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^
                  ((total ^ y) + (k[((n - 1) & 3) ^ e] ^ z)))
            v[n - 1] = (v[n - 1] + mx) & UINT32_MASK
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
                mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^
                      ((total ^ y) + (k[(p & 3) ^ e] ^ z)))
                v[p] = (v[p] - mx) & UINT32_MASK
                y = v[p]
            z = v[n - 1]
            mx = ((((z >> 5) ^ (y << 2)) + ((y >> 3) ^ (z << 4))) ^
                  ((total ^ y) + (k[(0 & 3) ^ e] ^ z)))
            v[0] = (v[0] - mx) & UINT32_MASK
            y = v[0]
            total = (total - DELTA) & UINT32_MASK
    return v


def xxtea_encrypt_direct(data, key_bytes):
    """Encrypt data using direct XXTEA (no length prefix - matches Cocos2d-x-lite)."""
    v = to_uint32_array(data)
    k = key_to_uint32(key_bytes)
    v = btea(v.copy(), len(v), k)
    return from_uint32_array(v)


def xxtea_decrypt_direct(data, key_bytes):
    """Decrypt data using direct XXTEA (no length prefix - matches Cocos2d-x-lite)."""
    v = list(struct.unpack('<' + 'I' * (len(data) // 4), data))
    k = key_to_uint32(key_bytes)
    v = btea(v.copy(), -len(v), k)
    return from_uint32_array(v)


def decrypt_jsc(jsc_path, key):
    """Decrypt a .jsc file and return the JavaScript code."""
    with open(jsc_path, 'rb') as f:
        encrypted = f.read()

    decrypted = xxtea_decrypt_direct(encrypted, key)

    # Decompress gzip
    js = gzip.decompress(decrypted)
    return js


def encrypt_jsc(js_code, key):
    """Encrypt JavaScript code into .jsc format."""
    # Gzip compress
    gzip_data = gzip.compress(js_code)

    # XXTEA encrypt
    encrypted = xxtea_encrypt_direct(gzip_data, key)

    return encrypted


def process_file(js_path, output_jsc_path, key):
    """Read JS file, encrypt to JSC."""
    with open(js_path, 'rb') as f:
        js = f.read()

    encrypted = encrypt_jsc(js, key)

    os.makedirs(os.path.dirname(output_jsc_path), exist_ok=True)
    with open(output_jsc_path, 'wb') as f:
        f.write(encrypted)

    print(f"  {js_path} ({len(js)} bytes) -> {output_jsc_path} ({len(encrypted)} bytes)")


# Main processing
if __name__ == '__main__':
    KEY = b'f0c18725-6d0a-4c'

    base = '/Users/maofeifei/Desktop/ad_reverse/dulai'

    # The main index.js is already modified (isShowCards returns !0)
    # We need to re-encrypt it to index.jsc
    js_input = f'{base}/qygame-release-decompiled/resources/assets/assets/main/index.js'
    jsc_output = f'{base}/repack/unpacked/assets/assets/main/index.jsc'

    print("Processing main/index.jsc...")
    process_file(js_input, jsc_output, KEY)

    # Verify: decrypt and check
    print("\nVerifying...")
    with open(jsc_output, 'rb') as f:
        encrypted = f.read()
    decrypted = xxtea_decrypt_direct(encrypted, KEY)
    js = gzip.decompress(decrypted)

    if b'return !0' in js:
        print("  VERIFIED: isShowCards modification present!")
    else:
        print("  WARNING: isShowCards modification NOT found!")

    # Verify round-trip
    re_encrypted = xxtea_encrypt_direct(gzip.compress(js), KEY)
    re_decrypted = xxtea_decrypt_direct(re_encrypted, KEY)
    re_js = gzip.decompress(re_decrypted)
    if re_js == js:
        print("  Round-trip OK!")
    else:
        print("  Round-trip FAILED!")

    print("\nDone! Ready to repack APK.")
