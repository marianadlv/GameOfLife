
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Font;

public class Game
{
   private static int size = 50;
   private static boolean[][] matrix = new boolean[size][size];
   private static boolean[][] matrixCambios = new boolean[size][size];
   private static double sizeRectRadius = 0.4;
   private static double xRect = 0;
   private static double yRect = 0;
   private static int factorSeparacion = 2;
   private static int scale = (size+2*factorSeparacion)/2;
   private static int min = 0;
   private static int max = size-1;
   private static boolean[][] glider = {{false,true,false},{false,false,true},{true,true,true}};
   private static int sizeGlider = 3;
   private static int sizeExploder = 4;
   private static boolean[][] exploder = {{false,true,false},{true,true,true},{true,false,true},{false,true,false}};
   
   private static Game g = null;
   
   private Game() {}
   
   public static Game getInstance() {
       
       if (g==null) g = new Game();
       return g;
    
    }
   
   public static void inicializarPantalla() {
    
       StdDraw.setCanvasSize(800,800);
       //StdDraw.setPenColor(new Color(246,246,246));     
       //StdDraw.filledRectangle(0,0,800,800);            // background
       StdDraw.enableDoubleBuffering();
       StdDraw.setXscale(-scale,scale);
       StdDraw.setYscale(-scale,scale);
       xRect = -scale+sizeRectRadius+factorSeparacion;
       yRect = scale-sizeRectRadius-factorSeparacion;
      
    }
    
    public static void entrarMatrizGlider() {
        
        int contR = 0;
        int contC = 0;
    
        for (int r=(size/2)-sizeGlider; r<size/2; r++) {
        
            for (int c = (size/2)-sizeGlider; c<size/2; c++) {
            
               matrix[r][c] = glider[contR][contC];
               matrixCambios[r][c] = glider[contR][contC];
               contC++;
            
            }
        
            contR++;
            contC = 0;
            
        }
    
    }
    
    public static void entrarMatrizExploder() {
        
        int contR = 0;
        int contC = 0;
    
        for (int r=(size/2)-sizeExploder; r<size/2; r++) {
        
            for (int c = (size/2)-sizeExploder; c<size/2-1; c++) {
            
               matrix[r][c] = exploder[contR][contC];
               matrixCambios[r][c] = exploder[contR][contC];
               contC++;
            
            }
        
            contR++;
            contC = 0;
            
        }
    
    }
    
    public static void entrarRandom() {
             
        int i,j;
        
        for (int r=0; r<size*.5; r++) {
        
            for (int c = 0; c<size*.5; c++) {
            
               i = (int)(Math.random()*size);
               j = (int)(Math.random()*size);
               matrix[i][j] = Math.random() < 0.5;
               matrixCambios[i][j] = matrix[i][j];
            
            }
            
        }
    
    }
    
    public static void dibujarMatriz() {
    
        xRect = -scale+sizeRectRadius+factorSeparacion;
        yRect = scale-sizeRectRadius-factorSeparacion;
        int cont = 0;
        for (int r=0; r<size; r++){
        
            for (int c=0; c<size;c++) {
              
                if (matrix[r][c]) StdDraw.setPenColor(Color.BLUE);
                else StdDraw.setPenColor(Color.GRAY);
                StdDraw.filledSquare(xRect,yRect,sizeRectRadius);
                xRect+=1;
            
            }
        
            yRect-=1;
            xRect = -scale+sizeRectRadius+factorSeparacion;
        }
        
        
    }
    
    public static void checarMatriz() {
        
        int factR = 1, factC = 1;
    
        for (int r=0; r<size; r++) {
        
            for (int c=0; c<size; c++) {
            
                int contVivos = 0;
                
                if (r==min) {
                
                    if (c==min) {
                        factR = 1;
                        factC = 1;
                    } else if (c==max) {
                        factR = 1;
                        factC = -1;
                    }
                    
                    if (c==min||c==max) {
                    
                        if (matrix[r][c+factC]) contVivos++;
                        if (matrix[r+factR][c]) contVivos++;
                        if (matrix[r+factR][c+factC]) contVivos++;
                        
                    } else {
                    
                        if (matrix[r+1][c-1]) contVivos++;
                        if (matrix[r+1][c]) contVivos++;
                        if (matrix[r+1][c+1]) contVivos++;
                        if (matrix[r][c-1]) contVivos++;
                        if (matrix[r][c+1]) contVivos++;
                    
                    }
                
                } else if (r==max) {
                
                    if (c==min) {
                        factR = -1;
                        factC = 1;
                    } else if (c==max) {
                        factR = -1;
                        factC = -1;
                    }
                    
                    if (c==min||c==max) {
                    
                        if (matrix[r][c+factC]) contVivos++;
                        if (matrix[r+factR][c]) contVivos++;
                        if (matrix[r+factR][c+factC]) contVivos++;
                        
                    } else {
                    
                        if (matrix[r-1][c-1]) contVivos++;
                        if (matrix[r-1][c]) contVivos++;
                        if (matrix[r-1][c+1]) contVivos++;
                        if (matrix[r][c-1]) contVivos++;
                        if (matrix[r][c+1]) contVivos++;
                    
                    }
                
                } else if (c == min) {
                
                    if (matrix[r-1][c]) contVivos++;
                    if (matrix[r-1][c+1]) contVivos++;
                    if (matrix[r][c+1]) contVivos++;
                    if (matrix[r+1][c]) contVivos++;
                    if (matrix[r+1][c+1]) contVivos++;
                
                } else if (c == max) {
                
                    if (matrix[r-1][c-1]) contVivos++;
                    if (matrix[r-1][c]) contVivos++;
                    if (matrix[r][c-1]) contVivos++;
                    if (matrix[r+1][c-1]) contVivos++;
                    if (matrix[r+1][c]) contVivos++;
                
                } else {            // si no es equina ni pared
                
                    if (matrix[r-1][c-1]) contVivos++;
                    if (matrix[r-1][c]) contVivos++;
                    if (matrix[r-1][c+1]) contVivos++;
                    if (matrix[r][c-1]) contVivos++;
                    if (matrix[r][c+1]) contVivos++;
                    if (matrix[r+1][c-1]) contVivos++;
                    if (matrix[r+1][c]) contVivos++;
                    if (matrix[r+1][c+1]) contVivos++;
                
                }
                
                // checo vida
                
                if (matrix[r][c]) {
                
                    if (contVivos<2||contVivos>3) matrixCambios[r][c] = false;
                
                } else {
                
                    if (contVivos == 3) matrixCambios[r][c] = true;
                
                }
                
            }
        
        }
        
        for (int r=0; r<size; r++) {
        
            for (int c=0; c<size; c++) {
            
                matrix[r][c] = matrixCambios[r][c];
            
            }
        
        }
        
    }
    
    public static void runEverything() {
    
        inicializarPantalla();
        int s = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Type 1 for Glider, 2 for Exploder or 3 for Random:"));
        if (s==1) entrarMatrizGlider();
        else if (s==2) entrarMatrizExploder();
        else if (s==3) entrarRandom();
        while (s!=1&&s!=2&&s!=3) {
           s = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Type a correct option please. Type 1 for Glider, 2 for Exploder or 3 for Random:"));
           if (s==1) entrarMatrizGlider();
           else if (s==2) entrarMatrizExploder();
           else entrarRandom();
        }
        dibujarMatriz();
        StdDraw.show(500);
        
        while(true) {
        
            checarMatriz();
            StdDraw.clear();
            dibujarMatriz();
            StdDraw.show(500);
        
        }
        
        //while (true) {
            
           // checarMatriz();
            //StdDraw.clear();
            //dibujarMatriz();
            //StdDraw.show(10);
        //}
        
        
    }
}
