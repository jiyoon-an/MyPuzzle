package mypuzzle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class CommonFunction {
    
    /**
     * In this method, the date of java.util is converted to java.sql.Timestamp type variable.
     * @return sqlDate
     */
    public java.sql.Timestamp getDate(){
        Date date = Calendar.getInstance().getTime();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
        return sqlDate;
    }
    
    /**
     * In this method, local pc name is called for checking user
     * @return name
     */
    public String getPCName() {
        Properties props = System.getProperties(); 
        String name = (String)props.get("user.name");
        return name;
    }
    
}
