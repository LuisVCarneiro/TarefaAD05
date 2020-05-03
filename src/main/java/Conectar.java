
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Conectar {
    dbConnection dbConnection = new dbConnection();
    App app = new App();

    public Conectar() {
    }
    
    String FILENAME = new String ("config.json");
    File arquivo = new File (FILENAME);
    
    public Conectar (dbConnection dbConnection, App app){
        this.dbConnection = dbConnection;
        this.app = app;
    }
    
    public Conectar leerJson(){
        try{
            FileReader fluxoDatos = new FileReader(arquivo);
            BufferedReader buferEntrada = new BufferedReader(fluxoDatos);
            
            StringBuilder jsonBuilder = new StringBuilder();
            String linea;
            while ((linea=buferEntrada.readLine()) != null){
                jsonBuilder.append(linea).append("\n");
            }
            buferEntrada.close();
            String json = jsonBuilder.toString();
            Gson gson = new Gson();
            Conectar connect = gson.fromJson(json,Conectar.class);
            return connect;
        }catch (Exception e){
            return null;
        }
    }
    
    public Connection conectarDB (){
        
        String url = dbConnection.address;
        String db = dbConnection.name;

        
        Properties props = new Properties();
        props.setProperty("user", dbConnection.user);
        props.setProperty("password", dbConnection.password);

        String postgres = "jdbc:postgresql://" + url + "/" + db;

        try {
            Connection conn = DriverManager.getConnection(postgres, props);
            System.out.println("Conexión establecida");
            return conn;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void desconectarDB(Connection con) {
        try {
            con.close();
            System.out.println("Desconexión realizada.");
        } catch (SQLException ex) {
            System.out.println("Non se poido cerrar a conexión");
        }

    }
}
