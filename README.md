## Heartstone Android by Niels Masdorp

### Architecture
This app is separated in 2 modules:

- app: Android module that is responsible for showing the actual app, it uses the MVP architecture for the presentation layer.
This module also contains the data providers
- domain: This is where all the business logic is located, it is a pure Java module with no Android related dependencies

In order to create a clear separation of concerns and to separate low level components (UI) from the high level components
(Entities and Use Cases) I have used the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
proposed by Uncle Bob.

### Third party libraries

In order to have proper dependency injection to tie all these components together I have chosen to use the
well known and community supported Dagger 2 library. For image loading I have chosen to use Glide, which is just a preference
over something like Picasso.

For logging I have used Jake Wharton's Timber. And lastly to create asynchronous streams I have used RxJava 2. For this project
I could have gone without since there is not a lot of data flowing through the application, but I chose to include it
to show that I am comfortable using it.

### Data

Cards: I have not chosen to host any data on a backend. I stored the JSON file in /assets and loaded it into memory.

Favorites: Are stored in Shared Preferences

Both data storage solutions are easily interchangeable with other solutions due to the architecture (e.g. implement storage interface
and bind new implementation in Dagger module)

### Filtering

Data repository accepts a request model with criteria for cards and is being used to query legendary cards with a certain
mechanic, this can of course be extended to support more criteria as well.

### Sorting

The request model also supports a sorting strategy (asc and desc), currently, this sorts the list by the name of the card
