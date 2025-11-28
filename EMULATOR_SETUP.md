# Firebase Emulator Setup Guide

Local development environment for FiscalCompass using Firebase Emulators.

---

## 📋 Quick Start

### Prerequisites

- Node.js (v16+)
- Java JDK (v11+)
- Firebase CLI

```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login to Firebase
firebase login
```

### Initialize Emulators (One-Time Setup)

```bash
# In project root
firebase init emulators

# Select: Authentication, Firestore, Functions
# Use default ports: Auth(9099), Firestore(8080), Functions(5001), UI(4000)
```

---

## 🔧 Configuration

### 1. Firebase Config

**`firebase.json`** - Already configured in repository:

```json
{
  "firestore": {
    "rules": "firestore.rules. dev"
  },
  "emulators": {
    "auth": { "port": 9099, "host": "0.0.0.0" },
    "firestore": { "port": 8080, "host": "0.0. 0.0" },
    "functions": { "port": 5001, "host": "0.0. 0.0" },
    "ui": { "enabled": true, "port": 4000, "host": "0.0. 0.0" }
  }
}
```

**`firestore.rules. dev`** - Permissive rules for development:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

> ⚠️ **Never use these rules in production! **

### 2. Android App Setup

The app is already configured with `dev` and `prod` flavors:

- **`dev`** → Uses emulators
- **`prod`** → Uses Cloud Firebase

**Key files (already in repo):**
- `app/build.gradle. kts` - Build variants with emulator config
- `app/src/dev/AndroidManifest.xml` - Cleartext traffic allowed
- `app/src/dev/res/xml/network_security_config.xml` - Network security config
- `app/src/main/java/com/fiscal/compass/di/FirebaseModule.kt` - Emulator connection logic

### 3. Firebase Console Setup

Add dev package to your Firebase project:

1.  Firebase Console → Project Settings → Add Android App
2. Package name: `com.fiscal.compass. dev`
3. Download updated `google-services.json` (contains both prod and dev)
4. Replace `app/google-services.json`

---

## 🚀 Running Emulators

### Start Emulators

```bash
# Basic start
firebase emulators:start

# With data persistence (recommended)
firebase emulators:start --import=./emulator-data --export-on-exit=./emulator-data
```

**Access Emulator UI:** http://localhost:4000

### Setup ADB Port Forwarding

**Required for Android emulators/devices:**

```bash
adb reverse tcp:9099 tcp:9099
adb reverse tcp:8080 tcp:8080
adb reverse tcp:5001 tcp:5001
```

### Build and Run App

```bash
# Build dev variant
./gradlew clean installDevDebug

# Or in Android Studio:
# Build Variants → Select "devDebug" → Run
```

## 🧪 Testing Functions

### Example Function (`functions/index.js`):

```javascript
const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

exports.getUserStats = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'Auth required');
  }

  const expenses = await admin.firestore()
    .collection('users')
    .doc(context. auth.uid)
    .collection('expenses')
    .get();

  return {
    userId: context.auth.uid,
    expenseCount: expenses.size
  };
});
```

### Test:

```bash
# HTTP: http://localhost:5001/your-project/us-central1/functionName
# Or use Emulator UI Functions tab
```

---

## 🔍 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Connection timeout** | Run `adb reverse` commands, ensure emulators are running |
| **Cleartext not permitted** | Verify `app/src/dev/AndroidManifest.xml` has `usesCleartextTraffic="true"` |
| **BuildConfig error** | Ensure `buildConfig = true` in `build.gradle.kts`, clean and rebuild |
| **Package name mismatch** | Add `com.fiscal.compass.dev` to Firebase Console |
| **Permission denied (Firestore)** | Check `firestore.rules.dev` is set in `firebase.json` |
| **Port in use** | Kill process on port or change port in `firebase.json` |

---

## 📌 Daily Workflow

```bash
# 1. Start emulators
firebase emulators:start --import=./emulator-data --export-on-exit=./emulator-data

# 2.  Setup port forwarding (in another terminal)
adb reverse tcp:9099 tcp:9099 && adb reverse tcp:8080 tcp:8080

# 3. Build and run
./gradlew installDevDebug

# 4. Access Emulator UI
# http://localhost:4000
```

---

## 🔗 Quick Reference

### Ports

- **Emulator UI:** http://localhost:4000
- **Auth:** 9099
- **Firestore:** 8080
- **Functions:** 5001

### Build Variants

- **devDebug** → Emulators
- **prodDebug** → Cloud Firebase

### Key Commands

```bash
# Start emulators
firebase emulators:start

# Port forwarding
adb reverse tcp:9099 tcp:9099 && adb reverse tcp:8080 tcp:8080

# Build dev
./gradlew installDevDebug

# Build prod
./gradlew installProdRelease

# Clear emulator data
rm -rf . firebase/ emulator-data/
```

---

## 📚 Resources

- [Firebase Emulator Suite Docs](https://firebase.google. com/docs/emulator-suite)
- [Connect Android to Emulators](https://firebase.google.com/docs/emulator-suite/connect_and_prototype)

---

**Last Updated:** 2025-01-15