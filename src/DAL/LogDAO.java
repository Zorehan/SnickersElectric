package DAL;

import BE.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDAO implements GenericDAO<Log> {

    @Override
    public List<Log> getAll() {

            List<Log> allLogs = new ArrayList<>();
            String sql = "SELECT * FROM dbo.Logs;";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    int referenceProfileId = rs.getInt("profile_id");
                    String logText = rs.getString("logText");

                    Log log = new Log(id, referenceProfileId, logText);
                    allLogs.add(log);
                }
            } catch (SQLException e){
                throw new RuntimeException();
            }
            return allLogs;
        }



    @Override
    public Log create(Log log) {
        String sql = "INSERT INTO dbo.Logs (profile_id, logText) VALUES (?,?);";
        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, log.getReferenceProfileId());
            stmt.setString(2, log.getLogText());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys())
            {
                if(rs.next())
                {
                    log.setId(rs.getInt(1));
                }
            }

            return log;
        } catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Log entity) {
        String sql = "DELETE FROM dbo.Logs WHERE id = ?";

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, entity.getId());

            stmt.executeUpdate();
        } catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public Log update(Log entity) {
        return null;
    }
}
