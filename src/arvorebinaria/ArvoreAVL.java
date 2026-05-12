package arvorebinaria;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    // Subclasse de No que armazena a altura em cache
    private class NoAVL extends No {
        int altura;

        NoAVL(T valor) {
            super(valor);
            this.altura = 0;
        }
    }

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    // Lê a altura do cache
    private int altura(No no) {
        if (no == null) return -1;
        return ((NoAVL) no).altura;
    }

    // Atualiza o cache do nó com base nos filhos
    private void atualizarAltura(No no) {
        ((NoAVL) no).altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    }

    private int getFatorBalanceamento(No no) {
        return no == null ? 0 : altura(no.esq) - altura(no.dir);
    }

    @Override
    public void adicionar(T valor) {
        raiz = adicionarRecursivo(raiz, valor);
    }

    @Override
    protected No adicionarRecursivo(No no, T valor) {
        if (no == null) {
            quantidade++;
            return new NoAVL(valor); // cria NoAVL em vez de No
        }

        int comp = comparador.compare(valor, no.valor);
        if (comp < 0) {
            no.esq = adicionarRecursivo(no.esq, valor);
        } else if (comp > 0) {
            no.dir = adicionarRecursivo(no.dir, valor);
        } else {
            return no; // duplicado ignorado
        }

        atualizarAltura(no); // atualiza cache antes de balancear
        return balancear(no);
    }

    @Override
    public boolean remover(T valor) {
        int qtdAntes = quantidade;
        raiz = removerRecursivo(raiz, valor);
        return quantidade < qtdAntes;
    }

    @Override
    protected No removerRecursivo(No no, T valor) {
        if (no == null) return null;

        int comp = comparador.compare(valor, no.valor);
        if (comp < 0) {
            no.esq = removerRecursivo(no.esq, valor);
        } else if (comp > 0) {
            no.dir = removerRecursivo(no.dir, valor);
        } else {
            quantidade--;
            if (no.esq == null) return no.dir;
            if (no.dir == null) return no.esq;

            No sucessor = no.dir;
            while (sucessor.esq != null) sucessor = sucessor.esq;

            no.valor = sucessor.valor;
            quantidade++;
            no.dir = removerRecursivo(no.dir, sucessor.valor);
        }

        atualizarAltura(no); // atualiza cache antes de balancear
        return balancear(no);
    }

    private No balancear(No no) {
        int fb = getFatorBalanceamento(no);

        if (fb > 1 && getFatorBalanceamento(no.esq) >= 0)
            return rotacaoDireita(no);

        if (fb > 1 && getFatorBalanceamento(no.esq) < 0) {
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }

        if (fb < -1 && getFatorBalanceamento(no.dir) <= 0)
            return rotacaoEsquerda(no);

        if (fb < -1 && getFatorBalanceamento(no.dir) > 0) {
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private No rotacaoDireita(No y) {
        No x = y.esq;
        No T2 = x.dir;
        x.dir = y;
        y.esq = T2;
        atualizarAltura(y); // filho primeiro
        atualizarAltura(x); // pai depois
        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.dir;
        No T2 = y.esq;
        y.esq = x;
        x.dir = T2;
        atualizarAltura(x); // filho primeiro
        atualizarAltura(y); // pai depois
        return y;
    }
}