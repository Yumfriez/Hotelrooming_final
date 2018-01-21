package by.tr.hotelbooking.controller.servlet;

public enum JspPageName {
    ADMIN_USER_PAGE("/jsp/admin-user.jsp"),
    WELCOME_PAGE("/jsp/welcome.jsp"),
    USERS_PAGE("/jsp/user-list.jsp"),
    HOTELROOMS_PAGE("/jsp/hotelrooms-list.jsp"),
    EDIT_HOTELROOM_PAGE("/jsp/page_for_edit_hotelroom.jsp"),
    ADD_HOTELROOM_PAGE("/jsp/page_for_add_hotelroom.jsp"),
    MAKE_ORDER_PAGE("/jsp/page_for_make_order.jsp"),
    COMMENTS_PAGE("/jsp/comments.jsp"),
    ORDERS_PAGE("/jsp/orders-list.jsp");

    private String path;

    JspPageName(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
