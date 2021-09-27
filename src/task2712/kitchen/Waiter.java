package task2712.kitchen;


import task2712.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

public class Waiter implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ConsoleHelper.writeMessage(arg + " was cooked by " + o);
    }
}
