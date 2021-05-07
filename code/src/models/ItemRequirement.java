
package models;


public class ItemRequirement {

    private final Item item;
    private  Float amount;

    public ItemRequirement(Item item  , Float amount) {
        this.item = item;
        this.amount = amount;
    }
    public int hashCode() {
        return item.hashCode();
    }

    public Float getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }
    
    

    public boolean equals(Object obj) {
        ItemRequirement item = null;
        if(obj instanceof ItemRequirement ){
            item = (ItemRequirement)obj;
        }else{
            return false;
        }
        boolean flag = false;
        if(this.item.equals(item.item))
        {
            flag = true;
            if(this.amount<21){
                item.amount = item.amount+this.amount;    
            }else{
                item.amount = item.amount+(this.amount/1000);
            }
             
        }
            return flag ;
    }

    @Override
    public String toString() {
        return this.item.getItemName() + this.amount +"\n";//To change body of generated methods, choose Tools | Templates.
    }
    
}
