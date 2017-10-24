package controller.protocols;

public interface PermissionDao {
    boolean checkKeyByUsername(String key, String username);
}
