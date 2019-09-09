package com.mygdx.game;

abstract class Unit {
    final static int index = 0;
    final static float SIZE = 50;

    public abstract boolean canStay(boolean key);

    public static Unit getByChar(char c) {
        switch (c) {
            case '|':
                return new Wall();
            case '*':
                return new Key();
            case '@':
                return new Door();
            case '^':
                return new Elevator();
            case 'x':
                return new Spawn();
            default:
                return new Space();
        }
    }

   public abstract int getIndex() ;
}
class Space extends Unit {
    final static int index = 0;

    public boolean canStay(boolean key) {
        return true;
    }

    public int getIndex() {
        return index;
    }
}
class Wall extends Unit {
    final static int index = 1;

    public boolean canStay(boolean key) {
        return false;
    }

    public  int getIndex() {
        return index;
    }
}

class Key extends Unit {
    final static int index = 2;

    public boolean canStay(boolean key) {
        return true;
    }

    public  int getIndex() {
        return index;
    }
}

class Door extends Unit {
    final static int index = 3;

    public boolean canStay(boolean key) {
        return key;
    }

    public  int getIndex() {
        return index;
    }
}

class Elevator extends Unit {
    final static int index = 4;

    public boolean canStay(boolean key) {
        return true;
    }

    public  int getIndex() {
        return index;
    }
}

class Spawn extends Unit {
    final static int index = 5;

    public boolean canStay(boolean key) {
        return true;
    }

    public  int getIndex() {
        return index;
    }
}
