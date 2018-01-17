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
    HOTELROOMS_LIST("hotelroomsList"),
    PAGES_COUNT("pages_count"),
    CURRENT_PAGE_NUMBER("currentPageNumber"),
    ROOM_TYPES_LIST("roomTypesList"),
    HOTELROOM("hotelroom"),
    HOTELROOM_ID("hotelroom_id"),
    NUMBER("number"),
    PLACES("places"),
    ROOM_IMAGE("room_image"),
    PRICE("price"),
    FLOOR("floor"),
    ROOM_TYPE("room_type"),

    PAGE("page");


    private String value;

    RequestParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
