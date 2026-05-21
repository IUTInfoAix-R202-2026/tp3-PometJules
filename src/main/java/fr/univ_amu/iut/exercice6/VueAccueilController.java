package fr.univ_amu.iut.exercice6;

import javafx.fxml.FXML;

public class VueAccueilController {

  @FXML private EnTeteController enTeteController;
  @FXML private ListeSitesController listeSitesController;

  @FXML
  private void initialize() {
    // TODO exercice 6 : faire dialoguer les deux sous-contrôleurs via celui-ci.
    enTeteController.setActionNouveauSite(
        () -> {
          int total = listeSitesController.ajouterSiteDemo();
          enTeteController.mettreAJourCompteur(total);
        });
    enTeteController.mettreAJourCompteur(listeSitesController.getNombreCartes());
  }

  public EnTeteController getEnTeteController() {
    return enTeteController;
  }

  public ListeSitesController getListeSitesController() {
    return listeSitesController;
  }
}
