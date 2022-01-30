# **Jumia Service Project Boot up process**

## Prerequisites

1. Java 8 or higher installed
2. maven 3.6.2 or higher installed
3. docker installed and running
4. Port 8080 is not in use
##  Steps
1. Navigate to the project root directory
2. Run the below command to create a .war file
   
   `mvn clean package`
3. Run the below command to build a docker image
   
   ```docker build -t coderbii/jumia .```
4. Run the below command to set the file up and running
   
   ```docker run -p 8080:8080 coderbii/jumia```

5. Open the link http://127.0.0.1:8080/ on your browser to access the customers' list