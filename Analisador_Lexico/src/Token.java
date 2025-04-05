
public class Token {
	public String lexema;
	public TipoToken padrao;
	
	public Token(String lexema, TipoToken padrao) {
		this.lexema = lexema;
		this.padrao = padrao;
		
	}


	@Override
	public String toString() {
		
		
		return "<"+lexema+","+padrao+">";
		
	}


	public String getLexema() {
		return lexema;
	}


	public void setLexema(String lexema) {
		this.lexema = lexema;
	}


	public TipoToken getPadrao() {
		return padrao;
	}


	public void setPadrao(TipoToken padrao) {
		this.padrao = padrao;
	}
	

}
