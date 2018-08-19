package net.rhuanrocha.eventcdiexample.event;

public class EmailEvent {

    private String emailTo;

    private String message;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static EmailEvent build(String emailTo, String message ){

        EmailEvent emailEvent = new EmailEvent();
        emailEvent.setEmailTo(emailTo);
        emailEvent.setMessage(message);

        return emailEvent;

    }
}
