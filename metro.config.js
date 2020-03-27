const blacklist = require("metro-config/src/defaults/blacklist");

module.exports = {
  resolver: {
    blacklistRE: blacklist([/android\/.*/]),
    sourceExts: ["jsx", "js", "ts", "tsx"]
  }
};
