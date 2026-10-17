import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleMenu extends JFrame {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JComboBox<String> genderComboBox;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTable peopleTable;
    private JLabel titleLabel;

    private Database database;
    private int selectedIndex = -1;

    public PeopleMenu() {
        // Inisialisasi Database
        database = new Database();

        // Load data tabel dari Database
        peopleTable.setModel(setTable());

        if (titleLabel != null) {
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        }

        // Setting pilihan Dropdown
        String[] kategoriData = {"--- Pilih Kategori ---", "Mahasiswa", "Dosen", "Tendik"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        String[] genderData = {"--- Pilih Gender ---", "Laki-laki", "Perempuan"};
        genderComboBox.setModel(new DefaultComboBoxModel<>(genderData));

        deleteButton.setVisible(false);

        // EVENT: Tombol Add / Update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // EVENT: Tombol Delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Apakah Anda yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // EVENT: Tombol Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // EVENT: Klik Baris Tabel (Tanpa ArrayList)
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = peopleTable.getSelectedRow();

                // Ambil nilai langsung dari model tabel
                String curId = peopleTable.getModel().getValueAt(selectedIndex, 0).toString();
                String curNama = peopleTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curTahun = peopleTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curKategori = peopleTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curGender = peopleTable.getModel().getValueAt(selectedIndex, 4).toString();

                idField.setText(curId);
                namaField.setText(curNama);
                tahunLahirField.setText(curTahun);
                kategoriComboBox.setSelectedItem(curKategori);
                genderComboBox.setSelectedItem(curGender);

                // ID tidak boleh diubah saat mode Update (Primary Key)
                idField.setEditable(false);

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    // SELECT / READ DATA DARI DATABASE
    public final DefaultTableModel setTable() {
        Object[] cols = {"ID", "Nama", "Tahun Lahir", "Kategori", "Gender"};
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM person");
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getString("id");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getInt("thn_lahir");
                row[3] = resultSet.getString("kategori");
                row[4] = resultSet.getString("gender");
                tmp.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tmp;
    }

    // INSERT / CREATE DATA KE DATABASE
    public void insertData() {
        String id = idField.getText();
        String nama = namaField.getText();
        String tahunStr = tahunLahirField.getText();
        String kategori = kategoriComboBox.getSelectedItem().toString();
        String gender = genderComboBox.getSelectedItem().toString();

        // VALIDASI 1: Cek apakah ada kolom input yang kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty() ||
                kategoriComboBox.getSelectedIndex() == 0 || genderComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Semua kolom input harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDASI 2: Cek apakah ID sudah digunakan (Primary Key Duplicate Check)
        try {
            ResultSet checkRs = database.selectQuery("SELECT * FROM person WHERE id = '" + id + "'");
            if (checkRs.next()) {
                JOptionPane.showMessageDialog(null, "ID '" + id + "' sudah terdaftar!", "Error Duplicate ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // VALIDASI 3: Tahun lahir harus berupa angka
        int tahunLahir;
        try {
            tahunLahir = Integer.parseInt(tahunStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Eksekusi Query Insert
        String sql = "INSERT INTO person VALUES ('" + id + "', '" + nama + "', " + tahunLahir + ", '" + kategori + "', '" + gender + "')";
        database.insertUpdateDeleteQuery(sql);

        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }

    // UPDATE DATA DI DATABASE
    public void updateData() {
        String id = idField.getText();
        String nama = namaField.getText();
        String tahunStr = tahunLahirField.getText();
        String kategori = kategoriComboBox.getSelectedItem().toString();
        String gender = genderComboBox.getSelectedItem().toString();

        // VALIDASI: Cek kolom kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty() ||
                kategoriComboBox.getSelectedIndex() == 0 || genderComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Semua kolom input harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tahunLahir;
        try {
            tahunLahir = Integer.parseInt(tahunStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Eksekusi Query Update
        String sql = "UPDATE person SET nama = '" + nama + "', thn_lahir = " + tahunLahir +
                ", kategori = '" + kategori + "', gender = '" + gender + "' WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sql);

        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    // DELETE DATA DARI DATABASE
    public void deleteData() {
        String id = idField.getText();
        String sql = "DELETE FROM person WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sql);

        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    // CLEAR FORM
    public void clearForm() {
        idField.setText("");
        idField.setEditable(true); // Aktifkan kembali input ID
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        genderComboBox.setSelectedIndex(0);
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }

    public static void main(String[] args) {
        PeopleMenu menu = new PeopleMenu();
        menu.setSize(700, 600);
        menu.setLocationRelativeTo(null);
        menu.setContentPane(menu.mainPanel);
        menu.getContentPane().setBackground(Color.WHITE);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}