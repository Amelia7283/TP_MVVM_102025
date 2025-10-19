package com.example.exercice.view;

import com.example.exercice.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.util.Objects;

public class LoginView {
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;
    @FXML private Button btnLogin, btnCancel;
    @FXML private Label lblError;
    private final LoginViewModel vm;
    private final Parent root;
    public LoginView(LoginViewModel vm) throws Exception {
        this.vm = vm;
        FXMLLoader l = new FXMLLoader(Objects.requireNonNull(
            getClass().getResource("/view/LoginView.fxml"),
            "FXML /view/LoginView.fxml introuvable"));
        l.setController(this);
        this.root = l.load();
    }
    public Parent getRoot(){ return root; }
    @FXML private void initialize(){
        txtUser.textProperty().bindBidirectional(vm.usernameProperty());
        txtPass.textProperty().bindBidirectional(vm.passwordProperty());
        lblError.textProperty().bind(vm.errorProperty());
        btnLogin.disableProperty().bind(vm.canLoginProperty().not());
        btnLogin.setOnAction(e -> vm.login());
        btnCancel.setOnAction(e -> { txtUser.clear(); txtPass.clear(); });
        txtPass.setOnAction(e -> vm.login());
    }
}
