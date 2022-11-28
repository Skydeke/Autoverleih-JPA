# Autoverleih-JPA
For our database course at the RWU we need to create a small Java-App that will access information from a database and present it.

Task
============
- 1. Return a car by its number plate.
- 2. Find all open loan agreements and print an Error-MSG if there are multible or none.
- 3. Write the current kilometre stand in the loan agreement and the car itself.
- 4. Put the current date as return-date in the loan agreement
- 5. Calculate the amount owed and write the bill

Information
===========
The Task has been done  
- without a GUI with the [Main-Class](src/main/java/CLIMain.java).
- with a GUI in the [App-Class](src/main/java/App.java). 

The GUI has been created using the library [JavaFX](https://openjfx.io/) and the Interface was designed 
using the libraries [SceneBuilder](https://gluonhq.com/products/scene-builder/). But this does come with some Problems, namely that starting the program with its
GUI can only be done using the maven command:
````
mvn compile javafx:run
````
OR alternatively the chosen IDE needs to run the APP with the maven goals compile and javafx:run

Adapted Code
=============
- The Dialogs (also known as Popups) defined in [InputController](src/main/java/controller/InputController.java) has been copied from this [website](https://code.makery.ch/blog/javafx-dialogs-official/)
- All other code was written by myself.
