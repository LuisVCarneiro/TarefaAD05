
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;


public class Directorio{
    int idDirectorio;
    String nomeDirectorio;
    
    public Directorio(){
    }
    
    public Directorio (int idDirectorio, String nomeDirectorio){
        this.idDirectorio = idDirectorio;
        this.nomeDirectorio = nomeDirectorio;
    }
    
}
