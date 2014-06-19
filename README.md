# Taller 1 - Tecnología de Objetos

## ToDo List

## Descripción de la problemática

El objetivo de esta tarea es programar una sencilla aplicación web,
usando técnicas de orientación a objetos.

Se requiere construir una aplicación web que permita registrar datos
de usuarios y sus preferencias.

El señor J. quiere tener una aplicación web para administrar fotografías
de su familia. Su idea es que cada miembro de la familia pueda subir sus fotos
y agregar un comentario (breve, no más de 100 caracteres). El sistema debe
permitir que los distintos miembros de la familia se registren en el sitio
(debe existir un "nombre de familia"). Cuando un miembro de la familia entra
a la aplicación, previa validación, puede ver todas las fotos que han subido
los miembros de la familia, y sus comentarios, y además, subir nuevas fotos.

Se pide que implemente esta aplicación web utilizando tecnologías Java:
Clases, Servlets, JSP, JavaBeans, etc.

Observaciones: Para el almacenamiento de información debe utilizar archivos.

Se debe entregar el código fuente y un documento donde se describan los objetos
usados, las páginas creadas, y la estrategia utilizada para resolver el problema.

## Cambios posteriores a la entrega

* En el servlet famipics.servlet.Landing.java se almacenan en la sesión
las rutas del directorio contenedor de imágenes, y la ruta del archivo XML.

* famipics.dao.jaxp.JaxpFactory.repositoryPath = System.getProperty("repoPath");