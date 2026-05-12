# 🌳 Trabalho 2 — Técnicas de Programação Avançada (TPA)

> Implementação e comparação de estruturas de dados baseadas em **Árvores** em Java.

---

## 📋 Sobre o Projeto

Este trabalho acadêmico implementa e compara três estruturas de árvore para gerenciar registros de **alunos** (matrícula, nome e nota), avaliando desempenho de inserção, busca e percurso em diferentes cenários de entrada.

As estruturas implementadas são:

- **Árvore Binária de Busca (BST)** — implementação manual (`ArvoreBinaria`)
- **Árvore AVL** — BST auto-balanceada (`ArvoreAVL`)
- **TreeMap do Java** — implementação da biblioteca padrão (`java.util.TreeMap`)

---

## 🗂️ Estrutura do Projeto

```
Arvores/
├── src/
│   ├── app/
│   │   ├── AppInterativo.java           # Interface interativa com o usuário
│   │   ├── AppTreeMap.java              # Demonstração do TreeMap do Java
│   │   └── ProgramaTestesDesempenho.java # Benchmark das estruturas (5 testes)
│   ├── arvorebinaria/
│   │   ├── ArvoreBinaria.java     # Árvore Binária de Busca
│   │   ├── ArvoreBinariaBase.java # Classe base / nó da árvore
│   │   └── ArvoreAVL.java         # Árvore AVL (auto-balanceada)
│   ├── colecao/
│   │   └── IColecao.java          # Interface genérica de coleção
│   └── modelo/
│       └── Aluno.java             # Modelo de dados (matrícula, nome, nota)
└── Arvores.iml
```

---

## 🧩 Classes Principais

### `Aluno` — `modelo/Aluno.java`
Representa um estudante com os atributos:
- `int matricula` — chave de identificação única
- `String nome`
- `double nota`

### `IColecao` — `colecao/IColecao.java`
Interface que define o contrato das operações suportadas pelas árvores (inserir, buscar, etc.).

### `ArvoreBinaria` — `arvorebinaria/ArvoreBinaria.java`
Árvore Binária de Busca (BST) clássica, sem balanceamento. O desempenho depende da ordem de inserção dos dados.

### `ArvoreAVL` — `arvorebinaria/ArvoreAVL.java`
Extensão da BST com **auto-balanceamento via rotações** (rotação simples e dupla), garantindo altura `O(log n)` mesmo no pior caso.

### `AppTreeMap` — `app/AppTreeMap.java`
Demonstração do uso do `java.util.TreeMap` (árvore rubro-negra) da biblioteca padrão do Java, utilizado como referência de comparação.

### `AppInterativo` — `app/AppInterativo.java`
Interface interativa para operações manuais com as árvores.

### `ProgramaTestesDesempenho` — `app/ProgramaTestesDesempenho.java`
Programa principal de benchmark. Lê um arquivo de alunos, popula uma árvore (BST ou AVL, alternável por comentário) e executa **5 testes cronometrados**:

| Teste | Descrição |
|-------|-----------|
| 1 | Leitura do arquivo e inserção de todos os registros |
| 2 | Pesquisa da **folha mais distante** (maior ID — pior caso de busca) |
| 3 | Pesquisa de um elemento **inexistente** (ID da folha + 1) |
| 4 | Remoção da folha mais distante |
| 5 | Contagem do total de nós (`quantidadeNos()`) |

Os tempos são medidos em nanossegundos e exibidos em milissegundos com alta precisão.

---

## 📂 Arquivos de Dados

O projeto utiliza arquivos `.csv` com registros de alunos para popular as árvores. O formato é:

```
<total_de_registros>
<matricula>;<nome>;<nota>
<matricula>;<nome>;<nota>
...
```

Exemplo:
```
2000000
1000000;Lorena Borges;4.71
500000;Vânia Elias;6.65
...
```

Os conjuntos de dados variam em tamanho (até 4 milhões de registros) e em **ordem de inserção**, permitindo avaliar o impacto no desempenho de cada estrutura:

| Arquivo            | Registros | Ordem de Inserção      |
|--------------------|-----------|------------------------|
| Conjunto 1         | 2.000.000 | Decrescente (pior caso BST) |
| Conjunto 2         | 1.000.000 | Decrescente            |
| Conjunto 3         | 2.000.000 | Crescente              |
| Conjunto 4         | 4.000.000 | Misto                  |

---

## ⚙️ Como Executar

### Pré-requisitos
- **Java JDK 21** ou superior
- IDE recomendada: IntelliJ IDEA (projeto já configurado com `.iml`)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/PauloVitorCallegari/trabalho2-tpa.git
   cd trabalho2-tpa
   ```

2. Abra o projeto no IntelliJ IDEA (ou compile via terminal):
   ```bash
   javac -d out src/**/*.java
   ```

3. Execute o programa de testes de desempenho:
   ```bash
   java -cp out app.ProgramaTestesDesempenho
   ```

   > Para alternar entre **BST** e **AVL**, edite a linha em `ProgramaTestesDesempenho.java`:
   > ```java
   > // Árvore Binária (BST):
   > IColecao<Aluno> arvore = new ArvoreBinaria<>((a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula()));
   >
   > // Árvore AVL:
   > IColecao<Aluno> arvore = new ArvoreAVL<>((a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula()));
   > ```
   > O arquivo de entrada também pode ser trocado na variável `nomeArquivo`.

4. Execute a aplicação interativa:
   ```bash
   java -cp out app.AppInterativo
   ```

---

## 📈 Exemplo de Saída — `ProgramaTestesDesempenho`

```
Iniciando testes (Árvore) para: 400000 registros. Arquivo: src/400kAlunosBalanceados.txt
1. Tempo para ler e inserir todos: 312.847 ms
2. Tempo para pesquisar a folha mais distante (ID 399999): 0.00312 ms
3. Tempo para pesquisar elemento INEXISTENTE (ID 400000): 0.00201 ms
4. Tempo para remover a folha mais distante (ID 399999): 0.00418 ms
5. Tempo para exibir quantidadeNos (399999 nós): 0.00089 ms
```

---

## 📊 Comparação das Estruturas

| Característica         | BST          | AVL              | TreeMap (Java) |
|------------------------|--------------|------------------|----------------|
| Balanceamento          | ❌ Não        | ✅ Sim (rotações) | ✅ Sim (Rubro-Negra) |
| Inserção (médio)       | O(log n)     | O(log n)         | O(log n)       |
| Inserção (pior caso)   | O(n)         | O(log n)         | O(log n)       |
| Busca (pior caso)      | O(n)         | O(log n)         | O(log n)       |
| Complexidade de impl.  | Baixa        | Média            | N/A (biblioteca) |

> **Conclusão esperada:** Para entradas ordenadas (crescente ou decrescente), a BST degenera em lista ligada (`O(n)`), enquanto AVL e TreeMap mantêm desempenho logarítmico.

---

## 👥 Autores

Trabalho desenvolvido para a disciplina de **Técnicas de Programação Avançada (TPA)**.

- [PauloVitorCallegari](https://github.com/PauloVitorCallegari)

---

## 🏫 Instituição

Curso de Ciência da Computação — Trabalho 2 de TPA
