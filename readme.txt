+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Pre-Requisites to Build
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
1) Unzip the file to a location. 

2) Check for Maven & Java 8 

Apache Maven 3.2.1 (ea8b2b07643dbb1b84b6d16e1f08391b666bc1e9; 2014-02-14T11:37:52-06:00)
Maven home: C:\downloads\Maven\apache-maven-3.2.1\bin\..
Java version: 1.8.0_91, vendor: Oracle Corporation
Java home: C:\Program Files\Java\jdk1.8.0_91\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 7", version: "6.1", arch: "amd64", family: "dos"

3) From the command line, run the maven build 
 - Navigate to the location of unzipped folder. The current location should have the pom.xml 
 - run Maven cmd > mvn clean install 
 - If Build fails for an artifact- check the settings.xml in your .m2 folder. Validate the Maven repo url. 

4) Navigate to the target directory. 

5) Run Java  CMD > java -jar resource-analytics-1.0.jar

6) The Server should start without issues. 

