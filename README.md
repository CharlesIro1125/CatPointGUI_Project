# CatPointGUI Project
#### Cat Detection GUI using AWS Rekognition model

The Cat Detection and Emergency Alarm Graphical User Interface developed using JPanel components can be downloaded and run on your intended IDE (such as Intellij). The multi-module project is built on the Maven framework.

> To install, first clone the remote repository into your local repository. Open the Maven build with your IDE (such as Intellij) from the parent pom in the base   directory "/catpointParent/pom.xml", and run the application from the main java file "securityService/src/main/java/com/catpoint/jpnd/securityService/application/CatpointApp.java".


> To use the Aws Rekognition model. input your Aws access credential in the "imageService/src/main/resources/config.properties" file and Change the ImageService implementation class in the CatpointGui class to use the AwsImageService instead of the FakeImageService.
Change new FakeImageService() -> new AwsImageService().





<img src="https://github.com/CharlesIro1125/CatPointGUI_Project/blob/2773dd9d4d8b0126e55d83df8772d74ad11f48e1/catpointParent/src/images/cat_sensor1.png" alt="schema" width="600" height="700" />
<img src="https://github.com/CharlesIro1125/CatPointGUI_Project/blob/4e3a60cb0261d176eb50022b309818acf12669b2/catpointParent/src/images/cat_sensor2.jpg" alt="schema" width="600" height="700" />

##### Directory structure of the Security GUI Repository.

```
├───.idea
└───catpointParent
    ├───.idea
    ├───imageService
    │   ├───src
    │   │   ├───main
    │   │   │   ├───java
    │   │   │   │   └───com
    │   │   │   │       └───catpoint
    │   │   │   │           └───jpnd
    │   │   │   │               └───imageService
    │   │   │   │                   └───Service
    │   │   │   └───resources
    │   │   └───test
    │   │       └───java
    │   │           └───com
    │   │               └───catpoint
    │   │                   └───jpnd
    │   │                       └───imageService
    │   └───target
    │       ├───classes
    │       │   └───com
    │       │       └───catpoint
    │       │           └───jpnd
    │       │               └───imageService
    │       │                   └───Service
    │       ├───generated-sources
    │       │   └───annotations
    │       ├───generated-test-sources
    │       │   └───test-annotations
    │       ├───maven-archiver
    │       ├───maven-status
    │       │   └───maven-compiler-plugin
    │       │       ├───compile
    │       │       │   └───default-compile
    │       │       └───testCompile
    │       │           └───default-testCompile
    │       ├───site
    │       │   ├───css
    │       │   └───images
    │       │       └───logos
    │       └───test-classes
    │           └───META-INF
    ├───securityService
    │   ├───src
    │   │   ├───main
    │   │   │   └───java
    │   │   │       └───com
    │   │   │           └───catpoint
    │   │   │               └───jpnd
    │   │   │                   └───securityService
    │   │   │                       ├───application
    │   │   │                       ├───data
    │   │   │                       └───service
    │   │   └───test
    │   │       └───java
    │   │           └───com
    │   │               └───catpoint
    │   │                   └───jpnd
    │   │                       └───securityService
    │   │                           └───service
    │   └───target
    │       ├───archive-tmp
    │       ├───classes
    │       │   └───com
    │       │       └───catpoint
    │       │           └───jpnd
    │       │               └───securityService
    │       │                   ├───application
    │       │                   ├───data
    │       │                   └───service
    │       ├───generated-sources
    │       │   └───annotations
    │       ├───generated-test-sources
    │       │   └───test-annotations
    │       ├───maven-archiver
    │       ├───maven-status
    │       │   └───maven-compiler-plugin
    │       │       ├───compile
    │       │       │   └───default-compile
    │       │       └───testCompile
    │       │           └───default-testCompile
    │       ├───site
    │       │   ├───css
    │       │   └───images
    │       │       └───logos
    │       ├───surefire-reports
    │       └───test-classes
    │           ├───com
    │           │   └───catpoint
    │           │       └───jpnd
    │           │           └───securityService
    │           │               └───service
    │           └───META-INF
    ├───src
    │   └───images
    └───target
        └───site
            ├───css
            └───images
                └───logos
```
