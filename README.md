# tbd-literatura-2-2018

### Para ejecutar el backend:

Crear la base de datos relacional usando el archivo:
```
backend/src/main/resources/literatura-db.sql
```

Inicializar mongo:
```
sudo service mongod start
```

Y cambiar la configuración del application.properties para conectarse a mongo y a la base de datos relacional:
```
backend/src/main/resources/application.properties
```

Indexador de tweets: Descargar elasticsearch y entrar a la carpeta para ejecutarlo con el comando:
```
./elasticsearch
```
Luego se usa para la indexación:
```
http://localhost:9200/twitter/data/_search
```

### Para ejecutar el frontend:

Instalar las dependencias de FrontEnd:
```
cd frontend/
npm install
```

Para correr frontend:

```
cd frontend/
npm run dev
```
