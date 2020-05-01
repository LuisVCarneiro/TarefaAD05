
public class Arquivo {
    int idArquivo;
    int idDirectorio;
    String nomeArquivo;

    public Arquivo() {
    }

    public Arquivo(int idArquivo, int idDirectorio, String nomeArquivo) {
        this.idArquivo = idArquivo;
        this.idDirectorio = idDirectorio;
        this.nomeArquivo = nomeArquivo;
    }

    public int getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(int idArquivo) {
        this.idArquivo = idArquivo;
    }

    public int getIdDirectorio() {
        return idDirectorio;
    }

    public void setIdDirectorio(int idDirectorio) {
        this.idDirectorio = idDirectorio;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    
}
