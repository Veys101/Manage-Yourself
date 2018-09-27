package movescreen;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MoveScreen {
    
    private double xOffset;
    private double yOffset;
    
    public MoveScreen(){
        
    }
    
    public MoveScreen(Parent root,Stage stage){
        root.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            
                xOffset=event.getSceneX();
                yOffset=event.getSceneY();
                
            }
            
        });
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            
                stage.setX(event.getScreenX()-xOffset);
                stage.setY(event.getScreenY()-yOffset);
                
            }
            
        });
    }
    
}
