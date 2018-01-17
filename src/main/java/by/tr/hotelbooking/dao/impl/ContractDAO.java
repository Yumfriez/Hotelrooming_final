package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.entities.Contract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContractDAO extends AbstractJDBCDao<Contract> {

    private final static String GET_ALL_CONTRACTS = "SELECT account.login, account.name, account.surname, contract.contract_id, "+
            "contract.date_in, contract.date_out, contract.total_price FROM hotelrooms.account JOIN hotelrooms.contract "+
            "ON hotelrooms.contract.u_id = hotelrooms.account.id";
    private static final String GET_CONTRACTS_BY_USER = "SELECT account.login, account.name, account.surname, contract.contract_id, "+
            "contract.date_in, contract.date_out, contract.total_price FROM hotelrooms.account JOIN hotelrooms.contract "+
            "ON hotelrooms.contract.u_id = hotelrooms.account.id WHERE contract.u_id=?";
    private static final String REMOVE_CONTRACT = "DELETE FROM hotelrooms.contract WHERE contract_id=?";
    private static final String ADD_CONTRACT = "INSERT INTO hotelrooms.contract (date_in, date_out, total_price, u_id) "+
            "VALUES (?, ?, ?, (SELECT id FROM account WHERE login=?))";

    private static final String GET_LAST_ID = "SELECT contract.contract_id FROM hotelrooms.contract ORDER BY contract.contract_id desc limit 1;";

    public ContractDAO(){
    }

    public List<Contract> getUserContracts(Integer userId) throws DAOException{
        return new ArrayList<>();
    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_CONTRACTS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return null;
    }

    @Override
    protected String getAddQuery() {
        return ADD_CONTRACT;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return REMOVE_CONTRACT;
    }

    @Override
    protected String getLastAdded() {
        return GET_LAST_ID;
    }

    @Override
    protected List<Contract> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Contract object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Contract object) throws DAOException {

    }

}
