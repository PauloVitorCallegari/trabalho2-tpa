package arvorebinaria;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;

public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    protected class No {
        public T valor;
        public No esq, dir;

        public No(T valor) {
            this.valor = valor;
        }
    }

    protected No raiz;
    protected int quantidade;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
        this.quantidade = 0;
    }

    @Override
    public void adicionar(T valor) {
        raiz = adicionarRecursivo(raiz, valor);
    }

    protected No adicionarRecursivo(No no, T valor) {
        if (no == null) {
            quantidade++;
            return new No(valor);
        }
        int comp = comparador.compare(valor, no.valor);
        if (comp < 0) no.esq = adicionarRecursivo(no.esq, valor);
        else if (comp > 0) no.dir = adicionarRecursivo(no.dir, valor);
        return no;
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivo(raiz, valor);
    }

    private T pesquisarRecursivo(No no, T valor) {
        if (no == null) return null;
        int comp = comparador.compare(valor, no.valor);
        if (comp == 0) return no.valor;
        return comp < 0 ? pesquisarRecursivo(no.esq, valor) : pesquisarRecursivo(no.dir, valor);
    }

    @Override
    public boolean remover(T valor) {
        int q = quantidade;
        raiz = removerRecursivo(raiz, valor);
        return quantidade < q;
    }

    protected No removerRecursivo(No no, T valor) {
        if (no == null) return null;
        int comp = comparador.compare(valor, no.valor);
        if (comp < 0) no.esq = removerRecursivo(no.esq, valor);
        else if (comp > 0) no.dir = removerRecursivo(no.dir, valor);
        else {
            quantidade--;
            if (no.esq == null) return no.dir;
            if (no.dir == null) return no.esq;
            No substituto = encontrarMax(no.esq);
            no.valor = substituto.valor;
            quantidade++;
            no.esq = removerRecursivo(no.esq, substituto.valor);
        }
        return no;
    }

    private No encontrarMax(No no) {
        while (no.dir != null) no = no.dir;
        return no;
    }

    @Override
    public int quantidadeNos() { return quantidade; }

    @Override
    public int altura() { return alturaRecursiva(raiz); }

    private int alturaRecursiva(No no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaRecursiva(no.esq), alturaRecursiva(no.dir));
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder("[");
        caminharEmOrdemRec(raiz, sb);
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        return sb.append("]").toString();
    }

    private void caminharEmOrdemRec(No no, StringBuilder sb) {
        if (no != null) {
            caminharEmOrdemRec(no.esq, sb);
            sb.append(no.valor).append(", ");
            caminharEmOrdemRec(no.dir, sb);
        }
    }

    @Override
    public String caminharEmNivel() {
        if (raiz == null) return "[]";
        StringBuilder sb = new StringBuilder("[\n");
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            int nivelTamanho = fila.size();
            for (int i = 0; i < nivelTamanho; i++) {
                No atual = fila.poll();
                sb.append(atual.valor).append(i == nivelTamanho - 1 ? "" : ", ");
                if (atual.esq != null) fila.add(atual.esq);
                if (atual.dir != null) fila.add(atual.dir);
            }
            sb.append("\n");
        }
        return sb.append("]").toString();
    }
}