package com.example.exercice.view;

import com.example.exercice.model.Todo;
import com.example.exercice.viewmodel.TodoViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Objects;
import java.time.LocalDate;
import com.example.exercice.Navigator;

public class TodoView {
    @FXML private TextField txtNew;
    @FXML private Button btnAdd, btnDel;
    @FXML private Button btnClearAll;
    @FXML private CheckBox chkShowDone;
    @FXML private TableView<Todo> table;
    @FXML private TableColumn<Todo, String> colTitle;
    @FXML private TableColumn<Todo, Boolean> colDone;
    @FXML private TableColumn<Todo, Integer> colLength;
    @FXML private TableColumn<Todo, LocalDate> colDeadline;
    @FXML private Label lblPreview;
    @FXML private Label lbBienvenue;
    @FXML private Button btnLogout;
    private final TodoViewModel vm;
    private final Parent root;
    public TodoView(TodoViewModel vm) throws Exception {
        this.vm = vm;
        FXMLLoader l = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/view/TodoView.fxml"),
                "FXML /view/TodoView.fxml introuvable"));
        l.setController(this);
        this.root = l.load();
    }
    public Parent getRoot(){ return root; }
    @FXML private void initialize(){
        table.setEditable(true);
        colDone.setEditable(true);
        table.setItems(vm.items());
        colTitle.setCellValueFactory(cd -> cd.getValue().titleProperty());
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colDone.setCellValueFactory(cd -> cd.getValue().doneProperty());
        colDone.setCellFactory(CheckBoxTableCell.forTableColumn(colDone));

        colLength.setCellValueFactory(cd -> {
            String title = cd.getValue().getTitle();
            return new SimpleIntegerProperty(title != null ? title.length() : 0).asObject();
        });

        txtNew.textProperty().bindBidirectional(vm.newTitleProperty());

        lblPreview.textProperty().bind(
                Bindings.concat("Vous allez ajouter : ", vm.newTitleProperty())
        );

        lbBienvenue.textProperty().bind(
                Bindings.concat("Bienvenue, ", vm.usernameProperty())
        );

        chkShowDone.selectedProperty().bindBidirectional(vm.showDoneProperty());
        btnAdd.disableProperty().bind(vm.canAddProperty().not());
        btnAdd.setOnAction(e -> vm.add());
        btnDel.setOnAction(e -> {
            vm.selectedProperty().set(table.getSelectionModel().getSelectedItem());
            vm.deleteSelected();
            table.getSelectionModel().clearSelection();
        });
        btnClearAll.setOnAction(e -> vm.clearAll());

        colDeadline.setCellValueFactory(data -> data.getValue().deadlineProperty());
        btnLogout.setOnAction(e -> Navigator.goToLogin());
    }
}
