package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;


public class Player implements ActionListener {
    final static int SPEED = 10;
    final static int SIZE = 30;
    final static int DELTA_SIZE = 10;
    final static int DELTA = 2;
    final static int LEVEL_COUNT = 3;
    private boolean key;
    private float x, y;
    private int level;
    public Level[] levels;
    private Timer timer;
    private int pastHor;
    private int pastVert;
    private int dir;
    public Player() {
        key = false;
        level = 2;
        levels = new Level[LEVEL_COUNT];
        levels[0] = new Level(0.5f, 1f, 0.5f, "Level1.txt");
        levels[1] = new Level(0.5f, 0.5f, 1f, "Level2.txt");
        levels[2] = new Level(1f, 0.5f, 0.5f, "Level3.txt");
        toStart();
        timer = new Timer(SPEED, this);
        timer.start();
    }
    public int getDir(){
        return dir;
    }

    public float getX() {
        return x;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getY() {
        return y;
    }

    private void toStart() {
        pastHor=-1;
        pastVert=-1;
        outerloop:
        for (int i = 0; i < Level.MAP_SIZE; i++) {
            for (int j = 0; j < Level.MAP_SIZE; j++) {
                if (levels[level].map[i][j].getIndex() == Spawn.index) {
                    x = DELTA_SIZE + j * Unit.SIZE;
                    y = DELTA_SIZE + i * Unit.SIZE;
                    break outerloop;
                }
            }
        }
    }

    public boolean isKey() {
        return key;
    }
    private void nextLevel(){
        if(level ==0){
           Gdx.app.exit();
        }else{
            level--;
            key=false;
            toStart();
        }
    }
    private void findRoute(){
        dir = 0;
        int smallest = routeRecursion(pastHor, pastVert+1, 3);
        int newDir = routeRecursion(pastHor+1, pastVert, 4);
        if(newDir<smallest){
            dir = 1;
            smallest = newDir;
        }
        newDir = routeRecursion(pastHor, pastVert-1, 1);
        if(newDir<smallest){
            dir = 2;
            smallest = newDir;
        }
        newDir = routeRecursion(pastHor-1, pastVert, 2);
        if(newDir<smallest){
            dir = 3;
            smallest = newDir;
        }

    }
    private int routeRecursion(int hor, int vert, int dir){
       if(!levels[level].map[vert][hor].canStay(key)) {
           return 1000;
       }
        if(levels[level].map[vert][hor].getIndex()==Key.index) {
            return 100;
        }
        if(levels[level].map[vert][hor].getIndex()==Elevator.index) {
            return 1;
        }
        int smallest = 10000;
        if(dir!=1){
           int up = routeRecursion(hor, vert+1, 3);
           if(up<smallest){
               smallest=up;
           }
        }
        if(dir!=2){
            int right = routeRecursion(hor+1, vert, 4);
            if(right<smallest){
                smallest=right;
            }
        }
        if(dir!=3){
            int down = routeRecursion(hor, vert-1, 1);
            if(down<smallest){
                smallest=down;
            }
        }
        if(dir!=4){
            int left = routeRecursion(hor-1, vert, 2);
            if(left<smallest){
                smallest=left;
            }
        }
        return smallest;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        x += Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? DELTA : 0;
        x -= Gdx.input.isKeyPressed(Input.Keys.LEFT) ? DELTA : 0;
        y += Gdx.input.isKeyPressed(Input.Keys.UP) ? DELTA : 0;
        y -= Gdx.input.isKeyPressed(Input.Keys.DOWN) ? DELTA : 0;
        int rightCell = (int)Math.floor((x+SIZE)/Unit.SIZE);
        int leftCell = (int)Math.floor(x/Unit.SIZE);
        int upCell = (int)Math.floor((y+SIZE)/Unit.SIZE);
        int downCell = (int)Math.floor(y/Unit.SIZE);
        int currVertCell = (int)Math.floor((y+SIZE/2)/Unit.SIZE);
        int currHorCell = (int)Math.floor((x+SIZE/2)/Unit.SIZE);
        if(!levels[level].map[currVertCell][rightCell].canStay(key)){
            x=rightCell*Unit.SIZE - SIZE;
        }
        if(!levels[level].map[currVertCell][leftCell].canStay(key)){
            x=currHorCell*Unit.SIZE;
        }
        if(!levels[level].map[upCell][currHorCell].canStay(key)){
            y=upCell*Unit.SIZE - SIZE;
        }
        if(!levels[level].map[downCell][currHorCell].canStay(key)){
            y=currVertCell*Unit.SIZE;
        }
        switch(levels[level].map[currVertCell][currHorCell].getIndex()){
            case Key.index: key = true;
            break;
            case Elevator.index: nextLevel();
            break;
        }
        if(currHorCell!=pastHor || currVertCell!=pastVert){
            pastVert=currVertCell;
            pastHor=currHorCell;
            findRoute();
        }


    }
}
