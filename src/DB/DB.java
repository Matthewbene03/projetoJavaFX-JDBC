package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() { //Esse metodo serve para iniciar a conecção com o banco
        if (conn == null) {
            try {
                Properties prop = loadProperties();
                String url = prop.getProperty("dburl");
                conn = DriverManager.getConnection(url, prop); //É feita a conecção com o banco atravez das informações de acesso do mesmo
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() { //Esse metodo serve para fechar a conecção com o banco
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) { //Esse metodo serve para fechar a conecção com o banco
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) { //Esse metodo serve para fechar a conecção com o banco
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() { //Esse metodo serve para ler as propriedades do banco de dados.
        try (FileInputStream fis = new FileInputStream("db.properties")) { //Cria um obj que vai conter as informações de acesso do Banco 
            Properties prop = new Properties();
            prop.load(fis); //Apartir desse obj prop, é feita a leitura dos dados do Banco
            return prop;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}