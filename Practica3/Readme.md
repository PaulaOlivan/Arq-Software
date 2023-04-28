# Inicializar el broker

Para inicializar el broker, primero se debe contar en hendrix con todas las clases relacionadas:

- 🟠 Broker.java
- 🟠 BrokerImpl.java
- 🟠 Servicio.java
- 🟠 Servidor.java
- 🟢 Cadenas.java
- 🟢 CadenasImpl.java
- 🔵 Mates.java
- 🔵 MatesImpl.java
- ⚫ Cliente.java

Se deben compilar mediante el comando **$ javac \*.java**

Empleando el comando 'java *nombreClase*' para ejecutar las clases y el comando 'rmiregistry *numPuerto*&' para preparar el rmi se realiza lo siguiente

- Se preparará el RMI con puerto 32004 y se ejecutará BrokerImpl en lab102-203
- Se preparará el RMI con puerto 32006 y seejecutará MatesImpl y CadenasImpl en lab102-206
- Se ejecutara Cliente desde cualquier lab102

Una vez se haya ejecutado estos pasos, al ejecutar Cliente se nos proporcionara las diferentes funciones de los servidores Mates y Cadenas, pudiendo ejecutarlas con el siguiente comando:

$ java Cliente *nombFuncion* *parametro1* *parametro2* *[...]*


