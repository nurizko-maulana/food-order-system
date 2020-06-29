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

class Menu extends Query {
    public Menu() {
        super();
    }

    public void list() {
        ResultSet rs = super.read("menu");

        System.out.println("+--------------------------------+");
        System.out.println("|       MENU KEDAI MAK CIK       |");
        System.out.println("+--------------------------------+");
        try {
            while (rs.next()) {
                final int idmenu = rs.getInt("menu_id");
                final String nama_menu = rs.getString("menu");
                final String harga_menu = rs.getString("price");

                System.out.println(String.format("%d. %s -- (%s)", idmenu, nama_menu, harga_menu));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insert() {
        try {
            // ambil input dari user
            System.out.print("Nama Menu: ");
            final String menu = input.readLine().trim();
            System.out.print("Harga: ");
            final String harga = input.readLine().trim();

            // insert ke database
            super.insert("menu", menu, harga);
            System.out.print(ANSI_GREEN + "Item added...!" + ANSI_RESET);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void update() {
        try {
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            final String idMenu = input.readLine();
            System.out.print("Nama menu: ");
            final String menu = input.readLine().trim();
            System.out.print("harga: ");
            final String harga = input.readLine().trim();

            // update ke database
            super.update("menu", menu, harga, idMenu);
            System.out.print(ANSI_GREEN + "update successfully...!" + ANSI_RESET);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void delete() {
        try {
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            final int idMenu = Integer.parseInt(input.readLine());
            super.delete("menu", idMenu);
            System.out.print(ANSI_GREEN + "delete successfully...!" + ANSI_RESET);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

}

class Query {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/food_order";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public Query() {
        try {
            // register driver
            Class.forName(JDBC_DRIVER);
            // register database address, user, password
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
    }

    public void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (final SQLException e) {
            System.out.print(e);
        }
    }

    public boolean connection() {
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void insert(final String table, final String value1, final String value2) {
        try {
            // query simpan
            String sql = "INSERT INTO %s (menu, price) VALUE('%s', '%s')";
            sql = String.format(sql, table, value1, value2);

            // simpan buku
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void update(String table, String value1, String value2, String value3) {
        try {
            // query update
            String sql = "UPDATE %s SET menu='%s', price='%s' WHERE menu_id=%s";
            sql = String.format(sql, table, value1, value2, value3);

            // update data buku
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet read(String table) {

        final String sql = "SELECT * FROM " + table;

        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public String delete(String table, int id) {
        try {
            // buat query hapus
            final String sql = String.format("DELETE FROM menu WHERE menu_id=%d", id);

            // hapus data
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}

public class FoodOrderSystem {

    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    static Menu menu = new Menu();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Query query = new Query();
        while (query.connection()) {
            showMenu();
        }
        query.closeConnection();
    }

    // static void login() {
    // try{
    // System.out.println("username");
    // final String username = input.readLine();
    // System.out.println("password");
    // final String password = input.readLine();
    // final String sql = "SELECT * FROM costumer"
    // }
    // catch(final IOException e){
    // System.out.println(e);
    // }
    // }

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
            final int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    menu.insert();
                    break;
                case 2:
                    menu.list();
                    break;
                case 3:
                    menu.update();
                    break;
                case 4:
                    menu.delete();
                    break;
                // case 5:
                // insertOrder();
                // break;
                // case 6:
                // listOrder();
                // break;
                // case 7:
                // updateOrder();
                // break;
                // case 8:
                // deleteOrder();
                // break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (IOException | NumberFormatException e) {
            System.out.print(e);
        }
    }

    static void listMenu() {
        Query query = new Query();
        ResultSet rs = query.read("menu");

        System.out.println("+--------------------------------+");
        System.out.println("| DATA MENU DI KEDAI MAK CIK |");
        System.out.println("+--------------------------------+");
        try {
            while (rs.next()) {
                final int idmenu = rs.getInt("menu_id");
                final String nama_menu = rs.getString("menu");
                final String harga_menu = rs.getString("price");

                System.out.println(String.format("%d. %s -- (%s)", idmenu, nama_menu, harga_menu));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void insertMenu() {
        Query query = new Query();
        try {
            // ambil input dari user
            System.out.print("Nama Menu: ");
            final String menu = input.readLine().trim();
            System.out.print("Harga: ");
            final String harga = input.readLine().trim();

            // insert ke database
            query.insert("menu", menu, harga);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }

    }

    static void updateMenu() {
        Query query = new Query();
        try {
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            final String idMenu = input.readLine();
            System.out.print("Nama menu: ");
            final String menu = input.readLine().trim();
            System.out.print("harga: ");
            final String harga = input.readLine().trim();

            // update ke database
            query.update("menu", menu, harga, idMenu);
            System.out.print("update successfully...!");
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    static void deleteMenu() {
        Query query = new Query();
        try {
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            final int idMenu = Integer.parseInt(input.readLine());
            query.delete("menu", idMenu);
            System.out.println("Data telah terhapus...");
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    // static void listOrder() {
    // final String sql = "SELECT * FROM menu";

    // try {
    // rs = stmt.executeQuery(sql);

    // System.out.println("+--------------------------------+");
    // System.out.println("| DATA MENU DI KEDAI MAK CIK |");
    // System.out.println("+--------------------------------+");

    // while (rs.next()) {
    // final int idmenu = rs.getInt("menu_id");
    // final String nama_menu = rs.getString("menu");
    // final String harga_menu = rs.getString("price");

    // System.out.println(String.format("%d. %s -- (%s)", idmenu, nama_menu,
    // harga_menu));
    // }

    // } catch (final SQLException e) {
    // System.out.println(e);
    // }
    // }

    // static void insertOrder() {
    // try {
    // // ambil input dari user
    // System.out.print("ID Menu: ");
    // final String menu = input.readLine().trim();
    // System.out.print("Quantity: ");
    // final String harga = input.readLine().trim();

    // // query simpan
    // String sql = "INSERT INTO menu (menu, price) VALUE('%s', '%s')";
    // sql = String.format(sql, menu, harga);

    // // simpan buku
    // stmt.execute(sql);

    // } catch (IOException | NumberFormatException | SQLException e) {
    // System.out.println(e);
    // }
    // }

    // static void updateOrder() {
    // try {

    // // ambil input dari user
    // System.out.print("ID yang mau diedit: ");
    // final int idMenu = Integer.parseInt(input.readLine());
    // System.out.print("Nama menu: ");
    // final String menu = input.readLine().trim();
    // System.out.print("harga: ");
    // final String harga = input.readLine().trim();

    // // query update
    // String sql = "UPDATE menu SET name='%s', price='%s' WHERE menu_id=%d";
    // sql = String.format(sql, menu, harga, idMenu);

    // // update data buku
    // stmt.execute(sql);

    // } catch (IOException | NumberFormatException | SQLException e) {
    // System.out.println(e);
    // }
    // }

    // static void deleteOrder() {
    // try {

    // // ambil input dari user
    // System.out.print("ID yang mau dihapus: ");
    // final int idMenu = Integer.parseInt(input.readLine());

    // // buat query hapus
    // final String sql = String.format("DELETE FROM menu WHERE menu_id=%d",
    // idMenu);

    // // hapus data
    // stmt.execute(sql);

    // System.out.println("Data telah terhapus...");
    // } catch (IOException | NumberFormatException | SQLException e) {
    // System.out.println(e);
    // }
    // }
}