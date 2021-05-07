package controllers;

import models.*;

public class Save {

    public Save(){
        
    }
    public static String saveItem(Item item){
            return item.saveItem();
    }
    public static String saveColony(Colony colony){
            return colony.saveColony();
    }
    public static int saveBill(Bill bill){
            return bill.saveBill();
    }
    public static String saveBillDescription(BillDescription billD){
            return billD.saveBillDescription();
    }
       
}
