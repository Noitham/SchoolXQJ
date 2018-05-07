/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolxqj.persist;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import net.xqj.exist.ExistXQDataSource;

/**
 *
 * @author DanielMoralesGonzale
 */
public class DBConnect {

    public XQConnection getConnection() throws XQException {

        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        xqs.setProperty("user", "usrschool");
        xqs.setProperty("password", "pswschool");
        XQConnection conn = xqs.getConnection();

        return conn;

    }

}
