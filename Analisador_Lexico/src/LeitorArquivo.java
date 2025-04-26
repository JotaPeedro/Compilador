import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//JOÃO PEDRO CAVANI MEIRELES RA:2321424
//Classe que abre o arquivo e le os dados,utilizando a função reaAllBytes,ela pega todo o conteudo do arquivo e coloca em um array.
//para que possa ser lido pelo analisador lexico
public class LeitorArquivo {

    private char[] conteudo;
    private int estado;
    private int pos;

    public LeitorArquivo(String nomeArq) throws IOException {
        String txtConteudo = new String(Files.readAllBytes(Paths.get(nomeArq)));//Funcao que lerá todos os bytes do arquivo e armazena em uma string
        conteudo = txtConteudo.toCharArray();//Manda os dados lidos do texeto para um vetor de caracteres
        pos = 0;
        estado = 0;
    }

    // Getter para retornar o conteúdo
    public char[] getConteudo() {
        return conteudo;
    }

    // Opcional: retornar como String também, se quiser
    public String getConteudoComoString() {
        return new String(conteudo);
    }

    // Outros getters/setters se precisar
    public int getPos() {
        return pos;
    }

    public int getEstado() {
        return estado;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

	

	
}
