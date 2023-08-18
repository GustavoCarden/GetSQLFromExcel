/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getsqlfromexcel;

/**
 *
 * @author GC014121
 */
public class Obj {
    
    private Integer id;
    private Integer cia_num;

    public Obj(Integer id, Integer cia_num) {
        this.id = id;
        this.cia_num = cia_num;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCia_num() {
        return cia_num;
    }

    public void setCia_num(Integer cia_num) {
        this.cia_num = cia_num;
    }
}
