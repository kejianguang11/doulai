window.__require = function e(r, n, o) {
function t(u, c) {
if (!n[u]) {
if (!r[u]) {
var f = u.split("/");
f = f[f.length - 1];
if (!r[f]) {
var s = "function" == typeof __require && __require;
if (!c && s) return s(f, !0);
if (i) return i(f, !0);
throw new Error("Cannot find module '" + u + "'");
}
u = f;
}
var p = n[u] = {
exports: {}
};
r[u][0].call(p.exports, function(e) {
return t(r[u][1][e] || e);
}, p, p.exports, e, r, n, o);
}
return n[u].exports;
}
for (var i = "function" == typeof __require && __require, u = 0; u < o.length; u++) t(o[u]);
return t;
}({
commonView: [ function(e, r) {
"use strict";
cc._RF.push(r, "fa72a3gAkhK56G4XjVTjd/E", "commonView");
cc.Class({
extends: cc.Component,
properties: {},
btClose: function() {
gg.audio.playEffect("sfx_button");
this.node.destroy();
}
});
cc._RF.pop();
}, {} ]
}, {}, [ "commonView" ]);