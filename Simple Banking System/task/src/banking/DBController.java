package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DBController {
    private Connection con;
    private Statement statement;
    private String dbName;

    public DBController(String dbName){
        setDbName(dbName);
        createTable();
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Connection connectToDB(){
        String url = "jdbc:sqlite:"+dbName;
        Connection con = null;
        try{
             con = DriverManager.getConnection(url);
//             if(con.isValid(5)){
//                 System.out.println(con.getMetaData().getURL());
//             }
        }catch(SQLException e){
            System.out.println(e);
        }
        return con;
    }

    public void setCon(Connection connection){
        con = connection;
    }

    public Connection getCon() {
        return con;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement createStatement(){
        try(Connection con = this.connectToDB()){
            Statement statement = con.createStatement();
            return statement;
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public void createTable(){
        try(Connection con = this.connectToDB();
        Statement statement = con.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT NOT NULL UNIQUE," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");
        }catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }

    public boolean insertCard(String cardNumber, String pin){
        String sql = "INSERT INTO card(number, pin) VALUES(?,?)";
        try(Connection con = this.connectToDB();
            PreparedStatement psmt = con.prepareStatement(sql)){
            psmt.setString(1,cardNumber);
            psmt.setString(2,pin);
            psmt.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
    public String[] getCardInfo(String cardNumber){
        String sql = "SELECT number, pin, balance FROM card WHERE number=?";
        try(Connection con = this.connectToDB();
            PreparedStatement psmt = con.prepareStatement(sql)){
            psmt.setString(1,cardNumber);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                return new String[]{rs.getString("number"),rs.getString("pin"),
                        String.valueOf(rs.getInt("balance"))};
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return new String[] {"error"};
    }

}
