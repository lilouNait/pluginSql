
Required 
- Intellij
- Maven
- JDK
- Java 8 
- Git

Install the project 
Use the ssh way directory on your intellij to clone the project, if you have maven installed on your environment, dependencies will be loaded when you clone the project in intellij
If you haven't installed maven on your environement, you can use this command on your cmder:
- ```sh
choco install maven
```

``Test the project using a local sonar docker image
Find in the pjoect a file named script.sh, you have only to execute this file after building your project, and you will have a sonar server that contains the plugin jar on it, you can lauch sonar on: localhost:9000 directory on your browser

``To test locally
Use this command : mvn clean package
To directory on sonar load the script.sh using this command: ./script.sh
