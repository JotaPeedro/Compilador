import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Os tokens serão gerados da seguinte forma:"+'\n'+"<Tipo de Token,Tipo de Operador,Conteudo do Token>"+"\n");
		AnalisadorLexico lex=new AnalisadorLexico("programa12.gyh");
		Token t = lex.proximoToken();
		while(t != null) {
		    
		    System.out.println(t.toString());
		    t = lex.proximoToken();
		}
		
		
		
		
	}

}