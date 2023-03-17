/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: fichero encargado de la implementación concreta de los observer
*/
#pragma once
#include <iostream>
#include "observer.hpp"
#include "concreteSubject.hpp"
#include <string>

using namespace std;

class Pescatero : public Observer {
    protected:
        // Atributos
        int state;
        int ocupado;
        Pantalla *miPantalla;

    public:
        Pescatero(string nombre, Pantalla* pant): Observer(nombre, pant), miPantalla(pant){};
        // Atributo con los subjects a los que esta suscrito

        void aumentarTurno (){
            string a =  to_string(state+1);
            cout << "Aumento turno a " + a + "\n";
            miPantalla->setState(state+1);
        }

        //Devuelve el estado
        int miEstado(){
            return state;
        };

        //Devuelve true si esta ocupado 
        bool estaOcupado(){
            if (ocupado == 0){//Esta ocupado
                return true;
            }
            else {//No esta ocupado
                return false;
            }
        }

        // Método que actualiza el estado del subject
        void update(){
            state = miPantalla->getState();
            ocupado = miPantalla->getOcupado();
        };
};

class Cliente : public Observer {
    private:
        int state;
        int turno;
        Pantalla *miPantalla;
        
    public:

        Cliente(string nombre, int _turno, Pantalla* pant): Observer(nombre, pant), turno(_turno), miPantalla(pant){};
        
        bool esMiTurno() {
            if (turno == state){
                
                cout << "Es mi turno, y soy " + miNombre + "\n";
                miPantalla->setOcupado();
                return true;
            }
            else {//Aun no es mi turno, me dormiré en el main
                return false;
            }
        }

        //Devuelve el estado
        int miEstado(){
            return state;
        };

        void update(){
            state = miPantalla->getState();
        };


        void comprarPescado(){
            cout << "Un salmón por favor \n";
            miPantalla->libre();
        }
};

class Inspector : public Observer {
    private:
        int state;
        int turno;
        int miPescado;   
        Inventario *miInventario;
        Pantalla *miPantalla;
        
    public:

        Inspector(string nombre, int _turno, Pantalla* pant, Inventario* inv): Observer(nombre, pant), turno(_turno),
                                                                                miPantalla(pant), miInventario(inv){};
        
        bool esMiTurno() {
            if (turno == state){
                cout << "Soy " + miNombre + " y es mi turno \n";
                miPantalla->setOcupado();
                return true;
            }
            else { //Aun no es mi turno, me dormiré en el main
                return false;
            }
        }

        //Devuelve el estado
        int miEstado(){
            return state;
        };

        //Comprueba si el pescado esta caducado
        bool estaCaducado(){
            
            if (miPescado == 0){
                miPantalla->libre();
                cout << "Oh! No! Este pescado está caducado \n";
                return true;
            }
            else{
                miPantalla->libre();
                cout << "Genial, mi compra está en condiciones óptimas \n";
                return false;
            }
        }

        //Al tener dos clientes uno siempre tendrá los números pares y el otro los impares
        void update(){
            state = miPantalla->getState();
        };

        //Recibe el pescado
        void inspeccionarPescado(){
            miPescado = miInventario->recibirPescado();
        }
        
        //Denuncia al pescatero
        void avisar(){
            miPantalla->denunciar();
            miInventario->denunciar();
        };
};

