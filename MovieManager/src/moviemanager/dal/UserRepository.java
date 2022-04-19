/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal;

import java.util.List;
import java.util.Optional;
import moviemanager.model.User;

/**
 *
 * @author fran
 */
public interface UserRepository {
    
    int CreateUser(User user) throws Exception;
    void CreateUsers(List<User> users) throws Exception;
    void UpdateUser(int id, User user) throws Exception;
    void DeleteUser(int id) throws Exception;
    Optional<User> SelectUser(int id) throws Exception;
    List<User> SelectUsers() throws Exception;

}
