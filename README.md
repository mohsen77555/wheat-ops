# wheat-ops · نظام تفريغ باخرة القمح السائب

تطبيق أندرويد لإدارة وتوثيق عمليات تفريغ باخرة القمح السائب: سجلات التصدير، الترحيل،
الاستقبال، الجودة والفحص، المطابقة المستقلة، التوقفات، المخالفات، المالية، وتسليم الورديات.
يعمل دون اتصال بالإنترنت، والبيانات محفوظة على الجهاز.

A real Android app (built with [Capacitor](https://capacitorjs.com/)) wrapping the
single-page web app in [`www/index.html`](www/index.html). The UI is Arabic / RTL and is
tuned to fit Android screens (status-bar tint, safe-area insets, dynamic viewport height,
no overscroll bounce, tablet-friendly layout).

## Project layout

```
www/                  Web app (source of truth): index.html, manifest, service worker, icons
resources/            Icon & splash sources (SVG + 1024px / 2732px PNG masters)
android/              Native Android (Capacitor) project — open in Android Studio
capacitor.config.json Capacitor configuration (appId, plugins, theme colors)
scripts/gen-icons.mjs Rasterizes resources/*.svg into PNG icons
.github/workflows/    CI that builds a debug APK on every push and on demand
```

- **App ID:** `com.salif.wheatops`
- **App name:** تفريغ القمح
- **Min / target SDK:** 24 / 36

## Install directly on your phone (ready-made APK)

A ready-to-install debug APK is published in this repo:
[`dist/wheat-ops-1.4.0.apk`](dist/wheat-ops-1.4.0.apk).

**First login:** username `admin`, password `admin` — then change it / add your team
from Settings → "إدارة المستخدمين والصلاحيات".

### Optional: real-time sync across devices

The app is fully usable offline on a single device. To make edits on one phone
appear on the others in real time, enable Cloud Sync (Settings → "المزامنة بين الأجهزة"):

1. Create a free Firebase project, then add a **Realtime Database** (start in test mode).
2. Copy the database URL (e.g. `https://xxxx-default-rtdb.firebaseio.com`).
3. On every device, paste the **same URL** and the **same team code**, then enable sync.
   (Use the "QR للمشاركة" button to copy the URL + team code to the other devices.)

Records are append-only and merged by id, so concurrent edits never lose data.
Sync needs internet; offline changes are merged once back online. For production,
tighten the Realtime Database security rules (e.g. require auth) instead of test mode.

On the phone:

1. Open this file on GitHub and tap **Download** (download the raw `.apk`).
2. Open the downloaded file. Android will ask to allow installing from this
   source — enable **"Allow from this source"** / **"Install unknown apps"**, then tap **Install**.
3. Launch **تفريغ القمح** from the app drawer.

> This is a debug-signed build for direct (sideload) installation, not a Play Store
> release. Update the file by rebuilding (see below) and replacing it.

## Build the APK in the cloud (no setup needed)

1. Open the **Actions** tab on GitHub.
2. Run the **Build APK** workflow (it also runs automatically on every push to `main`).
3. Download the `wheat-ops-apk` artifact and install `app-debug.apk` on the phone
   (allow "install from unknown sources").

## Build / develop locally

Requires Node 20+, JDK 21 (or 17), and the Android SDK (e.g. via Android Studio).

```bash
npm install              # install Capacitor + tooling
npm run assets           # regenerate launcher icons & splash from resources/*.svg
npm run sync             # copy www/ into the Android project
npm run open:android     # open the project in Android Studio
# or build a debug APK directly:
npm run build:apk        # → android/app/build/outputs/apk/debug/app-debug.apk
```

After editing anything under `www/`, run `npm run sync` (or `npx cap sync android`)
to copy the changes into the native project before building.

## Notes

- Records are **append-only**: nothing is deleted; corrections are added as notes
  (governance is documented inside the app under "حوكمة البرنامج").
- Export every register to CSV from the app for archiving / Excel reconciliation.
- يُعتمد أي تعديل على النماذج أو القواعد من رئيس الفريق ونائبه.
