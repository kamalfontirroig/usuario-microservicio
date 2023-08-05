
# Servicio Usuario 

Este microservicio es una pequeña API desarrollada en Java utilizando Spring Boot. Contiene un Dockerfile para facilitar la construcción y despliegue.

## Prerrequisitos

- Java 17
- Docker

## Configuración e Instalación

1. Clone el repositorio: 
   ```
   git clone https://github.com/kamalfontirroig/usuario-microservicio.git
   ```
2. Navegue al directorio del proyecto:
   ```
   cd usuario-microservicio
   ```
3. Construya la imagen Docker:
   ```
   docker build -t usuario-servicio .
   ```

## Uso

**Endpoint**: POST "/usuarios"

**Cuerpo de la solicitud**:

```json
{
    "email": "juan@rodriguez.org",
    "name": "Juan Rodriguez",
    "password": "hunt3rX1",
    "phones": [
        {
            "citycode": "1",
            "contrycode": "57",
            "number": "1234567"
        }
    ]
}
```

## Respuesta

#### La API devolverá una respuesta en formato JSON:


✅  `201  Created` - El usuario ha sido registrado, entregando un token JWT
```json
{
	"id": "6657344b-b71d-4289-bce5-c6eb82e0d1a9",
	"created": "2023-08-04T20:48:10.340+00:00",
	"modified": "2023-08-04T20:48:10.340+00:00",
	"lastLogin": "2023-08-04T20:48:10.340+00:00",
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxanUxMjNhbjNvQGRzcmlndWV6Lm9yZyIsImV4cCI6MTY5MTE4Mjk5MH0.RBHhq7bGQTfH8oHrWkcrNRjRSRg9YdYaFBxxA6X6OC_nZy_3KYqfG--55TELuXLC5-3AnR9oD7xN8MrdsVrpAw",
	"isActive": true
}
```

❌ `400 Bad Request` - La password debe contener, al menos, una letra mayúscula, dos dígitos, letras minúsculas.
```json
{
	"mensaje": "La clave es inválida"
}
```

❌ `400 Bad Request` - El formato del email debe ser simple: "aaaaa1@aaa2.com".
```json
{
	"mensaje": "Correo electrónico inválido"
}
```

⚠️ `409 Conflict` - El email ya fue utilizado para el registro de otro usuario.
```json
{
	"mensaje": "El correo ya está registrado"
}
```


