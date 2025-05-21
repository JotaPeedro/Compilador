import java.util.ArrayList;
import java.util.List;

public class Analisador_Sintatico {
	private final static int Tamanho_Buffer=10;
	AnalisadorLexico lex;
	List<Token>bufferTokens;
	
	boolean end=false;
	
	public Analisador_Sintatico(AnalisadorLexico lex) {
		this.lex=lex;
		bufferTokens=new ArrayList<>();
		lerToken();
	}
	
	private void lerToken() {
		if(bufferTokens.size()>0) {
			bufferTokens.remove(0);
		}
		while(bufferTokens.size()<Tamanho_Buffer && !end) {
			if (end && !bufferTokens.isEmpty() && bufferTokens.get(bufferTokens.size() - 1).padrao == TipoToken.EOF) {
	            break;
	        }
			Token proximo=lex.proximoToken();
			bufferTokens.add(proximo);
			if(proximo.padrao==TipoToken.EOF) {
				end=true;
				
			}
			System.out.println("Lido"+proximo);
		}
	}
		
		Token lookahead(int k){
			if (bufferTokens.isEmpty()) {
				return null;
			}
			if(k-1>=bufferTokens.size()) {
				return bufferTokens.get(bufferTokens.size()-1);	
			}
			return bufferTokens.get(k-1);
		}
	

		void match(TipoToken tipo) {
			if(lookahead(1).padrao==tipo) {
				System.out.println("Match"+lookahead(1));
				lerToken();
			}else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}


		// Programa → ':' 'DEC' ListaDeclaracoes ':' 'PROG' ListaComandos;
		public void Programa() {
			match(TipoToken.Delim);
			match(TipoToken.PCDec);
			listaDeclaracoes();
			match(TipoToken.Delim);
			match(TipoToken.PCProg);
			listaComandos();
		}
		// ListaDeclaracoes → Declaracao LD;
		public void listaDeclaracoes() {
			Declaracao();
			LD();
		}

		// LD → ListaDeclaracoes | Ɛ
		public void LD() {
			if (lookahead(1).padrao == TipoToken.Var) {
				listaDeclaracoes();
			}
		}

		// Declaracao → VARIAVEL ':' TipoVar;
		public void Declaracao() {
			match(TipoToken.Var);
			match(TipoToken.Delim);
			TipoVar();
		}

		// TipoVar → 'INT' | 'REAL';
		public void TipoVar() {
			if (lookahead(1).padrao == TipoToken.PCInt) {
				match(TipoToken.PCInt);
			} else if (lookahead(1).padrao == TipoToken.PCReal) {
				match(TipoToken.PCReal);
			} else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// ExpressaoAritmetica → TermoAritmetico ExpressaoAritmetica’;
		public void ExpressaoAritmetica() {
			TermoAritmetico();
			ExpressaoAritmeticaLinha();
		}

		// ExpressaoAritmetica’ → EA ExpressaoAritmetica’ | Ɛ;
		public void ExpressaoAritmeticaLinha() {
			if (lookahead(1).padrao == TipoToken.OpAritSoma || lookahead(1).padrao == TipoToken.OpAritSub) {
				EA();
				ExpressaoAritmeticaLinha();
			}
		}

		// EA → '+' TermoAritmetico | '-' TermoAritmetico
		public void EA() {
			if (lookahead(1).padrao == TipoToken.OpAritSoma) {
				match(TipoToken.OpAritSoma);
				TermoAritmetico();
			} else if (lookahead(1).padrao == TipoToken.OpAritSub) {
				match(TipoToken.OpAritSub);
				TermoAritmetico();
			} else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// TermoAritmetico → FatorAritmetico TermoAritmetico’;
		public void TermoAritmetico() {
			FatorAritmetico();
			TermoAritmeticoLinha();
		}

		// TermoAritmetico’ → TA TermoAritmetico’ | Ɛ;
		public void TermoAritmeticoLinha() {
			if (lookahead(1).padrao == TipoToken.OpAritMult || lookahead(1).padrao == TipoToken.OpAritDiv) {
				TA();
				TermoAritmeticoLinha();
			}
		}

		// TA → '*' FatorAritmetico | '/' FatorAritmetico
		public void TA() {
			if (lookahead(1).padrao == TipoToken.OpAritMult) {
				match(TipoToken.OpAritMult);
				FatorAritmetico();
			} else if (lookahead(1).padrao == TipoToken.OpAritDiv) {
				match(TipoToken.OpAritDiv);
				FatorAritmetico();
			} else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// FatorAritmetico → NUMINT | NUMREAL | VARIAVEL | '(' ExpressaoAritmetica ')'
		public void FatorAritmetico() {
			switch (lookahead(1).padrao) {
				case NumInt: match(TipoToken.NumInt); break;
				case NumReal: match(TipoToken.NumReal); break;
				case Var: match(TipoToken.Var); break;
				case AbrePar:
					match(TipoToken.AbrePar);
					ExpressaoAritmetica();
					match(TipoToken.FechaPar);
					break;
				default:
					throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// ExpressaoRelacional → TermoRelacional ExpressaoRelacional’;
		public void ExpressaoRelacional() {
			TermoRelacional();
			ExpressaoRelacionalLinha();
		}

		// ExpressaoRelacional’ → OperadorBooleano TermoRelacional ExpressaoRelacional’|Ɛ
		public void ExpressaoRelacionalLinha() {
			if (lookahead(1).padrao == TipoToken.OpBoolE || lookahead(1).padrao == TipoToken.OpBoolOu) {
				OperadorBooleano();
				TermoRelacional();
				ExpressaoRelacionalLinha();
			}
		}

		// TermoRelacional → ExpressaoAritmetica OP_REL ExpressaoAritmetica | '(' ExpressaoRelacional ')';
		public void TermoRelacional() {
			if (lookahead(1).padrao == TipoToken.AbrePar) {
				match(TipoToken.AbrePar);
				ExpressaoRelacional();
				match(TipoToken.FechaPar);
			} else {
				ExpressaoAritmetica();
				if (lookahead(1).padrao == TipoToken.OpRelDif ||
						lookahead(1).padrao == TipoToken.OpRelIgual ||
						lookahead(1).padrao == TipoToken.OpRelMenor ||
						lookahead(1).padrao == TipoToken.OpRelMenorIgual ||
						lookahead(1).padrao == TipoToken.OpRelMaior ||
						lookahead(1).padrao == TipoToken.OpRelMaiorIgual) {

						match(lookahead(1).padrao); // consome qualquer relacional
					}
				ExpressaoAritmetica();
			}
		}

		// OperadorBooleano → 'E' | 'OU';
		public void OperadorBooleano() {
			if (lookahead(1).padrao == TipoToken.OpBoolE) {
				match(TipoToken.OpBoolE);
			} else if (lookahead(1).padrao == TipoToken.OpBoolOu) {
				match(TipoToken.OpBoolOu);
			} else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// ListaComandos → Comando LC;
		public void listaComandos() {
			Comando();
			LC();
		}

		// LC → ListaComandos |Ɛ
		public void LC() {
			if (comandoInicia(lookahead(1).padrao)) {
				listaComandos();
			}
		}

		// Comando → ComandoAtribuicao | ComandoEntrada | ComandoSaida | ComandoCondicao | ComandoRepeticao | SubAlgoritmo;
		public void Comando() {
			switch (lookahead(1).padrao) {
				case Var: ComandoAtribuicao(); break;
				case PCLer: ComandoEntrada(); break;
				case PCImprimir: ComandoSaida(); break;
				case PCSe: ComandoCondicao(); break;
				case PCEnqto: ComandoRepeticao(); break;
				case PCIni: SubAlgoritmo(); break;
				default:
					throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// ComandoAtribuicao → VARIAVEL ':=' ExpressaoAritmetica;
		public void ComandoAtribuicao() {
			match(TipoToken.Var);
			match(TipoToken.Atrib);
			ExpressaoAritmetica();
		}

		// ComandoEntrada → 'LER' VARIAVEL;
		public void ComandoEntrada() {
			match(TipoToken.PCLer);
			match(TipoToken.Var);
		}

		// ComandoSaida → 'IMPRIMIR' VARIAVEL | 'IMPRIMIR' CADEIA;
		public void ComandoSaida() {
			match(TipoToken.PCImprimir);
			if (lookahead(1).padrao == TipoToken.Var) {
				match(TipoToken.Var);
			} else if (lookahead(1).padrao == TipoToken.Cadeia) {
				match(TipoToken.Cadeia);
			} else {
				throw new RuntimeException( "ERRO SINANTICO NA LINHA :"+lookahead(1).linha+" Token do tipo>> "+lookahead(1).padrao+"conteudo "+lookahead(1).texto+ " inesperado");
			}
		}

		// ComandoCondicao → 'SE' ExpressaoRelacional 'ENTAO' Comando CD;
		public void ComandoCondicao() {
			match(TipoToken.PCSe);
			ExpressaoRelacional();
			match(TipoToken.PCEntao);
			Comando();
			CD();
		}

		// CD → 'SENAO' Comando | Ɛ
		public void CD() {
			if (lookahead(1).padrao == TipoToken.PCSenao) {
				match(TipoToken.PCSenao);
				Comando();
			}
		}

		// ComandoRepeticao → 'ENQTO' ExpressaoRelacional Comando;
		public void ComandoRepeticao() {
			match(TipoToken.PCEnqto);
			ExpressaoRelacional();
			Comando();
		}

		// SubAlgoritmo → 'INI' ListaComandos 'FIM';
		public void SubAlgoritmo() {
			match(TipoToken.PCIni);
			listaComandos();
			match(TipoToken.PCFim);
		}

		// Auxiliar para LC
		public boolean comandoInicia(TipoToken token) {
			return token == TipoToken.Var || token == TipoToken.PCLer || token == TipoToken.PCImprimir ||
				   token == TipoToken.PCSe || token == TipoToken.PCEnqto || token == TipoToken.PCIni;
		}
		
		
		
		
		
		
	}
	
	

