package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Contract;
import by.tr.hotelbooking.entities.ContractDTO;
import by.tr.hotelbooking.entities.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContractDAO extends AbstractJDBCDao<Contract> {

    private final static String GET_ALL_CONTRACTS = "SELECT account.login, account.name, account.surname, contract.contract_id, "+
            "contract.date_in, contract.date_out, contract.total_price FROM hotelrooms.account JOIN hotelrooms.contract "+
            "ON hotelrooms.contract.u_id = hotelrooms.account.id";
    private final static String GET_CONTRACTS_FOR_PAGE = "SELECT contract.contract_id, contract.date_in, contract.date_out, " +
            " contract.total_price, contract.accept_status, account.login, hotelroom.number FROM contract INNER JOIN account ON " +
            " contract.u_id = account.u_id INNER JOIN hotelroom ON contract.hotelroom_id = hotelroom.id GROUP BY contract.contract_id LIMIT ?,?";
    private final static String GET_ACCEPTED_CONTRACTS_FOR_PAGE_BY_LOGIN = "SELECT contract.contract_id, contract.date_in, contract.date_out, " +
            " contract.total_price, contract.accept_status, account.login, hotelroom.number FROM contract INNER JOIN account ON " +
            " contract.u_id = account.u_id INNER JOIN hotelroom ON contract.hotelroom_id = hotelroom.id " +
            " WHERE account.u_id = (SELECT account.u_id FROM account WHERE account.login = ?) AND contract.accept_status = TRUE " +
            " GROUP BY contract.contract_id LIMIT ?,?";
    private final static String GET_NONACCEPTED_CONTRACTS_FOR_PAGE_BY_LOGIN = "SELECT contract.contract_id, contract.date_in, contract.date_out, " +
            " contract.total_price, contract.accept_status, account.login, hotelroom.number, hotelroom.places_count," +
            " hotelroom.floor, hotelroom.imageName, hotelroom.room_type_id, room_types.name FROM contract INNER JOIN account ON " +
            " contract.u_id = account.u_id INNER JOIN hotelroom ON contract.hotelroom_id = hotelroom.id " +
            " INNER JOIN room_types ON hotelroom.room_type_id = room_types.id"+
            " WHERE account.u_id = (SELECT account.u_id FROM account WHERE account.login = ?) AND contract.accept_status = FALSE " +
            " GROUP BY contract.contract_id LIMIT ?,?";
    private final static String GET_CONTRACT_BY_ID = "SELECT contract.contract_id, contract.date_in, contract.date_out, " +
            " contract.total_price, contract.accept_status, account.login, hotelroom.number FROM contract INNER JOIN contract ON " +
            " contract.u_id = account.u_id INNER JOIN comment ON contract.hotelroom_id = hotelroom.id WHERE contract.contract_id = ?";
    private static final String REMOVE_CONTRACT = "DELETE FROM hotelrooms.contract WHERE contract_id=?";
    private static final String ADD_CONTRACT = "INSERT INTO hotelrooms.contract (date_in, date_out, total_price, u_id, hotelroom_id) "+
            "VALUES (?, ?, ?, (SELECT u_id FROM account WHERE login=?), (SELECT id FROM hotelroom WHERE number=?))";
    private static final String GET_COUNT = "SELECT count(*) from hotelrooms.contract";
    private static final String GET_LAST_ID = "SELECT contract.contract_id FROM hotelrooms.contract ORDER BY contract.contract_id desc limit 1;";
    private static final String SET_CONTRACT_ACCEPTED_QUERY = "UPDATE contract SET accept_status = TRUE WHERE contract_id=?";

    public ContractDAO(){
    }

    public int getNumberOfContracts() throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_COUNT);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Contracts counting in DB error " + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return result;
    }

    public List<ContractDTO> getNonAcceptedContractsForPageByLogin(int pageNumber, int offset, String login) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<ContractDTO> contractList;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_NONACCEPTED_CONTRACTS_FOR_PAGE_BY_LOGIN);
            resultSet = getContractDataFromStatementByQueryAndParams(preparedStatement,login, pageNumber, offset);
            contractList = new ArrayList<>();
            while (resultSet.next()) {
                ContractDTO contractDTO = new ContractDTO();
                setContractDTOParameters(contractDTO, resultSet);
                contractList.add(contractDTO);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Get contracts from DB for page by login error ",e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return contractList;
    }

    public List<Contract> getAcceptedContractsForPageByLogin(int pageNumber, int offset, String login) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<Contract> contractList;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_ACCEPTED_CONTRACTS_FOR_PAGE_BY_LOGIN);
            resultSet = getContractDataFromStatementByQueryAndParams(preparedStatement,login, pageNumber, offset);
            contractList = new ArrayList<>();
            while (resultSet.next()) {
                Contract contract = new Contract();
                setContractParameters(contract, resultSet);
                contractList.add(contract);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Get accepted contracts from DB for page by login error ",e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return contractList;
    }

    public List<Contract> getContractsForPage(int pageNumber, int offset) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<Contract> contractList;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_CONTRACTS_FOR_PAGE);
            preparedStatement.setInt(1, pageNumber);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();
            contractList = new ArrayList<>();
            while (resultSet.next()) {
                Contract contract = new Contract();
                setContractParameters(contract, resultSet);
                contractList.add(contract);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            throw new DAOException("Get contracts from DB for page error ",e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return contractList;
    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_CONTRACTS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return GET_CONTRACT_BY_ID;
    }

    @Override
    protected String getAddQuery() {
        return ADD_CONTRACT;
    }

    @Override
    protected String getUpdateQuery() {
        return SET_CONTRACT_ACCEPTED_QUERY;
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
        List<Contract> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Contract contract=new Contract();
                setContractParameters(contract, rs);
                result.add(contract);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Contract object) throws DAOException {

        try {
            statement.setDate(1, object.getDateIn());
            statement.setDate(2, object.getDateOut());
            statement.setBigDecimal(3, object.getTotalPrice());
            statement.setString(4, object.getAccountLogin());
            statement.setInt(5, object.getHotelroomNumber());
        } catch (SQLException e){
            throw new DAOException(e);
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Contract object) throws DAOException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e){
            throw new DAOException(e);
        }
    }

    private ResultSet getContractDataFromStatementByQueryAndParams(PreparedStatement preparedStatement, String login,
                                                                   int pageNumber, int offset) throws SQLException {
        preparedStatement.setString(1, login);
        preparedStatement.setInt(2,pageNumber);
        preparedStatement.setInt(3, offset);
        return preparedStatement.executeQuery();
    }

    private void setContractParameters(Contract contract, ResultSet resultSet) throws DAOException {

        try {
            contract.setId(resultSet.getInt("contract_id"));
            contract.setDateIn(resultSet.getDate("date_in"));
            contract.setDateOut(resultSet.getDate("date_out"));
            contract.setTotalPrice(resultSet.getBigDecimal("total_price"));
            contract.setAcceptStatus(resultSet.getBoolean("accept_status"));
            contract.setAccountLogin(resultSet.getString("login"));
            contract.setHotelroomNumber(resultSet.getInt("number"));
        } catch (SQLException e){
            throw new DAOException(e);
        }

    }

    private void setContractDTOParameters(ContractDTO contractDTO, ResultSet resultSet) throws DAOException {

        try {
            contractDTO.setId(resultSet.getInt("contract_id"));
            contractDTO.setDateIn(resultSet.getDate("date_in"));
            contractDTO.setDateOut(resultSet.getDate("date_out"));
            contractDTO.setTotalPrice(resultSet.getBigDecimal("total_price"));
            contractDTO.setAccountLogin(resultSet.getString("login"));
            contractDTO.setHotelroomNumber(resultSet.getInt("number"));
            contractDTO.setFloor(resultSet.getInt("floor"));
            contractDTO.setImageName(resultSet.getString("imageName"));
            contractDTO.setPlacesCount(resultSet.getInt("places_count"));
            RoomType roomType = new RoomType();
            roomType.setId(resultSet.getInt("room_type_id"));
            roomType.setName(resultSet.getString("name"));
            contractDTO.setRoomType(roomType);
        } catch (SQLException e){
            throw new DAOException(e);
        }

    }

}
