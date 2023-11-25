package cn.easii.tutelary.service;

public interface AuthService {

    String login(String username, String password);

    void logout();

}
