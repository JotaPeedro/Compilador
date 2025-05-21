import java.io.IOException;
//JOÃO PEDRO CAVANI MEIRELES RA:2321424
//Classe Main que faz a insatanciação da classe Analisador lexico e Token,dando inicio a geração dos codigos
import java.util.List;

public class Main {
	List<Token>bufferTokens;
	public static void main(String[] args) throws IOException {
		System.out.println("Os tokens serão gerados da seguinte forma:"+'\n'+"<Tipo de Token,Tipo de Operador,Conteudo do Token,Linha que se encontra no programa original>"+"\n");
		AnalisadorLexico lex=new AnalisadorLexico(args[0]);//Funcao que lerá o arquivo informado
//		Token t = lex.proximoToken();//Cria o token e faz a analise do primeiro token
//		while(t != null) {//While que fica repetindo até não terem mais tokens gerados;
//		    
//		    System.out.println(t.toString());//Imprime os tokens
//		    t = lex.proximoToken();
//		}
		
	Analisador_Sintatico sint=new Analisador_Sintatico(lex);
	sint.Programa();
		
	}

}