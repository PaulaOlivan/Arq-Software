package Broker;

public class Resultado<T> {
    private T valor;

        public Resultado(T valor) {
            this.valor = valor;
        }

        public T getValor() {
            return valor;
        }

        public void setValor(T valor) {
            this.valor = valor;
        }
    }
