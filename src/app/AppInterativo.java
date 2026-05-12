package app;
import java.util.Scanner;
import java.util.Comparator;
import colecao.IColecao;
import arvorebinaria.ArvoreBinaria;
import modelo.Aluno;

public class AppInterativo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Configuração da Árvore Binária ===");
        System.out.println("Como deseja indexar os alunos?");
        System.out.println("1 - Por Matrícula");
        System.out.println("2 - Por Nome");
        System.out.print("Escolha: ");

        int escolhaIndex = Integer.parseInt(scanner.nextLine());
        Comparator<Aluno> comparador;

        if (escolhaIndex == 1) {
            comparador = (a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula());
            System.out.println("✔ Árvore indexada por Matrícula.");
        } else {
            comparador = (a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome());
            System.out.println("✔ Árvore indexada por Nome.");
        }

        // Instanciação da Árvore usando o Comparator escolhido
        IColecao<Aluno> colecaoAlunos = new ArvoreBinaria<>(comparador);

        int opcao = -1;
        System.out.println("\nBem-vindo ao Sistema de Gerenciamento de Alunos (Árvores)!");

        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println("1 - Adicionar novo aluno");
            System.out.println("2 - Pesquisar aluno");
            System.out.println("3 - Remover aluno");
            System.out.println("4 - Imprimir (Caminhamento em Ordem)");
            System.out.println("5 - Imprimir (Caminhamento em Nível)");
            System.out.println("6 - Ver quantidade de nós e altura");
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

                        colecaoAlunos.adicionar(new Aluno(mat, nome, nota));
                        System.out.println("✅ Aluno adicionado!");
                        break;

                    case 2:
                        System.out.print("Digite o dado para pesquisa (Matrícula ou Nome): ");
                        String busca = scanner.nextLine();

                        // Criamos o objeto de busca respeitando o critério escolhido
                        Aluno alvo = (escolhaIndex == 1) ?
                                new Aluno(Integer.parseInt(busca), "", 0) :
                                new Aluno(0, busca, 0);

                        Aluno encontrado = colecaoAlunos.pesquisar(alvo);
                        if (encontrado != null) {
                            System.out.println("✅ Encontrado: " + encontrado.toString());
                        } else {
                            System.out.println("❌ Não encontrado.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite o dado para remoção: ");
                        String rem = scanner.nextLine();

                        Aluno paraRemover = (escolhaIndex == 1) ?
                                new Aluno(Integer.parseInt(rem), "", 0) :
                                new Aluno(0, rem, 0);

                        if (colecaoAlunos.remover(paraRemover)) {
                            System.out.println("✅ Removido com sucesso!");
                        } else {
                            System.out.println("❌ Aluno não encontrado.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Exibindo em Ordem (Crescente) ---");
                        System.out.println(colecaoAlunos.toString());
                        break;

                    case 5:
                        System.out.println("\n--- Exibindo por Níveis (Topologia) ---");
                        // Precisamos fazer o cast para ArvoreBinaria para acessar o método específico
                        if (colecaoAlunos instanceof ArvoreBinaria) {
                            System.out.println(((ArvoreBinaria<Aluno>) colecaoAlunos).caminharEmNivel());
                        }
                        break;

                    case 6:
                        System.out.println("Quantidade total de nós: " + colecaoAlunos.quantidadeNos());
                        if (colecaoAlunos instanceof ArvoreBinaria) {
                            System.out.println("Altura da árvore: " + ((ArvoreBinaria<Aluno>) colecaoAlunos).altura());
                        }
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