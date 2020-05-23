package controllers;

import javafx.stage.Stage;


/**
 * Controller predstavuje rozhranie, ktore implementuje kazdy jeden kontroler obrazovky
 *
 * obsahuje metodu startController, ktora je spustacia metoda pre zapnutie daneho okna
 */
public interface Controller {
    void startController(Stage stage) throws Exception;
}
