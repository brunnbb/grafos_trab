package grafos;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JList;

/**
 * Nesta classe devem ser implementados todos os métodos de grafos de forma
 * estática
 *
 * @author vilson.junior
 */
public class Algoritmos {

    public static ArrayList<Vertice> percorreProfundidade(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList<Vertice>();
        Deque<Vertice> pilha = new LinkedList<Vertice>();
        pilha.push(inicio);
        while (!pilha.isEmpty()) {
            Vertice atual = (Vertice) pilha.pop();
            if (atual.obterVisitado() == 0) {
                atual.visitar();
                resultado.add(atual);

                for (Arco arco : atual.obterArcos()) {
                    if (arco.getDestino().obterVisitado() == 0) {
                        pilha.push(arco.getDestino());
                    }
                }
            }
        }
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
        }

        return resultado;
    }

    public static ArrayList<Vertice> percorreLargura(Grafo g, Vertice inicio) {
        ArrayList<Vertice> resultado = new ArrayList();
        Queue<Vertice> fila = new LinkedList<Vertice>();
        fila.add(inicio);
        while (!fila.isEmpty()) {
            Vertice atual = (Vertice) fila.poll();
            if (atual.obterVisitado() == 0) {
                atual.visitar();
                resultado.add(atual);

                for (Arco arco : atual.obterArcos()) {
                    if (arco.getDestino().obterVisitado() == 0) {
                        fila.add(arco.getDestino());

                    }
                }
            }
        }
        for (Vertice vertice : g.obterVertices()) {
            vertice.zerarVisitas();
        }

        return resultado;
    }

    public static Grafo calcularKruskal(Grafo g) {
        Grafo mstGrafo = new Grafo();
        double pesoTotal = 0;

        ArrayList<Arco> arcosOrdenados = new ArrayList<>(g.obterTodosOsArcos());
        Collections.sort(arcosOrdenados, Comparator.comparingDouble(Arco::getPeso));

        Map<Vertice, Integer> rotulos = new HashMap<>();
        int rotuloAtual = 1;        
        for (Vertice vertice : g.obterVertices()) {
            mstGrafo.adicionarVertice(vertice.toString());
            rotulos.put(vertice, rotuloAtual++);
        }

        for (Arco arco : arcosOrdenados) {
            Vertice origem = arco.getOrigem();
            Vertice destino = arco.getDestino();

            int rotuloOrigem = rotulos.get(origem);
            int rotuloDestino = rotulos.get(destino);

            if (rotuloOrigem != rotuloDestino) {
                mstGrafo.pesquisaVertice(origem.toString()).adicionarArco(mstGrafo.pesquisaVertice(destino.toString()),
                        arco.getPeso());
                mstGrafo.pesquisaVertice(destino.toString()).adicionarArco(mstGrafo.pesquisaVertice(origem.toString()),
                        arco.getPeso());
                pesoTotal += arco.getPeso();

                for (Map.Entry<Vertice, Integer> entry : rotulos.entrySet()) {
                    if (entry.getValue() == rotuloDestino) {
                        entry.setValue(rotuloOrigem);
                    }
                }
            }
        }
        mstGrafo.setPesoTotalMinimo(pesoTotal);
        return mstGrafo;
    }

}
