import data.BinarySearchTree;
import model.Player;
import ui.TreeVisualizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BinarySearchTree bst = new BinarySearchTree();

        try {
            BufferedReader csvReader = new BufferedReader(new java.io.FileReader("sup/players.csv"));
            csvReader.readLine();
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    bst.insert(new Player(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                }
            }
            csvReader.close();
        } catch (java.io.IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("""
                    -_- RANKING DE JOGADORES -_-
                    [1] - Inserir jogador
                    [2] - Buscar jogador
                    [3] - Remover jogador
                    [4] - Exibir em ordem
                    
                    [0] - Sair
                    Escolha:
                    """);

            String choice = reader.readLine();

            if (choice == null || choice.equals("0")) {
                break;
            }

            switch (choice) {
                case "1" -> {
                    System.out.print("Nickname: ");
                    String nickname = reader.readLine();
                    System.out.print("Ranking: ");
                    int ranking = Integer.parseInt(reader.readLine().trim());
                    if (bst.containsRanking(ranking)) {
                        System.out.println("Já existe um jogador com o ranking " + ranking + ".");
                    } else {
                        bst.insert(new Player(nickname, ranking));
                        System.out.println("Jogador inserido com sucesso.");
                    }
                }
                case "2" -> {
                    System.out.print("Nickname: ");
                    String nickname = reader.readLine();
                    boolean found = bst.search(nickname);
                    System.out.println(found ? "Jogador encontrado." : "Jogador não encontrado.");
                    if (found) TreeVisualizer.openSearch(bst, nickname);
                }
                case "3" -> {
                    System.out.print("Nickname: ");
                    String nickname = reader.readLine();
                    Player removed = bst.remove(nickname);
                    if (removed != null) {
                        System.out.println("Jogador " + removed.getNickname() + " removido.");
                    } else {
                        System.out.println("Jogador não encontrado.");
                    }
                }
                case "4" -> {
                    bst.inOrder();
                    TreeVisualizer.openAll(bst);
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
