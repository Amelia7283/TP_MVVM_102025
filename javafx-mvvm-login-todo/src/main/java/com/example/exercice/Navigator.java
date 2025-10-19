package com.example.exercice;

import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.exercice.auth.AuthService;
import com.example.exercice.viewmodel.LoginViewModel;
import com.example.exercice.view.LoginView;


public class Navigator {
    private static Stage stage;
    public static void init(Stage primary){ stage = primary; }
    public static void goTo(Scene scene, String title){
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    public static void goToLogin() {
        try {
            var auth = new AuthService();
            var loginVM = new LoginViewModel(auth);
            var loginView = new LoginView(loginVM);
            goTo(new Scene(loginView.getRoot(), 420, 260), "Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
