API para la Venta de Boletos de CineAPI RESTful desarrollada en Java y Spring Boot para la gestión y reserva de boletos de cine, control de funciones, administración de películas, salas y clientes, garantizando la validación de asientos disponibles para evitar sobreventas.Estado del Proyecto: Fase de Diseño y ArquitecturaActualmente se han definido los requerimientos de negocio, el modelo de datos (ER) y el diagrama de clases (UML). La fase de implementación del código y pipeline de DevOps está por iniciar.IntegrantesJosé Mira - mv25018 (@Mira-MV25018)Luis Castro - cm13032 (@CM13032)Angela Mena - mo23002 (@MO23002)Giovanni Zecena - zr25002 (@ZR25002)Ronald Bollates - be20001 (@BE20001)Objetivos del ProyectoDiseñar, construir, documentar y desplegar un backend funcional aplicando estándares de desarrollo actuales y prácticas de cultura DevOps:Implementación de una API RESTful con operaciones HTTP completas (GET, POST, PUT, DELETE).Estructuración mediante arquitectura en capas, uso del patrón DTO, librería Lombok y manejo global de excepciones.Garantía de calidad de software mediante pruebas unitarias con JUnit y Mockito.Contenerización de la aplicación utilizando Docker.Orquestación del despliegue en un entorno local de Kubernetes (Docker Desktop).Automatización del ciclo de vida del software (Build, Test, Deploy) construyendo un pipeline de CI/CD local utilizando act para simular GitHub Actions, gestionando de forma segura la conexión al clúster mediante KUBECONFIG.Descripción del Proyecto y Lógica de NegocioEl sistema permite gestionar el flujo de ventas de boletos de una cadena de cine:Películas y Salas: Una película se proyecta en diferentes salas de cine dentro de distintas funciones (horarios).Ventas y Boletos: Un cliente puede realizar compras de uno o más boletos para una función específica.Control de Asientos (Evitar Sobreventa): La API valida la capacidad total de asientos de la sala asignada a la función contra el número de asientos ocupados antes de confirmar cualquier venta.Tecnologías y HerramientasLenguaje: Java 17+Gestor de Proyecto y Dependencias: MavenFramework Backend: Spring Boot (Spring Data JPA, Spring Web)Librerías Adicionales: LombokPruebas: JUnit 5, MockitoBase de Datos: PostgreSQLContenerización y Orquestación: Docker, KubernetesDevOps y CI/CD: GitHub Actions (ejecutado localmente con act), KUBECONFIGPruebas de API: PostmanArquitectura y Diagramas (Definidos)1. Diagrama Entidad-Relación (Estructura de Tablas)Pelicula (idPelicula, titulo, duracion, clasificacion, genero)Sala (idSala, nombre, capacidadTotal)Cliente (idCliente, nombre, apellido, correo, telefono)Funcion (idFuncion, idPelicula, idSala, fechaHora, precioBase)Venta (idVenta, idCliente, fechaVenta, totalPagado)Boleto (idBoleto, idVenta, idFuncion, numeroAsiento, precioPagado)2. Diagrama de Clases (UML)    class Pelicula {
        -Long idPelicula
        -String titulo
        -Integer duracion
        -String clasificacion
        -String genero
    }

    class Sala {
        -Long idSala
        -String nombre
        -Integer capacidadTotal
    }

    class Cliente {
        -Long idCliente
        -String nombre
        -String apellido
        -String correo
        -String telefono
    }

    class Funcion {
        -Long idFuncion
        -LocalDateTime fechaHora
        -Double precioBase
        +verificarDisponibilidad(asientosOcupados: Integer) Boolean
    }

    class Venta {
        -Long idVenta
        -LocalDateTime fechaVenta
        -Double totalPagado
        +calcularTotalVenta() Double
    }

    class Boleto {
        -Long idBoleto
        -String numeroAsiento
        -Double precioPagado
    }

    Pelicula "1" -- "*" Funcion : asociación
    Sala "1" -- "*" Funcion : asociación
    Cliente "1" -- "*" Venta : asociación
    Funcion "1" -- "*" Boleto : asociación
    Venta "1" *-- "*" Boleto : composición
Estado de Avance / Roadmap[x] Fase 1: Definición de requisitos, alcance y arquitectura de la solución[x] Fase 2: Diseño del Diagrama Entidad-Relación (ER)[x] Fase 3: Diseño del Diagrama de Clases (UML)[ ] Fase 4: Construcción de la API REST (Arquitectura en capas, DTOs, Lombok, Controladores GET/POST/PUT/DELETE)[ ] Fase 5: Manejo global de excepciones y validación de reglas de negocio (control de asientos)[ ] Fase 6: Pruebas unitarias con JUnit y Mockito[ ] Fase 7: Contenerización con Docker y creación del Dockerfile[ ] Fase 8: Despliegue y orquestación en Kubernetes (Docker Desktop)[ ] Fase 9: Automatización de Pipeline CI/CD local con act (GitHub Actions)Endpoints Planificados (Especificación API REST)La API implementará las operaciones HTTP estándar (GET, POST, PUT, DELETE):Películas (/api/peliculas)MétodoEndpointDescripciónGET/api/peliculasListar todas las películasGET/api/peliculas/{id}Obtener detalle de una películaPOST/api/peliculasRegistrar nueva películaPUT/api/peliculas/{id}Actualizar datos de películaDELETE/api/peliculas/{id}Eliminar películaFunciones y Boletos (/api/funciones, /api/ventas)MétodoEndpointDescripciónGET/api/funcionesConsultar funciones programadasPOST/api/funcionesCrear una nueva funciónPOST/api/ventasProcesar una venta (valida disponibilidad de asientos)GET/api/ventas/{id}Consultar detalle de venta y boletosDELETE/api/ventas/{id}Cancelar una venta / boletoInstrucciones de Ejecución y Despliegue (Previsto)Esta sección contendrá los comandos de ejecución una vez iniciada la fase de código y automatización.Clonar el repositorio:git clone https://github.com/Mira-MV25018/Api-Rest-para-la-Venta-de-Boletos-de-Cine.git
cd Api-Rest-para-la-Venta-de-Boletos-de-Cine
Ejecutar Pruebas Unitarias:mvn test
Ejecución Local de Pipeline CI/CD con act:act
Despliegue en Kubernetes (Local):kubectl apply -f k8s/
