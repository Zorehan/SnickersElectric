package GUI.Model;

import BE.Log;
import BLL.LogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogModel {

    private static LogModel instance;
    private LogManager logManager;
    private ObservableList<Log> allLogs;

    private LogModel() {
        logManager = new LogManager();

        allLogs = FXCollections.observableArrayList();
        try {
            allLogs.addAll(logManager.getAllLogs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Log> getAllLogs()
    {
        return allLogs;
    }

    public static LogModel getInstance()  {
        if(instance == null)
        {
            instance = new LogModel();
        }
        return instance;
    }

    public Log createLog(Log newLog) throws Exception {
        Log log = logManager.createLog(newLog);
        allLogs.add(log);
        return log;
    }

    public void deleteLog(Log log) throws Exception {
        logManager.deleteLog(log);
        allLogs.remove(log);
    }
}
