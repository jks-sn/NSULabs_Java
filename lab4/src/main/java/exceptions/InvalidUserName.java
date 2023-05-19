package exceptions;

public class InvalidUserName extends Exception {
    private final String userName;
    public InvalidUserName(String message, String userName){
        super(message);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}
