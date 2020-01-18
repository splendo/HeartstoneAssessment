# General remarks

The assignment talks about making a web app, since I'm an iOS developer I assume I needed it to implement it as an native iOS app. Due to this I did not make a back end for this assignment.

The assignments mentions a way to navigate between the detail pages to the next and previous card in the  KLM houses app, I could not find any of this behavior in the KLM houses app. I did implement a horizontal scrolling behavior for this.

I'm not really happy myself with the way I parsed the `cards.json` file, this is due to the default way json decoding works via the Decodable protocol, I would preferred to have a json source file that would have had an array of card sets instead of a json object with multiple name referenced card sets in it which would be more flexible if more card set are added and easier to parse. 

#  External libraries

## AlamoFire 
used this third party library for web calls, it is widely used in many apps and for that is often a safe bet to use in basic apps.

## Frameworks folder
this folder contains some files from my own library to just simplify some code, these repositories are private and as such I copied in the code 

