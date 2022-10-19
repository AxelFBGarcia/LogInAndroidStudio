package com.axelbgarcia.testrfid.intefaces.login;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    String ip,port,db,DBUserNameStr,DBPasswordStr;


    @SuppressLint("NewApi")
    public Connection connectionclasss(String dbUserName, String dbPassword)
    {

        // Declaring Server ip, username, database name and password
        ip = "192.168.3.10";
        port = "1433";
        db = "AndroidTest";
        DBUserNameStr = dbUserName;
        DBPasswordStr = dbPassword;
        // Declaring Server ip, username, database name and password
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String ConnectionURL = null;
        if (DBPasswordStr.isEmpty() && DBUserNameStr.isEmpty());
        else if (DBUserNameStr.isEmpty() || DBPasswordStr.isEmpty());
        else{
            try
            {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port +";databasename="+ db + ";user=" + DBUserNameStr+ ";password=" + DBPasswordStr + ";";
                connection = DriverManager.getConnection(ConnectionURL);
            }
            catch (Exception e)
            {
                Log.e("error here 3 : ", e.getMessage());
            }
        }
        return connection;
    }
}
