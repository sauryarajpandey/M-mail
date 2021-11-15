package com.saurya.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {
    public Node getIconForFolder(String folderName){
        String lowerCaseFolderName= folderName.toLowerCase();
        ImageView imageView;
        try{
            if(lowerCaseFolderName.contains("@")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/computer.png")));
            }
            else if (lowerCaseFolderName.contains("inbox")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/gmail.png")));
            }
            else if (lowerCaseFolderName.contains("sent")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/sent .png")));
            }
            else if (lowerCaseFolderName.contains("spam")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/spam1.png")));
            }

            else if (lowerCaseFolderName.contains("trash")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/trash.png")));
            }
            else if (lowerCaseFolderName.contains("important")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/important.png")));
            }
            else if (lowerCaseFolderName.contains("all mail")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/allmail.png")));
            }
            else if (lowerCaseFolderName.contains("draft")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/draft.png")));
            }
            else if (lowerCaseFolderName.contains("starred")){
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/star.png")));
            }
            else {
                imageView =  new ImageView(new Image(getClass().getResourceAsStream("icons/folder.png")));
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //to make the image view bigger or smaller
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }
}
