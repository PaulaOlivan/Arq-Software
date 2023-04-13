/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: Clase Subject. Clase abstracta que define la interfaz de los sujetos.  
*/
#pragma once
#include "subject.hpp"
#include <iostream>

using namespace std;

void Subject::attach(Observer *o) {
    if (o != nullptr && numObservers < MAX_OBSERVERS) {
        observers.push_back(o);
        o->setAttached(this);
    }

    else{
        cout << "No se puede añadir el observador" << endl;
    }
}

void Subject::detach(Observer *o) {
    if (numObservers > 0 && o != nullptr) {
        o->detach();
        observers.remove(o);
    }
        
}

void Subject::notify() {
    for (auto o : observers) {
        o->update();
    }
};