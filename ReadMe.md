 # CheckPass

>CheckPass is an Android app designed to streamline passenger check-in for buses by enabling agents to quickly scan QR codes on tickets, instantly retrieving and displaying passenger details for check-in or cancellation.

## Key Features

- **Dual Scanning Support:**
    - **PDA Devices**: Uses built-in scan hardware via a `BroadcastReceiver` implementation.
    - **Camera Phones**: Utilizes CameraX and MLKit for QR code scanning, leveraging `setImageAnalysisAnalyzer` for live code detection.

- **Unified Codebase:**
    - Scanning logic is abstracted via the `ScannerManager` interface:
      ```kotlin
      interface ScannerManager {
          val scannedCodes: SharedFlow<String>
          fun startScan()
          fun stopScan()
      }
      ```
    - Device detection at runtime chooses the appropriate implementation (`BroadcastScannerManager` for PDAs, `CameraScannerManager` for camera phones).

- **Reactive Scanned Code Handling:**
    - QR codes scanned are delivered using `SharedFlow<String>`, allowing the app to respond immediately and display passenger dialogs.

- **Camera Permissions & Libraries:**
    - Camera usage on phones is managed with explicit permissions in the manifest.
    - The Google Accompanist library is used for elegant runtime permission handling.

## How It Works

1. **Agent scans the QR code** on the passenger's ticket (either with the PDA's physical button or the phone's camera).
2. **Passenger details appear in a dialog**; the agent can check in or cancel.
3. **Core logic adapts to device type** automatically.

### App Demo
| <img src="media/check.gif" width="250"/> |
|:----------------------------------------:|

## Code Highlights

- **BroadcastReceiver** for handling hardware scan events on PDA devices.
- **CameraX’s setImageAnalysisAnalyzer** for real-time QR detection on camera phones.
- **Google Accompanist Permissions** for runtime camera access.
- **SharedFlow** for propagating scanned QR codes to the UI.

CheckPass ensures a consistent, fast, and reliable check-in experience across device types by abstracting scanning logic and handling permissions gracefully.
