package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.ContractDAO;
import by.tr.hotelbooking.dao.impl.HotelroomDAO;
import by.tr.hotelbooking.dao.impl.OrderDAO;
import by.tr.hotelbooking.entities.Contract;
import by.tr.hotelbooking.entities.ContractDTO;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.services.ContractService;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class ContractServiceImpl implements ContractService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public List<Contract> getAcceptedUserContracts(int page, String userLogin) throws ServiceException {
        List<Contract> contracts = null;
        try {
            ContractDAO contractDAO = daoFactory.getContractDAO();
            int recordsCount = 10;
            contracts = contractDAO.getAcceptedContractsForPageByLogin((page-1)*recordsCount,recordsCount, userLogin);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return contracts;
    }

    @Override
    public List<ContractDTO> getNewUserOffers(int page, String userLogin) throws ServiceException {
        List<ContractDTO> contracts = null;
        try {
            ContractDAO contractDAO = daoFactory.getContractDAO();
            int recordsCount = 10;
            contracts = contractDAO.getNonAcceptedContractsForPageByLogin((page-1)*recordsCount,recordsCount, userLogin);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return contracts;
    }

    @Override
    public List<Contract> getContractsForPage(int page) throws ServiceException {
        List<Contract> contracts = null;
        try {
            ContractDAO contractDAO = daoFactory.getContractDAO();
            contracts = contractDAO.getContractsForPage((page-1)*10,10);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return contracts;
    }

    @Override
    public int getPagesCount(int recordsCount) throws ServiceException {
        return (int) Math.ceil(recordsCount * 1.0 / 10);
    }

    @Override
    public int getRecordsCount() throws ServiceException {
        int num = 0;
        try {
            num = daoFactory.getContractDAO().getNumberOfContracts();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return num;
    }

    @Override
    public int getPageNumber(String stringPageValue) {
        int page;
        try {
            page = Integer.parseInt(stringPageValue);
        } catch (NumberFormatException e) {
            page = 1;
        }
        return page;
    }

    @Override
    public void addContract(int orderId, int hotelroomId) throws ServiceException {
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            Order order = orderDAO.getByPK(orderId);
            HotelroomDAO hotelroomDAO = daoFactory.getHotelroomDAO();
            Hotelroom hotelroom = hotelroomDAO.getByPK(hotelroomId);

            Contract contract = createContractFromData(hotelroom, order);

            ContractDAO contractDAO = daoFactory.getContractDAO();
            contractDAO.add(contract);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void acceptContract(int contractId) throws ServiceException {
        ContractDAO contractDAO = daoFactory.getContractDAO();
        try {
            Contract contract = new Contract();
            contract.setId(contractId);
            contractDAO.update(contract);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void declineContract(int contractId) throws ServiceException {
        ContractDAO contractDAO = daoFactory.getContractDAO();
        try {
            contractDAO.delete(contractId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private Date addDaysToCurrentDate(Date currentDate, int daysCount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_YEAR,daysCount);
        return new Date(cal.getTimeInMillis());
    }

    private BigDecimal countTotalPrice(int daysCount, BigDecimal dailyPrice){
        return dailyPrice.multiply(new BigDecimal(daysCount));
    }

    private Contract createContractFromData(Hotelroom hotelroom, Order order){
        Contract contract = new Contract();
        Date dateIn = order.getPreferedDateIn();
        contract.setDateIn(dateIn);
        int daysCount = order.getDaysCount();
        Date dateOut = addDaysToCurrentDate(dateIn, daysCount);
        contract.setDateOut(dateOut);
        BigDecimal dailyPrice = hotelroom.getDailyPrice();
        BigDecimal totalPrice = countTotalPrice(daysCount, dailyPrice);
        contract.setTotalPrice(totalPrice);
        String accountLogin = order.getAccountLogin();
        contract.setAccountLogin(accountLogin);
        int hotelroomNumber = hotelroom.getNumber();
        contract.setHotelroomNumber(hotelroomNumber);
        return contract;
    }
}
