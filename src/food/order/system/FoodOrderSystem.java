package food.order.system;
/**
 *
 * @author Nurizko Maulana
 */
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert menu");
        System.out.println("2. Show menu");
        System.out.println("3. Edit menu");
        System.out.println("4. Delete menu");
        System.out.println("5. Insert Order");
        System.out.println("6. Show Order");
        System.out.println("7. Edit Order");
        System.out.println("8. Delete Order");
        System.out.println("0. Exit");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertMenu();
                    break;
                case 2:
                    listMenu();
                    break;
                case 3:
                    updateMenu();
                    break;
                case 4:
                    deleteMenu();
                    break;
                case 5:
                    insertOrder();
                    break;
                case 6:
                    listOrder();
                    break;
                case 7:
                    updateOrder();
                    break;
                case 8:
                    deleteOrder();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (IOException | NumberFormatException e) {
            System.out.print(e);
        }
    }

    static void listMenu() {
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

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void insertMenu() {
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

        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }

    }

    static void updateMenu() {
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

        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }

    static void deleteMenu() {
        try {

            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int idMenu = Integer.parseInt(input.readLine());

            // buat query hapus
            String sql = String.format("DELETE FROM menu WHERE menu_id=%d", idMenu);

            // hapus data
            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    static void listOrder() {
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

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    static void insertOrder() {
        try {
            // ambil input dari user
            System.out.print("ID Menu: ");
            String menu = input.readLine().trim();
            System.out.print("Quantity: ");
            String harga = input.readLine().trim();

            // query simpan
            String sql = "INSERT INTO menu (menu, price) VALUE('%s', '%s')";
            sql = String.format(sql, menu, harga);

            // simpan buku
            stmt.execute(sql);

        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    static void updateOrder() {
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

        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    static void deleteOrder() {
        try {

            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int idMenu = Integer.parseInt(input.readLine());

            // buat query hapus
            String sql = String.format("DELETE FROM menu WHERE menu_id=%d", idMenu);

            // hapus data
            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
}