package com.tutorial.nidhi.todoapp;

/**
 * Created by NidhiSaini on 6/24/16.
 */
public class TodoList {

    private int _id;
    private String todoListName;
    private String todoListDescription;
    private String todoListPriority;
    private String todoListCreated;
    /*private String todoListDueDate;*/

    //empty constructor
    public TodoList() {

    }

    public TodoList(String todoListName, String todoListDescription) {
        this.todoListName = todoListName;
        this.todoListDescription = todoListDescription;
    }

    public TodoList(int _id, String todoListName, String todoListDescription, String todoListPriority, String todoListCreated) {
        this._id = _id;
        this.todoListName = todoListName;
        this.todoListDescription = todoListDescription;
        this.todoListPriority = todoListPriority;
        this.todoListCreated = todoListCreated;
    }

    public TodoList(int _id, String todoListName, String todoListDescription) {
        this._id = _id;
        this.todoListName = todoListName;
        this.todoListDescription = todoListDescription;
       /* this.todoListPriority = todoListPriority;
        this.todoListDueDate = todoListDueDate;
        String todoListPriority, String todoListDueDate*/
    }

    //getters and setters
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }

    public String getTodoListDescription() {
        return todoListDescription;
    }

    public void setTodoListDescription(String todoListDescription) {
        this.todoListDescription = todoListDescription;
    }

    public String getTodoListPriority() {
        return todoListPriority;
    }

    public void setTodoListPriority(String todoListPriority) {
        this.todoListPriority = todoListPriority;
    }

    public String getTodoListCreated() {
        return todoListCreated;
    }

    public void setTodoListCreated(String todoListCreated) {
        this.todoListCreated = todoListCreated;
    }

   /* public String getTodoListDueDate() {
        return todoListDueDate;
    }

    public void setTodoListDueDate(String todoListDueDate) {
        this.todoListDueDate = todoListDueDate;
    }*/
}
