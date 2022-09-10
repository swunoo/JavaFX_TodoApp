package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class newTodoController {
   @FXML
   private TextField titleInput;
   @FXML
   private TextArea descriptionInput;
   @FXML
   private ChoiceBox<Integer> priorityInput;
   @FXML
   private DatePicker earlyStartInput;
   @FXML
   private DatePicker lateFinishInput;
   
   public TodoItem processRes() {
      String title = titleInput.getText().trim();
      String description = descriptionInput.getText().trim();
      int priority = priorityInput.getValue();
      LocalDate earlyStart = earlyStartInput.getValue();
      LocalDate lateFinish = lateFinishInput.getValue();
      
      TodoItem newItem = new TodoItem(title, description, priority, earlyStart, lateFinish);
      TodoData.getInstance().addTodoItem(newItem);
      return newItem;
   }
}
