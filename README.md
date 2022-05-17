## COMMENTS:

I wrote the app using kotlin. I used MVVM as my design pattern (Android architectural components), and Dagger2 to inject dependencies into my ViewModels.

My initial idea was to mock a server inside the app using an intereceptor to intercept api calls.
In this scenario, the app would launch a request through retrofit, the mock server would intercept it, read the response from the database directly, and return it in the body.
The approach worked for getting all cards until I realised I couldn't read the request body that way since it can only be read once (according to the internet). That limitation would prevent me from deciding responses based on the passed filters in the request body so I decided to drop the approach and read directly from the database in my ViewModels. I left the code for the retrofit implementation in the app anyway since it is relevant to the assignment (in MainViewModel.kt and NetworkModule.kt) .


When the app is launched for the first time, it populates the local database(https://objectbox.io/) with data from the JSON file.
After that, the main fragment has a ViewPager with tabs contaning a grid for each card set, with filtering available through a toolbar button.

Filtering and sorting works through objectbox as you would expect in a relational database, with the benefit of not having to write queries explicitly. I used the same library to save filters and favourite cards.




PS: one used library that I felt needed justification was the RangeSeekBar used to filter by rarity. The official android "Slider" class is only included in the alpha material designn library and seems to still be lacking features compared to others, so I decided to use a 3rd party library.


I also added some UI tests for checking favourites, sorting, and filtering. (Espresso and JUnit)

## Introduction

Hsiao here at Splendo is a very enthusiastic casual Hearthstone player. He is also a user of the KLM houses apps ([iOS](https://itunes.apple.com/nl/app/klm-houses/id371664245?l=en&mt=8) / [Android](https://play.google.com/store/apps/details?id=com.klm.mobile.houses&hl=en))

He wants you to build a web app that has similar UI/UX. Similar way to go from the grid view to detail view, and also being able to scroll through the detail views like a carousel (hint: download the Houses app and have a look at how it works) but he wants the app to show Hearthstone card images.

We have supplied you with a json file (`cards.json`) containing all the Heartstone cards currently available.

Hsiao is especially interested in the app showing `Legendary` cards with the `Deathrattle Mechanic`, below are examples of such a cards :

```json
{
   "cardId": "FP1_014",
   "name": "Stalagg",
   "cardSet": "Naxxramas",
   "type": "Minion",
   "rarity": "Legendary",
   "cost": 5,
   "attack": 7,
   "health": 4,
   "text": "<b>Deathrattle:</b> If Feugen also died this game, summon Thaddius.",
   "flavor": "Stalagg want to write own flavor text.  \"STALAGG AWESOME!\"",
   "artist": "Dany Orizio",
   "collectible": true,
   "elite": true,
   "playerClass": "Neutral",
   "howToGet": "Unlocked in The Construct Quarter, in the Naxxramas adventure.",
   "howToGetGold": "Crafting unlocked in The Construct Quarter, in the Naxxramas adventure.",
   "img": "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png",
   "imgGold": "http://wow.zamimg.com/images/hearthstone/cards/enus/animated/FP1_014_premium.gif",
   "locale": "enUS",
   "mechanics": [
     {
       "name": "Deathrattle"
     }
   ]
}
```

and

```json
{
   "cardId": "CFM_902",
   "name": "Aya Blackpaw",
   "cardSet": "Mean Streets of Gadgetzan",
   "type": "Minion",
   "rarity": "Legendary",
   "cost": 6,
   "attack": 5,
   "health": 3,
   "text": " <b>Battlecry and Deathrattle:</b> Summon a <b>Jade Golem</b>.",
   "flavor": "Though young, Aya took over as the leader of Jade Lotus through her charisma and strategic acumen when her predecessor was accidentally crushed by a jade golem.",
   "artist": "Glenn Rane",
   "collectible": true,
   "elite": true,
   "playerClass": "Neutral",
   "multiClassGroup": "Jade Lotus",
   "classes": [
     "Druid",
     "Rogue",
     "Shaman"
   ],
   "img": "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_902.png",
   "imgGold": "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/CFM_902_premium.gif",
   "locale": "enUS",
   "mechanics": [
     {
       "name": "Jade Golem"
     },
     {
       "name": "Battlecry"
     },
     {
       "name": "Deathrattle"
     }
   ]
}
```

## Assignment

You are free to choose the patterns and architectures to create this web app, the requirements are :

### Backend

* Create an API backend (Java, Kotlin or TypeScript) allowing you to get card information for at least legendary deathrattle cards.
* The API should also support filtering based on relevant request parameters. Ideally, the API should enable the following, listed from easy to hard:
  * filter by least the following fields: `type`, `rarity`, `classes`, and `mechanics`;
  * return sorted results (for example, alphabetically sorted), supporting both ascending and descending;
  * (optional) return the results by pages (based on a page size request parameter), iterating over the pages are maintained by a cursor which is included in the response, this cursor is used in the subsequent request.

### Web Application

* Create the web app using React + TypeScript.
* Show the card images in a grid like the houses app.
* when user click on a grid item , navigate to the card detail view where you can display more information regarding the card ( what you would like to show and how is up to you ), when in detail view the navigation to the next and previous card should be the same as the Houses App.
* The user should be able to set a card as favourite and this info should be persisted when the app closes, how to show cards that are tagged as favourites and how to persist that information is up to you.


## What we would like to see

* Proper handling of asynchonous calls
* Clean code
* Relevant design patterns
* React + TypeScript best practices
* UI should remain responsive during content loading
* Should you use 3rd party libraries and frameworks please motivate your choice
* Well-written unit tests 
* High code coverage
* Serve the backend in Google Cloud Platform using Cloud Functions is a plus

## Finally

To submit your result, fork this repository. When you are satisfied with your result, create a Pull Request. Make sure your backend is up and running somewhere for the duration of the review and tell us in the comments where to find it.

Good Luck!
