import javafx.beans.property.*;

public class Person{

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty cx;
    private SimpleIntegerProperty cy;
    private SimpleStringProperty cdate;
    private SimpleStringProperty ctime;
    private SimpleStringProperty hei;
    private SimpleStringProperty lx;
    private SimpleLongProperty ly;
    private SimpleLongProperty lz;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty col;
    private SimpleStringProperty pass;
    private SimpleStringProperty user;
    private Localit loc = new Localit();

    Person(Integer iid, String iname, Double icx, Integer icy, String icdate, String ictime, Double ihei, Double ilx, Long ily, Long ilz, String idate, String itime, String icol, String ipass, String iuser){
        this.id = new SimpleIntegerProperty(iid);
        this.name = new SimpleStringProperty(iname);
        this.cx = new SimpleStringProperty(loc.n(icx));
        this.cy = new SimpleIntegerProperty(icy);
        this.cdate = new SimpleStringProperty(loc.d(icdate,true));
        this.ctime = new SimpleStringProperty(ictime);
        this.hei = new SimpleStringProperty(loc.n(ihei));
        this.lx = new SimpleStringProperty(loc.n(ilx));
        this.ly = new SimpleLongProperty(ily);
        this.lz = new SimpleLongProperty(ilz);
        this.date = new SimpleStringProperty(loc.d(idate,false));
        this.time = new SimpleStringProperty(itime);
        this.col = new SimpleStringProperty(icol);
        this.pass = new SimpleStringProperty(ipass);
        this.user = new SimpleStringProperty(iuser);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCx() {
        return cx.get();
    }

    public SimpleStringProperty cxProperty() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx.set(cx);
    }

    public int getCy() {
        return cy.get();
    }

    public SimpleIntegerProperty cyProperty() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy.set(cy);
    }

    public String getCdate() {
        return cdate.get();
    }

    public SimpleStringProperty cdateProperty() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate.set(cdate);
    }

    public String getCtime() {
        return ctime.get();
    }

    public SimpleStringProperty ctimeProperty() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime.set(ctime);
    }

    public String getHei() {
        return hei.get();
    }

    public SimpleStringProperty heiProperty() {
        return hei;
    }

    public void setHei(String hei) {
        this.hei.set(hei);
    }

    public String getLx() {
        return lx.get();
    }

    public SimpleStringProperty lxProperty() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx.set(lx);
    }

    public long getLy() {
        return ly.get();
    }

    public SimpleLongProperty lyProperty() {
        return ly;
    }

    public void setLy(long ly) {
        this.ly.set(ly);
    }

    public long getLz() {
        return lz.get();
    }

    public SimpleLongProperty lzProperty() {
        return lz;
    }

    public void setLz(long lz) {
        this.lz.set(lz);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getCol() {
        return col.get();
    }

    public SimpleStringProperty colProperty() {
        return col;
    }

    public void setCol(String col) {
        this.col.set(col);
    }

    public String getPass() {
        return pass.get();
    }

    public SimpleStringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }
}