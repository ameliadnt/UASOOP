public class Terminal {

    private String merek_bus = null;
    private int id;
    private String tahun_pembuatan = null;
    private String warna = null;
    private String kapasitas = null ;


    public Terminal(int id, String merek_bus, String tahun_pembuatan, String warna, String kapasitas) {
        this.merek_bus = merek_bus;
        this.id = id;
        this.tahun_pembuatan = tahun_pembuatan;
        this.warna = warna;
        this.kapasitas = kapasitas;
    }

    public String getMerek_bus() {
        return merek_bus;
    }

    public int getId() {
        return id;
    }

    public String getTahun_pembuatan() {
        return tahun_pembuatan;
    }

    public String getWarna() {
        return warna;
    }

    public String getKapasitas() {
        return kapasitas;
    }

}