package tdannandjuliet.assignment.enkotaApp.helper;

public class ViewMatookeOrders {
    private String moid;
    private String moclientname;
    private String moname;
    private String moquantity;
    private String moamount;
    private String moaddress;
    private String moorderdate;
    private String  modeliverydate;
    private String  mostatus;
    private String moimage;

    public ViewMatookeOrders(String moid, String moclientname, String moname, String moquantity, String moamount, String moaddress, String moorderdate, String modeliverydate, String mostatus,String moimage) {
        this.moid = moid;
        this.moclientname = moclientname;
        this.moname = moname;
        this.moquantity = moquantity;
        this.moamount = moamount;
        this.moaddress = moaddress;
        this.moorderdate = moorderdate;
        this.modeliverydate = modeliverydate;
        this.mostatus = mostatus;
        this.moimage = moimage;
    }

    public String getMoid() {
        return moid;
    }

    public String getMoclientname() {
        return moclientname;
    }

    public String getMoname() {
        return moname;
    }

    public String getMoquantity() {
        return moquantity;
    }

    public String getMoamount() {
        return moamount;
    }

    public String getMoaddress() {
        return moaddress;
    }

    public String getMoorderdate() {
        return moorderdate;
    }

    public String getModeliverydate() {
        return modeliverydate;
    }

    public String getMostatus() {
        return mostatus;
    }

    public String getMoimage() {
        return moimage;
    }
}
