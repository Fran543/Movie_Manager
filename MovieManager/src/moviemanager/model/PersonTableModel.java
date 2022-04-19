/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author fran
 */
public class PersonTableModel extends AbstractTableModel{
    
    
    private static final String[] COLUMN_NAMES = { "Id", "First name", "Last name"};
    
    private List<Person> persons;

    public PersonTableModel(List<Person> persons) {
        this.persons = persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        //return COLUMN_NAMES.length;
        return Person.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return persons.get(rowIndex).getId();
            case 1:
                return persons.get(rowIndex).getFirstName();
            case 2:
                return persons.get(rowIndex).getLastName();
            default:
                return new RuntimeException("No such coulumn");
        }
    }
    
    @Override
    public String getColumnName (int column){
        return COLUMN_NAMES[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
