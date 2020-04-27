
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Main {
    public static void main (String [] args){
        
        Configuracion configuracion = new Configuracion();  
        Configuracion conf = configuracion.leerJson();

        String url = conf.dbConnection.address;
        String db = conf.dbConnection.name;
        
        Properties props = new Properties();
        props.setProperty("user",conf.dbConnection.user);
        props.setProperty("password",conf.dbConnection.password);
        
        String postgres = "jdbc:postgresql://"+ url + "/" + db ;
        try{
            Connection conn = DriverManager.getConnection(postgres,props);
            
        }catch (SQLException ex){
            System.err.println("Error: " + ex.toString());
        } 
        
        
        
    }
}
