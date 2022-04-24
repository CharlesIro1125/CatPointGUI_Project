# CatPointGUI_Project
Cat_detection_GUI_using_AWS_Rekognition.model

The Cat Detection and Emergency Alarm Graphical User Interface developed using JPanel components can be downloaded a and run on your intended IDE (such as Intellij). The multi-module project is built on the Maven framework.

To install, first clone the remote repository into your local repository. Open the Maven build with your IDE (such as Intellij) from the parent pom in the base directory /catpointParent/pom.xml, and run the application from the main java file securityService/src/main/java/com/catpoint/jpnd/securityService/application/CatpointApp.java

<img src="https://github.com/CharlesIro1125/CatPointGUI_Project/blob/b6f595d4317b6393a9d07014e73a541bd47f1a8f/catpointParent/src/images/cat_sensor1.png" alt="schema" width="600" height="700" />
<img src="https://github.com/CharlesIro1125/CatPointGUI_Project/blob/b6f595d4317b6393a9d07014e73a541bd47f1a8f/catpointParent/src/images/cat_sensor2.png" alt="schema" width="600" height="700" />

##### Directory structure of the Security GUI Repository.

```
├───.idea
└───catpointParent
    ├───.idea
    ├───imageService
    │   ├───src
    │   │   ├───main
    │   │   │   └───java
    │   │   │       └───com
    │   │   │           └───catpoint
    │   │   │               └───jpnd
    │   │   │                   └───imageService
    │   │   │                       └───Service
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
    └───target
        └───site
            ├───css
            └───images
                └───logos
```
