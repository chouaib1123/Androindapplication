package com.example.myapplication.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
//    private static final int INITIAL_POOL_SIZE = 10;
//    private static final LinkedList<Connection> connectionPool = new LinkedList<>();
//
//    static {
//        initializeConnectionPool();
//    }
//
//    private static void initializeConnectionPool() {
//        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
//            connectionPool.add(createNewConnection());
//        }
//    }
//
//    private static Connection createNewConnection() {
//        try {
//            return DatabaseUtil.connect();
//        } catch (Exception e) {
//            throw new RuntimeException("Error occured while trying to connect to the database", e);
//        }
//    }
//
//    public static Connection getConnectionFromPool() {
//        if (connectionPool.isEmpty()) {
//            connectionPool.add(createNewConnection());
//        }
//        return connectionPool.poll();
//    }
//
//    public static void returnConnectionToPool(Connection connection) {
//        if (connection != null) {
//            try {
//                if (!connection.isClosed()) {
//                    connectionPool.add(connection);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void closeAllConnections() {
//        for (Connection connection : connectionPool) {
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}