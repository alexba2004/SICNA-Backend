# SICNA - Sistema de Control de No Adeudo

## Índice

1. [Introducción](#introducción)
2. [Descripción General](#descripción-general)
3. [Beneficios](#beneficios)
4. [Requerimientos Funcionales](#requerimientos-funcionales)
5. [Criterios de Aceptación](#criterios-de-aceptación)
6. [Matriz de Rastreabilidad](#matriz-de-rastreabilidad)
7. [Tecnologías](#tecnologías)
8. [Arquitectura](#arquitectura)
9. [Licencia](#licencia)
10. [Contacto](#contacto)
11. [Despedida](#despedida)

## Introducción

Nombre del proyecto: SICNA.
Objetivo: Desarrollar un sistema que permita llevar el control de los préstamos que se realizan en las diferentes áreas, así como gestionar los adeudos por parte de los estudiantes.
Alcance: El sistema será escalable y dinámico para permitir la adaptabilidad a las necesidades de la institución, además de que se documentará el proceso con el fin de facilitar las modificaciones y mejoras al sistema.

## Descripción General

Descripción de la Aplicación: Aplicación web que permita registrar los préstamos de los objetos del inventario de una universidad y gestionar los adeudos de estos con el fin de llevar un control global y seguro.
Usuarios Destinatarios: El sistema está diseñado para ser utilizado por los encargados de las distintas áreas y departamentos de la universidad.

## Beneficios

- Facilitar la obtención del reporte de adeudo al momento para agilizar procesos y optimizar el tiempo.
- Llevar un registro seguro y un mayor control sobre los préstamos y adeudos en las diferentes áreas y departamentos de la universidad.
- Ahorrar tiempo en burocracia.

## Requerimientos Funcionales

1. Registro de usuarios (prestadores): El sistema debe permitir a los administradores registrar y gestionar las cuentas de los prestadores.
2. Autenticación y autorización: El sistema debe tener un mecanismo de autenticación seguro para asegurar la información y cuentas contenidas.
3. Gestión de áreas: Los administradores deben poder registrar y gestionar nuevas áreas en el sistema, lo que incluye agregar, editar y eliminar áreas según sea necesario.
4. Conexión al sistema de estudiantes: El sistema debe poder conectarse y usar los datos ya contenidos en una base de datos que administra la información de cada estudiante.
5. Conexión al sistema de inventario: El sistema debe poder conectarse a la información recopilada de cada producto con el que cuenta la universidad en sus áreas.
6. Registro de reportes: Los prestadores deben poder registrar reportes donde se indiquen todos los datos relacionados a un préstamo.
7. Generación de hojas de no adeudo: El sistema debe verificar automáticamente si un estudiante tiene adeudos pendientes en alguna área de la universidad para poder expedir el documento de no adeudo.
8. Notificaciones automáticas: El sistema debe detectar acciones pendientes o realizadas recientemente que cuenten con una alta importancia, como un préstamo pendiente o cerca de culminar.
9. Búsqueda y consulta de estudiantes: El sistema debe permitir a los prestadores y administradores buscar y consultar información de los estudiantes, incluyendo su estado de adeudos y el historial de productos entregados.
10. Gestión de permisos (roles): El sistema debe permitir a los administradores de sistemas asignar diferentes niveles de permisos a los prestadores, según sus responsabilidades y funciones dentro del proceso de no adeudo.

## Criterios de Aceptación

Especifica los criterios que deben cumplirse para considerar el requerimiento como completado satisfactoriamente.
Prioridad: Asigna una prioridad al requerimiento (por ejemplo: alta, media, baja).
Dependencias: Indica si el requerimiento tiene alguna dependencia de otros requerimientos o componentes del sistema.

## Matriz de Rastreabilidad

- ID del Requerimiento: Lista los identificadores de los requerimientos funcionales.
- Módulos o Componentes: Asocia cada requerimiento con los módulos o componentes específicos de la aplicación relacionados con su implementación.
- Casos de Uso: Identifica los casos de uso que están relacionados con cada requerimiento funcional.

## Tecnologías

Este proyecto utiliza las siguientes tecnologías:

- **Java**: Lenguaje de programación ampliamente utilizado en el desarrollo de aplicaciones empresariales.
- **Spring Boot**: Framework de Java para crear aplicaciones web de manera rápida y sencilla.
- **MySQL**: Sistema de gestión de bases de datos relacional.
- **Spring Web**: Módulo de Spring para el desarrollo de aplicaciones web.
- **Spring Security**: Módulo de Spring para la seguridad de aplicaciones.
- **Spring Data JPA**: Módulo de Spring para el acceso a datos basado en JPA.
- **WebSocket**: Protocolo de comunicación bidireccional en tiempo real.
- **Lombok**: Biblioteca de Java que reduce la necesidad de escribir código repetitivo.
- **Validation**: Módulo de Spring para la validación de datos.
- **Spring Boot DevTools**: Herramientas de desarrollo para mejorar la productividad.

## Arquitectura

Este proyecto está desarrollado bajo la arquitectura BFF (Backend For Frontend), que permite una mejor separación de preocupaciones y escalabilidad del sistema. BFF es una práctica arquitectónica que consiste en tener un backend dedicado para cada frontend o interfaz de usuario. Esto ayuda a optimizar la experiencia del usuario y simplificar el mantenimiento del código.

## Licencia

Este proyecto se encuentra bajo la Licencia MIT. Por favor, consulta el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Si tienes preguntas, sugerencias o comentarios, no dudes en ponerte en contacto conmigo. Puedes encontrarme en mis redes sociales.

## Despedida

Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarme. ¡Disfruta explorando mi perfil de GitHub!
