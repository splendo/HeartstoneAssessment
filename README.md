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
* Thread management - **Kotlin Coroutines, LiveData**
* Dependency provisioning - **Koin**
* Presentation pattern - **MVVM**
* Image loading - **Glide**
* Storage - **Shared Preferences**
* JSON parser - **GSON**
* Unit tests - **JUnit**, **Mockito**

## Backend
Project is implemented with [Ktor](https://ktor.io/) framework.
Source code is located under [CardsAPI](CardsApi).
Currently it is just returning **cards.json** content.
It is not finished because of limited time, but I will finish it anyway, because Ktor looks really interesting.

License
----

Source code: MIT
All rights to Heartstone content belongs to the legal owners.
