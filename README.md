# Namma-Mela - Digital Drama Box Office

Namma-Mela is an Android application for managing village drama and theater shows digitally. The app allows users to view the current play, check cast details, reserve seats, and post comments on a fan wall. It also includes an Admin Mode for managing show information and preparing the app for a new show.

## Project Overview

This project is built as a complete Android Studio application using modern Android development tools. It follows MVVM architecture and stores app data locally using Room Database.

The app supports two roles:

- User
- Admin

Normal users can browse show details, reserve seats, and add fan comments. Admin users can update play details, update cast details, reset seats, and clear fan comments.

## Tech Stack

- Language: Kotlin
- UI: Jetpack Compose
- Design System: Material 3
- Architecture: MVVM
- Database: Room Database
- State Management: StateFlow
- Navigation: Navigation Compose
- Image Loading: Coil
- Build Tool: Gradle
- Minimum SDK: 24
- Target SDK: 36
- App Version: 1.4

## Database

The project uses only Room Database. Firebase is not used in the current version.

Room tables:

- `play`
- `cast_members`
- `seats`
- `comments`

Main entities:

- `PlayEntity`
- `CastEntity`
- `SeatEntity`
- `CommentEntity`

Database file:

```text
namma_mela.db
```

## Main Modules

### Home Screen

Displays tonight's play details:

- Poster image
- Play name
- Duration

Admin users can edit these details from Admin Mode.

### Cast Screen

Displays cast information:

- Actor name
- Role
- Actor image

Admin users can update cast details from Admin Mode.

### Seat Booking Screen

Displays a 5 x 8 seat grid.

Seat colors:

- Green: Available
- Red: Reserved

Seat booking is stored in Room Database, so reserved seats remain reserved after app restart.

### Fan Wall Screen

Allows users to:

- Add comments
- View comments

Fan comments are stored in Room Database.

### Admin Mode

Admin Mode is protected using a demo Admin PIN.

Admin can:

- Edit play name
- Edit duration
- Edit poster URL
- Edit cast name, role, and image URL
- Save current changes
- Save as a new show and reset seats/fan comments
- Clear fan wall comments

Demo Admin PIN:

```text
2468
```

## User And Admin Access

### User Mode

Users can access:

- Play
- Cast
- Seats
- Fans

Users cannot access Admin Mode.

### Admin Mode

Admins can access:

- Play
- Cast
- Seats
- Fans
- Admin

The Admin tab and edit buttons are hidden from normal users.

## Project Structure

```text
app/
  src/main/
    AndroidManifest.xml
    java/com/example/internship_project/
      MainActivity.kt
      data/
        CastEntity.kt
        CommentEntity.kt
        NammaMelaDao.kt
        NammaMelaDatabase.kt
        NammaMelaRepository.kt
        PlayEntity.kt
        SeatEntity.kt
        ServiceLocator.kt
      ui/
        admin/
          AdminEditScreen.kt
          AdminViewModel.kt
        auth/
          AuthViewModel.kt
          LoginScreen.kt
        cast/
          CastScreen.kt
          CastViewModel.kt
        fanwall/
          FanWallScreen.kt
          FanWallViewModel.kt
        home/
          HomeScreen.kt
          HomeViewModel.kt
        seats/
          SeatScreen.kt
          SeatViewModel.kt
        theme/
          Color.kt
          Theme.kt
          Type.kt
```

## How To Run

1. Open Android Studio.
2. Select **Open**.
3. Choose the project folder:

```text
C:\Users\user\AndroidStudioProjects\internship_project
```

4. Wait for Gradle Sync to finish.
5. Select an emulator or connected Android device.
6. Click **Run**.

## Build Commands

Debug build:

```powershell
.\gradlew.bat :app:assembleDebug
```

Run unit tests:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

## Testing Done

Tested features:

- App builds successfully
- Login as user
- Login as admin using PIN
- Admin tab hidden for users
- Admin tab visible for admin
- Play details update from Admin Mode
- Cast details update from Admin Mode
- Seat booking persists using Room
- Reserved seats cannot be booked again
- Fan comments are saved using Room
- Empty fan comments are prevented
- New show reset clears seats and fan comments
- Logout returns to login screen

## Future Enhancements

- Replace demo Admin PIN with Firebase Authentication or backend login
- Add real payment integration
- Add QR ticket generation
- Add show date and time management
- Store multiple shows instead of one active show
- Add cloud sync for multiple devices

## Conclusion

Namma-Mela is a digital box office app for village theater shows. It provides a simple user experience for viewing drama details, booking seats, and posting fan comments. It also gives admins control over play details, cast details, and show reset actions. The project demonstrates Kotlin, Jetpack Compose, MVVM, Room Database, StateFlow, Navigation Compose, and Material 3 in a complete Android application.
