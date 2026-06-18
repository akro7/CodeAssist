<div align="center">

# CodeAssist

**A lightweight, on-device IDE that edits *and builds* Android & Java projects — no laptop, no Gradle daemon.**

[![License](https://img.shields.io/github/license/akro7/android-code-studio)](LICENSE)

</div>

## What is CodeAssist?

CodeAssist is an extensible IDE framework that runs **entirely on device** (Android/ART) and edits and
builds Android/Java projects **without hosting Gradle**. A full Gradle runtime is too heavy for a phone,
so CodeAssist models projects itself, mimics Gradle's incremental task engine without the Gradle daemon,
and drives the Android toolchain (aapt2, D8/R8, apksigner) directly.

### Highlights

- ☕ **Java code intelligence** — error-tolerant parsing, ranked completion, diagnostics, and quick-fixes
- 🟪 **Kotlin completion** — full Kotlin code completion on device
- 🧩 **Block editing** — project any Java file into a block tree and edit it there
- 📐 **Android XML assist** — tag, attribute, and resource completion for layouts/manifests
- 📦 **Real APK builds on device** — resolve, compile, dex, package, sign, and install
- 🔌 **Extensible everywhere** — module types, build systems, language backends

## Developer

- **Telegram:** [@A_KOJO](https://t.me/A_KOJO)
- **Contact:** 01508294415

## License

GPLv3 — see [LICENSE](LICENSE)
