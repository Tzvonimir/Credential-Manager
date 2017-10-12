# Credential-Manager
Simple Android application for storing credentials

## Development

Before you can build this project, you must install and configure the following things on your machine:

1. [Android Studio 3.0][]: We use Android Studio 3.0 because we need gradle 3.0 or higher so we can use Java 8,
   there are other ways of using Java 8 in android studio but we decided to go with Android Studio 3.0.

This application has been made primarily to test:

1. [Room Persistence Library][]: Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
2. [ViewModel][]: The ViewModel class is designed to store and manage UI-related data so that the data survives configuration changes such as screen rotations.
3. [LiveData][]: LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components,
   such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.

[Android Studio 3.0]: https://nodejs.org/
[Room Persistence Library]: https://developer.android.com/topic/libraries/architecture/room.html
[ViewModel]: https://developer.android.com/topic/libraries/architecture/viewmodel.html
[LiveData]: https://developer.android.com/topic/libraries/architecture/livedata.html
