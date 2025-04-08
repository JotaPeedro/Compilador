import java.io.IOException;

public class AnalisadorLexico {
	public LeitorArquivo ldat;
	 private int estado;
	private int pos;
	public char conteudo[];
	public int linha=1;
	

	public AnalisadorLexico(String nome_arq) throws IOException {
		ldat=new LeitorArquivo(nome_arq);
		conteudo=ldat.getConteudo();
		
	}
	
	public Token proximoToken() {

		String text="";
		char currentChar;
		
		estado=0;
		while(true) {
			
			currentChar=nextChar();
			if(currentChar=='\n') {
				
				linha++;
			}
			
					
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
					}else if(isCharLower(currentChar)) {
						text+=currentChar;
						estado=46;
					}else if(isComment(currentChar)) {
						
						estado=47;
						
					}else if(isSum(currentChar)) {
						text+=currentChar;
						estado=50;
						
					}else if(isSub(currentChar)) {
						text+=currentChar;
						estado=51;
						
					}else if(isDiv(currentChar)) {
						text+=currentChar;
						estado=52;
					}else if(isMult(currentChar)) {
						text+=currentChar;
						estado=53;
					}else if(isD(currentChar)) {
						text+=currentChar;
						estado=11;
					}else if(isP(currentChar)) {
						text+=currentChar;
						estado=13;
					}else if(isI(currentChar)) {
						text+=currentChar;
						estado=23;
					}else if(isL(currentChar)) {
						text+=currentChar;
						estado=16;
					}else if(isS(currentChar)) {
						text+=currentChar;
						estado=31;
					}else if(isE(currentChar)) {
						text+=currentChar;
						estado=35;
					}else if(isF(currentChar)) {
						text+=currentChar;
						estado=18;
					}else if(isR(currentChar)) {
						text+=currentChar;
						estado=20;
					}else if(isTwoPoint(currentChar)) {
						text+=currentChar;
						estado=41;
					}else if(isOpenP(currentChar)) {
						text+=currentChar;
						estado=54;
					}else if(isCloseP(currentChar)) {
						text+=currentChar;
						estado=55;
					}else if(isEOF()) {
						Token eoft=new Token("EOF",TipoToken.EOF,"eof");
						System.out.println(eoft.toString());
						return null;
						
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
					pos--;
					return new Token("<=",TipoToken.OpRelMenorIgual,text,linha);
					
				case 3:
					pos--;
					return new Token("<",TipoToken.OpRelMenor,text,linha);
					
				case 4:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=5;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 5:
					pos--;
					return new Token("==",TipoToken.OpRelIgual,text,linha);
					
				case 6:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=7;
					}else {
						
						estado=8;
					}
					break;
				case 7:
					pos--;
					return new Token(">=",TipoToken.OpRelMaiorIgual,text,linha);
				case 8:
					pos--;
					return new Token(">",TipoToken.OpRelMaior,text,linha);
				case 9:
					if(isEqual(currentChar)) {
						text+=currentChar;
						estado=10;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 10:
					pos--;
					return new Token("!=",TipoToken.OpRelDif,text,linha);
				case 11:
					if(isE(currentChar)) {
						text+=currentChar;
						estado=12;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 12:
					if(isC(currentChar)) {
						text+=currentChar;
						pos--;
						return new Token("PCDec",TipoToken.PCDec,text,linha);
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 13:
					if(isR(currentChar)) {
						text+=currentChar;
						estado=14;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 14:
					if(isO(currentChar)) {
						text+=currentChar;
						estado=15;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 15:
					if(isG(currentChar)) {
						text+=currentChar;
						return new Token("PCDProg",TipoToken.PCProg,text,linha);
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
						
					}
					
				case 16:
					if(isE(currentChar)) {
						text+=currentChar;
						estado=17;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 17:
					if(isR(currentChar)) {
						text+=currentChar;
						return new Token("PCLer",TipoToken.PCLer,text,linha);
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 18:
					if(isI(currentChar)) {
						text+=currentChar;
						estado=19;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 19:
					if(isM(currentChar)) {
						text+=currentChar;
						return new Token("PCFim",TipoToken.PCFim,text,linha);
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 20:
					if(isE(currentChar)) {
						text+=currentChar;
						estado=21;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 21:
					if(isA(currentChar)) {
						text+=currentChar;
						estado=22;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 22:
					if(isL(currentChar)) {
						text+=currentChar;
						return new Token("PCReal",TipoToken.PCReal,text,linha);
						
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
				case 23:
					if(isN(currentChar)) {
						text+=currentChar;
						estado=24;
					}else if(isM(currentChar)){
						text+=currentChar;
						estado=25;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");

					}
					break;
					
				case 24:
					if(isI(currentChar)) {
						text+=currentChar;
						return new Token("PCIni",TipoToken.PCIni,text,linha);
						
					}else if(isT(currentChar)) {
						text+=currentChar;
						return new Token("PCInt",TipoToken.PCInt,text,linha);

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 25:
					if(isP(currentChar)) {
						text+=currentChar;
						estado=26;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 26:
					if(isR(currentChar)) {
						text+=currentChar;
						estado=27;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 27:
					if(isI(currentChar)) {
						text+=currentChar;
						estado=28;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 28:
					if(isM(currentChar)) {
						text+=currentChar;
						estado=29;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 29:
					if(isI(currentChar)) {
						text+=currentChar;
						estado=30;
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 30:
					if(isR(currentChar)) {
						text+=currentChar;
						return new Token("PCInt",TipoToken.PCInt,text,linha);
					}else {
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 31:
					if(isE(currentChar)) {
						text+=currentChar;
						estado=32;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 32:
					if(isN(currentChar)) {
						text+=currentChar;
						estado=33;

					}else{
						return new Token("PCSe",TipoToken.PCSe,text,linha);
					}
					break;
				case 33:
					if(isA(currentChar)) {
						text+=currentChar;
						estado=34;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 34:
					if(isO(currentChar)) {
						text+=currentChar;
						return new Token("PCSenao",TipoToken.PCSenao,text,linha);

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 35:
					if(isN(currentChar)) {
						text+=currentChar;
						estado=36;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 36:
					if(isT(currentChar)) {
						text+=currentChar;
						estado=37;
						
					}else if(isQ(currentChar)) {
						text+=currentChar;
						estado=39;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 37:
					if(isA(currentChar)) {
						text+=currentChar;
						estado=38;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 38:
					if(isO(currentChar)) {
						text+=currentChar;
						return new Token("PCEntao",TipoToken.PCEntao,text,linha);

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
				case 39:
					if(isT(currentChar)) {
						text+=currentChar;
						estado=40;

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					break;
				case 40:
					if(isO(currentChar)) {
						text+=currentChar;
						return new Token("PCEnqto",TipoToken.PCEnqto,text,linha);

					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
				case 41:
					if(isEqual(currentChar)) {
						text+=currentChar;
						
						return new Token("PCAtrib",TipoToken.Atrib,text,linha);
					}else{
						pos--;
						estado=42;
						break;
					}
					
				case 42:
					 if(isCharUpper(currentChar)) {
						 pos--;
						
						return new Token("PCDelim",TipoToken.Delim,text,linha);
						
					}else{
						throw new RuntimeException("Simbolo não reconhecido");
					}
					
					
					
		
				case 43:
					if(isDigit(currentChar)) {
						text+=currentChar;
						estado=0;
					}else if(isPoint(currentChar)){
						text+=currentChar;
						estado=48;
						
						
					}else {
						pos--;
						estado=44;
					}
					break;
				case 44:
					pos--;
					return new Token("Inteiro",TipoToken.NumInt,text,linha);
				case 45:
					if(isAspas(currentChar)) {
						text+=currentChar;
						return new Token("Cadeia",TipoToken.Cadeia,text,linha);
					}else {
						pos--;
						text+=currentChar;
						estado=45;
					}
					break;
				case 46:
					if((isDigit(currentChar))||(isCharLower(currentChar))||(isCharUpper(currentChar))) {
						text+=currentChar;
						estado=46;
						
					}else {
						pos--;
						return new Token("Variavel",TipoToken.Var,text,linha);
					}
					break;
				case 47://COMENTARIOS ELE IRA IGNORAR ATE O FINAL DA LINHA
					if((currentChar=='\n') || (currentChar=='\r')) {
						estado=0;
					}else {
						estado=47;
					}
					break;
				case 48:
					if(isDigit(currentChar)) {
						text+=currentChar;
						estado=48;
					}else {
						estado=49;
					}
					break;
				case 49:
					pos--;
					return new Token("Real",TipoToken.NumReal,text,linha);
				case 50:
					pos--;
						return new Token("Soma",TipoToken.OpAritSoma,text,linha);

				case 51:
					pos--;
						return new Token("Subtração",TipoToken.OpAritSub,text,linha);

					
				case 52:
					pos--;
						return new Token("Divisão",TipoToken.OpAritDiv,text,linha);
					
				case 53:
					pos--;
						return new Token("Multiplicação",TipoToken.OpAritMult,text,linha);	
				case 54:
					pos--;
					return new Token("Abre Par",TipoToken.AbrePar,text,linha);
				case 55:
					pos--;
					return new Token("FechaPar",TipoToken.FechaPar,text,linha);
					
			}
			
		
			}
		
		
	}
	
	//Funções de Verificação

	
	private boolean isDigit(char c) {
		return c>= '0' && c<='9';
	
	}
	
	private boolean isA(char c) {
	return c=='A';
	}
	private boolean isE(char c) {
	return c=='E';
	}
	private boolean isC(char c) {
	return c=='C';
	}
	private boolean isS(char c) {
return c=='S';
	}
	private boolean isL(char c) {
return c=='L';
	}
	private boolean isI(char c) {
		return c=='I';
	}
	private boolean isP(char c) {
		return c=='P';
	}
	private boolean isD(char c) {
		return c=='D';
	}
	private boolean isM(char c) {
		return c=='M';
	}
	private boolean isR(char c) {
		return c=='R';
	}
	private boolean isG(char c) {
		return c=='G';
	}
	private boolean isO(char c) {
		return c=='O';
	}
	private boolean isF(char c) {
		return c=='F';
	}
	
	private boolean isT(char c) {
		return c=='T';
		}
	private boolean isQ(char c) {
		return c=='Q';
		}
	private boolean isN(char c) {
		return c=='N';
		}
	
	private boolean isSum(char c) {
		return c=='+';
	}
	
	private boolean isSub(char c) {
		return c=='-';
	}
	
	private boolean isDiv(char c) {
		return c=='/';
	}
	
	private boolean isMult(char c) {
		return c=='*';
	}
	
	private boolean isPoint(char c) {
		return c=='.';
	}

	private boolean isComment(char c) {
		return c== '#';
	
	}
	
	private boolean isTwoPoint(char c) {
		return c== ':';
	
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
	
	private boolean isOpenP(char c) {
		return c=='(';
	}
	
	private boolean isCloseP(char c) {
		return c==')';
	}
	
	
}


