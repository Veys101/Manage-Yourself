package application;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dbconnection.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movescreen.MoveScreen;

public class AppController implements Initializable {

    @FXML
    protected ListView<String> mondayList;
    @FXML
    protected ListView<String> tuesdayList;
    @FXML
    protected ListView<String> wednesdayList;
    @FXML
    protected ListView<String> thursdayList;
    @FXML
    protected ListView<String> fridayList;
    @FXML
    protected ListView<String> saturdayList;
    @FXML
    protected ListView<String> sundayList;
    @FXML
    protected JFXListView<String> loadTask;
    @FXML
    protected ListView<String> timeListView;
    @FXML
    protected Tab tabload;

    private ObservableList<String> timeList;
    private ObservableList<Integer> timeId;
    private ObservableList<String> list;
    
    protected ContextMenu menu,mondaymenu,tuesdaymenu,wednesdaymenu,thursdaymenu,fridaymenu,saturdaymenu,sundaymenu,timeMenu;
    protected MenuItem item1,delmon,deltue,delwed,delthu,delfri,delsat,delsun;
    protected MenuItem compmon,comptue,compwed,compthu,compfri,compsat,compsun;
    protected MenuItem addItem,deleteItem,deleteTask;
    protected ListCell<String> cell,timecell;
        
    private ObservableList<String> mondaylst;
    private ObservableList<String> tuesdaylst;
    private ObservableList<String> wednesdaylst;
    private ObservableList<String> thursdaylst;
    private ObservableList<String> fridaylst;
    private ObservableList<String> saturdaylst;
    private ObservableList<String> sundaylst;
    
    protected handleLoadTaskCell cellobj ;
    
    @FXML
    private JFXTextField taskname;
    @FXML
    private Label status;
    
    private loadTask load;
    
    protected String username,name;   
    protected int counter,keeper1,keeper2,len;
    private dbConnection dc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //add menu
        menu = new ContextMenu();
        item1= new MenuItem("Add Item");
        deleteTask=new MenuItem("Delete Task");
        menu.getItems().addAll(item1,deleteTask);

        cellobj = new handleLoadTaskCell();
        cellobj.handleCell(loadTask, menu);
        
        //deletemenu
        mondaymenu = new ContextMenu();       
        delmon  = new MenuItem("Delete");
        compmon  = new MenuItem("Complete");
        mondaymenu.getItems().addAll(delmon,compmon);
        
        tuesdaymenu = new ContextMenu();
        deltue  = new MenuItem("Delete");
        comptue  = new MenuItem("Complete");
        tuesdaymenu.getItems().addAll(deltue,comptue);
        
        wednesdaymenu = new ContextMenu();
        delwed  = new MenuItem("Delete");
        compwed  = new MenuItem("Complete");
        wednesdaymenu.getItems().addAll(delwed,compwed);
        
        thursdaymenu= new ContextMenu();
        delthu  = new MenuItem("Delete");
        compthu  = new MenuItem("Complete");
        thursdaymenu.getItems().addAll(delthu,compthu);
        
        fridaymenu=new ContextMenu();
        delfri  = new MenuItem("Delete");
        compfri=new MenuItem("Complete");
        fridaymenu.getItems().addAll(delfri,compfri);
        
        saturdaymenu = new ContextMenu();
        delsat  = new MenuItem("Delete");
        compsat  = new MenuItem("Complete");
        saturdaymenu.getItems().addAll(delsat,compsat);
        
        sundaymenu = new ContextMenu();
        delsun  = new MenuItem("Delete");
        compsun  = new MenuItem("Complete");
        sundaymenu.getItems().addAll(delsun,compsun);
        
        timeMenu = new ContextMenu();
        addItem = new MenuItem("Add");
        deleteItem = new MenuItem("Delete");
        timeMenu.getItems().addAll(addItem,deleteItem);

    }    
    
    public void getUserName(String name){
        this.username = name;
    }
    
    public void handleAddTask(ActionEvent event){
       
        String sql = "INSERT INTO loaddata(user,name) VALUES(?,?)";
       
        try {
            
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, taskname.getText());
            ps.execute();
            ps.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        status.setText("Added "+taskname.getText());
        load = new loadTask();
        loadTask.setItems(load.handleLoadTask(username));
        
    }
    
    public void handleLoadTask(MouseEvent event){

        dc = new dbConnection();
        name=loadTask.getSelectionModel().getSelectedItem();
        
        //add time
        LoadTimeTable timetable = new LoadTimeTable();
        timeList=timetable.loadTime(username, name);
        timeId=timetable.getTimeid();
        timeListView.setItems(timeList);
        
        try{
            
            TaskDistribution td = new TaskDistribution();
            
            mondaylst=td.getTask("select * from mondayTask", username,name);
            tuesdaylst=td.getTask("select * from tuesdayTask", username,name);
            wednesdaylst=td.getTask("select * from wednesdayTask", username,name);
            thursdaylst=td.getTask("select * from thursdayTask", username,name);
            fridaylst=td.getTask("select * from fridayTask", username,name);
            saturdaylst=td.getTask("select * from saturdayTask", username,name);
            sundaylst=td.getTask("select * from sundayTask", username,name);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mondayList.setItems(mondaylst);
        tuesdayList.setItems(tuesdaylst);
        wednesdayList.setItems(wednesdaylst);
        thursdayList.setItems(thursdaylst);
        fridayList.setItems(fridaylst);
        saturdayList.setItems(saturdaylst);
        sundayList.setItems(sundaylst);
        
        /*Add item to task*/

        cellobj = new handleLoadTaskCell();
        cellobj.handleCell(loadTask, menu);
        
        //Add item into day listviews
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               
                try{
                    
                    Stage stage = new Stage();
                    FXMLLoader loader  = new FXMLLoader(getClass().getResource("loadtaskitem.fxml"));
                    Parent root = loader.load();
                    
                    loadTaskItemController control = loader.<loadTaskItemController>getController();
                    control.acquireId(loadTask.getSelectionModel().getSelectedItem());  
                    control.acquireUser(username);
                    control.acquireListView(mondayList,tuesdayList,wednesdayList,thursdayList,fridayList,
                            saturdayList,sundayList);

                    MoveScreen screen = new MoveScreen(root, stage);
                    
                    Scene scene = new Scene(root);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.setTitle("Add Item");
                    stage.show();
                   
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        
        //Delete task
        deleteTask.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                String sql = "DELETE FROM loaddata WHERE user=? and name=?";
                try {
            
                    Connection conn = dbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, username);
                    ps.setString(2, name);
                    ps.execute();
                    conn.close();
                    
                    //timeTable items are deleted
                    conn = dbConnection.getConnection();
                    ps=conn.prepareStatement("DELETE FROM timeTable WHERE user=? and task=?");
                    ps.setString(1, username);
                    ps.setString(2, name);
                    ps.execute();
                    conn.close();
            
                } catch (SQLException ex) {
                    Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                //Items of days are deleted
                deleteSql deleteObj = new deleteSql();
                deleteObj.deleteTaskandItem(username, name, "monday");
                deleteObj.deleteTaskandItem(username, name, "tuesday");
                deleteObj.deleteTaskandItem(username, name, "wednesday");
                deleteObj.deleteTaskandItem(username, name, "thursday");
                deleteObj.deleteTaskandItem(username, name, "friday");
                deleteObj.deleteTaskandItem(username, name, "saturday");
                deleteObj.deleteTaskandItem(username, name, "sunday");
               
                //Refresh listTaskView 
                load = new loadTask();
                loadTask.setItems(load.handleLoadTask(username));
                
            }
            
        });

    }
    
    public void handleMonday(MouseEvent event){
 
        String name=mondayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            mondaymenu.hide();
            if(name!=null){

                if(name.isEmpty()){
                    compmon.setDisable(true);
                    mondaymenu.show(mondayList, event.getScreenX(), event.getScreenY());
                }else{
                    compmon.setDisable(false);
                    mondaymenu.show(mondayList, event.getScreenX(), event.getScreenY());
                }

            }
            
        }
        
        if(button==MouseButton.PRIMARY && mondaymenu.isShowing()){
            mondaymenu.hide();
        }
        
        delmon.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name, "monday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                mondayList.setItems(reload.refresh(username, id, "monday"));

            }
          
            
        });
        
        compmon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("mondayTask", username, id, name);
                
            }
        });
    }
    
    public void handleTuesday(MouseEvent event){

        String name=tuesdayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            tuesdaymenu.hide();
            if(name != null){
                if(name.isEmpty()){
                    comptue.setDisable(true);
                    tuesdaymenu.show(tuesdayList, event.getScreenX(), event.getScreenY());
                }else{
                    comptue.setDisable(false);
                    tuesdaymenu.show(tuesdayList, event.getScreenX(), event.getScreenY());
                }
            }
        }
        
        if(button==MouseButton.PRIMARY && tuesdaymenu.isShowing()){
            tuesdaymenu.hide();
        }
        
        
        deltue.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name,"tuesday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                tuesdayList.setItems(reload.refresh(username, id, "tuesday"));

            }
          
            
        });
        
        comptue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("tuesdayTask", username, id, name);
            }
        });
        
    }
     
    public void handleWednesday(MouseEvent event){

        String name=wednesdayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            wednesdaymenu.hide();
            if(name!=null){
             
                if(name.isEmpty()){
                    compwed.setDisable(true);
                    wednesdaymenu.show(wednesdayList, event.getScreenX(), event.getScreenY());
                }else{
                    compwed.setDisable(false);
                    wednesdaymenu.show(wednesdayList, event.getScreenX(), event.getScreenY());
                }
                
            }
                 
            
        }
        
        if(button==MouseButton.PRIMARY &&  wednesdaymenu.isShowing()){
             wednesdaymenu.hide();
        }

        delwed.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name,"wednesday");
                deleteObj.deleteSqlStatement();

                RefreshDayListView reload = new RefreshDayListView();
                wednesdayList.setItems(reload.refresh(username, id, "wednesday"));
                
            }
          
            
        });
        
        compwed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("wednesdayTask", username, id, name);
            }
        });
        
    }
   
    public void handleThursday(MouseEvent event){

        String name=thursdayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            thursdaymenu.hide();
            if(name!=null){
               
                if(name.isEmpty()){
                    compthu.setDisable(true);
                    thursdaymenu.show(thursdayList, event.getScreenX(), event.getScreenY());
                }else{
                    compthu.setDisable(false);
                    thursdaymenu.show(thursdayList, event.getScreenX(), event.getScreenY());
                }
            }
            
        }
        
        if(button==MouseButton.PRIMARY && thursdaymenu.isShowing()){
            thursdaymenu.hide();
        }
        
        
        delthu.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name,"thursday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                thursdayList.setItems(reload.refresh(username, id, "thursday"));

            }
          
            
        });
        
        compthu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("thursdayTask", username, id, name);
                
            }
        });
        
    }
    
    public void handleFriday(MouseEvent event){

        String name=fridayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            fridaymenu.hide();
            if(name!=null){
                
                if(name.isEmpty()){
                    compfri.setDisable(true);
                    fridaymenu.show(fridayList, event.getScreenX(), event.getScreenY());
                }else{
                    compfri.setDisable(false);
                    fridaymenu.show(fridayList, event.getScreenX(), event.getScreenY());
                }
            
            }
        }
        
        if(button==MouseButton.PRIMARY && fridaymenu.isShowing()){
            fridaymenu.hide();
        }
        
        
        delfri.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name, "friday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                fridayList.setItems(reload.refresh(username, id, "friday"));

            }
          
            
        });
        
        compfri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("fridayTask", username, id, name);
                
            }
        });
        
    }
    
    public void handleSaturday(MouseEvent event){

        String name=saturdayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            saturdaymenu.hide();
            if(name!=null){
                
                if(name.isEmpty()){
                    compsat.setDisable(true);
                    saturdaymenu.show(saturdayList, event.getScreenX(), event.getScreenY());
                }else{
                    compsat.setDisable(false);
                    saturdaymenu.show(saturdayList, event.getScreenX(), event.getScreenY());
                }
            }
        }
        
        if(button==MouseButton.PRIMARY && saturdaymenu.isShowing()){
            saturdaymenu.hide();
        }

        delsat.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name,"saturday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                saturdayList.setItems(reload.refresh(username, id, "saturday"));

            }
          
            
        });
        
        compsat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("saturdayTask", username, id, name);
                
            }
        });
        
    }
    
    public void handleSunday(MouseEvent event){

        String name=sundayList.getSelectionModel().getSelectedItem();
        String id = loadTask.getSelectionModel().getSelectedItem();
        
        MouseButton button = event.getButton();
        
        if(button==MouseButton.SECONDARY){
            
            sundaymenu.hide();
            if(name!=null){
                if(name.isEmpty()){ 
                    compsun.setDisable(true);
                    sundaymenu.show(sundayList, event.getScreenX(), event.getScreenY());
                }else{
                    compsun.setDisable(false);
                    sundaymenu.show(sundayList, event.getScreenX(), event.getScreenY());
                }
            }   
            
        }
        
        if(button==MouseButton.PRIMARY && sundaymenu.isShowing()){
            sundaymenu.hide();
        }
        
        
        delsun.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {

                deleteSql deleteObj = new deleteSql(username,id,name, "sunday");
                deleteObj.deleteSqlStatement();
                
                RefreshDayListView reload = new RefreshDayListView();
                sundayList.setItems(reload.refresh(username, id, "sunday"));

            }
          
            
        });
        
        compsun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             
                CompleteNumber completeObj = new CompleteNumber();
                completeObj.completeNumber("sundayTask", username, id, name);
                
            }
        });
        
    }
    
    public void handleTimeTable(MouseEvent event){

        String user = username;
        String task = name;
        counter=0;
        keeper1=0;
        if(timeId.isEmpty()){
            keeper2=0;
        }else{
            keeper2=timeId.get(0);
        }
        len = timeId.size();
        
        for(String s:timeList){
            if(s.equals(timeListView.getSelectionModel().getSelectedItem())){
                keeper1=timeId.get(counter);
            } 
            if(timeId.get(counter)>keeper2){
                keeper2=timeId.get(counter);
            }
            counter++;
        }
 
        MouseButton button = event.getButton();
       
        if(button==MouseButton.SECONDARY){
            
            timeMenu.hide();
            timeMenu.show(timeListView, event.getScreenX(), event.getScreenY());
            if(timeListView.getSelectionModel().getSelectedItem()==null){
                deleteItem.setDisable(true);
            }else{
                deleteItem.setDisable(false);
            }
                        
        }
        
        if(button==MouseButton.PRIMARY && timeMenu.isShowing()){
            timeMenu.hide();
        }
        
        timeListView.setEditable(true);   
        timeListView.setCellFactory(TextFieldListCell.forListView());
        
        timeListView.setOnEditCommit(new EventHandler<EditEvent<String>>() {
            @Override
            public void handle(EditEvent<String> e) {
                
                String sql = "UPDATE timeTable SET time=? WHERE id = ? and user=? and task=?";
        
                try {

                    Connection conn = dbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, e.getNewValue());
                    ps.setInt(2, keeper1);
                    ps.setString(3, user);
                    ps.setString(4, task);

                    ps.execute();
                    conn.close();
                    
                    timeList.clear();
                    LoadTimeTable timetable = new LoadTimeTable();
                    timeList=timetable.loadTime(username, name);
                    timeId=timetable.getTimeid();
                    timeListView.setItems(timeList);
                    

                } catch (SQLException ex) {
                    Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
            } 
        });
        
        addItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
             
                String sql = "INSERT INTO timeTable(user,task,id,time) VALUES(?,?,?,?)";
        
                try {

                    Connection conn = dbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, user);
                    ps.setString(2, task);
                    ps.setInt(3, keeper2+1);
                    ps.setString(4, "time");
                    
                    ps.execute();
                    conn.close();
                    
                    timeList.clear();
                    LoadTimeTable timetable = new LoadTimeTable();
                    timeList=timetable.loadTime(username, name);
                    timeId=timetable.getTimeid();
                    timeListView.setItems(timeList);
                    

                } catch (SQLException ex) {
                    Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                
            }
            
        });
        
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                String sql ="DELETE FROM timeTable WHERE user=? and task=? and id=?";
                try {

                    Connection conn = dbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, user);
                    ps.setString(2, task);
                    ps.setInt(3, keeper1);
                    ps.execute();
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                timeList.clear();
                LoadTimeTable timetable = new LoadTimeTable();
                timeList=timetable.loadTime(username, name);
                timeId=timetable.getTimeid();
                timeListView.setItems(timeList);
                
            }
        });
               
    }
    
    public void handleTabPane(MouseEvent event){
        
        if(tabload.isSelected()){
            
            load = new loadTask();
            loadTask.setItems(load.handleLoadTask(username));
        
        }
    }
    
    public void handleStatistic(ActionEvent event) throws IOException{
        
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistic.fxml"));
        Parent root = loader.load();
        
        StatisticController control = loader.<StatisticController>getController();
        control.acquireData(username, name);
        control.showStatistic();

        MoveScreen move = new MoveScreen(root, stage);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("statistic.css").toExternalForm());
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
  
    public void handleClearButton(ActionEvent event){
        
        taskname.clear();
        status.setText("");
        
    }
    
}
