# Final UDP

Mi implementación es diferente al código original

## Módulos
### socket-client
Contiene el código relacionado al cliente UDP

### socket-model
Contiene el código relacionado al modelo `RegistroMeteorologico` y la clase utilizada por el servidor y el cliente para comunicarse `RequestUDP`

### socket-server
Contiene el código relacionado del servidor UDP

## Ejecución
 1. Compilar el proyecto maven desde el directorio principal
    
`mvn clean install`
 
2. Ejecutar el server

`java -jar ./socket-server/target/socket-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
 
3. Ejecutar el cliente

`java -jar ./socket-client/target/socket-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar `
