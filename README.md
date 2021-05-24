# Contacts List

Greetings! This app allows you to register your contacts to later call or send emails to them.


| Home Screen      | Register Screen      |
|------------|-------------|
|   <img src="https://user-images.githubusercontent.com/66192808/116832889-d6a76d80-ab8c-11eb-80e5-1a48cd3e9c4c.gif" alt="drawing" width="350"/>|<img src="https://user-images.githubusercontent.com/66192808/117049933-2ac86400-aceb-11eb-9054-13fe7cf991af.gif" alt="drawing" width="350">|

I started this app by simply copying from [Kotlin MVVM tutorial](https://www.youtube.com/watch?v=v2yocpEcE_g) to learn a little bit more about mvvm, live data and data binding.
Then, I decided to make some changes. First, the app would have two screens: one for listing all the contacts and the second for registering and updating. The main obstacle was that I didn't know to work with a single view model class with 2 or more activities/fragments. I didn't know that my live data was refreshed since my view model class was being re instantiated. And before I could know more about [Communicating with fragments](https://developer.android.com/guide/fragments/communicate), I removed all ``@Binds`` and XML binding data stuff and completely change the purpose of the first tutorial.

After many changes in my app, I tried to learn new concepts: dependency injection, a more refined architecture components, native android functionalities and lots of tests.
For all this, I mixed a bit all these 6 links:
1. [Dependency injection in Android (You must read this before going to DI)](https://developer.android.com/training/dependency-injection?hl=en-us)
2. [Android Studio (Kotlin) Simple Dagger Hilt Room Database MVVM](https://www.youtube.com/watch?v=EMqlwjdNKcw&t=2007s)
3. [Testing on android playlist](https://www.youtube.com/playlist?list=PLQkwcJG4YTCSYJ13G4kVIJ10X5zisB2Lq),
4. [News APP](https://www.youtube.com/watch?v=kWAuZDIRdi8&t=549s)
5. [Espresso setup instructions](https://developer.android.com/training/testing/espresso/setup)
6. [Android's official dagger page](https://developer.android.com/training/dependency-injection/dagger-android?hl=en-us)
7. [Dagger's official website](https://dagger.dev/)
8. [Android Developers architecture-samples](https://github.com/android/architecture-samples)      
  
**Dagger-Hilt annotations overview:**      
```kotlin
@HiltAndroidApp
```
According to the official docs: *"All apps using Hilt must contain an Application class annotated with @HiltAndroidApp. @HiltAndroidApp kicks off the code generation of the Hilt components and also generates a base class for your application that uses those generated components."* Basically, all you have to do is create a class extending Application() and annotating it with: 
![cl2](https://user-images.githubusercontent.com/66192808/119385206-311f7f80-bc9c-11eb-8c46-ab1c1835debe.PNG)  
```kotlin
@AndroidEntryPoint
```
This annotation allows you to inject members in the entire application:    
![cl3](https://user-images.githubusercontent.com/66192808/119391224-7a73cd00-bca4-11eb-8492-7f91c3849366.PNG)  
```kotlin
@Module
```
A module is a container containing everything that we want to inject (dependencies) in our application. It can be a retrofit, database or any model class instance:

```kotlin
@InstallIn(SingletonComponent::class)
```
This is the scope where the components are going to be installed. In this case, on the singleton scope. The reason for this is because the singleton scope is the highest on the component hierarchy on Hilt-Dagger meaning that our dependencies will live through the entire application lifecycle.  
```kotlin
@Singleton
```
This annotation means that this object will be instanciante only once  
```kotlin
@Provides
```
This annotation means that we are returning an object that will satisfy a dependency when request (through Inject annotation)
![cl1](https://user-images.githubusercontent.com/66192808/119385199-2fee5280-bc9c-11eb-95df-1a63378339a0.PNG)  

**Lets take a closer look on this module function:**  
![cl4](https://user-images.githubusercontent.com/66192808/119404661-7fda1300-bcb6-11eb-8dc2-949dd932a38e.PNG)  
Here, we are declaring that when we need a ContactDAO instance, we will return ``contactsListDatabase.ContactDAO()``. Thats all what it means. We don't even need to invoke this function because Hilt-Dagger will do that for us during compilation time. But, if we don't invoke this function, where does it get called? See on screenshot below:  

![cl5](https://user-images.githubusercontent.com/66192808/119404670-810b4000-bcb6-11eb-8de9-7dcdd53da63d.PNG)  

![cl6](https://user-images.githubusercontent.com/66192808/119404671-81a3d680-bcb6-11eb-86f1-5c4d99002757.PNG)  
This is the class that will provide the dependencies when we request it. If its the first time that you are writing your module, all your functions will show a message saying that piece of code is unused. Only after building your app, it displays that its being invoked

**Now that the module and our dependencies are correctly provided, lets use it**
![cl7](https://user-images.githubusercontent.com/66192808/119405749-2377f300-bcb8-11eb-9569-8312d6b06298.PNG)
And here is where we are injecting the dependency! In this example, we are injecting a dependency on the constructor, but we could do it as a field annotation (on a attribute)




