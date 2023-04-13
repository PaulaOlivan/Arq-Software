/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: Clase Observer. Clase abstracta que define la interfaz de los observadores.  
*/
#pragma once
#include <iostream>
#include <string>
//#include "subject.hpp"

using namespace std;

class Subject;

//Clase observer
class Observer
{
protected:
    string miNombre;
    Subject *miSubject;

public:
    //Constructor
    Observer(string nombre, Subject* pant): miNombre(nombre), miSubject(pant){};
    
    //Function that notifies the observer so it updates its state
    virtual void update()=0;

    void setAttached(Subject *subject)
    {
        miSubject = subject;
    }

    void detach()
    {
        miSubject = nullptr;
    }

};
