public class ComandoRead extends Comando {
    
    private String id;
    private int readType;
    
    public ComandoRead(String id) {
        this.id = id;
        
        // verifica variavel
        if (!Programa.isDeclaredVar(this.id))
            throw new RuntimeException("Tentou escrever uma variavel nao declarada: " + this.id);
        Variavel v = Programa.varList.get(this.id);
        this.readType = v.getTipo();
    }
    
    public String writeCode(int nivel) {
        StringBuilder str = new StringBuilder();
        
        //for (int i=0; i<nivel; i++)
        //        str.append("\t");
        //str.append("java.util.Scanner sc = new java.util.Scanner(System.in);\n");
        
        for (int i=0; i<nivel; i++)
                str.append("\t");
        str.append(this.id).append(" = ");
        if (this.readType == Variavel.TYPE_TXT)
            str.append("new java.util.Scanner(System.in).nextLine();");
        else if (this.readType == Variavel.TYPE_INT)
            str.append("new java.util.Scanner(System.in).nextInt();");
        else if (this.readType == Variavel.TYPE_DEC)
            str.append("new java.util.Scanner(System.in).nextDouble();");
        
        str.append("\n");
        return str.toString();
    }
}