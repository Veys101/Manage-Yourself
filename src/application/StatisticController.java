package application;

import dbconnection.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StatisticController implements Initializable {

    @FXML 
    private Label closeLabel;
    @FXML
    private PieChart piechart;
    @FXML
    private Label nameTask;
    @FXML
    private Label percentlbl;
    
    private ObservableList<String> task;
    private ObservableList<Integer> completenum;
    private ObservableList<Data> dataList;
    
    private int counter=0;
    private int totalCount=0;
    
    private String user,id;
    private String dayArray[] = {"monday","tuesday","wednesday","thursday","friday","saturday","sunday"};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    }
    
    public void acquireData(String user,String id){
        this.user= user;
        this.id = id;
    }
    
    public void handleCloseLabel(MouseEvent event){
        
        Stage stage = (Stage)closeLabel.getScene().getWindow();
        stage.close();
        
        
    }
    
    public void showStatistic(){
        
        nameTask.setText("For "+id);
        nameTask.setAlignment(Pos.CENTER);
        
        task = FXCollections.observableArrayList();
        completenum = FXCollections.observableArrayList();
        
        for(int i=0;i<dayArray.length;i++){
            
            String sql = "Select * from "+dayArray[i]+"Task";
       
            try {
                Connection conn = dbConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while(rs.next()){
                
                    if(rs.getString(1).equals(user) && rs.getString(2).equals(id)){
                        if(!rs.getString(3).isEmpty()){
                            task.add(rs.getString(3));
                            completenum.add(rs.getInt(4));
                        }
                    }
                
                }
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
            } 

        }
        
        HashSet<String> setTask = new HashSet<>();
        for(int i=0;i<task.size();i++){
            setTask.add(task.get(i));
        }
        
        HashMap<String,Integer> map = new HashMap<>();
        
        Iterator itr = setTask.iterator();
        totalCount=0;
        while(itr.hasNext()){
            
            String str = String.valueOf(itr.next());
            counter=0;
            for(int i=0;i<task.size();i++){
                
                if(str.equals(task.get(i))){
                    counter+=completenum.get(i);
                    totalCount+=completenum.get(i);
                }
                
            }
            map.put(str, counter);
        }
        
        ArrayList<String> keyList = new ArrayList<>(setTask);
        
        dataList = FXCollections.observableArrayList();
        
        for(int i=0;i<keyList.size();i++){
            dataList.addAll(new PieChart.Data(keyList.get(i), map.get(keyList.get(i))));
        }
        
        piechart.setData(dataList);
        
        for(final PieChart.Data data :  piechart.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                
                    
                    double num = (data.getPieValue()/totalCount)*100;
                    String strNum = String.format("%.2f", num);
                    percentlbl.setText(data.getName()+" number --- "+(int)data.getPieValue()+"\n"+
                            data.getName()+" percentage"+" ---"+" % "+strNum);
                    
                    
                }
                
            });
        }
        
        percentlbl.setAlignment(Pos.CENTER);
    }
    
    public ObservableList<String> getTaskList(){
        return task;
    }
    
    public ObservableList<Integer> getCompletenumList(){
        return completenum;
    }
    
}
