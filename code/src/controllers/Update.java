
package controllers;

import models.*;

public class Update {

    public static void updateBill(Bill bill) {
        bill.updateAmount();
    }
    public boolean changeItemName(Item item){
        return item.changeName();
    }
    public boolean changeUnitPrize(Item item){
        System.out.println(item.getUnitPrize());
        return item.changeUnitPrize();
    }
    public boolean deleteItem(Item item){
        return item.deleteItem();
    }
    public boolean changeColonyName(Colony colony){
        return colony.changeName();
    }
    public boolean deleteColony(Colony colony){
        return colony.deleteColony();
    }
    public static boolean changeOrderStatus(int billNo, OrderStatus orderStatus) {
        return Bill.changeOrderStatus(billNo, orderStatus);
    }
    public static void incrementUserSerialNumber(Colony colony  ,int number ){
        User.incrementUserSerialNumber(colony , number );
    }
}
