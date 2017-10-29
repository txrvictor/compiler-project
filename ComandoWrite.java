public class ComandoWrite extends Comando {
    
    public static final int NUM  = 1;
    public static final int TEXT = 2;
    public static final int ID   = 3;
    
    private String content;
    private int type;
    
    public ComandoWrite(String content, int type) {
        this.content = content;
        this.type = type;
        
        if (this.type == ID)
            if (!Programa.isDeclaredVar(content))
                throw new RuntimeException("Tentou escrever uma variavel nao declarada: " + content);
    }
    
    public String writeCode(int nivel) {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<nivel; i++)
                str.append("\t");
                
        str.append("System.out.println(").append(this.content).append(");\n");
        return str.toString();
    }
    
}