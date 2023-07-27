package com.project.my;
 
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;
 
public class DataSourceTest {
 
    @Test
    public void testConnection() throws Exception{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC","root","1234");
        System.out.println(con);
        
    }
    
}