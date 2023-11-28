import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class JogoDasPalavrasComBD {
    private static List<String> allLines;

    public static void main(String[] args) {
        carregarPalavrasDoArquivo("sample.txt");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Jogo das Palavras!");

        // Sorteia uma letra
        char letraSorteada = sortearLetra();

        // Inicia o jogo
        System.out.println("A letra sorteada é: " + letraSorteada);
        System.out.println("Digite palavras que começam com a letra sorteada (4 ou mais letras).");
        System.out.println("Digite 'sair' para encerrar o jogo.");

        // Coleção para armazenar as palavras únicas
        Set<String> palavrasConhecidas = new HashSet<>();

        // Tempo máximo do jogo em segundos
        int tempoMaximo = 60;
        long tempoInicio = System.currentTimeMillis();

        while (true) {
            // Verifica o tempo decorrido
            long tempoDecorrido = (System.currentTimeMillis() - tempoInicio) / 1000;
            if (tempoDecorrido >= tempoMaximo) {
                break;
            }

            // Captura a palavra do jogador
            System.out.print("Digite uma palavra: ");
            String palavra = scanner.nextLine().toLowerCase();

            // Verifica se o jogador quer sair
            if (palavra.equals("sair")) {
                break;
            }

            // Verifica se a palavra é válida
            if (palavra.length() >= 4 && palavra.charAt(0) == letraSorteada) {
                // Verifica se a palavra está no banco de dados
                if (verificarPalavraNoBD(palavra)) {
                    // Verifica se a palavra já foi inserida
                    if (!palavrasConhecidas.contains(palavra)) {
                        palavrasConhecidas.add(palavra);
                        System.out.println("Palavra adicionada!");
                    } else {
                        System.out.println("Palavra repetida! Tente novamente.");
                    }
                } else {
                    System.out.println("Palavra inválida! Tente novamente.");
                }
            } else {
                System.out.println("Palavra inválida! Tente novamente.");
            }
        }

        // Fim do jogo
        System.out.println("Tempo esgotado! Fim do jogo.");
        System.out.println("Quantidade de palavras únicas: " + palavrasConhecidas.size());
        System.out.println("Palavras informadas: " + palavrasConhecidas);

        scanner.close();
    }

    // Método para sortear uma letra aleatória
    private static char sortearLetra() {
        Random random = new Random();
        // ASCII de 'a' é 97, e há 26 letras no alfabeto
        return (char) (random.nextInt(26) + 97);
    }

    // Método para carregar as palavras do arquivo
    private static void carregarPalavrasDoArquivo(String arquivo) {
        try {
            allLines = Files.readAllLines(Paths.get(arquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar se a palavra está no banco de dados (arquivo)
    private static boolean verificarPalavraNoBD(String palavra) {
        return allLines.contains(palavra);
    }
}