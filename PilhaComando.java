public class PilhaComando {
    
    private Comando itens[];
    private int     topo;
    
    public PilhaComando(){
        itens = new Comando[20];
        topo = -1;
    }
    
    public void push(Comando cmd){
        itens[++topo] = cmd;
    }
    public boolean isEmpty(){
        return topo == -1;
    }
    public Comando pop(){
        return itens[topo--];
    }
    
    public Comando getTopo(){
        return itens[topo];
    }
    
}