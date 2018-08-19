
## JAKARTAEE_EVENT_CDI_EXAMPLE ##

This is an example of how to use Event in CDI with Java EE 8. In this example we demonstrate an example of sending e-mail using event in CDI.
This content was created to my post in my blog https://rhuanrocha.net/2018/08/20/how-to-use-event-cdi-on-java-ee-8/

### Run Application ###
To run the application you need build the docker using the following command at home of project:

##### $ sudo docker build -t event-cdi-example . #####

After you need run the image using the following command:

##### $ sudo docker run -p 8080:8080 -p 4848:4848  -p 8009 -p 8181 event-cdi-example  #####

### Testing Application ###

To test the application you can send the following HTTP requests:

##### POST http://localhost:8080/event-cdi-example/resources/email/ #####

You need send the following parameters on POST. 
##### email : your-email   message : your-message ##### 

##### POST http://localhost:8080/event-cdi-example/resources/email/async #####

You need send the following parameters on POST. 
##### email : your-email   message : your-message ##### 

### Changing the Email Configurations ###

To change the email configurations you need update the following ENV's on the Dockerfile:

##### ENV EMAIL your-email #####
##### ENV PASSWORD your-passowrd #####
##### ENV EMAIL_USER your-user #####
##### ENV EMAIL_PORT port #####
##### ENV EMAIL_HOST smtp-host #####
