package com.jdbc;

import java.sql.*;

public class Main {

    public static void main( String args[] ) {
        Connection c = null; // соединение
        Statement stmt = null; // поток работы с БД

        try {
            Class.forName("org.sqlite.JDBC");  // формат работы бд
            c = DriverManager.getConnection("jdbc:sqlite:test.db"); // сама бд, подключение к файлу
            c.setAutoCommit(false);  // отключение авто сохронения
            System.out.println("Открытие бд, успех!");

            stmt = c.createStatement(); // бд в поток
            c.commit();

            String sql ="1";
            //String sql ="'1' or 1='1'";
            PreparedStatement preparedStatement= c.prepareStatement("SELECT * FROM COMPANY where ID=?");  // фильтация входных данных в sql
            preparedStatement.setString(1, sql);
            ResultSet rs = preparedStatement.executeQuery();// выборка по запросу sql

            while ( rs.next() ) { // пока не закончилась, доставать данные и выводить на экран
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close(); // закрытие
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() ); // ошибка
            System.exit(0);
        }
        System.out.println("Выборка данных, успех!");
    }
}
