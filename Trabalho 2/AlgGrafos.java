import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class AlgGrafos {
    public static void main(String[] args) {

        // Cria o grafo
        Graph g1 = new Graph(); 

        try {
            // Abre arquivo
            Scanner sc = new Scanner(new File("myfiles/grafo.txt"));

            // iterando as linhas do grafo
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                // Verifica se o grafo está de acordo com o padrão de arquivo
                if (!line.matches("\\d+(\\s=\\s)(\\d*\\s)*(\\d+)")) {
                    System.out.println("A linha ("+ line + ") do arquivo de entrada não segue o padrão");
                    return;
                }

                // Divide a linha
                String[] bruteFormat = line.split("=");

                // Pega o primeiro num como o id do vértice
                int vertexId = Integer.parseInt(bruteFormat[0].trim());

                // Divide a linha em espaços, converte o num para um int e o add no grafo
                Arrays.stream(bruteFormat[1].trim().split(" "))
                        .map(value -> Integer.parseInt(value.trim()))
                        .forEach((Integer i) -> g1.add_edge(vertexId, i));
            }
            sc.close();
        }  catch (FileNotFoundException e) {
            System.out.println("Arquivo de entrada não encontrado");
            throw new RuntimeException();
        }

        // Printa o Grafo
        g1.print();

        // Chama a função de reconhecimento de grafos AT-FREE
        // Em caso de false imprime o cojunto de vertices
        boolean result = g1.aTFreeRecognition();

        if (result) {
            System.out.println("\nÉ AT-Free\n");
        } else {
            System.out.println("\nNão é AT-Free\n");
        }

    }
}