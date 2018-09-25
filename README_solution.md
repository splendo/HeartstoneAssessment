This app uses MVP pattern, and the main model is represented by a data layer in charge of loading data about HeartStone cards from different sources.
The main Data Model is Card.

The Data layer is accessed through DataRepository class(es), which gathers different DataSource(s) and takes care of fetching/caching/falling back of the data, of behalf of the presenter.

The main DataSources are:

- Remote API (RemoteDataSource)
- DB Cache (DatabaseDataSource)

And I added 1 more (mainly to help prototype at beginning, but kept using):
- LocalDataSource: to embed the full jsons, including a fix to make it better parsable (LocalDataSource)

The app has a Splash Screen where the Data is retrieved in the following order: online (Github raw json file) -> DB (after first caching) -> Local copy of json file (embedded)
The data is stored in the DB after the first run, so other screens can use only that as a source. And can work offline
Thanks to this order, data can be refreshed every time the app is open (while remembering the state of favorites)
The second screen gets all the items from DB and shows the card in a grid, as requested.
The data is paginated, as the original set is big and the DB source easily allows for it. Some other modules are coded to allow it too, but there is no backend to support it.
The third screen, after selection of an item, shows the detailed view of a card in a ViewPager: I decided to load everything at the beginning, so it slows a bit upon selection but is faster later.

## GENERICS on Data Layer

The only DataSource that needs to be model-specific is RemoteDataSource, to better take advantage of Retrofit's capabilities, while the others can be generic.
I like the Data layer to stay generic, so it could support more data models, as typically the needs of this layer are common (fetching + caching) and the data models in an app typically many.
I created the package 'mappers' to easily enable/disable DataSources: is enough to define or omit an entry in one of the 'mappers' classes to toggle things like embedding the data in local assets, or fetching from remote api, etc..
This mappers could also be 
This is a matter of preference, and might be seen as an overkill for a project of just 1 model: I can of course easily avoid doing so if not appreciated.

## GENERICS elsewhere

I used generics, less heavily, also on other layers and tried to pay attention to divide the responsibilities between more and less typed classes.
Listing items for example is a common feature (as well as pagination and list restoring), while getting data from different sources (or a different order), displaying different UI (grid instead of list), etc.. are features that need custom classes.

This class has to adhere to the correct mvp contract (I called it Binding), by implementing GenericMVPBinding interfaces either directly or indirectly, to work properly.

There are several base classes - both for Views and Presenters - that helps defining less common code for new data models (such as BaseItemPresenter, ItemListPresenter, ItemListFragment, DataSource, etc..) - but also a specific handling of Cards in dedicated extending Classes that respects the "Binding".

## TO IMPROVE

I would have liked to improve the detailed view, to list differently properties that are less commonly filled in the dataset and give more importance to fields that every card has.
On the code style, I am sure a codereview would show me some methods or classes to move or optimize, but I am happy enough with the separation there currently is.
I put some TODO in the code, both for myself and towards the reviewer, to show that I know about some improvements but I think are outside the scope of the assignment.
Since I added the relations between Card, Mechanic and Class, fetching so many items started to slow the app down: is still responsive but the performances could improve by better parceling these items from screen to screen instead of using DB.
(especially from cards grid to detailed view)
I noticed in the data some html fields as well as newline characters: I planned on processing these text before displaying and replace with <br /> chars (and in general make better use of html tags to compose fields to display).. but this remained aso a quick TODO.

I don't mind being challenged or questioned about these and other parts in a technical follow up interview.


## STACK & CODESTYLE

# LIBS: I used Spork instead of the classical Butterknife/Dagger because is a lib made by an ex colleague and we use it in Elastique.
I Didn't notice before the assignment that 4.0 is finally out, which gives more freedom for Injection in general, which unfortunately I didn't have time yet to get into.
(Some areas of this code could greatly benefit from injection)

#  DB & persistance
I never used DBFlow but sounded interesting to try it out for this assignment. I liked how easy and readable it feels (but I had some issues initially to compile the relations in DB)
In folder tools, there is an sh script to copy the DB of a connected phone to pc, so to inspect it (without adding code to the app itself)
It could also be used to embed the filled up DB file back in the app, which would allow to remove completely the LocalDataSource module (as well as cards.json file)

# Documentation
On a best effort basis, trying to avoid redundancy.
As a rule in this project, what is never commented are simple getters/setters, very intuitive method names, @override classes (cause the original is commented), annotated view/res variables, semantically intuitive methods if without params.
Variables are commented with a 1-line comment (if fits) while methods on 3+ lines.
Some annotated 'v' variables are usually uncommented and on 1 line

#  Code Style
I am not strict on formatting but in general I prefer one-liners, if are readable enough.
I like ternaries, using lambdas as much as possible and java8 streams (couldn't use the latter though) and I like the syntax of RX apps, but I haven't used that either in the end.
I used 'v' as a prefix instead of 'm' in case of Views from xml
