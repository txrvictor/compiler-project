import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Programa {
    
    public static HashMap<String, Variavel> varList;
    ArrayList<Comando> comandos;
    
    public Programa() {
        varList = new HashMap<String, Variavel>();
        this.comandos = new ArrayList<Comando>();
    }
    
    public void run() { 
        
    }
    
    // -- Declaracao de variaveis
        
    public static boolean isDeclaredVar(Variavel v) {
        return !(varList.get(v.getId()) == null);
    }
    
    public static boolean isDeclaredVar(String id) {
        return !(varList.get(id) == null);
    }

    public void declareVar(String id, int tipo) {
        Variavel v = new Variavel(id, tipo);
        declareVar(v);
    }
    
    public void declareVar(Variavel v) {
        if (v.getTipo() <= 0 || v.getTipo() > 3)
            throw new RuntimeException("Tipo de variavel invalido");
        else if (isDeclaredVar(v))
            throw new RuntimeException("Variavel ja declarada: " + v.getId());
        else
            varList.put(v.getId(), v);
    }

    // -- Comandos
    
    public void addCommand(Comando cmd) {
        this.comandos.add(cmd);
    }
    
    // -- Imprimir programa em Java
   
    public String writeCode() {
        StringBuilder str = new StringBuilder();
        str.append("\npublic class Compilado {\n");
        
        // colocar todo o codigo dentro do Main()
        str.append("\tpublic static void main(String args[]) {\n");
        
        // declaracao variaveis
        str.append("\t\t// Variaveis \n");
        Set<String> keys = varList.keySet();
		for (String k : keys) {
			if (k != null) {
			    if (varList.get(k).getTipo() == Variavel.TYPE_INT)
			        str.append("\t\tint " + k + ";\n");
			    else if (varList.get(k).getTipo() == Variavel.TYPE_DEC)
			        str.append("\t\tdouble " + k + ";\n");
			    else if (varList.get(k).getTipo() == Variavel.TYPE_TXT)
			        str.append("\t\tString " + k + " = new String();\n");
			}   
		}
		
		// comandos, metodos e atribuicoes
		str.append("\n\t\t// Comandos \n");
        for (Comando cmd: comandos) {
            str.append(cmd.writeCode(2));
        }
        
        str.append("\n\t}\n}\n");
        
        return str.toString();
    }
    
}