# TP5DPBO2526C1<br>
TUGAS PRAKTIKUM 5 DPBO SWING GUI + CONNECT MYSQL<br>
Irsyad Afif Musyaffa 2508023<br>
Ilmu Komputer C1 Universitas Pendidikan Indonesia
<br>

# ☝🏻 JANJI<br>
Saya Irsyad Afif Musyaffa dengan NIM 2508023 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

<br>

# 🛠️ DESIGN PROGRAM<br>
Program ini dibuat menggunakan Java Swing untuk mengelola data person/people yang terhubung langsung dengan database MySQL. Fitur utama meliputi:<br>
- Create: Menambahkan data person baru ke database.<br>
- Read: Menampilkan data person dari database.<br>
- Update: Mengedit data person yang sudah ada di database.<br>
- Delete: Menghapus data person yang ada di database.<br>

<br>

## CLASS PADA PROGRAM:<br>
### 1. Class Person<br>
Class ini berisi atribut dan method getter/setter. Berikut atribut-atributnya:<br>
-  String id
-  String nama
-  int tahunLahir
-  String kategori
-  String gender (Atribut Tambahan Bonus +20)
Method:
- Constructor Person(String id, String nama, int tahunLahir, String kategori, String gender)
- Getter dan Setter untuk setiap atribut

### 2. Class PeopleMenu extends JFrame<br>
Class ini berisi Graphical User Interface (GUI) dari program, berfungsi untuk mengatur GUI, serta method untuk insert, update, dan delete.<br>

Class ini mengatur tampilan dan logika aplikasi berbasis GUI.
Berfungsi untuk menampilkan form, menampung event handler, serta mengatur integrasi ke database.

<b>Komponen Utama GUI:</b>
- JTextField → untuk input ID, Nama, dan Tahun Lahir
- JComboBox → untuk memilih Kategori dan Gender
- JTable → untuk menampilkan daftar person
- Tombol Add/Update, Delete, dan Cancel

<b>Fungsi utama dalam class ini:</b>
- insertData() – Menambahkan data person baru ke dalam database
- updateData() – Memperbarui data person terpilih di database
- deleteData() – Menghapus data person di database setelah konfirmasi dialog
- clearForm() – Mengosongkan semua input form
- setTable() – Mengatur isi tabel berdasarkan data dari database

### 3. Class Database<br>
Class ini berfungsi sebagai lapisan koneksi dan eksekusi perintah SQL.<br>
Class ini menangani komunikasi antara program dan database MySQL secara langsung.<br>
<br>
<b>Fungsi utama:</b><br>
selectQuery(String sql) → Menjalankan perintah SELECT dan mengembalikan hasilnya dalam bentuk ResultSet.<br>
insertUpdateDeleteQuery(String sql) → Menjalankan perintah INSERT, UPDATE, atau DELETE ke database.<br>

# ✅ ALUR PROGRAM
### 1. Tampilan Utama
Tampilan utama berisi formulir data person yang berisi:
- ID
- Nama
- Tahun Lahir
- Kategori
- Gender
- Tombol aksi Add/Update, Delete, dan Cancel.
Di bawah form terdapat tabel yang menampilkan daftar semua data person.

### 2. Penambahan Data Baru<br>
User dapat menambahkan data baru dengan mengisi form data person, kemudian klik tombol aksi “Add” untuk menyimpan data ke dalam database.<br>
- ID person harus unik. Jika ID yang dimasukkan sudah terdaftar di database, data tidak akan ditambahkan dan muncul dialog error.<br>
- Field ID, Nama, Tahun Lahir, Kategori, dan Gender wajib diisi/dipilih. Jika ada yang kosong atau belum dipilih, akan muncul pesan error.<br>

### 3. Update Data
Saat salah satu baris tabel diklik, data person akan muncul kembali di form input.<br>
Tombol “Add” akan otomatis berubah menjadi “Update”, dan tombol “Delete” akan muncul. Setelah itu user dapat mengubah data sesuai kebutuhan.<br>
- Field input wajib diisi secara lengkap. Jika ada yang kosong, akan muncul pesan error.<br>

### 4. Delete Data
Jika user ingin menghapus salah satu data, klik tombol "Delete" pada data yang dipilih.

Akan muncul konfirmasi dialog:
“Apakah Anda yakin ingin menghapus data ini?”

Jika memilih Yes, data akan dihapus dari database dan tabel.

### 5. Cancel/Membersihkan form
Klik tombol "Cancel" untuk mengosongkan semua input form.

# 📸 DOKUMENTASI
## READ
![Read Data](assets/Read.png)

## ADD
### Insert
![Insert Data](assets/insert.gif)

### Insert ID yang sudah ada
![Insert Duplicate ID](assets/insert-non-unique.png)

### Insert tapi salah satu form ada yang kosong
![Insert Empty Form](assets/insert-non-filled.png)


## UPDATE
### Update
![Update Data](assets/update.gif)

### Update tapi salah satu form ada yang kosong
![Update Empty Form](assets/update-non-filled.png)


## DELETE
![Delete Data](assets/delete.gif)
