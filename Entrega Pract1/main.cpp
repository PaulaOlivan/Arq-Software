/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: fichero encargado de la implementación en un caso práctico del
*             patrón observer
*/

#include <iostream>
#include <pthread.h>
#include "concreteObserver.hpp"
#include "concreteSubject.hpp"
#include <thread>
#include <chrono>

using namespace std;

void *comprador(void *arg){//Las funciones del comprador
    cout<<"\033[1;33mComprador creado\033[0m"<<endl;
    Cliente *cliente = (Cliente *) arg;
    while (cliente->miEstado()!=-1){
        if(!(cliente->esMiTurno())){ //Si no es mi turno, me duermo
            this_thread::sleep_for(chrono::seconds(2));
        }
        else{ //Si es mi turno, recibo el pescado
            cliente->comprarPescado();    
            this_thread::sleep_for(chrono::seconds(7));
        }
    }
}

void *pescatero(void *arg){
    Pescatero *pescatero = (Pescatero *) arg;
    cout<<"\033[1;34mPescatero creado\033[0m"<<endl;
    //Permite que los clientes entren primero
    this_thread::sleep_for(chrono::seconds(1));
    while (pescatero->miEstado()!=-1){
        pescatero->update();
        //Comprueba si esta ocupado
        cout << "Entro al while \n";
        if(pescatero->estaOcupado()){ // Si esta ocupado, me duermo y espero a que se vaya el cliente
            cout << "Me voy a dormir \n";
            this_thread::sleep_for(chrono::seconds(2));
        }
        else{ //Si no esta ocupado, atiendo al siguiente cliente
            cout << "Aumento turno \n";
            pescatero->aumentarTurno();
            this_thread::sleep_for(chrono::seconds(5));
        }
    }
    //pescatero->update(); Quitado porque solo debemos actualizar cuando aumentemos el turno?
}

void *inspector(void *arg){
    Inspector *inspector = (Inspector *) arg;
    cout<<"\033[1;31mInspector creado\033[0m"<<endl;
    while (inspector->miEstado()!=-1){
        if(!(inspector->esMiTurno())){ //Si no es mi turno, me duermo
            this_thread::sleep_for(chrono::seconds(2));
        }
        else{//Si es mi turno, inspecciono el pescado

            
            inspector->inspeccionarPescado();

            if (inspector->estaCaducado()){//Si esta caducado, aviso al pescatero
                inspector->avisar();
            }
            this_thread::sleep_for(chrono::seconds(7));
        }
    }
}

void *pantallaFun(void *arg){
    Pantalla *pantalla = (Pantalla *) arg;
    cout<<"Pantalla creada"<<endl;
    while (pantalla->getState()!=-1){
        // Actuamos normal en un bucle "infinito"
    }
    cout << "Cerramos pantalla \n";
    // Salimos del bucle porque nos han cerrado la pescadería
}


void *inventarioFun(void *arg){
    Inventario *inventario = (Inventario *) arg;
    cout<<"Inventario creado"<<endl;
    while (inventario->getStrikes() != 3){
        // Actuamos normal en un bucle "infinito"
    }
    cout << "Cerramos inventario \n";
    // Salimos del bucle porque nos han cerrado la pescadería
}	


int main(){

    cout << "Bienvenido al programa de la pescaderia" << endl;

    int estadoIni = 0;
    Pantalla pantalla ("Pantalla", estadoIni);

    Inventario inventario ("Inventario", estadoIni);
    
    Pescatero Juan ("Juan", &pantalla);

    Cliente MJ ("MJ", 0, &pantalla);
    Cliente Gregoria ("Gregoria", 1, &pantalla);

    Inspector Gadget ("Manolo", 2, &pantalla, &inventario);
    
    pantalla.attach(&Juan);
    pantalla.attach(&MJ);
    pantalla.attach(&Gregoria);
    pantalla.attach(&Gadget);
    inventario.attach(&Gadget);

    //pthreads
    pthread_t screen, fish, client1, client2, sanidad, inventory;

    cout << "\033[1;32mCreando threads\033[0m" << endl;
    //creación de los threads
    pthread_create(&screen, NULL, &pantallaFun, &pantalla);
    pthread_create(&inventory, NULL, &inventarioFun, &inventario);
    pthread_create(&fish, NULL, &pescatero, &Juan);
    pthread_create(&client1, NULL, &comprador, &MJ);
    pthread_create(&client2, NULL, &comprador, &Gregoria);
    pthread_create(&sanidad, NULL, &inspector, &Gadget);
    

    cout << "\033[1;32mThreads creados\033[0m" << endl;
    //Espera a que los threads terminen
    pthread_join(screen, NULL);
    pthread_join(inventory, NULL);
    pthread_join(fish, NULL);
    pthread_join(client1, NULL);
    pthread_join(client2, NULL);
    pthread_join(sanidad, NULL);

    
}