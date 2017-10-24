package view.protocols;

public interface AddEmployeeView extends BannerView {
    void clearMessages();
    void setSurnameMessage(String message);
    void setResultMessage(String message);
    void setUsernameMessage(String message);
}
