# Heartstone Assessment

This is a test project. You can find the requirements in [TASK.md](TASK.md).  
**Note:** There was a requirement to use MVVM presentation pattern.  
**Note:** [KLM Houses](https://play.google.com/store/apps/details?id=com.klm.mobile.houses) app has been updated design recently, so I've used the most matching version [1.8.0](https://www.apkmonk.com/download-app/com.klm.mobile.houses/2_com.klm.mobile.houses_2016-10-07.apk/) as a reference.

## Android application
### Project structure
Project consists of one Gradle module.  
Main sources are located under [app/src/main](Cards/app/src/main).  
Packages are organized by screen:
* [me.grapescan.cards.ui.list](Cards/app/src/main/java/me/grapescan/cards/ui/list) - grid list of cards
* [me.grapescan.cards.ui.preview](Cards/app/src/main/java/me/grapescan/cards/ui/preview) - card preview with swipe support
* [me.grapescan.cards.ui.info](Cards/app/src/main/java/me/grapescan/cards/ui/info) - card details screen
  
Unit tests are located under [app/src/test](Cards/app/src/test/java/me/grapescan/cards).

### Technology stack
* Programming language - **Kotlin**  
*Kotlin enables me to write less code and also supports many things that would look overcomplicated in Java.*

* Thread management - **Kotlin Coroutines**  
*Coroutines are great for running async code in background. Also they are promoted by Google and have support in Architecure components.*

* Dependency provisioning - **Koin**  
*Koin intensively uses language features of Kotlin to make dependency declaration and injection as fluent as possible. It can be less powerful than Dagger for some edge cases, but for small and medium sized projects it's good enough.*

* Presentation pattern - **MVVM with Android Architecure Components**  
*I've implemented MVVM by Google recommendations with LiveData, ViewModel and other architecture components.*

* Image loading - **Glide**  
*Glide is one of several options for image loading. I've chosen it because I have more experience with it, than with Picasso or Fresco.*

* Storage - **Shared Preferences**  
*There is no need to design a database for this scale of data. Ideally, data processing like sorting and filtering should be implemented by backend, so I need to store locally only favorites and probably some cache.*

* JSON parser - **GSON**  
*There are alternatives like Moshi and Jackson, but I'm usually using GSON in projects and didn't notice any issues with it.*

* Unit tests - **JUnit**, **Mockito**  
*It is the only mainstream choice to write unit tests.*


## Backend
Project is implemented with [Ktor](https://ktor.io/) framework.  
Source code is located under [CardsAPI](CardsApi).  
Currently it is just returning **cards.json** content.  
It is not finished because of limited time, but I will finish it anyway, because Ktor looks really interesting.

License
----

Source code: MIT  
All rights to Heartstone content belongs to the legal owners.
