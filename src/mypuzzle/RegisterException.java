package mypuzzle;

public class RegisterException extends Exception {
    public RegisterException(String message) {
        super(message);
    }
    
    public RegisterException(Throwable cause) {
        super(cause);
    }
    
    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * This method is for checking the name whether it's correct or not.
     * @param name
     * @return
     * @throws RegisterException 
     */
    public static boolean isValidName(String name) throws RegisterException {
        if(name.length() < Settings.NAME_MIN_LENGTH) {
            throw new RegisterException("Name is less than " + Settings.NAME_MIN_LENGTH + " characters.");
        } else if(name.length() > Settings.NAME_MAX_LENGTH) {
            throw new RegisterException("Name is more than " + Settings.NAME_MAX_LENGTH + " characters.");
        } else {
            PuzzleDAO dao = new PuzzleDAO();
            int count = dao.getExistingUser(name);
            if(count > 0) {
                throw new RegisterException("'" + name + "' isn't available.");
            }
        }
        return true;
    }
    
    /**
     * In this method, password is checked. if user entered wrong, the Exception is thrown.
     * @param password
     * @return
     * @throws RegisterException 
     */
    public static boolean isValidPW(String password) throws RegisterException{
        if(password.length() < Settings.PASSWORD_LENGTH) {
            throw new RegisterException("Password is less than " + Settings.PASSWORD_LENGTH + " digits.");
        } else if(password.length() > Settings.PASSWORD_LENGTH) {
            throw new RegisterException("Password is more than " + Settings.PASSWORD_LENGTH + " digits.");
        }
        
        int isNotNum = 0;
        for(int i=0; i<password.length(); i++) {
            if(password.charAt(i)<48 || password.charAt(i) > 57) {
                isNotNum++;
            }
        }
        if(isNotNum > 0) {
            throw new RegisterException("Password must consist of numeric digit.");
        }
        return true;
    }
    
    /**
     * In this Method, confirm password is checked. whether it's same with password or not
     * @param pwFirst
     * @param pwConfirm
     * @return
     * @throws RegisterException 
     */
    public static boolean isMatchedPW(String pwFirst, String pwConfirm) throws RegisterException{
        if(!pwFirst.equals(pwConfirm)) {
            throw new RegisterException("Please check password again.");
        }
        return true;
    }
}
