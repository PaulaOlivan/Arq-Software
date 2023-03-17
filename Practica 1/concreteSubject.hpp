/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: fichero encargado de la implementación concreta del subject
*/
#pragma once
#include <iostream>
#include "observer.hpp"
#include "subject.hpp"
#include <string>
#include <random>

using namespace std;

class Pantalla : public Subject {
    private:
        int state;
        int maximoClientes = 3;
        int strikes = 0;
        int ocupado = 1; // Libre = 1 (verdadero) ; Ocupado = 0 (falso) 

    public:

        Pantalla(string nombre, int _state): Subject(nombre), state(_state){
        };

        //Valor actualizado del estado, el pescatero coge siguiente número
        void setState(int newState){
            if(state!=-1){
                state = newState;
            }
            state = newState % maximoClientes;
            notify();
        }

        //Valor actual del estado para que los clientes puedan verlo
        int getState(){
            return state;
        }

        //Valor actual de ocupado
        int getOcupado(){
            return ocupado;
        }

        // Cuando un cliente tenga el turno de state, se marca como ocupado
        void setOcupado (){
            ocupado = 0;
            cout << "Turno ocupado\n";
            notify();
        }

        // Cuando el cliente se vaya, se marca como libre
        void libre(){
            ocupado = 1;
            cout << "Turno liberado\n";
            notify();
        }
        
        //Devuelve el turno para atender al cliente (no sera superior al número de clientes)
        int pedirTurno(){
            return (state+1)%maximoClientes;
        }
        
        //Aumenta el numero de strikes
        void denunciar(){
            strikes++;
            if (strikes == 3){ // Si hemos llegado al máximo de strikes cerramos la pescadería
                state = -1;
                notify();
            }
        }
};

class Inventario : public Subject {
    private:
        int maximoClientes = 0;
        int strikes = 0;
       
    public:

        Inventario(string nombre, int _state): Subject(nombre){
        {
            seed_seq seed{time(0)};
        };
        };

        //Devuelve un pescado con días hasta caducar entre 0 y 6
        int recibirPescado(){
            return rand() % 1;
        }
        
        // Añade un strike a los ya cometidos para saber su la pescaderia sigue funcionando
        void denunciar(){
            strikes++;
            notify();
        }

        //Devuelve el número de strikes
        int getStrikes(){
            return strikes;
        }


};

