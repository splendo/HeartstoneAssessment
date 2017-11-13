## Results
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/kdytbSfTa7U/0.jpg)](https://www.youtube.com/watch?v=kdytbSfTa7U)

# WARNING:
- I was unable to do the backend in time so paged data loading from backend is not implemented.
- I have used this assignment as chance to learn the Android Room persistence library, but it seems i've done it wrong:
    - Initial import can take **3 minutes**, because insert of ALL items is really not optimised and not paged.
    - I was unable to find the right way to fetch composite data objects from "joined" requests.
    
## General concept
![Concept](https://github.com/arnoid/HeartstoneAssessment/blob/master/concept.png?raw=true)

We have several definitions:
- UI: represents actors which consume data.
- Controllers: provide set of domain specific simple actions.
- UseCase: set of actions required by actor to be permormed and result.

So, for example, PullDataFromBackEndAndSave use case incorporates following actions:
- NetworkController:
    - Load data from backend
- Database controller
    - Save cards data
    - Map relations
    
Such approach helps us to follow SOLID principles. Also this helps us to concentrate on use-case testing, because controllers can be easily mocked and injected.

## 3rd Party libraries

Database - I have picked Android Room, because i see this assignment as perfect chance to learn something new. But it seems that I was far to unprepared for this. So it was a mistake. Next time I'd rather pick realm.io .

Network - Retrofit, simple stupid plus support of Rx.

Image loading - Picasso, simple stupid and fits for this case.

P.S. I wanted to use dependency injection, but it seems a bit an overkill for this project.

## Introduction

Hsiao here at Splendo is a very enthusiastic casual Hearthstone player. He is also a user of the KLM houses apps ([iOS](https://itunes.apple.com/nl/app/klm-houses/id371664245?l=en&mt=8) / [Android](https://play.google.com/store/apps/details?id=com.klm.mobile.houses&hl=en))

He wants you to build an app (iOS or Android) that has similar UI/UX. Similar way to go from the grid view to detail view, and also being able to scroll through the detail views like a carousel (hint: download the Houses app and have a look at how it works) but he wants the app to show Hearthstone card images.

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

You are free to choose the patterns and architectures to create this app, the requirements are :

### App

* Create the app for iOS (Swift) or Android (Java or Kotlin).
* Show the card images in a grid like the houses app
* when user click on a grid item , navigate to the card detail view where you can display more information regarding the card ( what you would like to show and how is up to you ), when in detail view the navigation to the next and previous card should be the same as the Houses App
* The user should be able to set a card as favourite and this info should be persisted when the app closes, how to show cards that are tagged as favourites and how to persist that information is up to you
* You can load the Card by creating an API or by loading in the JSON file directly from memory. Ideally, this data provider should enable the following options, listed from easy to hard:
  * filter by least the following fields: `type`, `rarity`, `classes`, and `mechanics`
  * return sorted results (for example, alphabetically sorted), supporting both ascending and descending
  * (optional when using remote API) return the results by pages (based on a page size request parameter), iterating over the pages are maintained by a cursor which is included in the response, this cursor is used in the subsequent request.


## What we would like to see

* Proper handling of asynchonous calls
* Clean code
* Relevant design patterns
* Java/Swift/Kotlin best practices
* UI should remain responsive during content loading
* Should you use 3rd party libraries and frameworks please motivate your choice
* Unit tests
* Writing a backend using Google AppEngine is a huge plus.

## Finally

To submit your result, fork this repository. When you are satisfied with your result, create a Pull Request. If you created a backend, make sure it is up and running somewhere for the duration of the review and tell us in the comments where to find it.

Good Luck!
