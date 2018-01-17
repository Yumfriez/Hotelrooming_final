package by.tr.hotelbooking.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Contract implements Identified{

    private int id;
    private String accountLogin;
    private Date dateIn;
    private Date dateOut;
    private BigDecimal totalPrice;

    @Override
    public Integer getId() {
        return id;
    }
}
