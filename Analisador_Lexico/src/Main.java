import java.io.IOException;
import java.util.List;
import java.util.Scanner;
//JOÃO PEDRO CAVANI MEIRELES RA:2321424
//Classe Main que faz a insatanciação da classe Analisador lexico e Token,dando inicio a geração dos codigos


public class Main {
	List<Token>bufferTokens;
	
	
	public static void main(String[] args) throws IOException {
		
		//Será verificado se o usuario deseja ver a emissao dos tokens
		//Caso digite T será feito somente o analisador lexico com a impressão dos tokens e depois a analise sintatica+lexica,sem a impressão dos tokens
		//Caso qualquer outra letra seja digitada será iniciado somente o sintatico+lexico sem impressão dos tokens
		System.out.println("Caso deseje ver o analisador Lexico com a impressão de todos os tokens digite T,caso contrario digite F:");
		
		Scanner scanner = new Scanner(System.in);
		String lexico = scanner.nextLine();
		char T='T';
		char tezinho='t';
		
		if(String.valueOf(T).equals(lexico) ||String.valueOf(tezinho).equals(lexico)) {
			System.out.println("Os tokens serao gerados da seguinte forma:"+'\n'+"<Tipo de Token / Tipo de Operador / Conteudo do Token / Linha que se encontra no programa original >"+"\n");
			
			//Somente analisador léxico
			AnalisadorLexico lex=new AnalisadorLexico("programa1.gyh");//Funcao que lerá o arquivo informado
			Token t = lex.proximoToken();//Cria o token e faz a analise do primeiro token
				while(t != null && t.padrao != TipoToken.EOF) {//While que fica repetindo até não terem mais tokens gerados;
				    	t = lex.proximoToken();	
				    	System.out.println(t.toString());//Imprime os tokens
				    
				}
				System.out.println("Todos os Tokens emitidos com sucesso"+'\n');
		}
	
		
		//Sintatico +Lexico
		System.out.println('\n'+"Analisador Lexico + Sintático,Compiladores");
		System.out.println("O analisador lexico irá gerar os tokens e o sintático irá atuar da seguinte maneira");
		System.out.println("Lido <Token> :Token carregado no buffer");
		System.out.println("Match <Token> :Sequencia de Tokens correta identificada pelo analisador sintático"+'\n');
		
		
		AnalisadorLexico lex=new AnalisadorLexico("programa1.gyh");//Funcao que lerá o arquivo informado
		Analisador_Sintatico sint=new Analisador_Sintatico(lex);
		sint.Programa();
		
	}

}