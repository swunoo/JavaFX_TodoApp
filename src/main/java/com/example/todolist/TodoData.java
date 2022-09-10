package com.example.todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TodoData {
   private  static TodoData instance = new TodoData();
   private static String filename = "TodoListItems.txt";
   
   private ObservableList<TodoItem> todoItems; //Optimized Collections.
   private DateTimeFormatter formatter;
   
   public static TodoData getInstance() {
      return instance;
   }
   
   private TodoData() {
      formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   }
   public ObservableList<TodoItem> getTodoItems(){
      return todoItems;
   }
   public void setTodoItems(ObservableList<TodoItem> todoItems){
      this.todoItems = todoItems;
   }
   public void loadTodoItems () throws IOException {
      todoItems = FXCollections.observableArrayList();
      Path path = Paths.get(filename);
      BufferedReader br = Files.newBufferedReader(path);
      
      String input;
      
      try {
         while((input = br.readLine()) != null) {
            String[] itemPieces = input.split("\t");
            String title = itemPieces[0];
            String description = itemPieces[1];
            String priority = itemPieces[2];
            LocalDate earlyStart = LocalDate.parse(itemPieces[3],formatter);
            LocalDate lateFinish = LocalDate.parse(itemPieces[4],formatter);
//            String isCompleted = itemPieces[5];
//            String remarks = itemPieces[6];
            TodoItem item = new TodoItem(title, description, parseInt(priority), earlyStart, lateFinish);
            todoItems.add(item);
         }
      } finally {
         if(br != null){
            br.close();
         }
      }
   }
   
   public void storeTodoItems() throws IOException {
      Path path = Paths.get(filename);
      BufferedWriter bw = Files.newBufferedWriter(path);
      try {
         Iterator<TodoItem> i = todoItems.iterator();
         while(i.hasNext()){
            TodoItem item = i.next();
            bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t\n",
               item.getTitle(),
               item.getDescription(),
               item.getPriority(),
               item.getEarlyStart(),
               item.getLateFinish(),
               item.isCompleted(),
               item.getRemarks()));
         }
      } finally {
         if(bw != null){
            bw.close();
         }
      }
   }
   public void addTodoItem(TodoItem item) {
      todoItems.add(item);
   }
   public void deleteTodoItem(TodoItem item) {
      todoItems.remove(item);
   }
}
