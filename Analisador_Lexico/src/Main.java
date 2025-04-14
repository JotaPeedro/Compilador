import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Os tokens serão gerados da seguinte forma:"+'\n'+"<Tipo de Token,Tipo de Operador,Conteudo do Token,Linha que se encontra no programa original>"+"\n");
		AnalisadorLexico lex=new AnalisadorLexico("programa12.gyh");//Funcao que lerá o arquivo informado
		Token t = lex.proximoToken();//Cria o token e faz a analise do primeiro token
		while(t != null) {//While que fica repetindo até não terem mais tokens gerados;
		    
		    System.out.println(t.toString());//Imprime os tokens
		    t = lex.proximoToken();
		}
		
		
		
		
	}

}