
public class Token {
	public String lexema;
	public TipoToken padrao;
	public String texto;
	
	public Token(String lexema, TipoToken padrao) {
		this.lexema = lexema;
		this.padrao = padrao;
		
	}
	
	public Token(String lexema, TipoToken padrao,String texto) {
		this.lexema = lexema;
		this.padrao = padrao;
		this.texto=texto;
		
	}

	@Override
	public String toString() {
		
		
		return "<"+lexema+","+padrao+","+"Valor:"+texto+">";
		
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	

}
