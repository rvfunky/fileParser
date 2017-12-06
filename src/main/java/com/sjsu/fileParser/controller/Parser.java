package com.sjsu.fileParser.controller;

import com.sjsu.fileParser.controller.Database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

public class Parser {
    Database database = new Database();
    BufferedReader bufferedReader;
    StringBuilder query = new StringBuilder(500);
    String email;
    String id;

    Parser(BufferedReader bufferedReader, String email, String id) {
        this.bufferedReader = bufferedReader;
        this.email = email;
        this.id = id;
        this.query.append("Insert into acceleration(diffSecs,N_samples,x_mean,x_absolute_deviation,x_standard_deviation,x_max_deviation,x_PSD_1,x_PSD_3,x_PSD_6,x_PSD_10,y_mean,y_absolute_deviation,y_standard_deviation,y_max_deviation,y_PSD_1,y_PSD_3,y_PSD_6,y_PSD_10,z_mean,z_absolute_deviation,z_standard_deviation,z_max_deviation,z_PSD_1,z_PSD_3,z_PSD_6,z_PSD_10,time,email_id,result_id) values ");
    }

    void parse() {
        String line = null;
        try {
            while ((line = this.bufferedReader.readLine()) != null) {
                StringBuffer part = new StringBuffer(150);
                part.append("(");
                line = line.replaceAll("[^\\p{Alpha}^\\p{Digit}^{-}^{,}^{:}^{.}]", "");
                String[] values = line.split(",");
                if (values.length != 27) break;
                int i = 0;
                while (i < 26) {
                    part.append("'");
                    part.append(values[i]);
                    part.append("'");
                    part.append(",");
                    ++i;
                }
                part.append("'");
                part.append(values[i]);
                part.append("',");
                part.append("'");
                part.append(this.email);
                part.append("',");
                part.append(this.id);
                part.append(")");
                this.query = this.query.append(part);
                this.query = this.query.append(",");
            }
            String finalQuery = this.query.toString();
            finalQuery = finalQuery.substring(0, finalQuery.length() - 1);
            System.out.println(finalQuery);
            try {
                this.database.executeUpdate(finalQuery);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}