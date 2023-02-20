package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;



public class ReadTable {
    public static Boolean SeeIfTableExist(String TableBasename) {
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState" + TableBasename;
            ResultSet rs = stmt.executeQuery(sql);

            rs.close();
            con.close();
            return true;

        } catch (Exception e) {
            System.out.println("Error in the SQL class: " + e);
            return false;
        }


    }
}

    









