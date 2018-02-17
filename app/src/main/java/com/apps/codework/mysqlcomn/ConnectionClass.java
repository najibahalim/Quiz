package com.apps.codework.mysqlcomn;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by najib on 07-10-2017.
 */
public class ConnectionClass {
    String classs="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://192.168.10.194/login";
    String un="pc1";
    String pw="";

    @SuppressLint("NewApi")
    public Connection CONN(){
        //StrictMode.ThreadPolicy policy=new StrictMode().ThreadPolicy.Builder()
                //.permitAll().build();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        Connection conn=null;
        String ConnUrl=null;
        try{
            Class.forName(classs);
            conn= DriverManager.getConnection(url,un,pw);
            conn=DriverManager.getConnection(ConnUrl);
        }catch (SQLException e){
            Log.e("error",e.getMessage());
        }
        catch (ClassNotFoundException e){
            Log.e("error",e.getMessage());
        }catch (Exception e){
            Log.e("error",e.getMessage());
        }
        return conn;

    }


}
