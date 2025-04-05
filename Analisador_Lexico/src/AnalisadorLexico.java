import java.io.IOException;

public class AnalisadorLexico {
	public LeitorArquivo ldat;
	 private int estado;
	private int pos;
	public char conteudo[];

	public AnalisadorLexico(String nome_arq) throws IOException {
		ldat=new LeitorArquivo(nome_arq);
		conteudo=ldat.getConteudo();
		
	}
	
	public Token proximoToken() {
		String text="";
		char currentChar;
		if(isEOF()) {
			return null;
		}
		
		estado=0;
		while(true) {
			currentChar=nextChar();
			
			
			switch(estado) {
				case 0 :
					
					if(isSpace(currentChar)) {
							estado=0;
					}else if(isSmaller(currentChar)) {
						text+=currentChar;
						estado=1;
					}else if(isEqual(currentChar)) {
						text+=currentChar;
						estado=4;
						
					}else if(isBigger(currentChar)) {
						text+=currentChar;
						estado=6;
					
					}else if(isExclamation(currentChar)) {
						text+=currentChar;
						estado=9;
					}else if(isDigit(currentChar)) {
						text+=currentChar;
						estado=43;
						
					}else if(isAspas(currentChar)) {
						text+=currentChar;
						estado=45;
					}
						break;
				case 1:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=2;
						
					}else {
						
						estado=3;
					}
					break;
				case 2:
					
					return new Token("<=",TipoToken.OpRelMenorIgual,text);
					
				case 3:
					
					return new Token("<",TipoToken.OpRelMenor,text);
					
				case 4:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=5;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 5:
					
					return new Token("==",TipoToken.OpRelIgual,text);
					
				case 6:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=7;
					}else {
						
						estado=8;
					}
					break;
				case 7:
					
					return new Token(">=",TipoToken.OpRelMaiorIgual,text);
				case 8:
					
					return new Token(">",TipoToken.OpRelMaior,text);
				case 9:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=10;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 10:
					
					return new Token("!=",TipoToken.OpRelDif,text);
					
				case 43:
					if(isDigit(currentChar)) {
						text+=currentChar;
						estado=0;
					}else {
						
						estado=44;
					}
					break;
				case 44:
					return new Token("Inteiro",TipoToken.NumInt,text);
				case 45:
					if(isAspas(currentChar)) {
						text+=currentChar;
						return new Token("Cadeia",TipoToken.Cadeia,text);
					}else {
						text+=currentChar;
						estado=45;
					}
					
			}
			
		
			}
		
		
	}
	
	
	private void back() {
		pos--;
	}
	
	private boolean isDigit(char c) {
		return c>= '0' && c<='9';
	
	}

	private boolean isAspas(char c) {
		return c=='"';
	
	}
	
	private boolean isCharLower(char c) {
		return (c>= 'a' && c<='z');
	
	}
	
	private boolean isCharUpper(char c) {
		return (c>= 'A' && c<='Z');
	
	}

	private boolean isOperator(char c) {
		return c== '>' || c=='<' || c=='=';
	}
	
	private boolean isBigger(char c) {
		return c== '>' ;
	
	}
	
	private boolean isSmaller(char c) {
		return c== '<'  ;
	
	}
	
	private boolean isEqual(char c) {
		return c== '='  ;
	
	}
	
	private boolean isExclamation(char c) {
		return c== '!'  ;
	
	}
	
	private boolean isSpace(char c) {
		return c== ' ' ||c== '\n' ||c== '\t' ||c== '\r' ;
	
	}
	
	private char nextChar() {
	    if (pos < conteudo.length) {
	        return conteudo[pos++];
	    } else {
	        return (char) -1; 
	    }
	}
	
	private boolean isEOF() {
		return pos == conteudo.length;
	}
	
	
	
}


