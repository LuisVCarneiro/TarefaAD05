
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
       
       
         //Busco os directorios
        String ruta = new String ("D:\\minidrive");
        File file = new File (ruta);
        FileInputStream fis = new FileInputStream(file);
            
        String sqlInsertDirectorio = new String (
                "INSERT INTO Directorio VALUES (?,?)");
        PreparedStatement psd = conn.prepareStatement(sqlInsertDirectorio);
        
        String sqlInsertArquivo = new String (
                "INSERT INTO Arquivo VALUES (?,?,?)");
        PreparedStatement psa = conn.prepareStatement(sqlInsertArquivo);
        
        int id = 1;
        if (file.exists()){
            String [] lectura = file.list();
            if (file.isDirectory()){
            for (int i = 0; i < lectura.length; i++){
                psd.setInt(1, id);
                id++;
                psd.setString(2,lectura[i]);
                psd.executeUpdate();
                }   
            }
        }
        psd.close();
        fis.close();
        
        /*String ruta = new String ("D:\\minidrive");
        File file = new File (ruta);
        if(file.exists()){//Si el fichero existe
            String[] fileRead = file.list();
            if(file.isDirectory()){
            for (int i=0; i< fileRead.length; i++){
                psd.setInt(1,1);//Asigno en el idDirectorio el valor 1
                //id++;//Sumo un valor para que en la siguiente iteracion coloque el 2
                psd.setString(2,fileRead[i]);//Asigno el nombre del directorio en la base de datos
                createFunction = conn.prepareCall(sqlInsertDirectorio);
                createFunction.execute();
                //createFunction.close();
                /*File f = new File (file.getAbsolutePath(),fileRead[i]);
                String [] subDirectorio = f.list();
                if (f.isDirectory()){
                    for (int j = 0; j < subDirectorio.length; j++ ){
                        System.out.println(subDirectorio[i]);
                        int ida = 1;
                        psa.setInt(1,ida);
                        ida++;
                        psa.setString(2,subDirectorio[j]);
                        psa.setInt(3,id);
                        createFunction = conn.prepareCall(sqlInsertArquivo);
                        createFunction.execute();
                        createFunction.close();
                    }
                }
            }
            }
        }
        createFunction.close();
        psd.executeUpdate();
        psa.executeUpdate();
        psd.close();
        psa.close();*/
            
            } catch (SQLException e){
                e.printStackTrace();
            } catch (FileNotFoundException fnfe){
                fnfe.printStackTrace();
            } catch (IOException ioe){
                ioe.getMessage();
            }
    }
}
