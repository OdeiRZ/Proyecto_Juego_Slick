import java.io.File;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Programa principal que lanza el contenedor utilizado para generar 
 * el menú y la pantalla de juego del proyecto
 * 
 * @author Odei
 * @version 15.11.2015
 */
public class StateMain extends StateBasedGame {
    
    public static Music musica;                                                 //Variable usada para poder cambiar la melodía desde cualquiera de los 'estados' del programa
    
    public static void main(String[] args) throws SlickException {
         System.setProperty("org.lwjgl.librarypath", 
                new File(new File(System.getProperty("user.dir"), "native"), 
                LWJGLUtil.getPlatformName()).getAbsolutePath());
          System.setProperty("net.java.games.input.librarypath", 
                  System.getProperty("org.lwjgl.librarypath"));
          try {
              AppGameContainer agc = new AppGameContainer(new StateMain());     //Creamos un contenedor para nuestro juego
              agc.setDisplayMode(640, 480, false);                              //estableciendo el tamaño de la ventana en 640x480
              agc.setTargetFrameRate(60);                                       //y la Tasa de Refresco (FrameRate) en 60
              agc.setVSync(true);                                               //además activamos la sincronización vertical 
              agc.setShowFPS(false);                                            //y desactivamos la visualización de FPS
              agc.setUpdateOnlyWhenVisible(false);                              
              agc.start();
          } catch (SlickException e) { }
    }

    public StateMain() {
        super("Juego Slick 2D");                                                //Título de la ventana del juego
    }
 
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new StateMenu());                                              //Agregamos el 'estado' del menú (arrancado por defecto)
        addState(new StateJuego());                                             //y el 'estado' del propio juego 
    }
}