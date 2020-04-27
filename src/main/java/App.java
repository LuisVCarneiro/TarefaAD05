public class App{
    String directory;

    public App(String directory) {
        this.directory = directory;
    }

    public App() {
    }

    public String getDirectory() {
        return directory;
    }

    @Override
    public String toString() {
        return "App{" + "directory=" + directory + '}';
    }
    
}
