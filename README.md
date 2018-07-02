Some rules for consistency:

* API/impl versions, e.g. v1, v2...etc, should only used in artifact and package names, not in class names.
This does not apply to use case modules.

Rules for creating a new API version:

* Adding new methods does not require creating a new version of the interface.
Old clients are unaffected since they would not call this new method.
Problem would be for implementors of the old interface, adding a new method could be problematic.
However, with Java 8 and 'default' keyword, any method can be added without affecting implementors.

* Changing return type, number of parameters or type of parameters. 

Dummy APIs demonstrated:

* DummyMap v1 is a location-independent, key/value API with put returning previous value associated with key.
Most of the time, users want to store a value but don't care about previous value.
As implementors, we have to stick to the API, so we have to calculate previous value which might be used.
Calculating previous value might be costly, requiring an extra RPC or visit to the cache store.
This was an actual mistake we made in the original Infinispan API.

* DummyMap v2 is a location-independent, key/value API which fixes put to not return previous value.
We want the users to continue using `put()` since that's the most idiomatic way to simply store a value.
This is better than getting those users to use a different method that stores a value but does not return previous value.
Changing the return value is an incompatible API change, so instead let's move those users to a new API version.
 