package com.sjsu.fileParser.controller;

import com.sjsu.fileParser.controller.Database;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ResultId {
    String email;
    Database database = new Database();

    ResultId(String email) {
        this.email = email;
    }

    int getResultId() {
        List rows;
        block5 : {
            block4 : {
                try {
                    rows = null;
                    rows = this.database.executeQuery("select max(id) from neuro_db.result where email_id = '" + this.email + "' and classification = \"\"");
                    if (rows.size() != 0) break block4;
                    return -1;
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    return -2;
                }
            }
            if (((Map)rows.get(0)).get("max(id)") != null) break block5;
            return -1;
        }
        return (Integer)((Map)rows.get(0)).get("max(id)");
    }

    public void insert() {
        try {
            this.database.executeUpdate("insert into result(email_id,classification) values('" + this.email + "','')");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}