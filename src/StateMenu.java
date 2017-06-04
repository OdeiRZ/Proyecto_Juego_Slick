import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Clase que contiene los métodos principales que controlan el menú 
 * del juego del actual proyecto
 * 
 * @author Odei
 * @version 15.11.2015
 */
public class StateMenu extends BasicGameState {
    
    public static final int ID = 1;                                             //Variable que asigna el valor 1 al 'estado' del menú 
    
    private char DifiSelec;
    private boolean pulsado;
    private Sound seleccion;
    private Image fondo,btnJugar,btnSalir,btnDifi,btnDifiN,btnDifiF,btnDifiD;   //Imágenes que usaremos como botones en nuestro menú
    private float posX, posY;
    
    private final float btnJugarX = 375, btnJugarY = 130;                       //Coordenadas x e y de las imágenes usadas en el menú
    private final float btnDifiX = 382, btnDifiY = 202;
    private final float btnDifiNX = 525, btnDifiNY = 217;
    private final float btnDifiFX = 525, btnDifiFY = 192;
    private final float btnDifiDX = 525, btnDifiDY = 242;
    private final float btnSalirX = 367, btnSalirY = 270;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        fondo = new Image(StateJuego.ruta + "menu.jpg");
        btnJugar = new Image(StateJuego.ruta + "jugar.png");
        btnDifi = new Image(StateJuego.ruta + "dificultad.png");                //Asignamos las imágenes a las variables creadas
        btnDifiF = new Image(StateJuego.ruta + "dificultad-f.png");
        btnDifiN = new Image(StateJuego.ruta + "dificultad-n3.png");
        btnDifiD = new Image(StateJuego.ruta + "dificultad-d.png");
        btnSalir = new Image(StateJuego.ruta + "salir.png");
        //gc.setMouseCursor(StateJuego.ruta + "cursor.png",0,0);
        seleccion = new Sound(StateJuego.ruta + "coin-object.ogg");
        StateMain.musica = new Music(StateJuego.ruta + "menu.ogg");             //Añadimos música de fondo
        StateMain.musica.loop();                                                //y la reproducimos en bucle                   
        pulsado = false;
        DifiSelec = 'n';                                                        //Asignamos la dificultad seleccionada a n (normal)
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        posX = Mouse.getX();                                                    //Capturamos las coordenadas x e y del ratón
        posY = gc.getHeight() - Mouse.getY();                                   //invirtiendo las mismas en el eje y
        
        if ((posX>=btnJugarX && posX<=btnJugarX+btnJugar.getWidth()) &&
            (posY>=btnJugarY && posY<=btnJugarY+btnJugar.getHeight())) {        //Si el puntero del ratón entra en las coordenadas del botón comenzar
            btnJugar = new Image(StateJuego.ruta + "jugar2.png");               //cambiamos la imágen para dar la sensación de interacción con el botón
            if (Mouse.isButtonDown(0)) {                                        //y si pulsamos el botón izquierdo (0)
                seleccion.play();
                StateMain.musica = new Music(StateJuego.ruta + "music_land.ogg");//cambiamos la canción de fondo
                StateMain.musica.loop();
                sbg.enterState(StateJuego.ID);                                  //y lanzamos el 'estado' del juego
            }
        } else {
            btnJugar = new Image(StateJuego.ruta + "jugar.png");                //si le quitamos el foco al botón volerá a su imágen inicial
        }
            
        if ((posX>=btnDifiX && posX<=btnDifiX+btnDifi.getWidth()) && 
            (posY>=btnDifiY && posY<=btnDifiY+btnDifi.getHeight())) {           //Repetimos el proceso para el botón dificultad
            if (Mouse.isButtonDown(0)) {
                if(!seleccion.playing()) {
                    seleccion.play();
                }
                pulsado = true;
            }
            if (pulsado) {
                btnDifi = new Image(StateJuego.ruta + "dificultad4.png");
            } else {
                btnDifi = new Image(StateJuego.ruta + "dificultad2.png");
            }
        } else if (pulsado){
            btnDifi = new Image(StateJuego.ruta + "dificultad3.png");

            if ((posX>=btnDifiFX && posX<=btnDifiFX+btnDifiF.getWidth()) && 
                (posY>=btnDifiFY && posY<=btnDifiFY+btnDifiF.getHeight())) {    //Y de nuevo repetimos el proceso para el botón dificultad fácil
                if (Mouse.isButtonDown(0)) {
                    if(!seleccion.playing()) {
                        seleccion.play();
                    }
                    StateJuego.npcVelocidad = 0.04F;                            //en éste caso cambiamos la velocidad del enemigo por una más lenta
                    DifiSelec = 'f';                                            //y asignamos la dificulad a una variable que usamos para cambiar de color la imágen
                }
                if(DifiSelec == 'f') {
                    btnDifiF = new Image(StateJuego.ruta + "dificultad-f3.png");
                } else {
                    btnDifiF = new Image(StateJuego.ruta + "dificultad-f2.png");
                }
            } else if(DifiSelec != 'f') {
                btnDifiF = new Image(StateJuego.ruta + "dificultad-f.png");
            }

            if ((posX>=btnDifiNX && posX<=btnDifiDX+btnDifiN.getWidth()) && 
                (posY>=btnDifiNY && posY<=btnDifiNY+btnDifiN.getHeight())) {    //Repetimos el proceso para el botón dificultad media
                if (Mouse.isButtonDown(0)) {
                    if(!seleccion.playing()) {
                        seleccion.play();
                    }
                    StateJuego.npcVelocidad = 0.06F;
                    DifiSelec = 'n';
                }
                if(DifiSelec == 'n') {
                    btnDifiN = new Image(StateJuego.ruta + "dificultad-n3.png");
                } else {
                    btnDifiN = new Image(StateJuego.ruta + "dificultad-n2.png");
                }
            } else if(DifiSelec != 'n') {
                btnDifiN = new Image(StateJuego.ruta + "dificultad-n.png");
            }
            
            if ((posX>=btnDifiDX && posX<=btnDifiDX+btnDifiD.getWidth()) && 
                (posY>=btnDifiDY && posY<=btnDifiDY+btnDifiD.getHeight())) {    //y de nuevo para el botón dificultad alta
                if (Mouse.isButtonDown(0)) {
                    if(!seleccion.playing()) {
                        seleccion.play();
                    }
                    StateJuego.npcVelocidad = 0.08F;
                    DifiSelec = 'd';
                }
                if(DifiSelec == 'd') {
                    btnDifiD = new Image(StateJuego.ruta + "dificultad-d3.png");
                } else {
                    btnDifiD = new Image(StateJuego.ruta + "dificultad-d2.png");
                }
            } else if(DifiSelec != 'd') {
                btnDifiD = new Image(StateJuego.ruta + "dificultad-d.png");
            }
        } else {
            btnDifi = new Image(StateJuego.ruta + "dificultad.png");
        }
        
        if ((posX>=btnSalirX && posX<=btnSalirX+btnSalir.getWidth()) && 
            (posY>=btnSalirY && posY<=btnSalirY+btnSalir.getHeight())) {        //Hacemos lo mismo con el botón salir
            btnSalir = new Image(StateJuego.ruta + "salir2.png");
            if (Mouse.isButtonDown(0)) {
                seleccion.play();
                gc.sleep(200);                                                  //durmiendo el programa unos milisegundos para terminar de reproducir el audio
                gc.exit();                                                      //de selección previo cierre del mismo
            }
        } else {
            btnSalir = new Image(StateJuego.ruta + "salir.png");
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
            throws SlickException {
        fondo.draw();
        btnJugar.draw(btnJugarX, btnJugarY);
        btnDifi.draw(btnDifiX, btnDifiY);
        if (pulsado) {                                                          //Si hemos pulsado el botón dificultad
            btnDifiN.draw(btnDifiNX, btnDifiNY);                                //mostramos las opciones pertinentes
            btnDifiF.draw(btnDifiFX, btnDifiFY);
            btnDifiD.draw(btnDifiDX, btnDifiDY);
        }
        btnSalir.draw(btnSalirX, btnSalirY);
        g.drawString("Odei", 590, 455);
    }
    
    @Override
    public int getID() {
        return ID;
    }
}