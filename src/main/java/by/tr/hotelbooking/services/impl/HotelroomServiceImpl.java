package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.Dao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.HotelroomDAO;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class HotelroomServiceImpl implements HotelroomService {
    private final DaoFactory daoFactory = new DaoFactory();

    private static final HotelroomServiceImpl instance = new HotelroomServiceImpl();

    private HotelroomServiceImpl() {

    }

    public static HotelroomServiceImpl getInstance() {
        return instance;
    }


    @Override
    public void addHotelroom(String numberString, String placesCountString, String floorString, String dailyPriceString,
                             String roomTypeIdString, Part part, String uploadDir) throws ServiceException {

        Hotelroom hotelroom = new Hotelroom();
        Dao<Hotelroom> hotelroomDao = daoFactory.getHotelroomDAO();
        try {

            int number = Integer.parseInt(numberString);
            int floor = Integer.parseInt(floorString);
            int placesCount = Integer.parseInt(placesCountString);
            BigDecimal dailyPrice = new BigDecimal(dailyPriceString);
            int roomTypeId = Integer.parseInt(roomTypeIdString);
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
    public void editHotelroom(String idString, String numberString, String placesCountString, String floorString, String dailyPriceString,
                              String roomTypeIdString, Part part, String uploadDir) throws ServiceException {
        Hotelroom hotelroom = new Hotelroom();
        Dao<Hotelroom> hotelroomDao = daoFactory.getHotelroomDAO();
        try {

            int id = Integer.parseInt(idString);
            int number = Integer.parseInt(numberString);
            int floor = Integer.parseInt(floorString);
            int placesCount = Integer.parseInt(placesCountString);
            BigDecimal dailyPrice = new BigDecimal(dailyPriceString);
            int roomTypeId = Integer.parseInt(roomTypeIdString);
            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

            if(!imageName.isEmpty()){
                hotelroom.setImageName(imageName);
            }
            hotelroom.setId(id);
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
        return (int) Math.ceil(recordsCount * 1.0 / 4);
    }

    @Override
    public List<Hotelroom> getHotelroomsFromPage(int page) throws ServiceException{
        List<Hotelroom> hotelrooms = null;
        try {
            HotelroomDAO hotelroomDAO = daoFactory.getHotelroomDAO();
            hotelrooms = hotelroomDAO.getHotelroomsForPage((page-1)*4,4);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return hotelrooms;
    }

    @Override
    public int getRecordsCount() throws ServiceException {
        int num = 0;
        try {
            num = daoFactory.getHotelroomDAO().getNumberOfHotelrooms();
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
    public void deleteHotelroom(String idString) throws ServiceException {
        Dao<Hotelroom> hotelroomDao = null;
        try {
            int id = Integer.parseInt(idString);
            hotelroomDao = daoFactory.getHotelroomDAO();
            hotelroomDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void uploadImage(Part filePart, String fileName, String uploadDir) throws ServiceException {
        try {

            File dir = new File(uploadDir+"images"+File.separator+"hotelrooms");
            if (!dir.exists()) {
                Path path = Paths.get(uploadDir+"images"+File.separator+"hotelrooms");
                Files.createDirectories(path);
            }
            File file = new File(dir, fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new ServiceException("Imga upload error ", e);
        }
    }

}
