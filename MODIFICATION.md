# 显示所有牌功能 - 修改说明

## 修改目标
在麻将游戏中显示所有玩家的手牌（原本只显示自己的牌）。

## 修改位置
`assets/assets/main/index.jsc` → 解密后 `index.js` 第 14416 行

### 原始代码:
```javascript
isShowCards: function(e) {
    for (var t = 0; t < this.showCardsUser.length; t++) {
        var r = this.showCardsUser[t];
        if (e == this.switchViewId(t)) return r;
    }
    return !1;
},
```

### 修改后:
```javascript
isShowCards: function(e) {
    return !0;
},
```

## XXTEA 加密信息
- **密钥**: `f0c18725-6d0a-4c` (在 `libcocos2djs.so` 的 `.rodata` 段中)
- **加密方式**: 直接 XXTEA 加密 (无长度前缀)，数据是 gzip 压缩的 JS 代码
- **流程**: JS 文本 → gzip 压缩 → XXTEA 加密 → .jsc 文件

## 文件列表
- `process_jsc.py` - JSC 文件解密/加密处理脚本
- `debug_xxtea.py` - XXTEA 调试脚本（长度在末尾的方式）
- `debug_xxtea2.py` - XXTEA 调试脚本（长度在开头的方式）

## 使用方法
```bash
# 修改 index.js 后重新加密
python3 process_jsc.py

# 重新打包 APK
cd repack/unpacked
zip -r -n .arsc ../qygame-unsigned.apk .
cd ..
zipalign -p 4 qygame-unsigned.apk qygame-aligned.apk
apksigner sign --ks debug.keystore qygame-aligned.apk

# 安装
adb install -r qygame-aligned.apk
```
