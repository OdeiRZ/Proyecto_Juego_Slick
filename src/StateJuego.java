import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Clase que contiene los métodos principales que controlan el juego 
 * relativo al proyecto actual
 * 
 * @author Odei
 * @version 15.11.2015
 */
public class StateJuego extends BasicGameState {
    
    public static final int ID = 2;                                             //Variable que asigna el valor 2 al 'estado' del juego en sí 
    
    public static final String ruta = "src/data/";                              //Variable de tipo string con la ruta por defecto de los ficheros usados
    
    private TiledMap mapa;                                                      //Variables relativas a nuestro mapa de juego
    private int tileWidth, tileHeight, mapaWidth, mapaHeight;
    private int totalTilesWidth, totalTilesHeight;
    
    private SpriteSheet cuadrosHeroe;                                           //Variables relativas el estado del jugador (héroe)
    private float heroeX, heroeY, heroeVeloc ,heroeAntX, heroeAntY;
    private Animation heroe,heroeArriba,heroeDerecha,heroeAbajo,heroeIzquierda;
    private int heroeWidth, heroeHeight;
    private boolean heroeVivo, heroeGana;
         
    private SpriteSheet cuadrosNpc;                                             //Variables relativas el estado del enemigo (npc)
    private float npcX, npcY, npcAntX, npcAntY;
    private Animation npc, npcArriba, npcDerecha, npcAbajo, npcIzquierda;
    private int npcWidth, npcHeight;
    private final int[] ubicacionNpcX = {160, 480, 610, 33};                    //Ubicaciones de spawn del npc que seleccionaremos de manera aleatoria
    private final int[] ubicacionNpcY = {390, 70, 445, 32};
    public static float npcVelocidad;
    
    private Image espadas, armadura, escudo, collar, botas, barco;              //Variables relativas a los objetos 'interactivos' mostrados en el juego
    private int espadasWidth, espadasHeight, armaduraWidth, armaduraHeight;
    private int escudoWidth, escudoHeight, collarWidth, collarHeight;
    private int botasWidth, botasHeight, barcoWidth, barcoHeight;
    private float espadasX, armaduraX, escudoX, collarX, botasX;
    private float espadasY, armaduraY, escudoY, collarY, botasY;
    private final float[] ubicacionEspadasX = {98,415,415,290};                 //Ubicaciones de spawn de los objetos que seleccionaremos de manera aleatoria
    private final float[] ubicacionEspadasY = {98,32,450,257};
    private final float[] ubicacionArmaduraX = {575,33,193,225};
    private final float[] ubicacionArmaduraY = {32,257,2,420};
    private final float[] ubicacionEscudoX = {128,415,160,350};
    private final float[] ubicacionEscudoY = {320,320,257,65};
    private final float[] ubicacionCollarX = {610,480,130,35};
    private final float[] ubicacionCollarY = {450,205,32,420};
    private final float[] ubicacionPowerX = {480,258,607,290};
    private final float[] ubicacionPowerY = {168,2,290,318};
    private final float barcoX = 510, barcoY = 400;                             //Coordenadas del barco mostrado en la partida
    private boolean cogerEspadas,cogerArmadura,cogerEscudo,cogerCollar,cogerBotas;
    private Sound sonidoCapturado, sonidoCogeHerramienta;                       //Sonidos ejecutados durante la partida
    private Sound sonidoCaerAgujero, sonidoBarcoHundido, sonidoAplausos;
    
    private boolean[][] obstaculosBase, obstaculosHeroe, agujeros;              //Variables usadas para almacenar las coordenadas de obstáculos y agujeros
    
    private String msj;
    private int semilla;                                                        //Otras variables usadas durante el juego
    private UnicodeFont fuente;
    private float FP, tiempo;                                                   //Variable FP usada como 'factor de proximidad' al acercarse a objetos y tiempo como 'posición' de canción
    private Input entrada;
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        mapa = new TiledMap(ruta + "mi_mapa.tmx", ruta);                        //Cargamos mapa de juego
        cuadrosHeroe = new SpriteSheet(ruta + "caballero.png", 24, 32);
        cuadrosNpc = new SpriteSheet(ruta + "npc.png", 24, 32);                 //los spritesheets del héroe y el npc
        espadas = new Image(ruta + "espadas.png");                              //e imágenes de objetos que mostraremos durante el juego
        armadura = new Image(ruta + "armadura.png");
        escudo = new Image(ruta + "escudo.png");
        collar = new Image(ruta + "collar.png");
        botas = new Image(ruta + "botas.png");
        barco = new Image(ruta + "barcoFlotando.png");
        
        semilla = (int)(Math.random()*ubicacionEspadasX.length);                //Generamos posiciones pares aleatorias para posicionar los objetos en el mapa
        espadasX = ubicacionEspadasX[semilla];                                  //entre las posibles predefinidas con anterioridad
        espadasY = ubicacionEspadasY[semilla];
        semilla = (int)(Math.random()*ubicacionArmaduraX.length);
        armaduraX = ubicacionArmaduraX[semilla];
        armaduraY = ubicacionArmaduraY[semilla];
        semilla = (int)(Math.random()*ubicacionEscudoX.length);
        escudoX = ubicacionEscudoX[semilla];
        escudoY = ubicacionEscudoY[semilla];
        semilla = (int)(Math.random()*ubicacionCollarX.length);
        collarX = ubicacionCollarX[semilla];
        collarY = ubicacionCollarY[semilla];
        semilla = (int)(Math.random()*ubicacionPowerX.length);
        botasX = ubicacionPowerX[semilla];
        botasY = ubicacionPowerY[semilla];
        
        heroeX = 262;                                                           //Cargamos al héroe en la posición x e y original
        heroeY = 70;
        heroeVivo = true;                                                       //e indicamos que está vivo inicialmente
        heroeGana = false;
        heroeVeloc = 0.1f;                                                      //asignando su velocidad inicial
        heroeArriba = new Animation(cuadrosHeroe,0,0,2,0,true,150,false);
        heroeDerecha = new Animation(cuadrosHeroe,0,1,2,1,true,150,false);
        heroeAbajo = new Animation(cuadrosHeroe,0,2,2,2,true,150,false);        //Cargamos las animaciones del héroe
        heroeIzquierda = new Animation(cuadrosHeroe,0,3,2,3,true,150,false);
        heroe = heroeAbajo;                                                     //y lo inicializamos en la posición de abajo

        semilla = (int)(Math.random()*ubicacionNpcX.length);                    //Generamos un número aleatorio para posicionar al npc 
        npcX = ubicacionNpcX[semilla];                                          //entre algunas de las ubicaciones predefinidas
        npcY = ubicacionNpcY[semilla];
        npcVelocidad = 0.06F;                                                   //Le asignamos una velocidad inferior al héroe que puede ser cambiada
        npcArriba = new Animation(cuadrosNpc,0,0,2,0,true,150,true);
        npcDerecha = new Animation(cuadrosNpc,0,1,2,1,true,150,true);           
        npcAbajo = new Animation(cuadrosNpc,0,2,2,2,true,150,true);             //Cargamos las animaciones del npc
        npcIzquierda = new Animation(cuadrosNpc,0,3,2,3,true,150,true);
        npc = npcAbajo;                                                         //y lo inicializamos en la posición de abajo
                
        mapaWidth = mapa.getWidth()*mapa.getTileWidth();                        //Obtenemos tamaños de varias variables
        mapaHeight = mapa.getHeight()*mapa.getTileHeight();                     //que necesitaremos durante el juego
        totalTilesWidth = mapa.getWidth();
        totalTilesHeight = mapa.getHeight();
        heroeWidth = heroe.getWidth();
        heroeHeight = heroe.getHeight();
        npcWidth = npc.getWidth();
        npcHeight = npc.getHeight();
        tileWidth = mapa.getTileWidth();
        tileHeight = mapa.getTileHeight();
        espadasWidth = espadas.getWidth();
        espadasHeight = espadas.getHeight();
        armaduraWidth = armadura.getWidth();
        armaduraHeight = armadura.getHeight();
        escudoWidth = escudo.getWidth();
        escudoHeight = escudo.getHeight();
        collarWidth = collar.getWidth();
        collarHeight = collar.getHeight();
        botasWidth = botas.getWidth();
        botasHeight = botas.getHeight();
        barcoWidth = barco.getWidth();
        barcoHeight = barco.getHeight();
        
        obstaculosBase = new boolean[totalTilesWidth][totalTilesHeight];
        obstaculosHeroe = new boolean[totalTilesWidth][totalTilesHeight];
        for (int x = 0; x < totalTilesWidth; x++) {
            for (int y = 0; y < totalTilesHeight; y++) {
                obstaculosBase[x][y] = (mapa.getTileId(x, y, 2) != 0);          //Generamos matriz con los obstáculos que afectaran tanto al héroe como al npc (2)
                obstaculosHeroe[x][y] = (mapa.getTileId(x, y, 3) != 0);         //a su vez generamos otra matriz con los obstáculos que sólo afectaran al héroe (3)
            }
        }
        agujeros = new boolean[totalTilesWidth][totalTilesHeight];
        for (int x = 0; x < totalTilesWidth; x++) {
            for (int y = 0; y < totalTilesHeight; y++) {
                agujeros[x][y] = (mapa.getTileId(x, y, 5) != 0);                //Hacemos lo mismo con lso agujeros que sólo afectaran al héroe (5)
            }
        }
        
        cogerEspadas = false;                                                   //Asignamos valores iniciales a todas las variables de tipo boolean que usaremos
        cogerArmadura = false;
        cogerEscudo = false;
        cogerCollar = false;
        cogerBotas = false;
        heroeVivo = true;
        heroeGana = false;
        
        fuente = new UnicodeFont(ruta + "llcooper.ttf", 30, false, false);      //Cargamos fuente que usaremos para mostrar mensajes en pantalla
        fuente.addAsciiGlyphs();                                                //Añadimos las letras ASCII estándar
        fuente.addGlyphs("áéíóúÑÁÉÍÓÚÑ");                                       //y los caracteres españoles
        fuente.getEffects().add(new ColorEffect(java.awt.Color.decode("#542589"))); //Agregamos color a la fuente utilizada
        fuente.loadGlyphs();                                                    //y cargamos los símbolos del tipo de letra

        sonidoCapturado = new Sound(ruta + "game-over.ogg");                    //Cargamos los efectos de sonido cuando el héroe es capturado por el npc
        sonidoCogeHerramienta = new Sound(ruta + "coin-object.ogg");            //Cuando el héroe recoge cada uno de los objetos
        sonidoCaerAgujero = new Sound(ruta + "falling-whistle.ogg");            //Cuando el héroe cae por alguno de los agujeros
        sonidoAplausos = new Sound(ruta + "success-1.ogg");                     //Cuando el héroe ha ganado la partida
        sonidoBarcoHundido = new Sound(ruta + "falling-down-water.ogg");        //Cuando el héroe toca el barco
        
        FP = 0.5F;                                                              //Establecemos el factor de proximidad para que no sea muy difícil obtener los objetos cuando pasemos por su lado
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        entrada = gc.getInput();                                                //Capturamos la tecla pulsada para operar con ella
        heroeAntX = heroeX;                                                     //y obtenemos las coordenadas previas del héroe y npc
        heroeAntY = heroeY;                                                     //para posteriormente desplazarlas en el mapa o no
        npcAntX = npcX;
        npcAntY = npcY;
        
        if (!heroeVivo || heroeGana) {                                          //Si ha muerto el héroe o ha ganado ya no se actualiza nada
            StateMain.musica.stop();
            if (entrada.isKeyDown(Input.KEY_ENTER)) {                           //Si pulsamos la tecla intro volvemos al menu del juego
                StateMain.musica = new Music(ruta + "menu.ogg");                //Cambiando la canción por la del menú
                StateMain.musica.loop();                                        //y reproduciendola en bucle
                sbg.getState(StateJuego.ID).init(gc, sbg);                      //Recargamos estado del juego para reiniciar variables
                sbg.enterState(StateMenu.ID);                                   //y lanzamos el 'estado' del menú
            }
            return;
        }
                                                                                //Bloque de movimiento de héroe
        if (entrada.isKeyDown(Input.KEY_DOWN)) {                                //Si pulsamos la tecla abajo movemos el héroe hacia esa posición
            heroeY += delta*heroeVeloc;
            heroe = heroeAbajo;
            heroe.update(delta);
        }
        if (entrada.isKeyDown(Input.KEY_UP)) {                                  //Si pulsamos la tecla arriba movemos el héroe hacia esa posición
            heroeY -= delta*heroeVeloc;
            heroe = heroeArriba;
            heroe.update(delta);
        }
        if (entrada.isKeyDown(Input.KEY_RIGHT)) {                               //Si pulsamos la tecla derecha movemos el héroe hacia esa posición
            heroeX += delta*heroeVeloc;
            heroe = heroeDerecha;
            heroe.update(delta);
        }
        if (entrada.isKeyDown(Input.KEY_LEFT)) {                                //Si pulsamos la tecla izquerda movemos el héroe hacia esa posición
            heroeX -= delta*heroeVeloc;
            heroe = heroeIzquierda;
            heroe.update(delta);
        }
        if ((heroeX < 0 || heroeY < 0 || heroeX > (mapaWidth - heroeWidth) || heroeY > (mapaHeight - heroeHeight)) ||
            (obstaculosBase[(int) (heroeX / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)]) ||
            (obstaculosBase[(int) ((heroeX+heroeWidth) / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)]) ||
            (obstaculosHeroe[(int) (heroeX / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)]) ||
            (obstaculosHeroe[(int) ((heroeX+heroeWidth) / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)])) {
            heroeX = heroeAntX;                                                 //Si el héroe se encuentra con cualquier obstáculo
            heroeY = heroeAntY;                                                 //retornamos su posición a la original con lo que impedimos su movimiento
        }      
        if ((agujeros[(int) (heroeX / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)]) ||
            (agujeros[(int) ((heroeX+heroeWidth) / tileWidth)][((int) (heroeY + heroeHeight) / tileHeight)])) {
            sonidoCaerAgujero.play();                                           //Si el héroe se encuentra con cualquier agujero
            heroeVivo = false;                                                  //reproducimos un sonido de caíday lo 'matamos'
        }
                                                                                //Bloque de movimiento de npc
        if (heroeY > npcY) {                                                    //Si el héroe se encuentra por encima del npc
            npcY += delta*npcVelocidad;                                         //desplazamos hacia arriba al npc
            npc = npcAbajo;                                                     //cambiando su sprite (caso no apreciable debido a la continua actualización del juego)
        }
        if (heroeY < npcY) {                                                    //Si el héroe se encuentra por debajo del npc
            npcY -= delta*npcVelocidad;                                         //desplazamos hacia abajo al npc
            npc = npcArriba;                                                    //(caso tampoco apreciable debido a la continua actualización del juego)
        }
        if (heroeX > npcX) {                                                    //Si el héroe se encuentra a la derecha del npc
            npcX += delta*npcVelocidad;                                         //desplazamos a la derecha al npc
            npc = npcDerecha;
        }
        if (heroeX < npcX) {                                                    //Si el héroe se encuentra a la izquierda del npc
            npcX -= delta*npcVelocidad;                                         //desplazamos a la izquierda al npc
            npc = npcIzquierda;
        }
        if ((npcX < 0 || npcY < 0 || npcX > (mapaWidth - npcWidth) || npcY > (mapaHeight - npcHeight)) ||
            (obstaculosBase[(int) (npcX / tileWidth)][((int) (npcY + npcHeight) / tileHeight)]) ||
            (obstaculosBase[(int) ((npcX+npcWidth) / tileWidth)][((int) (npcY + npcHeight) / tileHeight)])) {
            npcX = npcAntX;                                                     //Si el npc se encuentra con obstáculos de tipo muro
            npcY = npcAntY;                                                     //retornamos su posición a la original con lo que impedimos su movimiento
        }
                                                                                //Bloque de interacción entre personajes y objetos
        if (heroeX>=npcX-npcWidth && heroeX<=npcX+npcWidth && 
            heroeY>=npcY-npcHeight && heroeY<=npcY+npcHeight) {                 //Si el héroe entra en colisión con el npc
            sonidoCapturado.play();                                             //reproducimos un sonido y lo 'matamos'
            heroeVivo = false; 
        }
        if (heroeX>=espadasX-(espadasWidth*FP) && heroeX<=espadasX+(espadasWidth*FP) && 
            heroeY>=espadasY-(espadasHeight*FP) && heroeY<=espadasY+(espadasHeight*FP) && !cogerEspadas) {
            sonidoCogeHerramienta.play();                                       //Si entramos en colisión con las espadas
            cogerEspadas = true;                                                //las cambiamos de posición y activamos su recolección
            espadasX = 385;
            espadasY = 2;
        }
        if (heroeX>=armaduraX-(armaduraWidth*FP) && heroeX<=armaduraX+(armaduraWidth*FP) && 
            heroeY>=armaduraY-(armaduraHeight*FP) && heroeY<=armaduraY+(armaduraHeight*FP) && !cogerArmadura) {
            sonidoCogeHerramienta.play();                                       //Realizamos la misma tarea con cada uno de los objetos del juego
            cogerArmadura = true;
            armaduraX = 447;
            armaduraY = 2;
        }
        if (heroeX>=escudoX-(escudoWidth*FP) && heroeX<=escudoX+(escudoWidth*FP) && 
            heroeY>=escudoY-(escudoHeight*FP) && heroeY<=escudoY+(escudoHeight*FP) && !cogerEscudo) {
            sonidoCogeHerramienta.play();
            cogerEscudo = true;
            escudoX = 510;
            escudoY = 2;
        }
        if (heroeX>=collarX-(collarWidth*FP) && heroeX<=collarX+(collarWidth*FP) && 
            heroeY>=collarY-(collarHeight*FP) && heroeY<=collarY+(collarHeight*FP) && !cogerCollar) {
            sonidoCogeHerramienta.play();
            cogerCollar = true;
            collarX = 577;
            collarY = 2;
        }
        if (heroeX>=botasX-(botasWidth*FP) && heroeX<=botasX+(botasWidth*FP) && 
            heroeY>=botasY-(botasHeight*FP) && heroeY<=botasY+(botasHeight*FP) && !cogerBotas) {
            sonidoCogeHerramienta.play();                                       //Si entramos en colisión con las botas
            cogerBotas = true;                                                  //aumentamos la velocidad del héroe a modo de power-up
            heroeVeloc = 0.15f;
            tiempo = StateMain.musica.getPosition();                            //aumentando a su vez la velocidad de la música    
            StateMain.musica.loop(1.7F,1);                                      //para dar una sensación de super velocidad
            StateMain.musica.setPosition(tiempo);
        }
        if (heroeX>=barcoX-(barcoWidth*0.15) && heroeX<=barcoX+(barcoWidth*0.7) && 
            heroeY>=barcoY-(barcoHeight*FP) && heroeY<=barcoY+(barcoHeight*FP)) { 
            sonidoBarcoHundido.play();                                          //Si entramos en colisión con el barco
            barco = new Image(ruta + "barcoHundido.png");                       //cambiamos la imágen del mismo y 'matamos' al héroe
            heroeVivo = false;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        mapa.render(0, 0, 0);                                                   //Dibujamos el tilemap capa por capa empezando por la base o suelo
        mapa.render(0, 0, 1);                                                   //continuamos dibujando la capa de decoración
        mapa.render(0, 0, 2);                                                   //posteriormente dibujamos la capa de obstáculos base o generales que afectan a ambos personajes
        mapa.render(0, 0, 3);                                                   //más tarde dibujamos la capa de obstáculos que sólo afectan al héroe
        mapa.render(0, 0, 5);                                                   //y a continuación dibujamos la capa de agujeros

        espadas.draw(espadasX, espadasY);
        armadura.draw(armaduraX, armaduraY);                                    //Dibujamos los objetos del juego en las coordenadas previamente cargadas
        escudo.draw(escudoX, escudoY);
        collar.draw(collarX, collarY);
        barco.draw(barcoX, barcoY);
        if(!cogerBotas) {                                                       //Mostramos las botas (power-up) mientras que no las 'usemos'
            botas.draw(botasX, botasY);
        }
        if (heroeVivo) {                                                        //Dibujamos al héroe si está vivo
            heroe.draw(heroeX, heroeY);
        } else {                                                                //en caso contrario mostramos un mensaje indicando que hemos perdido
            msj="Lo sentimos, has perdido la Partida\n   Pulsa Intro para volver al Menú";
            fuente.drawString((gc.getWidth() - fuente.getWidth(msj)) / 2,       //mostrado en la mitad de la pantalla
                ((gc.getHeight() - fuente.getHeight(msj)) / 2) + 18, msj);
        }
        npc.draw(npcX, npcY);                                                   //Dibujamos el npc
        mapa.render(0, 0, 4);                                                   //y por último la capa de altura después de dibujar al héroe
        
        if (cogerArmadura && cogerEscudo && cogerEspadas && cogerCollar){       //Comprobamos que el héroe haya recogido todos los objetos
            if (!heroeGana){                                                    //en cuyo caso reproducimos un sonido de victoria
                sonidoAplausos.play();
            }
            heroeGana=true;
            msj="¡Enhorabuena, has ganado la Partida!\n  Pulsa Intro para volver al Menú";
            fuente.drawString((gc.getWidth() - fuente.getWidth(msj)) / 2,       //y mostramos un mensaje centrado indicando que hemos ganado
                    ((gc.getHeight() - fuente.getHeight(msj)) / 2) + 18, msj);                           
        }
    }
    
    @Override
    public int getID() {
        return ID;
    }
}