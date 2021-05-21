package tdannandjuliet.assignment.enkotaApp.helper;

public class DisplayMatooke {
    private String pdId;
    private String pdt_name;
    private String pdt_type;
    private String pdct_price;
    private String pdct_qty;
    private String pdt_location;
    private String pdt_image;
    private String seller_phone;
    public DisplayMatooke(String pdId, String pdt_name, String pdt_type, String pdct_price, String pdct_qty, String pdt_location, String pdt_image, String seller_phone) {
        this.pdId = pdId;
        this.pdt_name = pdt_name;
        this.pdt_type = pdt_type;
        this.pdct_price = pdct_price;
        this.pdct_qty = pdct_qty;
        this.pdt_location = pdt_location;
        this.pdt_image = pdt_image;
        this.seller_phone = seller_phone;
    }

    public String getPdId() {
        return pdId;
    }

    public String getPdt_name() {
        return pdt_name;
    }

    public String getPdt_type() {
        return pdt_type;
    }

    public String getPdct_price() {
        return pdct_price;
    }

    public String getPdct_qty() {
        return pdct_qty;
    }

    public String getPdt_location() {
        return pdt_location;
    }

    public String getPdt_image() {
        return pdt_image;
    }



    public String getSeller_phone() {
        return seller_phone;
    }
}
