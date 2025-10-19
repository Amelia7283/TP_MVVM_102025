package com.example.exercice.repo;

import com.example.exercice.model.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.UUID;
import java.time.LocalDate;

public class TodoRepository {
    private final ObservableList<Todo> store = FXCollections.observableArrayList(
        todo -> new javafx.beans.Observable[]{ todo.doneProperty(), todo.titleProperty() }
    );
    public ObservableList<Todo> findAll(){ return store; }
    public Todo add(String title) {
        Todo t = new Todo(UUID.randomUUID().toString(), title, false);
        t.setDeadline(LocalDate.now().plusDays(3));
        store.add(t);
        return t;
    }
    public void remove(Todo t){ store.remove(t); }
    public void clear() {findAll().clear(); }
}
