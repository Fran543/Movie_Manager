/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import moviemanager.model.User;
import moviemanager.dal.UserRepository;

/**
 *
 * @author fran
 */
public class UserSQLRepository implements UserRepository {

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ROLE = "Role";
    private static final String ACTIVE = "Active";


    
    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?,?)}";
    private static final String UPDATE_USER = "{ CALL updateUser (?,?,?,?,?)}";
    private static final String DELETE_USER = "{ CALL deleteUser (?)}";
    private static final String SELECT_USER = "{ CALL selectUser (?)}";
    private static final String SELECT_USERS = "{ CALL selectUsers }";

    
    @Override
    public int CreateUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().getName());
            stmt.setBoolean(4, user.isActive());
            stmt.registerOutParameter(5, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(5);
        }
    }

    @Override
    public void CreateUsers(List<User> user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            
            for (User client : user) {
            stmt.setString(1, client.getUsername());
            stmt.setString(2, client.getPassword());
            stmt.setString(3, client.getRole().getName());
            stmt.setBoolean(4, client.isActive());
            stmt.registerOutParameter(5, Types.INTEGER);
            stmt.executeUpdate();
            }   
        }
    }

    @Override
    public void UpdateUser(int id, User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().getName());
            stmt.setBoolean(4, user.isActive());
            stmt.setInt(5, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_USER)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> SelectUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new User(
                        rs.getInt(ID_USER),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        User.Role.from(rs.getString(ROLE)).get(),
                        rs.getBoolean(ACTIVE)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> SelectUsers() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USERS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                    rs.getInt(ID_USER),
                    rs.getString(USERNAME),
                    rs.getString(PASSWORD),
                    User.Role.from(rs.getString(ROLE)).get(),
                    rs.getBoolean(ACTIVE)
                ));
            }
        }
        return users;
    }
    
}
