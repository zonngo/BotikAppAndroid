package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

/**
 * Created by marwuinh@gmail.com on 5/4/2017.
 */

public class MH_DataModel_Ubigeo {
    int id;
    String name;
    int tipo;
    int parent;
    boolean rowRelected = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public boolean isRowRelected() {
        return rowRelected;
    }

    public void setRowRelected(boolean rowRelected) {
        this.rowRelected = rowRelected;
    }
}
