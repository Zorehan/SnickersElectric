package BLL;

import BE.Log;
import DAL.LogDAO;

import java.util.List;

public class LogManager {

    private final LogDAO logDAO;

    public LogManager()
    {
        logDAO = new LogDAO();
    }

    public List<Log> getAllLogs() throws Exception {
        try{
            return logDAO.getAll();
        } catch (Exception e)
        {
            throw new Exception("Error fetching logs", e);
        }
    }

    public Log createLog(Log log) throws Exception
    {
        try{
            return logDAO.create(log);
        } catch (Exception e)
        {
            throw new Exception("Error creating Log", e);
        }
    }

    public void deleteLog(Log log) throws Exception
    {
        try{
            logDAO.delete(log);
        } catch (Exception e)
        {
            throw new Exception("Error deleting log", e);
        }
    }

}
