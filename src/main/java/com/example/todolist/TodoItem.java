package com.example.todolist;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class TodoItem {
   private String title;
   private String description;
   private int priority;
   private LocalDate earlyStart;
   private LocalDate lateFinish;
   private boolean isCompleted = false;
   private String remarks = "";
   
   public Map<Integer, String> priorityText = new HashMap<>();
   
   public TodoItem(String title, String description, int priority, LocalDate earlyStart, LocalDate lateFinish) {
      this.title = title;
      this.description = description;
      this.priority = priority > 5 ? 5 : (Math.max(priority, 1));
      this.earlyStart = earlyStart;
      this.lateFinish = lateFinish;
      
      priorityText.put(1, "Very Low");
      priorityText.put(2, "Low");
      priorityText.put(3, "Medium");
      priorityText.put(4, "High");
      priorityText.put(5, "Very High");
   }
   
   public void setCompleted(boolean completed) {
      this.isCompleted = completed;
   }
   public void setCompleted(boolean completed, String remarks) {
      isCompleted = completed;
      this.remarks = remarks;
   }
   
   public String getTitle() {
      return title;
   }
   
   public String getDescription() {
      return description;
   }
   
   public int getPriority() {
      return priority;
   }
   
   public String getPriorityText() {
      return priorityText.get(priority);
   }
   
   public LocalDate getEarlyStart() {
      return earlyStart;
   }
   
   public LocalDate getLateFinish() {
      return lateFinish;
   }
   
   public boolean isCompleted() {
      return isCompleted;
   }
   
   public String getRemarks() {
      return remarks;
   }
   
   @Override
   public String toString(){
      return title;
   }
}
