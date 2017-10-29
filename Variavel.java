public class Variavel {
    
    // tipos de variaveis
    public static final int TYPE_INT = 1;
    public static final int TYPE_DEC = 2;
    public static final int TYPE_TXT = 3;
    
    private String id;
    private int    tipo;
    
    public Variavel(String id, int tipo) {
        this.id   = id;
        this.tipo = tipo;
    }
    
    public Variavel() {
        this("", -1);
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public int getTipo() {
        return this.tipo;
    }
    
}