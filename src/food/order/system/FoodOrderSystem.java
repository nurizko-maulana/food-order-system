package food.order.system;
/**
 *
 * @author Nurizko Maulana
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class FoodOrderSystem {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/food_order";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertBuku();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateBuku();
                    break;
                case 4:
                    deleteBuku();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM menu";

        try {
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("|    DATA MENU DI KEDAI MAK CIK   |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                int idmenu = rs.getInt("menu_id");
                String nama_menu = rs.getString("menu");
                String harga_menu = rs.getString("price");

                System.out.println(String.format("%d. %s -- (%s)", idmenu, nama_menu, harga_menu));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertBuku() {
        try {
            // ambil input dari user
            System.out.print("Nama Menu: ");
            String menu = input.readLine().trim();
            System.out.print("Harga: ");
            String harga = input.readLine().trim();

            // query simpan
            String sql = "INSERT INTO menu (menu, price) VALUE('%s', '%s')";
            sql = String.format(sql, menu, harga);

            // simpan buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateBuku() {
        try {

            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int idMenu = Integer.parseInt(input.readLine());
            System.out.print("Nama menu: ");
            String menu = input.readLine().trim();
            System.out.print("harga: ");
            String harga = input.readLine().trim();

            // query update
            String sql = "UPDATE menu SET name='%s', price='%s' WHERE menu_id=%d";
            sql = String.format(sql, menu, harga, idMenu);

            // update data buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteBuku() {
        try {

            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int idMenu = Integer.parseInt(input.readLine());

            // buat query hapus
            String sql = String.format("DELETE FROM menu WHERE menu_id=%d", idMenu);

            // hapus data
            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}