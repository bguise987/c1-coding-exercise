# c1-coding-exercise

To compile and run the application, first clone the repository to your machine.

Next, assemble the application.properties file as below and place it within the parent directory ('c1-coding-exercise')

**NOTE:** This is important to complete BEFORE attempting compilation, as failure to do so will cause the build to fail in the test phase.
...that said, if you're feeling adventurous you can always ask Gradle to skip the tests by adding `-x test` in the compilation step. But since you'll need this file to run it anyway, I don't recommend that approach :)

## Assembling the application.properties file
Format your file like the example below, replacing the elements in <<>> with your own values (but do not include the brackets).

```
# Credentials for accessing the APIs
# WARNING: Ensure these are not tracked in version control!
authString={\"args\": {\"token\": \"<<your token here>>\",\"api-token\": \"<<Your api-token here>>\",\"uid\": <<your uid here>>}}

# Use this property to set whether program should ignore donut transactions
# Please use lower case true or false for this
ignoreDonuts=false
```

## Compiling
Once the application.properties file is assembled, you can compile the application. The build tool used is Gradle and the Gradle Wrapper has been included in the repository. To compile the application, navigate to the root project directory and type:

`./gradlew build`

Gradle will download necessary dependencies, compile, run tests, and package the application into a jar file.

## Running the program
Within the same project root directory, type the following to execute the program.

`java -jar build/libs/c1-exercise-1.1.jar`

This will run the application on the command line and it will output the appropriate values.

## Don't want to track your donut purchases?
To omit donut-related purchases from the data, edit the application.properties file and change the 'ignoreDonuts' property to true. Then, rerun the program as above.


