package app;

import java.util.Scanner;
import java.util.TreeMap;
import modelo.Aluno;

public class AppTreeMap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Configuração da Árvore Nativa do Java (TreeMap) ===");
        System.out.println("Como deseja indexar os alunos?");
        System.out.println("1 - Por Matrícula");
        System.out.println("2 - Por Nome");
        System.out.print("Escolha: ");

        int escolhaIndex = Integer.parseInt(scanner.nextLine());

        // TreeMaps tipados separados — sem cast genérico inseguro
        TreeMap<Integer, Aluno> porMatricula = new TreeMap<>();
        TreeMap<String, Aluno>  porNome      = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        if (escolhaIndex == 1) {
            System.out.println("✔ TreeMap indexado por Matrícula.");
        } else {
            System.out.println("✔ TreeMap indexado por Nome.");
        }

        int opcao = -1;
        System.out.println("\nBem-vindo ao Sistema (Versão Biblioteca Padrão Java - TreeMap)!");

        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println("1 - Adicionar novo aluno");
            System.out.println("2 - Pesquisar aluno");
            System.out.println("3 - Remover aluno");
            System.out.println("4 - Imprimir (Caminhamento em Ordem)");
            System.out.println("5 - Ver quantidade de alunos");
            // Opção 6 do AppInterativo (caminhamento em nível) não existe no TreeMap
            System.out.println("   [Caminhamento em Nível não disponível no TreeMap]");
            System.out.println("0 - Sair");
            System.out.println("=================================");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Digite a matrícula: ");
                        int mat = Integer.parseInt(scanner.nextLine());
                        System.out.print("Digite o nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite a nota: ");
                        int nota = Integer.parseInt(scanner.nextLine());

                        Aluno novo = new Aluno(mat, nome, nota);

                        // put() faz inserção com balanceamento automático (Rubro-Negra) — O(log n)
                        // Equivale ao adicionar() da ArvoreBinaria
                        porMatricula.put(mat, novo);
                        porNome.put(nome, novo);
                        System.out.println("✅ Aluno adicionado ao TreeMap!");
                        break;

                    case 2:
                        System.out.print("Digite o dado para pesquisa (Matrícula ou Nome): ");
                        String busca = scanner.nextLine();

                        // get() busca em O(log n) — equivale ao pesquisar() da ArvoreBinaria
                        Aluno encontrado = (escolhaIndex == 1)
                                ? porMatricula.get(Integer.parseInt(busca))
                                : porNome.get(busca);

                        if (encontrado != null) {
                            System.out.println("✅ Encontrado no TreeMap: " + encontrado);
                        } else {
                            System.out.println("❌ Não encontrado.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite o dado para remoção: ");
                        String rem = scanner.nextLine();

                        // remove() retorna o valor removido ou null — O(log n)
                        // Equivale ao remover() da ArvoreBinaria
                        Aluno removido = (escolhaIndex == 1)
                                ? porMatricula.remove(Integer.parseInt(rem))
                                : porNome.remove(rem);

                        if (removido != null) {
                            // Mantém consistência entre os dois mapas
                            if (escolhaIndex == 1) porNome.remove(removido.getNome());
                            else porMatricula.remove(removido.getMatricula());
                            System.out.println("✅ Removido do TreeMap: " + removido);
                        } else {
                            System.out.println("❌ Aluno não encontrado.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Exibindo em Ordem (Crescente) ---");
                        // values() percorre na ordem das chaves — equivale ao caminharEmOrdem()
                        // O TreeMap já mantém os nós ordenados internamente — O(n)
                        if (escolhaIndex == 1) {
                            porMatricula.values().forEach(a -> System.out.println(a.toString()));
                        } else {
                            porNome.values().forEach(a -> System.out.println(a.toString()));
                        }
                        break;

                    case 5:
                        // size() é O(1) — equivale ao quantidadeNos() da ArvoreBinaria
                        int total = (escolhaIndex == 1) ? porMatricula.size() : porNome.size();
                        System.out.println("Quantidade total de alunos (size): " + total);
                        System.out.println("Nota: O TreeMap não expõe altura nativamente,");
                        System.out.println("pois os detalhes internos da Rubro-Negra são encapsulados.");
                        break;

                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;

                    default:
                        System.out.println("⚠️ Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Erro: Entrada inválida para o critério escolhido.");
            } catch (Exception e) {
                System.out.println("⚠️ Erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }
}