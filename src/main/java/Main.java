
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public class Main {
    
    public static void main (String [] args) throws SQLException{
 
        Directorio d = new Directorio();
        Arquivo a = new Arquivo();
        
        Configuracion configuracion = new Configuracion();  
        Configuracion conf = configuracion.leerJson();
        
        String url = conf.dbConnection.address;
        String db = conf.dbConnection.name;
        
        Properties props = new Properties();
        props.setProperty("user",conf.dbConnection.user);
        props.setProperty("password",conf.dbConnection.password);
        
        String postgres = "jdbc:postgresql://"+ url + "/"+ db ;
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
       
        
        String ruta = new String ("D:\\minidrive");
        File file = new File (ruta);
        System.out.println(file.getPath());
        if(file.exists()){
            String[] fileRead = file.list();
            if(file.isDirectory()){
            for (int i=0; i< fileRead.length; i++){
                System.out.println(fileRead[i]);
                File f = new File (file.getAbsolutePath(),fileRead[i]);
                String [] subDirectorio = f.list();
                if (f.isDirectory()){
                    for (int j = 0; j < subDirectorio.length; j++ ){
                        System.out.println(subDirectorio[i]);
                    }
                }
            }
            }
        }
            
    } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
