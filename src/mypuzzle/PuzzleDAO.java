package mypuzzle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleDAO {
    
    public PuzzleDAO() { }
    
    /**
     * Connection to database
     * @return
     * @throws Exception 
     */
    private Connection getConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://JIYOON//dbPuzzle", "", "");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    /**
     * load the userId by using userName
     * @param name
     * @return 
     */
    public int selectUser(String name) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        int id = 0;
        try {
            con = getConnection();
            sql = "SELECT userId from tblUser where userName=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            
            rs = pstmt.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("Failed to insert user");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
        return id;
    }
    
    /**
     * Add new User
     * @param name
     * @param password
     * @param pcName
     * @param date 
     */
    public void setUser(String name, char password[], String pcName, java.sql.Timestamp date) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String pw = "";
        for(int i=0; i<password.length; i++) {
            pw += password[i];
        }
        
        try{
            con = getConnection();
            sql = "INSERT INTO tblUser (userName, userPassword, userPC, lastLogin) VALUES (?,?,?,?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, pw);
            pstmt.setString(3, pcName);
            pstmt.setTimestamp(4, date);
            
            pstmt.executeUpdate();
           
        } catch(Exception e) {
            System.out.println("Failed to insert user");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
    }
    
    /**
     * update the login date and time.
     * @param name
     * @param date 
     */
    public void setUserLogin(String name, java.sql.Timestamp date) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        
        try{
            con = getConnection();
            sql = "UPDATE tblUser set lastLogin=? where userName=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setTimestamp(1, date);
            pstmt.setString(2, name);
            
            pstmt.executeUpdate();
           
        } catch(Exception e) {
            System.out.println("Failed to update user last login");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
    } 
    
    /**
     * Before adding new user, check db first whether user already exist or not.
     * @param name
     * @return 
     */
    public int getExistingUser(String name) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        int count = 0;
        
        try {
            con = getConnection();
            sql = "SELECT COUNT(*) FROM tblUser WHERE userName=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            
            rs = pstmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return count;
        }
    }
    
    /**
     * load images from db by using userId and where it's from
     * @param imageFrom
     * @param userId
     * @return 
     */
    public ArrayList getImages(String imageFrom, int userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        ArrayList arrImages = new ArrayList();
        
        try {
            con = getConnection();
            sql = "SELECT * FROM tblImage WHERE imageFrom=? and userId=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, imageFrom);
            pstmt.setInt(2, userId);
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                HashMap mapImg = new HashMap(); 
                mapImg.put("imageId", rs.getInt(1));
                mapImg.put("imageFile", rs.getBinaryStream(2));
                mapImg.put("imageName", rs.getString(3));
                mapImg.put("imagePath", rs.getString(4));
                
                arrImages.add(mapImg);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return arrImages;
        }
    }
    
    /**
     * 
     * @param imageId
     * @return 
     */
    public HashMap getSelectedImage(int imageId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        HashMap mapImg = new HashMap(); 
        
        try {
            con = getConnection();
            sql = "SELECT * FROM tblImage WHERE imageId=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, imageId);
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                mapImg.put("imageId", rs.getInt(1));
                mapImg.put("imageFile", rs.getBinaryStream(2));
                mapImg.put("imageName", rs.getString(3));
                mapImg.put("imagePath", rs.getString(4));
                
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return mapImg;
        }
    }
    
    /**
     * Load user's information who is using current pc
     * @param userPC
     * @return 
     */
    public ArrayList getUser(String userPC) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        ArrayList arrUser = null;
        
        try {
            con = getConnection();
            sql = "SELECT * FROM tblUser WHERE userPC=? ORDER BY lastLogin DESC";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userPC);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                arrUser = new ArrayList(); 
                do {
                    HashMap mapUser = new HashMap();
                    mapUser.put("userId", rs.getInt(1));
                    mapUser.put("userName", rs.getString(2));
                    mapUser.put("userPassword", rs.getString(3));
                    mapUser.put("userPC", rs.getString(4));
                    mapUser.put("lastLogin ", rs.getDate(5));
                
                    arrUser.add(mapUser);
                } while(rs.next());
            }
            while(rs.next()){
                
            }
        } catch(Exception e) {
            System.out.println("Failed to get user information");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
        return arrUser;
    }
    
    /**
     * After finishing game, add new ranking 
     * @param userId
     * @param level
     * @param time
     * @param step
     * @param score
     * @param imageId 
     */
    public void setRanking(int userId, int level, int time, int step, int score, int imageId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        
        try{
            con = getConnection();
            sql = "INSERT INTO tblRanking (userId, difficulty, time, step, score, imageId) VALUES (?,?,?,?,?,?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, level);
            pstmt.setInt(3, time);
            pstmt.setInt(4, step);
            pstmt.setInt(5, score);
            pstmt.setInt(6, imageId);
            
            pstmt.executeUpdate();
           
        } catch(Exception e) {
            System.out.println("Failed to insert user");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
    }
    
    /**
     * To check ranking of images, load ranking data by using imageId
     * @param imageId
     * @return 
     */
    public ArrayList getRanking(int imageId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        ArrayList arrRanking = new ArrayList();
        
        try {
            con = getConnection();
            sql = "SELECT TOP 5 * FROM tblRanking WHERE imageId=? order by score desc";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, imageId);
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                HashMap mapRanking = new HashMap(); 
                mapRanking.put("rankId", rs.getInt(1));
                mapRanking.put("userId", rs.getInt(2));
                mapRanking.put("difficulty", rs.getInt(3));
                mapRanking.put("time", rs.getInt(4));
                mapRanking.put("step", rs.getInt(5));
                mapRanking.put("score", rs.getInt(6));
                mapRanking.put("imageId", rs.getInt(7));
                
                arrRanking.add(mapRanking);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return arrRanking;
        }
    }
    
    /**
     * Load userName by using userId
     * @param userId
     * @return 
     */
    public String getUserName(int userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        String userName = "";
        
        try {
            con = getConnection();
            sql = "SELECT userName FROM tblUser WHERE userId=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                userName = rs.getString(1);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return userName;
        }
    }
    
    /**
     * After finishing game, if the image is from local, insert new image to db
     * @param imgPath
     * @param imgName
     * @param imgFrom
     * @param userId 
     */
    public void insertImage(String imgPath, String imgName, String imgFrom, int userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        try {
            con = getConnection();
            sql = "INSERT INTO tblImage (imageFile, imageName, imagePath, imageFrom, userId) SELECT BulkColumn, ?, ?, ? ,? FROM Openrowset (Bulk '" + imgPath + "', Single_Blob) as image";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, imgName);
            pstmt.setString(2, imgPath);
            pstmt.setString(3, imgFrom);
            pstmt.setInt(4, userId);
            
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            System.out.println("Failed to insert user's image");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
    }
    
    /**
     * Before insert image from local, check if it already exist
     * @param userId
     * @param imgName
     * @return 
     */
    public int getExistingImage(int userId, String imgName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        int count = 0;
        
        try {
            con = getConnection();
            sql = "SELECT COUNT(*) FROM tblImage WHERE userId=? and imageName=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, imgName);
            
            rs = pstmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return count;
        }
    }
    public int getImageId(String imgPath, String imgName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        int imageId = 0;
        
        try {
            con = getConnection();
            sql = "SELECT imageId FROM tblImage WHERE imagePath=? and imageName=?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, imgPath);
            pstmt.setString(2, imgName);
            
            rs = pstmt.executeQuery();
            if(rs.next()){
                imageId = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("Failed to search user name");
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
            return imageId;
        }
    }
}
