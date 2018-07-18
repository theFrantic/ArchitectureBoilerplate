# Architecture Boilerplate

This is a project that will serve to show a simple way to integrate the basic Architecture Components on Android Applications. The main goal is to provide an easy entry to understand better how to use this libraries.

## Getting Started

Juts clone the project, import it on your Android Studio IDE and build it. It's just that simple.

### Prerequisites

You will need:
    - Android Studio IDE 3+
    - Android SDK (min SDK 21 - target 27)
    - AVD Manager (or any other way to run and test the code)

## Chagelog

### Version 0.1
First version of the project that includes: 
* Dagger 2, in order to manage the DI - Retrofit for API requests
* Butterknife to provide view binding (and eliminate the findViewById calls)
* Glide an efficient image load framework
* Gson to deal with JSON
* Room to provide a caching database
* ViewModel to handle the communication with the data layer and provide data to an specific component
* LiveData to provide observables

