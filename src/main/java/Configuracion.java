
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;




public class Configuracion {
    dbConnection dbConnection = new dbConnection();
    App app = new App();
    
    String FILENAME = new String ("config.json");
    File arquivo = new File (FILENAME);
    
    public Configuracion (dbConnection dbConnection, App app){
        this.dbConnection = dbConnection;
        this.app = app;
    }
    
    public Configuracion(){
        
    }

    @Override
    public String toString() {
        return "Configuracion{" + "db=" + dbConnection + ", app=" + app + '}';
    }
    
    public void verValores(){
        System.out.println(dbConnection.address);
        System.out.println(dbConnection.user);
        System.out.println(dbConnection.password);
        System.out.println(dbConnection.name);
    }
    
    
    public Configuracion leerJson(){
        
        try{
            FileReader fluxoDatos = new FileReader(arquivo);
            BufferedReader buferEntrada = new BufferedReader(fluxoDatos);

            StringBuilder jsonBuilder = new StringBuilder();
            String linea;
            while ((linea=buferEntrada.readLine()) != null) {
                jsonBuilder.append(linea).append("\n");
            }
            buferEntrada.close();

            String json = jsonBuilder.toString();
            Gson gson = new Gson();
            Configuracion config = gson.fromJson(json, Configuracion.class);
            return config;
        }catch(Exception e){
            return new Configuracion();
        }
    }

}




