# Tasker
Code test involving background services and Kotlin.

## Setup and Installation
1. Clone the repo with `git clone --recursive https://github.com/nicholaspark09/tasker.git`
2. cd tasker

## Branching Strategy
* The `master` branch holds the currently released version
* The `dev` branch holds what will be released in the next version
* Feature branches should be created from the `develop` branch with the developer's initials_feature or ticket as the branch name
   For example: `git checkout -b np_task1`

## Libraries used
FusionLocationProvider
   * I used this provider because it allows for efficient location updates
   * By using `PRIORITY_BALANCED_POWER_ACCURACY` the FusionLocationServices rarely uses actual GPS preferring WIFI and cell        info which is important in a library
   * It also allows for future extensible features such as geofencing

GCMNetworkManager
   * Gcm Network Manager was used because it batches network and service calls in an energy efficient way
   * GCM Network Manager was also chosen because a JobScheduler is minSdk 21+ whereas the networkmanager is available 9+
