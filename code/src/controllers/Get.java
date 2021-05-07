
package controllers;

import java.sql.Date;
import java.util.ArrayList;
import models.*;

public class Get {
    public ArrayList<Item> getItems(){
        return Item.getItems();
    }
    public ArrayList<Colony> getColonies(){
        return Colony.getColonies();
    }
    public static ArrayList<BillingItem> getBillDescriptions(int billNo , Date billingDate){
        return BillDescription.getBillDescriptions(billNo, billingDate);
    }
}
