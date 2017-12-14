package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

public class MH_DataModelLogin {

    private Integer status;
    private String mess;
    private String session_id;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The mess
     */
    public String getMess() {
        return mess;
    }

    /**
     * @param mess The mess
     */
    public void setMess(String mess) {
        this.mess = mess;
    }

    /**
     * @return The sessionId
     */
    public String getSessionId() {
        return session_id;
    }

    /**
     * @param sessionId The session_id
     */
    public void setSessionId(String sessionId) {
        this.session_id = session_id;
    }

}
