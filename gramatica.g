class MeuParser extends Parser;
{
    private Programa programa;
    private Comando cmd;
    private Expressao tempExpr;
    private String element;
    private PilhaComando stack;
    
    public void init() {
        programa = new Programa();
        stack = new PilhaComando();
    }
}

prog   : "inicio"
         bloco
         "fim"
         {
             System.out.println(programa.writeCode());
         }
       ;

bloco  :  CABR (metodo | comando | declara | atribui)* CFCH
       ;

declara:   "declare" { int varType; }
    	   ( "txt" { varType = Variavel.TYPE_TXT; }
    	     | 
    	     "int" { varType = Variavel.TYPE_INT; }
    	     | 
    	     "dec" { varType = Variavel.TYPE_DEC; }
    	   )
    	   (ID { programa.declareVar(LT(0).getText(), varType); }
                (VIRG 
                 ID { programa.declareVar(LT(0).getText(), varType); }
                )*
    	   )+
    	   PF
       ;

atribui:  ID { String varId = LT(0).getText(); }
          EQ 
          expressao
          PF
          {
              cmd = new ComandoAtribuicao(varId, tempExpr);
              if (stack.isEmpty()){
                  programa.addCommand(cmd);
              }
              else {
                Comando temporario = stack.getTopo();
                if (temporario instanceof ComandoIf)
                    ((ComandoIf)temporario).addCommand(cmd);
                else if (temporario instanceof ComandoElse)
                    ((ComandoElse)temporario).addCommand(cmd);
                else if (temporario instanceof ComandoWhile)
                    ((ComandoWhile)temporario).addCommand(cmd);
                else if (temporario instanceof ComandoDoWhile)
                    ((ComandoDoWhile)temporario).addCommand(cmd);
              }
          }
       ;

expressao: (            { tempExpr = new Expressao(); }
            expr        
            | 
                PABR    { tempExpr.addOther(LT(0).getText()); }
                expr 
                PFCH    { tempExpr.addOther(LT(0).getText()); }
            )
         ;
       
expr : termo
       exprl
     ;
          
exprl : (
         (
           ART   { tempExpr.addOperadorAritmetico(LT(0).getText()); }
           | 
           REL   { tempExpr.addOperadorLogico(LT(0).getText()); }
         )
         termo
        )*
      ;
      
termo : ID     { tempExpr.addVariavel(LT(0).getText()); }
        | 
        NUM    { tempExpr.addValor(LT(0).getText(), false); }
        | 
        TXT    { tempExpr.addValor(LT(0).getText(), true); }
      ;

metodo  : (leia | escreva)
        ;
       
leia    : "leia"
          PABR
          ID { element = LT(0).getText(); }
          PFCH
          PF
          {
            cmd = new ComandoRead(element); 
            if (stack.isEmpty()){
               programa.addCommand(cmd);
            }
            else {
               Comando temporario = stack.getTopo();
               if (temporario instanceof ComandoIf)
                    ((ComandoIf)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoElse)
                    ((ComandoElse)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoWhile)
                    ((ComandoWhile)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoDoWhile)
                    ((ComandoDoWhile)temporario).addCommand(cmd);
            }
          }
        ;
        
escreva: "escreva" { int writeType; }
         PABR 
         (
          ID    { writeType = ComandoWrite.ID; } 
          | 
          TXT   { writeType = ComandoWrite.TEXT; }
          |
          NUM   { writeType = ComandoWrite.NUM; }
         )      { element = LT(0).getText(); } 
         PFCH
         PF 
         {
            cmd = new ComandoWrite(element, writeType);
            if (stack.isEmpty()) {
               programa.addCommand(cmd);
            }
            else {
               Comando temporario = stack.getTopo();
               if (temporario instanceof ComandoIf)
                    ((ComandoIf)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoElse)
                    ((ComandoElse)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoWhile)
                    ((ComandoWhile)temporario).addCommand(cmd);
               else if (temporario instanceof ComandoDoWhile)
                    ((ComandoDoWhile)temporario).addCommand(cmd);
            }
         }

        ;
       
comando:  (se | enquanto | faz)
       ;
       
se     : "se"
         PABR 
         expressao
         { 
           cmd = new ComandoIf();
           ((ComandoIf)cmd).setLogicalExpr(tempExpr);
           stack.push(cmd);
         }
         PFCH
         "entao"
         bloco
         {
            Comando cmdTopo = stack.pop();
            if (stack.isEmpty()) {
              programa.addCommand(cmdTopo);
            }
            else {
              Comando temporario = stack.getTopo();
              if (temporario instanceof ComandoIf)
                   ((ComandoIf)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoElse)
                   ((ComandoElse)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoWhile)
                   ((ComandoWhile)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoDoWhile)
                   ((ComandoDoWhile)temporario).addCommand(cmdTopo);
            }
         }         
         
         ("senao"
         {
           cmd = new ComandoElse();
           stack.push(cmd);
         }
         bloco
         {
            Comando cmdTopoElse = stack.pop();
            if (stack.isEmpty()){
              programa.addCommand(cmdTopoElse);
            }
            else {
              Comando temporario = stack.getTopo();
              if (temporario instanceof ComandoIf)
                   ((ComandoIf)temporario).addCommand(cmdTopoElse);
              else if (temporario instanceof ComandoElse)
                   ((ComandoElse)temporario).addCommand(cmdTopoElse);
              else if (temporario instanceof ComandoWhile)
                   ((ComandoWhile)temporario).addCommand(cmdTopoElse);
              else if (temporario instanceof ComandoDoWhile)
                   ((ComandoDoWhile)temporario).addCommand(cmdTopoElse);
            } 
         }
         )?
         

        ;
        
enquanto: "enquanto"
          PABR 
          expressao
          { 
               cmd = new ComandoWhile();
               ((ComandoWhile)cmd).setLogicalExpr(tempExpr);
               stack.push(cmd);
          }
          PFCH
          "faca"
          bloco
          {
            Comando cmdTopo = stack.pop();
            if (stack.isEmpty()) {
              programa.addCommand(cmdTopo);
            }
            else {
              Comando temporario = stack.getTopo();
              if (temporario instanceof ComandoIf)
                   ((ComandoIf)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoElse)
                   ((ComandoElse)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoWhile)
                   ((ComandoWhile)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoDoWhile)
                   ((ComandoDoWhile)temporario).addCommand(cmdTopo);
            }
          }
        ;
        

faz: "faz" 
      {
            Comando doWhileTemp = new ComandoDoWhile(); // nao perder referencia
            cmd = doWhileTemp;
            stack.push(cmd);
      }
      bloco
      {
            Comando cmdTopo = stack.pop();
            if (stack.isEmpty()) {
              programa.addCommand(cmdTopo);
            }
            else {
              Comando temporario = stack.getTopo();
              if (temporario instanceof ComandoIf)
                   ((ComandoIf)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoElse)
                   ((ComandoElse)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoWhile)
                   ((ComandoWhile)temporario).addCommand(cmdTopo);
              else if (temporario instanceof ComandoDoWhile)
                   ((ComandoDoWhile)temporario).addCommand(cmdTopo);
            }
      }
      "durante"
      PABR 
      expressao
      { 
           ((ComandoDoWhile)doWhileTemp).setLogicalExpr(tempExpr); // colocar expr atrasado
      }
      PFCH
      PF
     ;

valor  : (NUM | TXT)
       ;
       


class MeuLexico extends Lexer;

options
{
   caseSensitive = true;  
   charVocabulary = '\0'..'\377';
   k = 2;
}
 
ID    : ('a'..'z') ('a'..'z')*  
      ;
      
NUM   : ('0'..'9')+ ('.' ('0'..'9')+)?
      ;

CABR  : '{'
      ;
     
CFCH  : '}'
      ;
      
PABR  : '('
      ;

PFCH  : ')'
      ;

TXT   : '"'('a'..'z' | 'A'..'Z' | '0'..'9' | ' ' | '.' | ',' | ':' | ';')+'"'
      ;

BLANK : (' ' | '\t' | '\n' | '\r') { _ttype = Token.SKIP; }
      ;
      
PF    : ';'
      ;
      
VIRG  : ','
      ;
      
EQ    : '='
      ;
      
REL   : ('>' | '<' | ">=" | "<=" | "?=" | "!=" | '|' | '&')
      ;
      
ART   : ('+' | '-' | '/' | '*' | '%')
      ;
      
