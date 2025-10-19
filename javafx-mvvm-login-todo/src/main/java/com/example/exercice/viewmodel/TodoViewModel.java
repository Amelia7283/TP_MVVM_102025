package com.example.exercice.viewmodel;

import com.example.exercice.model.Todo;
import com.example.exercice.repo.TodoRepository;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class TodoViewModel {
    private final TodoRepository repo;
    private final StringProperty newTitle = new SimpleStringProperty("");
    private final StringProperty username = new SimpleStringProperty("");
    private final ObjectProperty<Todo> selected = new SimpleObjectProperty<>(null);
    private final ReadOnlyBooleanWrapper canAdd = new ReadOnlyBooleanWrapper();
    private final ReadOnlyBooleanWrapper canDelete = new ReadOnlyBooleanWrapper();
    private final BooleanProperty showDone = new SimpleBooleanProperty(false);
    private final ObservableList<Todo> source;
    private final FilteredList<Todo> filtered;
    public TodoViewModel(TodoRepository repo, String initialUsername) {
        this.repo = repo;
        this.username.set(initialUsername == null ? "" : initialUsername);
        this.source = repo.findAll();
        this.filtered = new FilteredList<>(source, this::match);
        canAdd.bind(Bindings.createBooleanBinding(() -> {
            String s = newTitle.get();
            return s != null && s.trim().length() >= 5;
        }, newTitle));
        canDelete.bind(selected.isNotNull());
        showDone.addListener((obs, o, v) -> recomputePredicate());
        source.forEach(this::watchTodo);
        source.addListener((ListChangeListener<Todo>) c -> {
            while (c.next()){ if (c.wasAdded()) c.getAddedSubList().forEach(this::watchTodo); }
        });
    }
    private void watchTodo(Todo t){ t.doneProperty().addListener((obs, ov, nv) -> recomputePredicate()); }
    private boolean match(Todo t){ return showDone.get() || !t.isDone(); }
    private void recomputePredicate(){ filtered.setPredicate(this::match); }
    public void add(){ if (canAdd.get()){ repo.add(newTitle.get().trim()); newTitle.set(""); } }
    public void deleteSelected(){ if (selected.get()!=null){ repo.remove(selected.get()); } }
    public void toggleDone(Todo t){ if (t != null) t.setDone(!t.isDone()); }
    public ObservableList<Todo> items(){ return filtered; }
    public StringProperty newTitleProperty(){ return newTitle; }
    public StringProperty usernameProperty(){ return username; }
    public ObjectProperty<Todo> selectedProperty(){ return selected; }
    public ReadOnlyBooleanProperty canAddProperty(){ return canAdd.getReadOnlyProperty(); }
    public ReadOnlyBooleanProperty canDeleteProperty(){ return canDelete.getReadOnlyProperty(); }
    public BooleanProperty showDoneProperty(){ return showDone; }
    public void clearAll() {repo.clear(); }

}