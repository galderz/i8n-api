[![Build Status](https://travis-ci.com/galderz/i8n-api.svg?branch=master)](https://travis-ci.com/galderz/i8n-api)

# Requirements

These are requirements to be met by APIs:

* Each high level data structure (e.g. map, search, ...etc) should provide sync, dependency-free async and Reactive (Rx) APIs.

* All Rx APIs should build on top of dependency-free async APIs.
This makes Rx APIs simple facades that are easier to integrate with other async APIs and offer more capabilities.  

* All APIs should strive to offer embedded and remote implementations.
In some cases, remote implementations might not be doable due to lack of direct cross-platform representations, e.g. Java lambdas. 
In such cases, to achieve location-independent APIs might require finding ways to define abstractions in a platform independent way.
  * For example, a possible cross-platform equivalent of Java lambdas could be Javascript functions or lambdas?
  * Or alternatively, lambdas could be executed client-side rather than server side.
  However, this would require client-side locking (or transactions), if the lambdas belong to atomic operations. 


# Design

These are the design principles guiding this API design:

* An API will never be perfect. 
So the dummy APIs offered in this repo support versioning from the start to make it easy to evolve them over time.

* APIs are loaded using the service loader pattern which lookup high level APIs, e.g. `v1.ApiMap` or `ApiAsyncMap`.
Service loader does not directly load the API instance, but it loads a factory that creates instances of that API.
This makes it easy to load multiple instances of the same API in the same classloader, e.g. doing clustering.

* From a user perspective, the user simply says: "I want a version of X of map" and it provides either the embedded or remote implementation as dependency.
The API underneath uses service loader to find a factory that implements that version of the map and invokes it. 

* It assumes that given a high level API, e.g. `v1.ApiMap`, the user will only load one implementation at the time.
For example, for `v1.ApiMap`, it would load either the embedded or remote implementation, but not both at the same time.

* There's nothing stopping the user from loading two versions of the same API, e.g. `v1.ApiMap` and `v2.ApiMap`.
This is not desirable but a solution for this has not yet been implemented, but it's the in the works.

* Loading different APIs should be allowed though. 
For example, a user should be able to load a map and query API at the same time.


# Rules for consistency

* API/impl versions, e.g. v1, v2...etc, should only used in artifact and package names, not in class names.
This does not apply to use case modules.
Doing so makes it easy for users to switch between different versions of an API just by changing package names.
This reduces number of changes compared to having versions in class names since class names might be used more wide spread.
Also, removing versions for class names avoids polluting API descriptions.

* API module names follow this convention:

    [prefix]-[api-name]-[optional async/rx]-v[number]-[embedded/remote]

* Implementation module names follow this convention:

    [prefix]-[api-name]-[optional async/rx]-v[number]-[embedded/remote]-impl

* Package names follow this convention:

    [prefix].api.[api-name].[optional async/rx].v[number].impl.[embedded/remote]


Rules for creating a new API version:

* Adding new methods does not require creating a new version of the interface.
Old clients are unaffected since they would not call this new method.
Problem would be for implementors of the old interface, adding a new method could be problematic.
However, with Java 8 and 'default' keyword, any method can be added without affecting implementors.

* Changing return type, number of parameters or type of parameters. 

All APIs names start with `Dummy` to make it clear that these are just dummy examples of potential APIs.
They are not intended as final API products, but just as examples. 


# Dummy APIs

None of the data structure APIs in this repo are meant to be final.
They're just mere examples of what they could look like.
It'd be up to individual teams to work on individual APIs and refine.
And as said above, an API will never be perfect, so it's fine to be bold and try things.
With experience you will learn what works and what doesn't.

* DummyMap v1 is a location-independent, key/value API with put returning previous value associated with key.
Most of the time, users want to store a value but don't care about previous value.
As implementors, we have to stick to the API, so we have to calculate previous value which might be used.
Calculating previous value might be costly, requiring an extra RPC or visit to the cache store.
This was an actual mistake we made in the original Infinispan API.

* DummyMap v2 is a location-independent, key/value API which fixes put to not return previous value.
We want the users to continue using `put()` since that's the most idiomatic way to simply store a value.
This is better than getting those users to use a different method that stores a value but does not return previous value.
Changing the return value is an incompatible API change, so instead let's move those users to a new API version.
This API also adds ability to process all values stored in the map using Java Stream API.

* DummyMap v3 is an embedded-only API that offers a extra Java lambda-based operation.
It is designed as embedded-only is because there's no easy way to transform Java lambdas into functions in other languages.

* DummyAsyncMap v1 is a location-independent, async-only API.
Barring Reactive Streams dependency (which is part of Java 9), it has no other dependencies.
This means that it offers an async API that works for both operations with single or multiple returns without external dependencies.
Higher level async-only APIs, such as Rx, would build on top of this API.
Those users interested in using async APIs are often interested in using them exclusive.
That is, they don't want to mix up up sync and async APIs.
This is why the API is standalone and independent from the sync APIs.
One example user here would be Vert.x.

* DummyRxMap v1 is a location-independent, async-only API.
It offers an RxJava2 based API which is a higher level async-only API compared with DummyAsyncMap.
Compared with Reactive Streams, RxJava2 it offers a richer API.
For example, RxJava2 offers combinators and it's easier to compose with other reactive/async data streams. 
The DummyRxMap implementations are simply wrappers around the DummyAsyncMap offering little maintenance effort. 

* DummySearch v1 is a location-independent, search API.
This is an demo sync API for executing queries and retrieving results.


# TODO

- [ ] Search Rx API
- [ ] Avoid loading multiple versions of same API
- [ ] Counter API
- [ ] MultiMap API
- [ ] Lock API
- [ ] Listeners
- [ ] Management/Monitoring APIs
- [ ] Lifecycle APIs
- [X] Service loader - load a factory instead of individual API instances.
So that you can load multiple instances of an API (e.g. cluster functionality).
- [X] Search API
- [X] Search Async API
- [X] Rx Map API 
- [X] Async Map API - CompletionStage
- [X] Async Map API - Publisher
