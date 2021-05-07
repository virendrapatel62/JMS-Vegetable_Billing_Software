
package models;

public class BillingItem {
    float unitPrize;
    float quantity;
    Item item;
    float lineTotal;
    public static float total = 0;
    public static int billNumber;
    public BillingItem() {
    }

    public BillingItem(float unitPrize, float quantity, Item item, float lineTotal) {
        this.unitPrize = unitPrize;
        this.quantity = quantity;
        this.item = item;
        this.lineTotal = lineTotal;
    }

    public Item getItem() {
        return item;
    }

    public float getLineTotal() {
        return lineTotal;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getUnitPrize() {
        return unitPrize;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public void setUnitPrize(float unitPrize) {
        this.unitPrize = unitPrize;
    }
     
}
