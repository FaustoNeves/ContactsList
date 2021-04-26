# Contacts List

Greetings! This app allows you to register your contacts to later call or send emails to them.


I started this app by simply copying from [Kotlin MVVM tutorial](https://www.youtube.com/watch?v=v2yocpEcE_g) to learn a little bit more about mvvm, live data and data binding.
Then, I decided to make some changes. First, the app would have two screens: one for listing all the contacts and the second for registering and updating. The main obstacle was that I didn't know to work with a single view model class with 2 or more activities/fragments. I didn't know that my live data was refreshed since my view model class was being re instantiated. And before I could know more about [Communicating with fragments](https://developer.android.com/guide/fragments/communicate), I removed all ``@Binds`` and XML binding data stuff and completely change the purpose of the first tutorial.

After many changes in my app, I tried to learn new concepts: dependency injection, a more refined architecture components and native functionalities of android.
For all this, I mixed a bit all these 5 links:
1. [Android Studio (Kotlin) Simple Dagger Hilt Room Database MVVM](https://www.youtube.com/watch?v=EMqlwjdNKcw&t=2007s)
2. [Testing on android playlist](https://www.youtube.com/playlist?list=PLQkwcJG4YTCSYJ13G4kVIJ10X5zisB2Lq),
3. [News APP](https://www.youtube.com/watch?v=kWAuZDIRdi8&t=549s)
4. [Android's official dagger page](https://developer.android.com/training/dependency-injection/dagger-android?hl=en-us)
5. [Android Developers github profile](https://github.com/android/architecture-samples)

Now the app has dagger-hilt dependency injection, unit and instrumented tests for repository, view models and database operations and a very basic github actions workflow and currently improving :)
