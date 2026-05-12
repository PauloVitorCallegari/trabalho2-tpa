package app;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import arvorebinaria.ArvoreAVL;
import colecao.IColecao;
import arvorebinaria.ArvoreBinaria;
import modelo.Aluno;

public class ProgramaTestesDesempenho {

    public static void main(String[] args) {

        // TROQUE AQUI:
        String nomeArquivo = "src/400kAlunosBalanceados.txt";

        // Instanciando a Árvore Binária com o Comparator de Matrícula (Requisito da Etapa 2)
        // IColecao<Aluno> arvore = new ArvoreBinaria<>((a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula()));
        IColecao<Aluno> arvore = new ArvoreAVL<>((a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula()));
        int idFolhaMaisDistante = -1; // Guardará o maior ID, que sempre será a folha extrema direita

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            int numRegistros = Integer.parseInt(reader.readLine().trim());
            System.out.println("Iniciando testes (Árvore) para: " + numRegistros + " registros. Arquivo: " + nomeArquivo);

            String linha;
            long inicio, fim;
            double tempoMs;

            // =================================================================
            // TESTE 1: LER ARQUIVO E INSERIR TODOS
            // =================================================================
            inicio = System.nanoTime();
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                // Resolve o problema da vírgula flutuante nos arquivos gerados
                int nota = (int) Float.parseFloat(partes[2].replace(",", "."));

                Aluno a = new Aluno(id, nome, nota);
                arvore.adicionar(a);

                // Em árvores BST, o maior valor sempre desce para a folha mais profunda à direita
                if (id > idFolhaMaisDistante) {
                    idFolhaMaisDistante = id;
                }
            }
            fim = System.nanoTime();
            tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.printf("1. Tempo para ler e inserir todos: %.3f ms%n", tempoMs);

            // =================================================================
            // TESTE 2: PESQUISAR FOLHA MAIS DISTANTE (PIOR CASO)
            // =================================================================
            Aluno alunoMaisDistante = new Aluno(idFolhaMaisDistante, "", 0);
            inicio = System.nanoTime();
            arvore.pesquisar(alunoMaisDistante);
            fim = System.nanoTime();
            tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.printf("2. Tempo para pesquisar a folha mais distante (ID %d): %.5f ms%n", idFolhaMaisDistante, tempoMs);

            // =================================================================
            // TESTE 3: PESQUISAR ELEMENTO INEXISTENTE (FILHO DA FOLHA)
            // =================================================================
            Aluno alunoInexistente = new Aluno(idFolhaMaisDistante + 1, "", 0);
            inicio = System.nanoTime();
            arvore.pesquisar(alunoInexistente);
            fim = System.nanoTime();
            tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.printf("3. Tempo para pesquisar elemento INEXISTENTE (ID %d): %.5f ms%n", idFolhaMaisDistante + 1, tempoMs);

            // =================================================================
            // TESTE 4: REMOVER A FOLHA MAIS DISTANTE
            // =================================================================
            inicio = System.nanoTime();
            arvore.remover(alunoMaisDistante);
            fim = System.nanoTime();
            tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.printf("4. Tempo para remover a folha mais distante (ID %d): %.5f ms%n", idFolhaMaisDistante, tempoMs);

            // =================================================================
            // TESTE 5: QUANTIDADE DE NÓS
            // =================================================================
            inicio = System.nanoTime();
            int qtd = arvore.quantidadeNos();
            fim = System.nanoTime();
            tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.printf("5. Tempo para exibir quantidadeNos (%d nós): %.5f ms%n", qtd, tempoMs);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}