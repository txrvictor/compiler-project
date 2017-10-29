// $ANTLR 2.7.6 (2005-12-22): "gramatica.g" -> "MeuParser.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class MeuParser extends antlr.LLkParser       implements MeuParserTokenTypes
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

protected MeuParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public MeuParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected MeuParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public MeuParser(TokenStream lexer) {
  this(lexer,1);
}

public MeuParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void prog() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_inicio);
			bloco();
			match(LITERAL_fim);
			
			System.out.println(programa.writeCode());
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
	}
	
	public final void bloco() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(CABR);
			{
			_loop4:
			do {
				switch ( LA(1)) {
				case LITERAL_leia:
				case LITERAL_escreva:
				{
					metodo();
					break;
				}
				case LITERAL_se:
				case LITERAL_enquanto:
				case LITERAL_faz:
				{
					comando();
					break;
				}
				case LITERAL_declare:
				{
					declara();
					break;
				}
				case ID:
				{
					atribui();
					break;
				}
				default:
				{
					break _loop4;
				}
				}
			} while (true);
			}
			match(CFCH);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
	}
	
	public final void metodo() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_leia:
			{
				leia();
				break;
			}
			case LITERAL_escreva:
			{
				escreva();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void comando() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_se:
			{
				se();
				break;
			}
			case LITERAL_enquanto:
			{
				enquanto();
				break;
			}
			case LITERAL_faz:
			{
				faz();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void declara() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_declare);
			int varType;
			{
			switch ( LA(1)) {
			case LITERAL_txt:
			{
				match(LITERAL_txt);
				varType = Variavel.TYPE_TXT;
				break;
			}
			case LITERAL_int:
			{
				match(LITERAL_int);
				varType = Variavel.TYPE_INT;
				break;
			}
			case LITERAL_dec:
			{
				match(LITERAL_dec);
				varType = Variavel.TYPE_DEC;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			int _cnt10=0;
			_loop10:
			do {
				if ((LA(1)==ID)) {
					match(ID);
					programa.declareVar(LT(0).getText(), varType);
					{
					_loop9:
					do {
						if ((LA(1)==VIRG)) {
							match(VIRG);
							match(ID);
							programa.declareVar(LT(0).getText(), varType);
						}
						else {
							break _loop9;
						}
						
					} while (true);
					}
				}
				else {
					if ( _cnt10>=1 ) { break _loop10; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt10++;
			} while (true);
			}
			match(PF);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void atribui() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(ID);
			String varId = LT(0).getText();
			match(EQ);
			expressao();
			match(PF);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void expressao() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case NUM:
			case TXT:
			{
				tempExpr = new Expressao();
				expr();
				break;
			}
			case PABR:
			{
				match(PABR);
				tempExpr.addOther(LT(0).getText());
				expr();
				match(PFCH);
				tempExpr.addOther(LT(0).getText());
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			termo();
			exprl();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void termo() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case ID:
			{
				match(ID);
				tempExpr.addVariavel(LT(0).getText());
				break;
			}
			case NUM:
			{
				match(NUM);
				tempExpr.addValor(LT(0).getText(), false);
				break;
			}
			case TXT:
			{
				match(TXT);
				tempExpr.addValor(LT(0).getText(), true);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void exprl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop18:
			do {
				if ((LA(1)==ART||LA(1)==REL)) {
					{
					switch ( LA(1)) {
					case ART:
					{
						match(ART);
						tempExpr.addOperadorAritmetico(LT(0).getText());
						break;
					}
					case REL:
					{
						match(REL);
						tempExpr.addOperadorLogico(LT(0).getText());
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					termo();
				}
				else {
					break _loop18;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void leia() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_leia);
			match(PABR);
			match(ID);
			element = LT(0).getText();
			match(PFCH);
			match(PF);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void escreva() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_escreva);
			int writeType;
			match(PABR);
			{
			switch ( LA(1)) {
			case ID:
			{
				match(ID);
				writeType = ComandoWrite.ID;
				break;
			}
			case TXT:
			{
				match(TXT);
				writeType = ComandoWrite.TEXT;
				break;
			}
			case NUM:
			{
				match(NUM);
				writeType = ComandoWrite.NUM;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			element = LT(0).getText();
			match(PFCH);
			match(PF);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void se() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_se);
			match(PABR);
			expressao();
			
			cmd = new ComandoIf();
			((ComandoIf)cmd).setLogicalExpr(tempExpr);
			stack.push(cmd);
			
			match(PFCH);
			match(LITERAL_entao);
			bloco();
			
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
			
			{
			switch ( LA(1)) {
			case LITERAL_senao:
			{
				match(LITERAL_senao);
				
				cmd = new ComandoElse();
				stack.push(cmd);
				
				bloco();
				
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
				
				break;
			}
			case CFCH:
			case LITERAL_declare:
			case ID:
			case LITERAL_leia:
			case LITERAL_escreva:
			case LITERAL_se:
			case LITERAL_enquanto:
			case LITERAL_faz:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void enquanto() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_enquanto);
			match(PABR);
			expressao();
			
			cmd = new ComandoWhile();
			((ComandoWhile)cmd).setLogicalExpr(tempExpr);
			stack.push(cmd);
			
			match(PFCH);
			match(LITERAL_faca);
			bloco();
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void faz() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_faz);
			
			Comando doWhileTemp = new ComandoDoWhile(); // nao perder referencia
			cmd = doWhileTemp;
			stack.push(cmd);
			
			bloco();
			
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
			
			match(LITERAL_durante);
			match(PABR);
			expressao();
			
			((ComandoDoWhile)doWhileTemp).setLogicalExpr(tempExpr); // colocar expr atrasado
			
			match(PFCH);
			match(PF);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void valor() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NUM:
			{
				match(NUM);
				break;
			}
			case TXT:
			{
				match(TXT);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"inicio\"",
		"\"fim\"",
		"CABR",
		"CFCH",
		"\"declare\"",
		"\"txt\"",
		"\"int\"",
		"\"dec\"",
		"ID",
		"VIRG",
		"PF",
		"EQ",
		"PABR",
		"PFCH",
		"ART",
		"REL",
		"NUM",
		"TXT",
		"\"leia\"",
		"\"escreva\"",
		"\"se\"",
		"\"entao\"",
		"\"senao\"",
		"\"enquanto\"",
		"\"faca\"",
		"\"faz\"",
		"\"durante\"",
		"BLANK"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1841303968L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 700453248L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 147456L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 933888L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
