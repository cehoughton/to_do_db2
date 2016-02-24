import java.util.List;
import org.sql2o.*;

public class Task {
  private int category_Id;
  private int id;
  private String description;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return category_Id;
  }

  public Task(String description, int category_Id) {
    this.description = description;
    this.category_Id = category_Id;
  }

public static List<Task> all() {
  String sql = "SELECT id, description, category_Id FROM Tasks";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Task.class);
  }
 }

 @Override
 public boolean equals(Object otherTask) {
   if (!(otherTask instanceof Task)) {
     return false;
   } else {
     Task newTask = (Task) otherTask;
     return this.getDescription().equals(newTask.getDescription()) &&
      this.getId() == newTask.getId() &&
      this.getCategoryId() == newTask.getCategoryId();
   }
 }

 public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO Tasks (description, category_Id) VALUES (:description, :category_Id)";
     this.id = (int) con.createQuery(sql, true)
      .addParameter("description", this.description)
      .addParameter("category_Id", this.category_Id)
      .executeUpdate()
      .getKey();
   }
 }

 public static Task find(int id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM Tasks where id=:id";
     Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
   }
 }

 public void update(String description) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE tasks SET description = :description WHERE id = :id";
    con.createQuery(sql)
      .addParameter("description", description)
      .addParameter("id", id)
      .executeUpdate();
  }
}

}
