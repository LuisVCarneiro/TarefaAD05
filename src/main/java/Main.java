
import java.io.File;
import java.sql.Connection;



public class Main {
    
    public static void main (String [] args){
        
        Operacions operacions = new Operacions();
        Conectar connect = new Conectar();
        Conectar conn = connect.leerJson();

        //Conecto a base de datos e creo as táboas directorio e Arquivo
        Connection con = conn.conectarDB();
        Directorio d = new Directorio();
        d.createTableDirectorio(con);
        Arquivo a = new Arquivo();
        a.createTableArquivo(con);
        
        //Carpeta donde están os directorios e arquivos no documento config.json
        File carpetaPrincipal = new File (conn.app.directory);
       
        if (!carpetaPrincipal.exists()) {
                carpetaPrincipal.mkdir();
            }
        
        //Inserto directorio raíz e posteriormente recórroo para insertar na base de datos os directorios e arquivos que conteña 
        d.insertarDirectorio(carpetaPrincipal, con);
        d.recorrerDirectorios(carpetaPrincipal.getPath(), con);
        a.recorrerArquivos(carpetaPrincipal.getPath(), con);
        
        d.restaurarDirectorios(con);
        a.restaurarArquivo(con);
        
        operacions.Funcion(con);
        operacions.Trigger(con);
        
        conn.desconectarDB(con);
    }
}
