package com.example.exercice.viewmodel;

import com.example.exercice.auth.AuthService;
import com.example.exercice.auth.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import java.util.function.Consumer;

public class LoginViewModel {
    private final AuthService auth;
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty error    = new SimpleStringProperty("");
    private final ReadOnlyBooleanWrapper canLogin = new ReadOnlyBooleanWrapper();
    private Consumer<User> onSuccess = u -> {};
    public LoginViewModel(AuthService auth){
        this.auth = auth;
        canLogin.bind(Bindings.createBooleanBinding(
            () -> nonBlank(username.get()) && nonBlank(password.get()),
            username, password
        ));
    }
    public void setOnSuccess(Consumer<User> c){ this.onSuccess = c; }
    public void login(){
        error.set("");
        String u = username.get();
        String p = password.get();
        if (!canLogin.get()){ error.set("Veuillez saisir identifiant et mot de passe."); return; }
        boolean ok = auth.authenticate(u, p);
        if (ok){ onSuccess.accept(new User(u.trim())); }
        else { error.set("Identifiants invalides."); }
    }
    private boolean nonBlank(String s){ return s != null && !s.isBlank(); }
    public StringProperty usernameProperty(){ return username; }
    public StringProperty passwordProperty(){ return password; }
    public StringProperty errorProperty(){ return error; }
    public ReadOnlyBooleanProperty canLoginProperty(){ return canLogin.getReadOnlyProperty(); }
}
