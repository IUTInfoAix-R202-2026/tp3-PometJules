package fr.univ_amu.iut.exercice7;

import java.time.LocalTime;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class QualificationController {

  @FXML private TableView<Sequence> tableView;
  @FXML private TableColumn<Sequence, LocalTime> colHorodatage;
  @FXML private TableColumn<Sequence, Number> colFrequence;
  @FXML private TableColumn<Sequence, Number> colDuree;
  @FXML private TableColumn<Sequence, String> colStatut;
  @FXML private Label labelSelection;
  @FXML private Button boutonEcouter;
  @FXML private Label labelLecture;
  @FXML private ChoiceBox<String> choiceBoxVerdict;
  @FXML private TextArea zoneCommentaire;
  @FXML private Label labelVerdictGlobal;

  private final NuitVerification nuit = NuitVerification.genererJeu(10);

  @FXML
  private void initialize() {
    // Étape 1 : alimenter la TableView
    colHorodatage.setCellValueFactory(c -> c.getValue().horodatageProperty());
    colFrequence.setCellValueFactory(c -> c.getValue().frequenceDominanteKHzProperty());
    colDuree.setCellValueFactory(c -> c.getValue().dureeSecondesProperty());
    colStatut.setCellValueFactory(c -> c.getValue().statutProperty());
    tableView.setItems(nuit.getSequences());

    // Étape 2 : afficher la séquence sélectionnée
    labelSelection.setText("(sélectionnez une séquence dans le tableau)");
    labelLecture.setText("");

    tableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, ancien, nouveau) -> {
              if (nouveau == null) {
                labelSelection.setText("(sélectionnez une séquence dans le tableau)");
              } else {
                labelSelection.setText(
                    "Séquence "
                        + nouveau.getHorodatage()
                        + " - "
                        + String.format("%.1f kHz", nouveau.getFrequenceDominanteKHz()));
              }
            });

    // Étape 3 : bouton Écouter désactivé sans sélection
    boutonEcouter
        .disableProperty()
        .bind(tableView.getSelectionModel().selectedItemProperty().isNull());

    // Étape 4 : peupler la ChoiceBox
    choiceBoxVerdict.getItems().addAll("OK", "Douteux", "À jeter");

    // Étape 5 : label verdict global
    labelVerdictGlobal
        .textProperty()
        .bind(
            Bindings.when(nuit.verdictGlobalProperty().isEmpty())
                .then("Verdict global : (à saisir)")
                .otherwise(Bindings.concat("Verdict global : ", nuit.verdictGlobalProperty())));

    // Étape 6 : lier la TextArea au modèle (bidirectionnel)
    zoneCommentaire.textProperty().bindBidirectional(nuit.commentaireProperty());
  }

  @FXML
  private void ecouter() {
    // Étape 7 : mettre le statut à "Écoutée" et afficher "Lecture en cours..."
    Sequence selection = tableView.getSelectionModel().getSelectedItem();
    if (selection != null) {
      selection.setStatut("Écoutée");
      labelLecture.setText("Lecture en cours...");
      PauseTransition pause = new PauseTransition(Duration.millis(600));
      pause.setOnFinished(e -> labelLecture.setText(""));
      pause.play();
    }
  }

  @FXML
  private void enregistrerVerdict() {
    // Étape 8 : enregistrer le verdict dans le modèle
    String verdict = choiceBoxVerdict.getValue();
    if (verdict != null) {
      nuit.setVerdictGlobal(verdict);
    }
  }

  public NuitVerification getNuit() {
    return nuit;
  }
}
