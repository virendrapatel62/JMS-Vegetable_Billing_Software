/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

public enum OrderStatus {
    PENDING(1) , COMPLETED(2);
    private int id;
    private OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public static OrderStatus getOrderStatusById(int id){
          switch(id){
              case 1:
                  return PENDING;
              case 2:
                  return COMPLETED;
              default:
                  return null;
          }
    }
}
