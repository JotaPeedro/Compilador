import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		AnalisadorLexico lex=new AnalisadorLexico("arq.gyh");
		Token t = lex.proximoToken();
		while(t != null) {
		    
		    System.out.println(t.toString());
		    t = lex.proximoToken();
		}
		
		
		
		
	}

}