
//JOÃO PEDRO CAVANI MEIRELES RA:2321424
import java.io.IOException;

//Classe principal do projeto,o analisador lexico em sim,que faz a leitura dos dados no array e gera o token baseado nos estados
//da linguagem GYH.

public class AnalisadorLexico {
	//Variaveis a serem utilizadas
	public LeitorArquivo ldat;
	private int estado;
	private int pos;
	public char conteudo[];
	public int linha = 1;
	private boolean eofJaEmitidoComoToken = false;
	
	public AnalisadorLexico(String nome_arq) throws IOException {
		ldat = new LeitorArquivo(nome_arq);
		conteudo = ldat.getConteudo();
	}
//A função analisador lexico faz basicamente todo o processo.Basicamente como um automato que irá fazer a mudança de estados com um switch/case
	public Token proximoToken() {
		if (eofJaEmitidoComoToken) {
           return new Token("EOF", TipoToken.EOF, "eof", linha);
       }
		String text = "";//Recebe todo o codigo e armazena em um vetor
		char currentChar;//Variavel para pegar o caractere atual da string text
		estado = 0;//Variacoes de estado do automato

		while (true) {//Loop para ficar analisando cada caracter da string text;
			
			currentChar = nextChar();//Pega o proximo caractere da string;
			  if (currentChar == (char) -1) { // Tratamento primário de EOF
	                this.eofJaEmitidoComoToken = true;
	                return new Token("EOF", TipoToken.EOF, "eof", linha);
	            }
			if (currentChar == '\n') linha++;//Verifica se é um /n para contar as linhas e imprimir nos tokens

			switch (estado) {//Basicamente o automato,onde estão todos os estados
				case 0://Estado 0 é o estado inicial que irá fazer a validação do primeiro caractere e direcionar aos proximos estados
					text="";
					if (isSpace(currentChar)) estado = 0;
					else if (isSmaller(currentChar)) { text += currentChar; estado = 1; }
					else if (isEqual(currentChar)) { text += currentChar; estado = 4; }
					else if (isBigger(currentChar)) { text += currentChar; estado = 6; }
					else if (isExclamation(currentChar)) { text += currentChar; estado = 9; }
					else if (isDigit(currentChar)) { text += currentChar; estado = 43; }
					else if (isAspas(currentChar)) { text += currentChar; estado = 45; }
					else if (isCharLower(currentChar)) { text += currentChar; estado = 46; }
					else if (isComment(currentChar)) { estado = 47; }
					else if (isSum(currentChar)) { 
                        text += currentChar; 
                        return new Token("Soma", TipoToken.OpAritSoma, text, linha);
                    } else if (isSub(currentChar)) {
                        text += currentChar;
                        return new Token("Subtração", TipoToken.OpAritSub, text, linha);
                    } else if (isDiv(currentChar)) {
                        text += currentChar;
                        return new Token("Divisão", TipoToken.OpAritDiv, text, linha);
                    } else if (isMult(currentChar)) {
                        text += currentChar;
                        return new Token("Multiplicação", TipoToken.OpAritMult, text, linha);
                    }
					else if (isD(currentChar)) { text += currentChar; estado = 11; }
					else if (isP(currentChar)) { text += currentChar; estado = 13; }
					else if (isI(currentChar)) { text += currentChar; estado = 23; }
					else if (isL(currentChar)) { text += currentChar; estado = 16; }
					else if (isS(currentChar)) { text += currentChar; estado = 31; }
					else if (isE(currentChar)) { text += currentChar; estado = 35; }
					else if (isF(currentChar)) { text += currentChar; estado = 18; }
					else if (isR(currentChar)) { text += currentChar; estado = 20; }
					else if (isTwoPoint(currentChar)) { text += currentChar; estado = 41; }
					else if (isOpenP(currentChar)) { text += currentChar; estado = 54; }
					else if (isCloseP(currentChar)) { text += currentChar; estado = 55; }
					else if (isO(currentChar)) {text += currentChar; estado = 56; }
					else if (isEOF()) {Token eoft = new Token("EOF", TipoToken.EOF, "eof");System.out.println(eoft.toString());return null;//Caso o ultimo caractere seja lido como fim do arquivo ele para o loop
					}else {throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");}
					break;//Caso não receba nenhum caracter reconhecido pela linguagem de inicio ele da erro e finaliza.

						//Cada caso abaixo verifica um caractere por vez,caso seja reconhecido,armazena o caractere em um vetor de texto para imprimir,...
					// ...vai para um proximo estado até chegar um estado final de palavra reconhecida pela linguagem assim imprimindo o token,
					//Caso encontre algo não reconhecido pela linguagem manda uma mensagem de erro e finaliza o programa.
				case 1: if (isEqual(currentChar)) { text += currentChar; estado = 2; } else estado = 3; break;
				case 2: pos--; return new Token("<=", TipoToken.OpRelMenorIgual, text, linha);
				case 3: pos--; return new Token("<", TipoToken.OpRelMenor, text, linha);
				case 4: if (isEqual(currentChar)) { text += currentChar; estado = 5; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 5: pos--; return new Token("==", TipoToken.OpRelIgual, text, linha);
				case 6: if (isEqual(currentChar)) { text += currentChar; estado = 7; } else estado = 8; break;
				case 7: pos--; return new Token(">=", TipoToken.OpRelMaiorIgual, text, linha);
				case 8: pos--; return new Token(">", TipoToken.OpRelMaior, text, linha);
				case 9: if (isEqual(currentChar)) { text += currentChar; estado = 10; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 10: pos--; return new Token("!=", TipoToken.OpRelDif, text, linha);
				case 11: if (isE(currentChar)) { text += currentChar; estado = 12; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 12: if (isC(currentChar)) { text += currentChar; return new Token("PCDec", TipoToken.PCDec, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 13: if (isR(currentChar)) { text += currentChar; estado = 14; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 14: if (isO(currentChar)) { text += currentChar; estado = 15; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 15: if (isG(currentChar)) { text += currentChar; return new Token("PCDProg", TipoToken.PCProg, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 16: if (isE(currentChar)) { text += currentChar; estado = 17; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 17: if (isR(currentChar)) { text += currentChar; return new Token("PCLer", TipoToken.PCLer, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 18: if (isI(currentChar)) { text += currentChar; estado = 19; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 19: if (isM(currentChar)) { text += currentChar; return new Token("PCFim", TipoToken.PCFim, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 20: if (isE(currentChar)) { text += currentChar; estado = 21; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 21: if (isA(currentChar)) { text += currentChar; estado = 22; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 22: if (isL(currentChar)) { text += currentChar; return new Token("PCReal", TipoToken.PCReal, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 23:
					if (isN(currentChar)) { text += currentChar; estado = 24; }
					else if (isM(currentChar)) { text += currentChar; estado = 25; }
					else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
					break;
				case 24:
					if (isI(currentChar)) return new Token("PCIni", TipoToken.PCIni, text += currentChar, linha);
					else if (isT(currentChar)) return new Token("PCInt", TipoToken.PCInt, text += currentChar, linha);
					else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 25: if (isP(currentChar)) { text += currentChar; estado = 26; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 26: if (isR(currentChar)) { text += currentChar; estado = 27; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 27: if (isI(currentChar)) { text += currentChar; estado = 28; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 28: if (isM(currentChar)) { text += currentChar; estado = 29; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 29: if (isI(currentChar)) { text += currentChar; estado = 30; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 30: if (isR(currentChar)) { text += currentChar; return new Token("PCImprimir", TipoToken.PCImprimir, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 31: if (isE(currentChar)) { text += currentChar; estado = 32; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 32: if (isN(currentChar)) { text += currentChar; estado = 33; } else return new Token("PCSe", TipoToken.PCSe, text, linha);
				case 33: if (isA(currentChar)) { text += currentChar; estado = 34; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 34: if (isO(currentChar)) { text += currentChar; return new Token("PCSenao", TipoToken.PCSenao, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 35: if (isN(currentChar)) { text += currentChar; estado = 36; } else return new Token("PCE", TipoToken.OpBoolE, text, linha); break;
				case 36:
					if (isT(currentChar)) { text += currentChar; estado = 37; }
					else if (isQ(currentChar)) { text += currentChar; estado = 39; }
					else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
					break;
				case 37: if (isA(currentChar)) { text += currentChar; estado = 38; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 38: if (isO(currentChar)) { text += currentChar; return new Token("PCEntao", TipoToken.PCEntao, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 39: if (isT(currentChar)) { text += currentChar; estado = 40; } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); break;
				case 40: if (isO(currentChar)) { text += currentChar; return new Token("PCEnqto", TipoToken.PCEnqto, text, linha); } else throw new RuntimeException( "ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				case 41:
					if (isEqual(currentChar)) {
						text += currentChar;
						return new Token("PCAtrib", TipoToken.Atrib, text, linha);
					} else {
						pos--;
						return new Token("PCDelim", TipoToken.Delim, text, linha);
					}
				
				
				case 43:
					if (isDigit(currentChar)) text += currentChar;
					else if (isPoint(currentChar)) {if (text.contains(".")) { throw new RuntimeException("ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido "); }
                     text += currentChar; estado = 48; }
					else {pos--; return new Token("Inteiro", TipoToken.NumInt, text, linha);}
					break;
				
				case 45:
					if (currentChar == (char) -1 || currentChar == '\n' || currentChar == '\r') {throw new RuntimeException("ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
					}else if (isAspas(currentChar)) return new Token("Cadeia", TipoToken.Cadeia, text += currentChar, linha);
					else {  text += currentChar; }
					break;
				case 46:
					if (isDigit(currentChar) || isCharLower(currentChar) || isCharUpper(currentChar)) text += currentChar;
					else { pos--; return new Token("Variavel", TipoToken.Var, text, linha); }
					break;
				case 47:
					if (currentChar == '\n' || currentChar == '\r') estado = 0;
					break;
				case 48:
					if (isDigit(currentChar)) {
						
					 text += currentChar;
                    
                 } else { // Não é mais dígito. Fim do número real.
                     pos--; 
                     if (text.endsWith(".")) { 
                         throw new RuntimeException("ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
                     }
                     return new Token("Real", TipoToken.NumReal, text, linha);
                 }
                 break;
				
				case 50: pos--; return new Token("Soma", TipoToken.OpAritSoma, text, linha);
				case 51: pos--; return new Token("Subtração", TipoToken.OpAritSub, text, linha);
				case 52: pos--; return new Token("Divisão", TipoToken.OpAritDiv, text, linha);
				case 53: pos--; return new Token("Multiplicação", TipoToken.OpAritMult, text, linha);
				case 54: pos--; return new Token("Abre Par", TipoToken.AbrePar, text, linha);
				case 55: pos--; return new Token("FechaPar", TipoToken.FechaPar, text, linha);
				case 56: if (isU(currentChar)) { text += currentChar; return new Token("PCOU", TipoToken.OpBoolOu, text, linha); } else throw new RuntimeException("ERRO LEXICO NA LINHA  "+linha+" Simbolo>>>  "+text+currentChar+"  <<<não reconhecido ");
				
			}
		}
	}

	// Funções de Verificação são utilizadas para verificar os caracteres,uma para cada caractere disponivel na linguagem.

	private boolean isDigit(char c) { return c >= '0' && c <= '9'; }
	private boolean isA(char c) { return c == 'A'; }
	private boolean isE(char c) { return c == 'E'; }
	private boolean isC(char c) { return c == 'C'; }
	private boolean isS(char c) { return c == 'S'; }
	private boolean isL(char c) { return c == 'L'; }
	private boolean isI(char c) { return c == 'I'; }
	private boolean isP(char c) { return c == 'P'; }
	private boolean isD(char c) { return c == 'D'; }
	private boolean isM(char c) { return c == 'M'; }
	private boolean isR(char c) { return c == 'R'; }
	private boolean isG(char c) { return c == 'G'; }
	private boolean isO(char c) { return c == 'O'; }
	private boolean isF(char c) { return c == 'F'; }
	private boolean isT(char c) { return c == 'T'; }
	private boolean isQ(char c) { return c == 'Q'; }
	private boolean isN(char c) { return c == 'N'; }
	private boolean isU(char c) { return c == 'U'; }
	private boolean isSum(char c) { return c == '+'; }
	private boolean isSub(char c) { return c == '-'; }
	private boolean isDiv(char c) { return c == '/'; }
	private boolean isMult(char c) { return c == '*'; }
	private boolean isPoint(char c) { return c == '.'; }
	private boolean isComment(char c) { return c == '#'; }
	private boolean isTwoPoint(char c) { return c == ':'; }
	private boolean isAspas(char c) { return c == '"'; }
	private boolean isCharLower(char c) { return c >= 'a' && c <= 'z'; }
	private boolean isCharUpper(char c) { return c >= 'A' && c <= 'Z'; }
	private boolean isBigger(char c) { return c == '>'; }
	private boolean isSmaller(char c) { return c == '<'; }
	private boolean isEqual(char c) { return c == '='; }
	private boolean isExclamation(char c) { return c == '!'; }
	private boolean isSpace(char c) {return c == ' ' || c == '\n' || c == '\t' || c == '\r';}
	private boolean isEOF() {return pos == conteudo.length;}
	private boolean isOpenP(char c) { return c == '('; }
	private boolean isCloseP(char c) { return c == ')'; }
	//Função next Char,avança o caractere para o proximo 
	private char nextChar() {return (pos < conteudo.length) ? conteudo[pos++] : (char) -1;}
}