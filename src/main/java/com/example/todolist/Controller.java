package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
   
   private List<TodoItem> todoItems;
   
   @FXML
   private ListView<TodoItem> todoListView;
   @FXML
   private TextArea todoItemDetails;
   @FXML
   private Label itemES;
   @FXML
   private Label itemLF;
   @FXML
   private Label itemTitle;
   
   public void initialize () throws IOException {
      
//      initializeData();
      
      System.out.println(TodoData.getInstance().getTodoItems());
      todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
      todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      todoListView.getSelectionModel().selectFirst();
   }
   
   public void initializeData() {
      TodoItem todoItem1 = new TodoItem(
         "FE Prep", "Read Chapter 3", 3, LocalDate.of(2022, Month.SEPTEMBER, 10), LocalDate.of(2022, Month.SEPTEMBER, 15)
      );
      TodoItem todoItem2 = new TodoItem(
         "UoPeople", "Statistic coursework", 3, LocalDate.of(2022, Month.SEPTEMBER, 9), LocalDate.of(2022, Month.SEPTEMBER, 18)
      );
      TodoItem todoItem3 = new TodoItem(
         "Remote Work", "Code in Java", 2, LocalDate.of(2022, Month.SEPTEMBER, 15), LocalDate.of(2022, Month.SEPTEMBER, 20)
      );
      
      todoItems = new ArrayList<>();
      todoItems.add(todoItem1);
      todoItems.add(todoItem2);
      todoItems.add(todoItem3);
      TodoData.getInstance().setTodoItems(todoItems);
      todoListView.getItems().setAll(todoItems);
   
   }
   
   @FXML
   public void onClickListView() {
      TodoItem item = todoListView.getSelectionModel().getSelectedItem();
      itemES.setText(item.getEarlyStart().toString());
      itemLF.setText(item.getLateFinish().toString());
      itemTitle.setText(item.getTitle());
      basicWay(item);
   }
   
   public void basicWay(TodoItem item){
      StringBuilder sb = new StringBuilder();
//      sb.append(item.getTitle());
      sb.append("\n\n\n");
      sb.append("Priority: ").append(item.getPriorityText());
      sb.append("\n\n");
      sb.append("Description: ").append(item.getDescription());
      sb.append("\n\n");
      sb.append("Early Start: ").append(item.getEarlyStart());
      sb.append("\n\n");
      sb.append("Late Finish: ").append(item.getLateFinish());

      todoItemDetails.setText(String.valueOf(sb));
   }
}