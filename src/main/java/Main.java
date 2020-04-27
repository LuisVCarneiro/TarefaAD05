
import java.sql.CallableStatement;
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
            
            //Creao a táboa que conterá os directorios
            String sqlDirectorio = new String(
                "CREATE TABLE IF NOT EXISTS Directorio ("
                        + "idDirectorio integer primary key not null, "
                        + "nomeDirectorio text"
                        + ");");

            CallableStatement createFunction = conn.prepareCall(sqlDirectorio);
            createFunction.execute();
            createFunction.close();
            
            //Creo a táboa que conterá os arquivos
            String sqlArquivo = new String(
                "CREATE TABLE IF NOT EXISTS Arquivo ("
                        + "idArquivo integer primary key not null,"
                        + " nomeArquivo text, "
                        + "idDirectorio integer, "
                        + "FOREIGN KEY (idDirectorio) REFERENCES Directorio (idDirectorio)"
                        + ");");

            createFunction = conn.prepareCall(sqlArquivo);
            createFunction.execute();
            createFunction.close();
            
            
            
        }catch (SQLException ex){
            System.err.println("Error: " + ex.toString());
        } 
        
        
        
    }
}
