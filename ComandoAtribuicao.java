import java.util.ArrayList;

public class ComandoAtribuicao extends Comando {
    
    private Expressao expr;
    private Variavel var;
    
    public ComandoAtribuicao(String id, Expressao expr) {
        // verificar se var foi declarada
        Programa.isDeclaredVar(id);
        
        Variavel v = Programa.varList.get(id);
        this.var = v;
        this.expr = expr;
        
        this.validaAtribuicao();
    }
    
    private void validaAtribuicao() {
        // verificar se expressao e aritmetica
        if (this.expr.getIsLogical())
            throw new RuntimeException("Tentativa de atribuir expressao logica na variavel: " + this.var.getId());
        
        // verificar se tipo de retorno de expresso eh valida para var
        if (this.expr.getTipoDeRetorno() != this.var.getTipo()) {
            throw new RuntimeException("Tentativa de atribuicao de tipo de valor incorreto na variavel: " + this.var.getId());
        }
    }
    
    public String writeCode(int nivel) {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<nivel; i++)
                str.append("\t");
        str.append(this.var.getId()).append(" = ");
        str.append(expr.ImprimeExpressao()).append(";\n");
        return str.toString();
    }
    
}