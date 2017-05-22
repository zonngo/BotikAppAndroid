
package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;


public class MH_DataModel_ListarProdByLatLog {

    private static boolean favorite=false;
    private Integer id;
    private Double lat;
    private Double lng;
    private Double distance=0.00;

    private MH_DataModel_DetalleFarmacia detalleFarmacias;
    private MH_DataModel_DetalleFarmaco detalleFarmaco;



    //


    /**
     *
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     *     The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     *     The lng
     */
    public Double getLng() {
        return lng;
    }

    /**
     *
     * @param lng
     *     The lng
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     *
     * @return
     *     The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     *     The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public MH_DataModel_ListarProdByLatLog(Integer id, double distance)
    {
        this.id=id;
        this.distance=distance;
    }

    public MH_DataModel_DetalleFarmacia getDetalleFarmacias() {
        return detalleFarmacias;
    }

    public void setDetalleFarmacias(MH_DataModel_DetalleFarmacia detalleFarmacias) {
        this.detalleFarmacias = detalleFarmacias;
    }

    public MH_DataModel_DetalleFarmaco getDetalleFarmaco() {
        return detalleFarmaco;
    }

    public void setDetalleFarmaco(MH_DataModel_DetalleFarmaco detalleFarmaco) {
        this.detalleFarmaco = detalleFarmaco;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public boolean changeFavorite() {
        favorite=!favorite;
        return favorite;
    }
}
