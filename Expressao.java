import java.util.ArrayList;

public class Expressao {
    
    ArrayList<String> partes;
    int tipoDeRetorno;
    boolean isLogical;
    
    public Expressao() {
        this.partes = new ArrayList<String>();
        this.tipoDeRetorno = -1; // invalido
        this.isLogical = false; // supoe aritmetica
    }
    
    public int getTipoDeRetorno() {
        return this.tipoDeRetorno;
    }
    
    public void addVariavel(String id) {

        if (!Programa.isDeclaredVar(id))
            throw new RuntimeException("Uso de variavel nao declarada em expressao: " + id);
        
        // Sempre ficar com o maior tipo de variavel pois sera o tipo de retorno da expr
        Variavel v = Programa.varList.get(id);
        if (this.tipoDeRetorno < v.getTipo())
            this.tipoDeRetorno = v.getTipo();
            
        this.partes.add(id);
    }
    
    public void addValor(String valor, boolean isText) {
        int tipoVar = -1;
        
        if (isText)
            tipoVar = Variavel.TYPE_TXT;
        else {
            if (isInteger(valor))
                tipoVar = Variavel.TYPE_INT;
            else
               tipoVar = Variavel.TYPE_DEC;
        }
        
        if (this.tipoDeRetorno < tipoVar)
            this.tipoDeRetorno = tipoVar;
        
        this.partes.add(valor);
    }
    
    public void addOperadorAritmetico(String operador) {
        this.addOperador(operador);
    }
    
    public void addOperadorLogico(String operador) {
        this.isLogical = true;
        this.addOperador(operador);
    }
    
    private void addOperador(String operador) {
        // verificar e traduzir operador para java se for diferente
        // da nossa linguagem
        if (operador.equals("?="))
            this.partes.add("==");
        else if (operador.equals("|"))
            this.partes.add("||");
        else if (operador.equals("&"))
            this.partes.add("&&");
        else
             this.partes.add(operador);
    }
    
    public void addOther(String p) {
        this.partes.add(p);
    }
    
    public boolean getIsLogical() {
        return this.isLogical;
    }
    
    public String ImprimeExpressao() {
        StringBuilder str = new StringBuilder();
        for (String p : this.partes) {
            str.append(p);
        }
        return str.toString();
    }
    
    // verificar se valor inteiro
    private boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(Exception e) { 
            return false; 
        } 
        return true;
    }
}