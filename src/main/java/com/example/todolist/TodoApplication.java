package com.example.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoApplication extends Application {
   @Override
   public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(TodoApplication.class.getResource("todolist_view.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 600, 300);
      stage.setTitle("TinyTodos");
      stage.setScene(scene);
      stage.show();
   }
   
   public static void main(String[] args) {
      launch();
   }
   
   @Override
   public void stop() throws Exception {
      try{
         TodoData.getInstance().storeTodoItems();
      }catch (IOException e){
         e.printStackTrace();
      }
   }
   
   @Override
   public void init() throws Exception {
      try{
         TodoData.getInstance().loadTodoItems();
      }catch (IOException e){
         e.printStackTrace();
      }
   }
}