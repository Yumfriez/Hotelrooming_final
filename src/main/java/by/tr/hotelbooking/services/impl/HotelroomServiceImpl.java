package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.Dao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.HotelroomDAO;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.HotelroomDTO;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.resource.DataParameter;
import by.tr.hotelbooking.resource.DataResourceManager;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.utils.LogicException;
import by.tr.hotelbooking.services.utils.LogicValidator;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class HotelroomServiceImpl implements HotelroomService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public HotelroomServiceImpl() {

    }

    @Override
    public void addHotelroom(int number, int placesCount, int floor, BigDecimal dailyPrice,
                             int roomTypeId, Part part, String uploadDir) throws ServiceException, LogicException {

        Hotelroom hotelroom = new Hotelroom();
        Dao<Hotelroom> hotelroomDao = daoFactory.getHotelroomDAO();
        try {

            LogicValidator.checkHotelroomNumber(number);
            LogicValidator.checkHotelroomFloor(floor);
            LogicValidator.checkPlacesCount(placesCount);
            LogicValidator.checkPrice(dailyPrice);

            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

            if(!imageName.isEmpty()){
                hotelroom.setImageName(imageName);
            }
            hotelroom.setNumber(number);
            hotelroom.setFloor(floor);
            hotelroom.setPlacesCount(placesCount);
            hotelroom.setDailyPrice(dailyPrice);
            RoomType roomType = new RoomType();
            roomType.setId(roomTypeId);
            hotelroom.setRoomType(roomType);

            hotelroomDao.add(hotelroom);

            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, uploadDir);
            }



        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void editHotelroom(int hotelroomId, int number, int placesCount, int floor,  BigDecimal dailyPrice,
                              int roomTypeId, Part part, String uploadDir) throws ServiceException, LogicException {
        Hotelroom hotelroom = new Hotelroom();
        Dao<Hotelroom> hotelroomDao = daoFactory.getHotelroomDAO();
        try {

            LogicValidator.checkHotelroomNumber(number);
            LogicValidator.checkHotelroomFloor(floor);
            LogicValidator.checkPlacesCount(placesCount);
            LogicValidator.checkPrice(dailyPrice);

            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

            if(!imageName.isEmpty()){
                hotelroom.setImageName(imageName);
            }
            hotelroom.setId(hotelroomId);
            hotelroom.setNumber(number);
            hotelroom.setFloor(floor);
            hotelroom.setPlacesCount(placesCount);
            hotelroom.setDailyPrice(dailyPrice);
            RoomType roomType = new RoomType();
            roomType.setId(roomTypeId);
            hotelroom.setRoomType(roomType);

            hotelroomDao.update(hotelroom);

            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, uploadDir);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Hotelroom getHotelroomForEdit(Integer hotelroomId) throws ServiceException {
        HotelroomDAO hotelroomDAO = new HotelroomDAO();
        Hotelroom hotelroom;
        try {
            hotelroom = hotelroomDAO.getByPK(hotelroomId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return hotelroom;
    }



    @Override
    public List<Hotelroom> getAllHotelrooms() throws ServiceException {
        HotelroomDAO hotelroomDAO = new HotelroomDAO();
        List<Hotelroom> hotelrooms;
        try {
            hotelrooms = hotelroomDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return hotelrooms;
    }

    @Override
    public int getPagesCount(int recordsCount) throws ServiceException {
        int recordsOnPageCount = getRecordsOnPageCount();
        return (int) Math.ceil(recordsCount * 1.0 / recordsOnPageCount);
    }

    @Override
    public List<Hotelroom> getHotelroomsFromPage(int page) throws ServiceException{
        List<Hotelroom> hotelrooms = null;
        try {
            HotelroomDAO hotelroomDAO = daoFactory.getHotelroomDAO();
            int recordsOnPageCount = getRecordsOnPageCount();
            hotelrooms = hotelroomDAO.getRecordsWithOffset((page-1)*recordsOnPageCount,recordsOnPageCount);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return hotelrooms;
    }

    @Override
    public List<Hotelroom> findHotelroomsForPage(int placesCount, BigDecimal minPrice, BigDecimal maxPrice, int roomTypeId,
                                                 Date dateIn, int daysCount, int page) throws ServiceException, LogicException {

        LogicValidator.checkPlacesCount(placesCount);
        LogicValidator.checkPrice(minPrice);
        LogicValidator.checkPrice(maxPrice);
        LogicValidator.checkMinAndMaxPrices(minPrice, maxPrice);
        LogicValidator.checkDaysCount(daysCount);
        LogicValidator.checkDateIn(dateIn);


        HotelroomDAO hotelroomDAO = daoFactory.getHotelroomDAO();
        HotelroomDTO hotelroomDTO = new HotelroomDTO();
        hotelroomDTO.setMinPrice(minPrice);
        hotelroomDTO.setMaxPrice(maxPrice);
        hotelroomDTO.setPlacesCount(placesCount);
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        hotelroomDTO.setRoomType(roomType);
        hotelroomDTO.setDateIn(dateIn);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateIn);
        cal.add(Calendar.DAY_OF_YEAR,daysCount);
        Date dateOut = new Date(cal.getTimeInMillis());
        hotelroomDTO.setDateOut(dateOut);
        List<Hotelroom> hotelrooms;
        try {
            int recordsOnPageCount = getRecordsOnPageCount();
            hotelrooms = hotelroomDAO.getHotelroomsByParameters(hotelroomDTO, (page-1)*recordsOnPageCount,recordsOnPageCount);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return hotelrooms;
    }

    @Override
    public int getRecordsByCriteriaCount(int placesCount, BigDecimal minPrice, BigDecimal maxPrice, int roomTypeId,
                                         Date dateIn, int daysCount) throws ServiceException, LogicException {
        LogicValidator.checkPlacesCount(placesCount);
        LogicValidator.checkPrice(minPrice);
        LogicValidator.checkPrice(maxPrice);
        LogicValidator.checkMinAndMaxPrices(minPrice, maxPrice);
        LogicValidator.checkDaysCount(daysCount);
        LogicValidator.checkDateIn(dateIn);

        int num = 0;
        HotelroomDTO hotelroomDTO = new HotelroomDTO();
        hotelroomDTO.setMinPrice(minPrice);
        hotelroomDTO.setMaxPrice(maxPrice);
        hotelroomDTO.setPlacesCount(placesCount);
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        hotelroomDTO.setRoomType(roomType);
        hotelroomDTO.setDateIn(dateIn);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateIn);
        cal.add(Calendar.DAY_OF_YEAR,daysCount);
        Date dateOut = new Date(cal.getTimeInMillis());
        hotelroomDTO.setDateOut(dateOut);

        try {
            num = daoFactory.getHotelroomDAO().getNumberOfHotelroomsByParams(hotelroomDTO);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return num;
    }

    @Override
    public int getRecordsCount() throws ServiceException {
        int num = 0;
        try {
            num = daoFactory.getHotelroomDAO().getRecordsCount();
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
    public void deleteHotelroom(int id) throws ServiceException {
        Dao<Hotelroom> hotelroomDao = null;
        try {
            hotelroomDao = daoFactory.getHotelroomDAO();
            hotelroomDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private int getRecordsOnPageCount(){
        DataResourceManager manager = DataResourceManager.getInstance();
        String recordsOnPageString = manager.getValue(DataParameter.HOTELROOMS_ON_PAGE_COUNT);
        int recordsOnPage;
        try {
            recordsOnPage = Integer.parseInt(recordsOnPageString);
        } catch (NumberFormatException e) {
            recordsOnPage = 4;
        }
        return recordsOnPage;
    }

    private void uploadImage(Part filePart, String fileName, String uploadDir) throws ServiceException {
        try {

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                Path path = Paths.get(uploadDir);
                Files.createDirectories(path);
            }
            File file = new File(dir, fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("Image upload error ", e);
        }
    }

}
