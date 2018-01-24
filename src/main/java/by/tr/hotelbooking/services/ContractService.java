package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.Contract;
import by.tr.hotelbooking.entities.ContractDTO;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.util.List;

public interface ContractService {
    List<Contract> getAcceptedUserContracts(int page, String userLogin) throws ServiceException;
    List<ContractDTO> getNewUserOffers(int page, String userLogin) throws ServiceException;
    List<Contract> getContractsForPage(int page) throws ServiceException;
    int getPagesCount(int recordsCount) throws ServiceException;
    int getRecordsCount() throws ServiceException;
    int getPageNumber(String stringPageValue);

    void addContract(int orderId, int hotelroomId) throws ServiceException;
    void acceptContract(int contractId) throws ServiceException;
    void declineContract(int contractId) throws ServiceException;
}
