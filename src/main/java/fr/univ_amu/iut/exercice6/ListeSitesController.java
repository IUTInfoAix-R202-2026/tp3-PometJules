package fr.univ_amu.iut.exercice6;

import fr.univ_amu.iut.exercice5.SiteCarte;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ListeSitesController {

  @FXML private VBox conteneurCartes;

  private int compteurDemo = 0;

  public int ajouterSiteDemo() {
    // TODO exercice 6 : ajouter une nouvelle SiteCarte au VBox conteneurCartes.
    compteurDemo++;
    SiteCarte carte = new SiteCarte();
    carte.setNumeroCarre("Carré " + (640000 + compteurDemo));
    carte.setNomConvivial("📍 Site de démonstration #" + compteurDemo);
    carte.setNombrePoints((compteurDemo % 3) + 1);
    carte.setNombrePassages(compteurDemo * 2);
    carte.setJoursDepuisDernierPassage(compteurDemo * 4);
    conteneurCartes.getChildren().add(0, carte);
    return conteneurCartes.getChildren().size();
  }

  public int getNombreCartes() {
    return conteneurCartes.getChildren().size();
  }
}
