
# Sergio Cabify Challange

Este es un poyecto de exhibición donde se resuelve el [reto técnico de Cabify](https://github.com/cabify/MobileChallenge).


## Requisitos
Para resolver los requisitos de este reto los he separado en 3 funcionalidades:

- **Tienda de productos**. Se muestra un listado de los productos disponibles junto con la información más importante de los mismos, tales como el nombre, el precio o si cuentan con alguna promoción especial.
- **Detalles de un producto**. Se muestra una vista con la información detallada del producto seleccionado resaltando en qué consiste la promoción que se le puede aplicar, además de poder añadir dicho producto al carrito en la cantidad que queramos.
- **Información del carrito**. Se muestra un listado de los productos, sus cantidades, el precio final (junto con el descuento que se le haya podido aplicar), incluyendo la posibilidad de editar la cantidad de unidades de cada producto. Además durante todo el momento la aplicación muestra una sección donde se puede consultar el precio total del carrito.


## Screenshots
<img src="https://github.com/SergioMunozGomez1996/SergioCabifyCallange/blob/master/screenshots/Store_Screenshot.png?raw=true" width="200"> <img src="https://github.com/SergioMunozGomez1996/SergioCabifyCallange/blob/master/screenshots/Item_Details_Screenshot.png?raw=true" width="200"> <img src="https://github.com/SergioMunozGomez1996/SergioCabifyCallange/blob/master/screenshots/Cart_Screenshot.png?raw=true" width="200">


## Arquitectura
Todo el proyecto está siguiendo patrones de diseño para asegurar las buenas prácticas como **Clean Architecture** para separar las diferentes funcionalidades de la aplicación en capas independientes:

- **Presentación**: Capa que representa todo lo relacionado con la gestión de las vistas, los viewmodels para la comuncación con otras capas y, en definitiva, todo lo que tenga que ver con los componentes propios de, en este caso, android.
- **Dominio**: Capa que representa toda la lógica de negocio que se usa en la aplicación. Esta capa es la principal ya que, al ser independiente de la tecnología, puede ser reutilizada en otros proyectos. En esta capa también se incluye un modelo de datos uniforme.
- **Data**: Capa que representa la gestión de las diferentes funetes de datos que pueda hacer uso la aplicación. En este caso el uso de las preferencias como de datos proporcionados por una API.

Para la comuncación más eficiente entre las diferentes capas se incluye la injección de dependencia.

#### MVVM
Como arquitectura propia de android he decidido usar la recomentada MVVM ya que nos proporcina una buena independencia y a la vez una buena integración de programación reactiva. He utilizado esta arquitectura porque es con la que más he trabajado, aunque otra arquitectura interesante hubiera sido MVI ya que es una evolución de MVVM unificando estados, además de que es muy útil para el uso con Compose.

#### Componentes de vistas y navegación
Debido a que este proyecto es una exposición de mi experiencia, he optado por utilizar de manera genérica el componente estandarizado **XML con binding** para el diseño de las vistas. Aunque para demostrar mis conocimientos de **Compose**, he decidido crear la vista del carrito usando esta tecnología e integrarla con la anterior.

Repescto al tema de la navegación he decidido utilizar el componente **navigation** que proporciona android ya que integra muy bien el uso de los fragments clásicos y permite tener, de una manera muy sencilla, un buen control de la navegación gracias a su grafos.

## Tests
Otra de las partes importantes para el desarrollo de una app es la creación de una pila de test para comprobar de una forma más rápida y sencilla el correcto comportamiento de la app.

Debido a que este proyecto es una prueba no están realizados todos los tests que se podrían incluir pero sí que he realizado varios test unitarios para probar diferentes funcionalidades de un caso de uso y de un viewModel.

## Librerías
En esta sección se exponen las diferentes librerías que se utilizan en el proyecto y para qué sirven:

- **Corrutinas y Flow**: Herramientas que sirven para la gestión de los diferentes hilos, la concurrencia y la programación reactiva. He elegido esta herramienta frente a otras como RxJava ya que tengo más experiencia trabajando con ello, además de que se integra mejor con kotlin.

- **Navigation**: Herramienta que sirve para la gestión de la navegación entre las diferentes vistas de la aplicación. Permite, de una forma sencilla, indicar diferentes acciones de navegación junto con el paso de parámetros. He elegido esta herramienta para la gestión de la navegación porque considero que facilita bastante dicha gestión y funciona muy bien junto con XML y binding. Si hubiera hecho la app 100% con Compose, hubiera sido más intereante usar Jetpack Compose Navigation.

- **Retrofit**: Herramienta utilizada para la gestión de la comunicación con el API que proporciona la información a la aplicación.

- **Hilt**: Herramienta utilizada para la gestión de la inyección de dependencia. He utilzado esta librería, frente a otras como Dagger, porque considero que tiene un uso mucho más sencillo para su cometido.

- **Glide**: Herramienta utilizada para la carga asíncrona de imágenes. He elegido esta herramienta porque he trabajando con ella en el pasado y considero que permite realizar su propósito de una forma muy rápida y sencilla.

- **DataStore**: Herramienta utilizada para la gestión de la persistencia de datos de la aplicación. Es cierto que se podría utilizar otra herramienta más potente para gestionar una base de datos como Room, pero considero que para la sencillez que implica la gestión de un carrito de compra y que no se necesita guardar en local la información de los productos, con el uso de las preferencias de DataStore es suficiente.

- **Jetpack Compose**: Herramienta utilizada para parte de la creación de vistas. Es una herramienta muy potente ya que utiliza la programación reactiva para actualizar la vistas creadas de forma automática.

- **MaterialIcons**: Herramienta de material utilzada para agregar paquetes de iconos a la aplicación usados en las vistas creadas por Compose.

- **Mockk**: Herramienta utilizada para la creación de test unitarios. He utilizado esta herramienta frente a otras como Mockito porque considero que tiene una mejor integración con otros elementos de kotlin como las corrutinas y flows, y tienen una forma sencilla de crear mocks para las estancias y datos de prueba para los test.

