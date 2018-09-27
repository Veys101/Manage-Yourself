package application;

import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class handleLoadTaskCell {

    private ListCell<String> cell;
    
    public void handleCell(JFXListView<String> loadTask,ContextMenu menu){
        
        loadTask.setCellFactory(lv->{
            
            cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            
            cell.emptyProperty().addListener((obs,wasEmpty,isNowEmpty)->{
                if(isNowEmpty){
                    cell.setContextMenu(null);
                }else{
                    cell.setContextMenu(menu);
                }
            });
            cell.setPrefHeight(36);
         
            return cell;
        
        });
        
        if(cell!=null){
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    
                    MouseButton button = event.getButton();
                    if(button==MouseButton.SECONDARY){
 
                        menu.show(loadTask, event.getScreenX(), event.getScreenY());
                       
                        
                    }

                }

            });

           
        }
        
        
    }
    
}
