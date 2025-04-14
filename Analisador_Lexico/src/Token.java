
public class Token {
	public String lexema;
	public TipoToken padrao;
	public String texto;
	public int linha;
	//Tipos de Token a serem criados
	public Token(String lexema, TipoToken padrao) {
		this.lexema = lexema;
		this.padrao = padrao;
		
	}
	
	public Token(String lexema, TipoToken padrao,String texto) {
		this.lexema = lexema;
		this.padrao = padrao;
		this.texto=texto;
		
	}
	//O Token será gerado com o Tpo de Lexema,o Tipo de Token,o valor do lexema(seus caracteres)
	public Token(String lexema, TipoToken padrao,String texto,int linha) {
		this.lexema = lexema;
		this.padrao = padrao;
		this.texto=texto;
		this.linha=linha;
		
	}

	@Override
	public String toString() {
		
		
		return "< "+lexema+" , "+padrao+" , "+" Valor: "+texto+" , "+" Linha: "+linha+" >";
		
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

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	

}
