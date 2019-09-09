## WebUrl Shortener Example ##

Example Spring Boot project to demonstrate building RESTful web service which can convert long web urls to short links.

## Installation

### IDE

To run this Spring Boot app you can clone the repository and open the project inside you favorite IDE and run/debug the app from there for development purposes.

### Manually

To run the app as a standalone java app, you first need to build it.

 - Use Maven command from the root of your project:

	```
		clean install
	```

 - Run the app with java -jar command:
 
	 ```
	 	java -jar target/weburl-shortener-0.0.1-SNAPSHOT.jar
	 ```

  - Or run the app using Maven plugin:
 
	 ```
	 	mvn spring-boot:run
	 ```

## Using the App

Run the app, go to [http://localhost:8080](http://localhost:8080) and follow the QuickStart guide.

### Technology ##
Following libraries were used during the development of this app:

- **Spring Boot** - Server side framework (v2.1.7)
- **H2** - in-memory database 
- **Thymeleaf** - Templating engine
- **Bootstrap** - CSS framework
- **[PrettyDocs](https://github.com/xriley/PrettyDocs-Theme)** - Theme for QuickGuide pages

## License ##
This project is licensed under the terms of the MIT license.


