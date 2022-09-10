package com.example.todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
   @FXML
   private BorderPane mainBorderPane;
   @FXML
   private ContextMenu listContextMenu;
   
   public void initialize () throws IOException {
      
//      initializeData();
      
      listContextMenu = new ContextMenu();
      MenuItem deleteItem = new MenuItem("Delete");
      listContextMenu.getItems().addAll(deleteItem);
   
      deleteItem.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            deleteItem(item);
         }
      });
   
      SortedList<TodoItem> sortedList = new SortedList<>(TodoData.getInstance().getTodoItems(), new Comparator<TodoItem>() {
         @Override
         public int compare(TodoItem o1, TodoItem o2) {
            if(o1.getLateFinish().equals(o2.getLateFinish())){
               return 0;
            }
            return o1.getLateFinish().isAfter(o2.getLateFinish()) ? 1 : -1;
         }
      });
      
      todoListView.getItems().setAll(sortedList);
      todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
         @Override
         public void changed(ObservableValue<? extends TodoItem> observableValue, TodoItem oldValue, TodoItem newValue) {
            if(newValue != null){
               TodoItem item = todoListView.getSelectionModel().getSelectedItem();
               itemES.setText(item.getEarlyStart().toString());
               itemLF.setText(item.getLateFinish().toString());
               itemTitle.setText(item.getTitle());
               basicWay(item);
            }
      
         }
      });
      todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      todoListView.getSelectionModel().selectFirst();
      
      todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
         @Override
         public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
            ListCell<TodoItem> itemCell = new ListCell<>(){
               @Override
               protected void updateItem(TodoItem item, boolean empty){
                  super.updateItem(item, empty);
                  if(empty){
                     setText(null);
                  } else {
                     setText(item.getTitle());
                     if(item.getLateFinish().isBefore(LocalDate.now().plusDays(1))){
                        setTextFill(Color.CRIMSON);
                        // OR MAYBE JUST MODIFY THE TOSTRING() IN TODOITEM.
                     } else if (item.getEarlyStart().equals(LocalDate.now())){
                        setTextFill(Color.BROWN);
                     }
                  }
               }
            };
            
            itemCell.emptyProperty().addListener(
               (obs, wasEmpty, isNowEmpty) -> {
                  if(isNowEmpty){
                     itemCell.setContextMenu(null);
                  } else {
                     itemCell.setContextMenu(listContextMenu);
                  }
               }
            );
            
            return itemCell;
         }
      });
   }
   @FXML
   public void onKeyed (KeyEvent keyEvent){
      TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
      if(selectedItem != null && (keyEvent.getCode().equals(KeyCode.DELETE))){
         deleteItem(selectedItem);
      }
   }
   @FXML
   public void showNewTodoDialog() throws IOException {
      Dialog<ButtonType> dialog = new Dialog<>(); //Default is that it will be modal.
      dialog.initOwner(mainBorderPane.getScene().getWindow());
      dialog.setTitle("NEW TODO ITEM");
      dialog.setHeaderText("This is to C new TIs");
      
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("new_todo.fxml"));
      dialog.getDialogPane().setContent(fxmlLoader. load());
      dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
      dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
   
      Optional<ButtonType> result = dialog.showAndWait();
      if(result.isPresent() && result.get() == ButtonType.OK){
         newTodoController controller = fxmlLoader.getController();
         
         // Manual way.
         TodoItem newlyAdded = controller.processRes();
//         todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
   
         // Data binding (auto update) using Observables.
         todoListView.setItems(TodoData.getInstance().getTodoItems());
         todoListView.getSelectionModel().select(newlyAdded);
   
   
      } else {
         System.out.println("Cancel Pressed.");
      }
   }
   public void initializeData() {
         /*

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
      
          */
   }
   
   @FXML
   public void onClickListView() {
//      TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//      itemES.setText(item.getEarlyStart().toString());
//      itemLF.setText(item.getLateFinish().toString());
//      itemTitle.setText(item.getTitle());
//      basicWay(item);
   }
   
   public void deleteItem(TodoItem item){
      Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
      confirmation.setTitle("Delete Todo Item");
      confirmation.setHeaderText("Delete: " + item.getTitle());
      confirmation.setContentText("Are you sure?");
      Optional<ButtonType> result = confirmation.showAndWait();
      
      if(result.isPresent() && (result.get() == ButtonType.OK)){
         TodoData.getInstance().deleteTodoItem(item);
      }
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