package model;

import java.util.List;
import java.util.ArrayList;

// CITATION: Copied from AlarmSystem from CPSC 210 Github
public abstract class Observable {
    protected List<Observer> observers;

    public Observable() {
        observers = new ArrayList<Observer>();
    }


    public void addObserver(Observer o) {
        observers.add(o);
    }


    public abstract void notifyObservers();
}

