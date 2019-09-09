package com.mygdx.game;

import java.io.File;
import java.util.Scanner;

public class Level {
    final static int MAP_SIZE = 10;
    public Unit [][] map;
    public float red, green, blue;
    public Level(float red, float green, float blue, String filePath){
        this.red = red;
        this.green = green;
        this.blue = blue;
        map=new Unit[MAP_SIZE][MAP_SIZE];
        loadLevel(filePath);
    }
    public void loadLevel(String filePath){
        try {
            Scanner sc = new Scanner(new File(filePath));
            for(int i=0; i<MAP_SIZE; i++){
                String current = sc.nextLine();
                for(int j=0; j<MAP_SIZE; j++){
                    map[i][j] = Unit.getByChar(current.charAt(j));
                }

            }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
