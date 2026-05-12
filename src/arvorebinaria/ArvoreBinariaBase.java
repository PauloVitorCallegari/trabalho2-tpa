package arvorebinaria;

import java.util.Comparator;
import colecao.IColecao;

public abstract class ArvoreBinariaBase<T> implements IColecao<T> {
    protected final Comparator<T> comparador;

    protected ArvoreBinariaBase(Comparator<T> comparador) {
        this.comparador = comparador;
    }

    public abstract int altura();
    public abstract String caminharEmNivel();
    public abstract String caminharEmOrdem();

    @Override
    public String toString(){
        return caminharEmOrdem();
    }
}