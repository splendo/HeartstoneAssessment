# Stavros Tsikinas (iOS Developer)
Hi! The source code provided is an attempt I did to build an app based on **Hearthstone Cards**. The following *Markdown* file is meant to explain the app. 

## App UI/UX
The application is based on the KLM Houses app to an extent. There is a collection view with cells showing:

 - Card Image
 - Card Title
 - Favorite Button
 
There are 2 collection screens on the app:
 1. All Cards
 2. Favourite Cards

In both collections, the user is able to tap the **magic** Hsiao Featured button, that filters the cards and shows only the Hsiao's favourite ones.

The **Detail View** presents the selected card with the description and also the *type*. The user is able to add/remove the card to favourites, via a UIBarButtonItem.

## Data
The data (cards) are retrieved from the *"cards.json"* file that was provided in the master branch. An assumption that all card categories are known, therefore the *"CardsResponse"* struct is constructed the way it is.

## Persist Data
The cards data is persisted via the file explained in the previous section. In order to persist favourite cards, **CoreData** is used. The choice was based on the following:
 - is a native framework
 - is easier expandable to large DBs
 - could be expanded to store large amount of data and/or images, compared to **UserDefaults** (scalability)

## Version Control
Trunk-based development was followed, since the project consisted of 1 developer and the app was starting up.
