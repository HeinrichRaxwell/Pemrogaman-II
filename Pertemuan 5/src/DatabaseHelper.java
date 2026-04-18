import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:datamhs.db";

    // Koneksi ke SQLite
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Inisialisasi tabel saat aplikasi pertama kali dibuka
    public static void initDB() {
        String sql = "CREATE TABLE IF NOT EXISTS datamhs ("
                   + "nim      VARCHAR(15) PRIMARY KEY NOT NULL,"
                   + "nama     VARCHAR(30),"
                   + "semester INT,"
                   + "kelas    VARCHAR(1))";
        try (Connection conn = connect();
             Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("Database siap.");
        } catch (SQLException e) {
            System.out.println("Init DB gagal: " + e.getMessage());
        }
    }

    // INSERT data mahasiswa
    public static boolean simpanData(String nim, String nama, int semester, String kelas) {
        String sql = "INSERT INTO datamhs (nim, nama, semester, kelas) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pStat = conn.prepareStatement(sql)) {
            pStat.setString(1, nim);
            pStat.setString(2, nama);
            pStat.setInt(3, semester);
            pStat.setString(4, kelas);
            pStat.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Simpan gagal: " + e.getMessage());
            return false;
        }
    }

    // SELECT semua data mahasiswa
    public static ResultSet getAllData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM datamhs";
        Statement st = conn.createStatement();
        return st.executeQuery(sql);
    }

    // UPDATE data mahasiswa berdasarkan NIM
    public static boolean updateData(String nim, String nama, int semester, String kelas) {
        String sql = "UPDATE datamhs SET nama=?, semester=?, kelas=? WHERE nim=?";
        try (Connection conn = connect();
             PreparedStatement pStat = conn.prepareStatement(sql)) {
            pStat.setString(1, nama);
            pStat.setInt(2, semester);
            pStat.setString(3, kelas);
            pStat.setString(4, nim);
            int rows = pStat.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Update gagal: " + e.getMessage());
            return false;
        }
    }

    // DELETE data mahasiswa berdasarkan NIM
    public static boolean hapusData(String nim) {
        String sql = "DELETE FROM datamhs WHERE nim=?";
        try (Connection conn = connect();
             PreparedStatement pStat = conn.prepareStatement(sql)) {
            pStat.setString(1, nim);
            int rows = pStat.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Hapus gagal: " + e.getMessage());
            return false;
        }
    }
}
