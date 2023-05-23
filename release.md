# Steps to create a release

To release the library, the following steps needs to be done:

* push all changes to `dev`
* wait until the action has successfully finished
* create new branch from `dev` and name it `release/x.x.x`
* change revision in POM from 'HEAD-SNAPSHOT' to 'x.x.x'
* change return value of the `getVersion`-method from 'HEAD-SNAPSHOT' to 'x.x.x.' in class `Malio`
* push changes using comment 'set version to x.x.x'
* merge the `release/x.x.x`-branche into main (create PR)
* release new version (Nexus)
* Checkout locally the dev branch and merge the pulled main into it
* Change pom version back to HEAD-SNAPSHOT
* Change `getVersion`-method back to HEAD-SNAPSHOT
