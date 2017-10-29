import java.util.ArrayList;

public class ComandoElse extends ComandoDeControle {
    
    private ArrayList<Comando> commandList;
    
    public ComandoElse() {
        this.commandList = new ArrayList<Comando>();
    }
    
    public void addCommand(Comando comando) {
        this.commandList.add(comando);
    }
    
    public void run() {
        
    }
    
    public String writeCode(int nivel) {
        StringBuilder str = new StringBuilder();
        
        for (int i=0; i<nivel; i++)
                str.append("\t");
                
        str.append("else {\n");
        
        for(Comando c: commandList){
            str.append(c.writeCode(nivel+1));
        }
        
        for (int i=0; i<nivel; i++)
                str.append("\t");
                
        str.append("}\n");
        return str.toString();
    }
    
}