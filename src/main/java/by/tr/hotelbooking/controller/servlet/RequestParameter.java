package by.tr.hotelbooking.controller.servlet;

public enum RequestParameter {
    INFORMATION("information"),
    ROLE("role"),
    LOGIN("login"),
    USER("USER"),
    ADMIN("ADMINISTRATOR"),
    USER_LOCALE("userLocale"),
    WELCOME_LOCALE("welcomeLocale"),
    COMMAND("command"),
    PASSWORD("password"),
    EMAIL("email"),
    NAME("name"),
    LASTNAME("lastname"),
    LOCALE("locale"),
    USER_LIST("userList"),
    PAGINATION("pagination"),
    SHOW_HOTELROOMS("show_hotelrooms"),
    SHOW_COMMENTS("show_comments"),
    HOTELROOMS_LIST("hotelroomsList"),
    PAGES_COUNT("pages_count"),
    CURRENT_PAGE_NUMBER("currentPageNumber"),
    ROOM_TYPES_LIST("roomTypesList"),
    HOTELROOM("hotelroom"),
    HOTELROOM_ID("hotelroom_id"),
    NUMBER("number"),
    PLACES("places_count"),
    ROOM_IMAGE("room_image"),
    PRICE("price"),
    FLOOR("floor"),
    ROOM_TYPE("room_type"),
    DATE_IN("date_in"),
    DATE_OUT("date_out"),
    DAYS_COUNT("days_count"),
    MIN_PRICE("min_price"),
    MAX_PRICE("max_price"),
    ORDERS_LIST("ordersList"),

    COMMENT_ID("comment_id"),
    COMMENTS_LIST("commentsList"),
    COMMENT_TEXT("comment_text"),
    COMMENT_DATE("comment_date"),
    CLIENT_LOGIN("client_login"),
    PAGE("page");


    private String value;

    RequestParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
