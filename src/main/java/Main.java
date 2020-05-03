
import java.sql.Connection;



public class Main {
    
    public static void main (String [] args){
        
        
        Conectar connect = new Conectar();
        Conectar conn = connect.leerJson();

        //Conecto a base de datos e creo as táboas directorio e Arquivo
        Connection con = conn.conectarDB();
        Directorio d = new Directorio();
        d.createTableDirectorio(con);
        Arquivo a = new Arquivo();
        a.createTableArquivo(con);
        
        
    }
}
