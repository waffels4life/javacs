package com.javacs.database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * java.sql is essentially the standard language Java uses to communicate with relational databases.</p>
 * Java needs some mechanism to:
 *     [1] connect to the database
 *     [2] send that SQL
 *     [3] let the database execute it
 *     [4] receive the result
 *     [5] read the returned rows
 *     [6] handle errors
 *     [7] commit or rollback changes
 *
 * JDBC: Java Database Connectivity.
 *
 * The JDBC driver is the bridge between Java and a specific database.
 *        Java
 *          │ java.sql
 *          ▼
 *     MySQL JDBC Driver
 *          │
 *          ▼
 *      MySQL Server
 *
 * The Five Monsters You Need to Understand:
 *      [1] Driver
 *      [2] Connection
 *      [3] Statement / PreparedStatement
 *      [4] ResultSet
 *      [5] SQLException
 *
 * Think of Connection as   => An active communication channel between your Java program and the database.
 *                          => It isn't the database itself.
 *                          => It isn't a socket in the Java API sense either.
 *                          => It's a JDBC abstraction representing a database session.
 *        Java
 *          │
 *          ▼
 *      Connection
 *          │
 *          ▼
 *      Database
 * DriverManager    => Where did this come from?
 *                  => DriverManager manages JDBC drivers and helps establish connections.
 *
 * The JDBC URL     => "jdbc:mysql://localhost:3306/mydb"
 * jdbc:
 *   │
 *   └── JDBC protocol
 *
 * mysql:
 *   │
 *   └── database type
 *
 * localhost:
 *   │
 *   └── database server
 *
 * 3306:
 *   │
 *   └── database port
 *
 * mydb:
 *   │
 *   └── database name
 *      => "Use JDBC to connect to the MySQL database mydb running on port 3306 on this machine."
 *
 */
public class JSQL {
    public void jsql() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb",
                    "root",
                    "password"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM table"
            );

            // wrong
            String userName = "demo";
            String sqlString =
                    "SELECT * FROM users WHERE username = " + userName;
            String sql =
                    "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement
                    = connection.prepareStatement(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
