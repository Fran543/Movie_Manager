/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;


/**
 *
 * @author fran
 */
public class Director extends Person{

    public Director() {
    }

    public Director(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Director(String firstName, String lastName) {
        super(firstName, lastName);
    } 
}
