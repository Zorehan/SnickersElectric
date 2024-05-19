package GUI.Model;

import BE.Log;
import BLL.LogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogModel {

    private static LogModel instance;
    private LogManager logManager;
    private ObservableList<Log> allLogs;

    private LogModel() throws Exception {
        logManager = new LogManager();

        allLogs = FXCollections.observableArrayList();
        allLogs.addAll(logManager.getAllLogs());
    }

    public static LogModel getInstance() throws Exception {
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
