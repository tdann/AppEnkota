package tdannandjuliet.assignment.enkotaApp.helper;

public class BuyerOrders {
    private String oid;
    private String oimage;
    private String oname;
    private String oqty;
    private String oamount;
    private String oaddress;
    private String ostatus;
    private String o_orderDate;
    private String o_deliverydate;

    public BuyerOrders(String oid, String oimage, String oname, String oqty, String oamount, String oaddress, String ostatus, String o_orderDate, String o_deliverydate) {
        this.oid = oid;
        this.oimage = oimage;
        this.oname = oname;
        this.oqty = oqty;
        this.oamount = oamount;
        this.oaddress = oaddress;
        this.ostatus = ostatus;
        this.o_orderDate = o_orderDate;
        this.o_deliverydate = o_deliverydate;
    }

    public String getOid() {
        return oid;
    }

    public String getOimage() {
        return oimage;
    }

    public String getOname() {
        return oname;
    }

    public String getOqty() {
        return oqty;
    }

    public String getOamount() {
        return oamount;
    }

    public String getOaddress() {
        return oaddress;
    }

    public String getOstatus() {
        return ostatus;
    }

    public String getO_orderDate() {
        return o_orderDate;
    }

    public String getO_deliverydate() {
        return o_deliverydate;
    }
}
