# Proyecto Juego Slick

Videojuego 2D en Java construido sobre la librería Slick2D: recorre un mapa recogiendo objetos mientras un enemigo (NPC) te persigue por el escenario.

## Características

- Mapa de juego cargado desde un fichero Tiled (`mi_mapa.tmx`), con detección de obstáculos a partir de las capas del mapa.
- Máquina de estados del juego (`StateBasedGame`) con menú principal, selección de dificultad y estado de partida.
- Menú interactivo con botones sensibles al ratón (cambian de imagen al pasar el cursor o al pulsarlos) y tres niveles de dificultad (fácil, normal, difícil) que ajustan la velocidad del NPC perseguidor.
- Personaje del héroe con animaciones direccionales (arriba/abajo/izquierda/derecha) mediante spritesheets.
- NPC enemigo que persigue al héroe automáticamente calculando su posición relativa en cada fotograma, con posiciones de aparición aleatorias.
- Objetos coleccionables (espada, armadura, escudo, collar, botas) posicionados aleatoriamente en el mapa, con efecto de sonido al recogerlos.
- Música de fondo distinta para el menú y la partida, y efectos de sonido (recoger objeto, ser atrapado, fin de partida).
- Condición de derrota al ser alcanzado por el NPC, con su propio sonido de "game over".

## Tecnologías

- Java
- Slick2D (motor 2D sobre LWJGL: gráficos, sonido, animaciones, mapas Tiled)
- Apache Ant + NetBeans (estructura de proyecto NetBeans)

## Instalación / Cómo ejecutarlo

1. Abre el proyecto con NetBeans (o cualquier IDE compatible con Ant) — las librerías de Slick2D/LWJGL necesarias están incluidas para poder importarlo sin configuración adicional.
2. Compila y ejecuta la clase `StateMain` (contiene el `main` y arranca la máquina de estados).
3. En el menú, elige la dificultad y pulsa "Jugar"; recoge todos los objetos del mapa evitando que el NPC te alcance.

Requiere Java 7 o superior.

Ejercicio académico que practica el desarrollo de un juego 2D con Slick2D: gestión de estados de juego, mapas Tiled, animaciones por spritesheet, colisiones simples y audio.

## Licencia

GPL versión 3 (ver archivo [LICENSE](LICENSE)).
