# PichiTube 📺

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="PichiTube Logo" width="120" />
</p>

<h3 align="center">A Premium, Lightweight, Privacy-Focused & 100% Ad-Free YouTube Client for Android</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Version-v1.0.0-red.svg" alt="Version 1.0.0" />
  <img src="https://img.shields.io/badge/Platform-Android_8.0+-green.svg" alt="Android 8.0+" />
  <img src="https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-blue.svg" alt="Architecture" />
  <img src="https://img.shields.io/badge/UI-Jetpack_Compose-purple.svg" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/License-GPL_v3-yellow.svg" alt="License" />
</p>

---

## 🌟 Key Features

- **🚫 Pure Ad-Free Streaming:** No pre-roll, mid-roll, or banner ads.
- **⏭️ SponsorBlock Auto-Skip:** Skips sponsored integrations, intro animations, and subscribe nags.
- **🎬 Ultra HD & 4K Playback:** Seamlessly stream 4K (2160p), 1440p, 1080p60, 720p, or Audio-Only with `MergingMediaSource`.
- **🎧 Background Playback & Media Notification:** Listen to videos with the screen locked. Full media notification in notification shade with artwork and playback controls.
- **📱 Fluid Mini-Player & PiP:** Docked miniplayer above the bottom navigation bar and automatic Picture-in-Picture mode.
- **⚡ Dedicated Shorts Player:** Full-screen vertical swipe experience (`VerticalPager`) with sound and channel overlay.
- **🔍 Smart Live YouTube Search:** Autocomplete query suggestions with 1-tap query insert arrows.
- **🇧🇩 Regional Feeds (Bangladesh Default):** Curated regional trending kiosks, Bangla natok, music, tech, and personalized recommendations.
- **⏱️ Sleep Timer:** Automatic sleep timer with "End of video" stop option.
- **🔄 In-App Self-Updater:** Automatically notifies and downloads APK updates with 1-tap in-app package installation (no browser needed).
- **🔒 Zero Telemetry / 100% Local Data:** No Google account needed. History, bookmarks, and settings are saved only in local SQLite Room database.

---

## 📥 Installation

1. Download the latest release APK from [Releases](https://github.com/mohammad-rabius-sani/PichiTube/releases/latest/download/PichiTube.apk).
2. Open the downloaded `PichiTube.apk` on your Android device.
3. Allow "Install from unknown sources" if prompted, then tap **Install**.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Kotlin 2.1.0
- **UI Framework:** Jetpack Compose with Material 3 Design
- **Media Engine:** AndroidX Media3 / ExoPlayer 1.6.1 (`MergingMediaSource`, `HlsMediaSource`, `DashMediaSource`)
- **Extraction Engine:** NewPipeExtractor
- **Dependency Injection:** Hilt / Dagger
- **Local Persistence:** Room Database & DataStore Preferences
- **Networking:** OkHttp 4.12 & Kotlinx Serialization
- **Image Loading:** Coil Compose

---

## 👨‍💻 Author & Contact

**Rabius Sani**  
*Owner & CEO, PichiPie Lab*

- 🌐 **Portfolio:** [https://rabius-sani-portfolio.vercel.app/](https://rabius-sani-portfolio.vercel.app/)
- 📧 **Personal Email:** mohammad.rabius.sanii@gmail.com
- 📬 **Support Email:** pichipie.official@gmail.com
- 🐙 **GitHub:** [https://github.com/mohammad-rabius-sani](https://github.com/mohammad-rabius-sani)

---

## 📄 License

This project is open source and available under the terms of the [GPL-3.0 License](LICENSE).
