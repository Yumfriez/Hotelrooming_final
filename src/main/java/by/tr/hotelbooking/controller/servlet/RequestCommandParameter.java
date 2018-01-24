package by.tr.hotelbooking.controller.servlet;

public enum  RequestCommandParameter {
    SHOW_HOTELROOMS("show_hotelrooms"),
    FIND_HOTELROOMS("find_hotelrooms"),
    SHOW_NEW_OFFERS("show_new_offers"),
    SHOW_USER_CONTRACTS("show_user_contracts"),
    SHOW_CONTRACTS("show_contracts"),
    SHOW_COMMENTS("show_comments"),
    REDIRECT("redirect"),
    SHOW_ORDERS("show_orders");

    private String value;

    RequestCommandParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
