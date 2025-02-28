# Module 3

# 1. Penerapan Prinsip SOLID dalam Kode Saya

## 1. SRP (Single Responsibility Principle)
- **Yang sudah dilakukan:**
   - Pada kode awal, terjadi pelanggaran SRP karena `CarController` mewarisi `ProductController`, sehingga tanggung jawabnya bercampur.
   - Saya telah memisahkan controller menjadi tiga kelas berbeda: `HomeController` untuk halaman utama, `ProductController` untuk produk, dan `CarController` untuk mobil. Masing-masing controller hanya menangani satu domain atau fungsi spesifik.
- **Kesimpulan:**  
  Karena setiap kelas sekarang hanya memiliki satu alasan untuk berubah, SRP sudah diterapkan.

## 2. OCP (Open/Closed Principle)
- **Yang sudah dilakukan:**
   - Saya merancang kode agar modular dengan memisahkan service dan repository berdasarkan entitas.
   - Jika di masa depan ingin menambahkan entitas baru (misalnya, `Bike`), saya hanya perlu menambahkan kelas baru tanpa harus mengubah kode yang sudah ada.
- **Kesimpulan:**  
  Sistem saya terbuka untuk ekstensi (dengan menambah fitur atau entitas baru) namun tertutup untuk modifikasi pada kode yang sudah ada, sehingga OCP sudah diterapkan.

## 3. LSP (Liskov Substitution Principle)
- **Yang sudah dilakukan:**
   - Saya menggunakan interface seperti `CarService` sehingga implementasi konkret seperti `CarServiceImpl` dapat dengan mudah digantikan oleh implementasi lain tanpa mengubah perilaku program.
   - Awalnya, terdapat masalah karena pewarisan yang tidak tepat. Dengan memisahkan controller dan bergantung pada abstraksi (interface), setiap pengganti akan berperilaku sesuai kontrak yang ditetapkan.
- **Kesimpulan:**  
  Karena setiap kelas yang mengimplementasikan interface dapat menggantikan satu sama lain tanpa mengganggu fungsionalitas aplikasi, LSP sudah terpenuhi.

## 4. ISP (Interface Segregation Principle)
- **Yang sudah dilakukan:**
   - Pada kode awal, interface seperti `CarService` dan `ProductService` sudah cukup kecil dan spesifik untuk entitasnya masing-masing.
   - Jika diperlukan, saya bisa memecah interface tersebut lebih jauh (misalnya, memisahkan operasi baca dan tulis) agar klien hanya bergantung pada metode yang relevan.
- **Kesimpulan:**  
  Karena klien (seperti controller) hanya bergantung pada metode yang memang mereka gunakan, saya sudah menerapkan ISP.

## 5. DIP (Dependency Inversion Principle)
- **Yang sudah dilakukan:**
   - Saya telah mengubah service yang awalnya bergantung langsung pada implementasi konkret repository menjadi bergantung pada repository interface (misalnya, `CarRepositoryInterface` dan `ProductRepositoryInterface`).
   - Dengan cara ini, service saya hanya bergantung pada abstraksi, sehingga jika nantinya implementasi repository diubah, service tidak perlu diubah.
- **Kesimpulan:**  
  Karena service saya bergantung pada abstraksi (interface) dan bukan pada detail implementasi, DIP sudah diterapkan.

---
# 2.  Keuntungan Menerapkan Prinsip SOLID pada Proyek Saya

## 1. Single Responsibility Principle (SRP)
- **Keuntungan:**
   - Masing-masing kelas hanya memiliki satu tanggung jawab sehingga kode menjadi lebih mudah dipahami, diuji, dan dipelihara.
- **Contoh:**
   - Saya memisahkan controller berdasarkan domain. Misalnya, `CarController` hanya menangani operasi terkait mobil, sedangkan `ProductController` hanya menangani operasi produk. Jika saya perlu mengubah logika pada produk, saya tidak akan mengganggu logika pada mobil.

## 2. Open/Closed Principle (OCP)
- **Keuntungan:**
   - Kode terbuka untuk penambahan fitur baru tanpa perlu mengubah kode yang sudah ada.
- **Contoh:**
   - Jika saya ingin menambahkan entitas baru seperti `Bike`, saya cukup menambahkan kelas model, service, dan repository baru yang mengimplementasikan interface yang telah ada, tanpa harus memodifikasi kode untuk `CarService` atau `ProductService`.

## 3. Liskov Substitution Principle (LSP)
- **Keuntungan:**
   - Implementasi dari suatu interface atau kelas dasar dapat digantikan oleh subclass atau implementasi lain tanpa mengganggu kebenaran dan konsistensi aplikasi.
- **Contoh:**
   - Saya menggunakan interface seperti `CarService` sehingga jika suatu saat saya mengganti implementasi `CarServiceImpl` dengan implementasi lain (misalnya `CarServiceEnhanced`), controller yang bergantung pada `CarService` tidak akan terpengaruh dan tetap berfungsi sesuai kontrak yang telah ditetapkan.

## 4. Interface Segregation Principle (ISP)
- **Keuntungan:**
   - Klien hanya bergantung pada interface yang relevan dengan kebutuhannya, sehingga tidak perlu mengimplementasikan metode yang tidak digunakan.
- **Contoh:**
   - Dalam perbaikan kode, saya memisahkan operasi baca dan tulis untuk entitas `Car` dengan membuat interface terpisah seperti `CarReadService` dan `CarWriteService`. Dengan begitu, controller yang hanya membutuhkan operasi baca tidak perlu mengetahui operasi tulis, sehingga kode menjadi lebih spesifik dan mudah dipahami.

## 5. Dependency Inversion Principle (DIP)
- **Keuntungan:**
   - Modul tingkat tinggi (seperti service) tidak bergantung pada implementasi konkrit modul tingkat rendah (seperti repository), melainkan bergantung pada abstraksi (interface). Hal ini membuat kode lebih fleksibel dan mudah diuji.
- **Contoh:**
   - Saya telah mengubah service sehingga tidak bergantung langsung pada `CarRepository` atau `ProductRepository` konkrit, melainkan pada interface seperti `CarRepositoryInterface` dan `ProductRepositoryInterface`. Jika nanti saya ingin mengganti logika penyimpanan (misalnya dari penyimpanan in-memory ke database), saya hanya perlu membuat implementasi baru dari interface tersebut tanpa harus mengubah kode di service.

---

# 3. Kerugian Tidak Menerapkan Prinsip SOLID pada Proyek Saya

## 1. Single Responsibility Principle (SRP)
- **Kerugian:**
   - Jika satu kelas menangani banyak tanggung jawab, perubahan di satu bagian bisa mempengaruhi bagian lain, menyebabkan kode sulit dipelihara dan diuji.
- **Contoh:**
   - Jika `CarController` juga menangani logika produk (seperti pada awalnya ketika terjadi pewarisan dari `ProductController`), maka perubahan pada logika produk dapat menyebabkan bug pada fungsi mobil.

## 2. Open/Closed Principle (OCP)
- **Kerugian:**
   - Kode yang tidak terbuka untuk ekstensi mengharuskan saya untuk mengubah kode yang sudah ada saat menambah fitur baru, sehingga meningkatkan risiko munculnya bug.
- **Contoh:**
   - Jika saya ingin menambahkan entitas `Bike` dan harus mengubah kode pada `ProductService` atau `CarService` yang sudah ada, maka fitur yang sudah berjalan bisa terpengaruh dan mengakibatkan error.

## 3. Liskov Substitution Principle (LSP)
- **Kerugian:**
   - Jika subclass tidak sepenuhnya memenuhi kontrak dari kelas dasarnya, maka penggantian dengan implementasi lain dapat menyebabkan perilaku aplikasi yang tidak konsisten.
- **Contoh:**
   - Jika implementasi `CarServiceImpl` tidak sepenuhnya sesuai dengan kontrak `CarService`, maka menggantinya dengan implementasi lain akan mengakibatkan error atau perilaku aplikasi yang tidak terduga pada controller.

## 4. Interface Segregation Principle (ISP)
- **Kerugian:**
   - Menggabungkan semua fungsi dalam satu interface besar memaksa klien untuk bergantung pada metode yang tidak relevan dengan kebutuhannya, sehingga membuat kode lebih kompleks dan sulit dipahami.
- **Contoh:**
   - Jika saya memiliki satu interface besar yang mencakup operasi baca, tulis, dan validasi, controller yang hanya membutuhkan operasi baca harus mengabaikan operasi tulis dan validasi, yang dapat membuat kode menjadi tidak efisien dan membingungkan.

## 5. Dependency Inversion Principle (DIP)
- **Kerugian:**
   - Jika modul tingkat tinggi bergantung langsung pada implementasi konkrit modul tingkat rendah, maka setiap perubahan pada implementasi bawah akan memaksa saya mengubah kode pada modul atas, sehingga menyulitkan pemeliharaan dan pengujian.
- **Contoh:**
   - Jika `CarServiceImpl` bergantung langsung pada `CarRepository` konkrit, maka jika saya ingin mengganti repository tersebut (misalnya untuk mendukung database yang berbeda), saya harus mengubah kode di service, yang berpotensi menimbulkan bug dan meningkatkan kompleksitas.

---

# Module 2
# 1. Fixed code scanning:

Total fixed : 23/30 vulnerability 
1. Token-Permissions (1 vulnerability)

Penjelasan: Saya membatasi izin GITHUB_TOKEN secara global (di level top-level) menjadi read-only (contents: read) pada workflow CI agar sesuai prinsip least privilege. Ini mencegah potensi penyalahgunaan token untuk mengirim kode berbahaya.

2. Dependency-Update-Tool (1 vulnerability)

Penjelasan: Saya menambahkan konfigurasi Dependabot (melalui file .github/dependabot.yml) agar dependensi selalu diperiksa dan diperbarui secara otomatis. Hal ini memastikan bahwa proyek tetap menggunakan versi dependensi yang aman dan terverifikasi.

3. Pinned-Dependencies (10 vulnerability)

Penjelasan: Saya memperbaiki referensi ke image dan GitHub Actions di Dockerfile serta workflow (misalnya, actions/checkout, actions/setup-java, pmd/pmd-github-action, dan github/codeql-action/upload-sarif) dengan mengganti tag versi dengan commit hash spesifik. Pinning dependency ini memastikan bahwa build selalu menggunakan versi yang telah diuji dan tidak berubah sewaktu-waktu.

4. SAST (1 vulnerability)

Penjelasan: Saya mengintegrasikan alat SAST (melalui CodeQL) sehingga setiap commit dan pull request diperiksa terhadap potensi kerentanan. Ini membantu mendeteksi masalah keamanan sejak dini sebelum masuk ke produksi.

5. Security-Policy (1 vulnerability)

Penjelasan: Saya menambahkan file SECURITY.md di root repository yang menjelaskan kebijakan keamanan, termasuk prosedur pelaporan kerentanan. Hal ini meningkatkan kesadaran dan kesiapan proyek dalam menangani isu keamanan.

6. License (1 vulnerability)

Penjelasan: Saya menambahkan file LICENSE (misalnya, lisensi MIT) di root repository, yang mendefinisikan hak cipta dan penggunaan kode, sehingga memberikan kejelasan bagi pengguna dan kontributor.

7. CI-Tests (1 vulnerability)

Penjelasan: Saya memastikan workflow CI melakukan pengujian secara menyeluruh, sehingga setiap perubahan kode diverifikasi dengan unit test sebelum di-merge. Ini membantu menjaga kualitas kode dan mencegah regresi.

8. PMD Warnings (6 vulnerability)

    * "This utility class has a non-private constructor" (2 vulnerability):
    
    Penjelasan: Meskipun PMD menganggap EshopApplication sebagai utility class, kelas ini adalah entry point aplikasi Spring Boot. Saya menonaktifkan peringatan ini dengan menambahkan @SuppressWarnings("PMD.UseUtilityClass") agar tetap menjaga fungsionalitas.
    
    * "This class has only private constructors and may be final" (1 vulnerability):
    
    Penjelasan: Saya menyesuaikan deklarasi konstruktor di EshopApplication dan menonaktifkan peringatan yang tidak relevan dengan kebutuhan bootstrap Spring Boot.
    
    * Unused import 'org.springframework.web.bind.annotation.*' (1 vulnerability):
    
    Penjelasan: Saya mengganti import wildcard dengan import spesifik untuk kelas-kelas yang benar-benar digunakan, sehingga mengurangi ambiguitas dan menjaga kebersihan kode.
    
    * Unused import 'java.util.UUID' (2 vulnerability):
    
    Penjelasan: Saya menghapus import java.util.UUID yang tidak digunakan, sehingga mengurangi ketidakakuratan dan menjaga agar kode tetap rapi.
# 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Implementasi CI/CD yang ada sudah memenuhi definisi Continuous Integration dan Continuous Deployment. Pertama, setiap push dan pull request memicu workflow CI yang menjalankan unit test dan analisis kode (seperti PMD dan Scorecard), sehingga membantu mendeteksi error dan masalah kualitas kode sejak dini. Kedua, dengan adanya Dockerfile yang terintegrasi dan mekanisme auto-deploy ke Koyeb, setiap perubahan yang sudah di-merge ke branch utama secara otomatis dibangun dan dideploy ke lingkungan produksi. Ketiga, pipeline yang terstruktur dan terotomatisasi ini mengurangi risiko human error dan mempercepat feedback loop. Hal ini memastikan bahwa setiap commit dapat diverifikasi secara konsisten sebelum masuk ke produksi.
# E-Shop Application (Spring Boot)

## Project Overview
E-Shop Application adalah aplikasi berbasis **Spring Boot** yang memungkinkan pengguna untuk **membuat, melihat, mengedit, dan menghapus produk**. Aplikasi ini menggunakan **Thymeleaf** sebagai template engine dan menyimpan data dalam **memori (List)** tanpa database.

## Features Implemented
- Create Product
- View Product List
- Edit Product
- Delete Product

## Clean Code Principles Applied
Beberapa prinsip **Clean Code** yang diterapkan dalam aplikasi ini:

1. **Meaningful Naming**
    - Variabel, metode, dan kelas memiliki nama deskriptif, seperti `ProductService`, `findById()`, `deleteById()`.
    - Menghindari penggunaan nama yang ambigu seperti `p`, diganti dengan `product` agar lebih jelas.

2. **Single Responsibility Principle (SRP)**
    - `ProductController.java` hanya menangani request.
    - `ProductService.java` hanya menangani business logic.
    - `ProductRepository.java` hanya menangani penyimpanan data.

3. **Avoiding Hardcoded Values**
    - ID produk dibuat otomatis menggunakan **UUID** untuk menghindari duplikasi.

4. **Code Readability**
    - Menggunakan anotasi **`@Service`**, **`@Repository`**, dan **`@Controller`** untuk menandai peran setiap kelas.
    - **Looping menggunakan iterator** dalam `findById()` untuk menghindari error **ConcurrentModificationException**.

## Secure Coding Practices Applied
Beberapa prinsip keamanan yang diterapkan dalam kode:

1. **Data Validation**
    - ID produk di-generate otomatis jika kosong menggunakan `UUID`.
    - Tombol Delete memiliki konfirmasi dengan `onclick="return confirm('Are you sure?');"`.

2. **Preventing Null Reference**
    - `findById()` menangani kasus jika ID tidak ditemukan dengan return `null`.
    - Sebelum menyimpan produk, ID dicek agar tidak `null`.

3. **No Direct Database Exposure**
    - Tidak ada query SQL langsung karena data disimpan dalam memori.

## Code Review & Refactoring Plan
Beberapa perbaikan yang dapat dilakukan untuk meningkatkan kualitas kode:

1. **Refactoring Penyimpanan Data agar Konsisten**
    - Saat ini, metode `update()` hanya ada di `ProductServiceImpl`, sementara operasi lainnya (create, delete) berada di `ProductRepository`.
    - Agar lebih konsisten, **logic update juga harus dipindahkan ke `ProductRepository`** sehingga semua operasi CRUD ada di satu tempat.

2. **Menggunakan `List<Product>` daripada `Iterator<Product>` dalam `findAll()`**
    - `findAll()` saat ini mengembalikan `Iterator<Product>`, yang menambah kompleksitas.
    - Sebaiknya ubah agar langsung mengembalikan `List<Product>` untuk kemudahan manipulasi data.

Melalui refactoring ini, struktur kode akan lebih terorganisir, mudah dipahami, dan lebih mudah dalam perawatan di masa depan.


# Refleksi Setelah Menulis Unit Test dan Functional Test

## **1. Refleksi Setelah Menulis Unit Test**
### **Bagaimana Perasaan Setelah Menulis Unit Test?**
Setelah menulis unit test, saya merasa lebih yakin bahwa kode berjalan sesuai harapan.  
Unit test membantu menemukan bug lebih awal, meningkatkan stabilitas aplikasi, dan memastikan fitur bekerja dalam berbagai skenario.  
Namun, menulis unit test juga membutuhkan pemikiran lebih dalam, terutama untuk menangani edge cases.

---

### **Berapa Banyak Unit Test yang Harus Dibuat dalam Satu Kelas?**
Jumlah unit test bergantung pada kompleksitas kode.  
Sebagai panduan umum:
- **CRUD Service** → 4-6 tes (Create, Read, Update, Delete, edge cases).
- **Helper Utility Class** → 3-5 tes (berdasarkan jumlah metode).
- **Controller dengan banyak request** → 6-10 tes (valid dan invalid request).

Setiap metode penting harus memiliki setidaknya satu tes dengan skenario positif dan negatif.

---

### **Bagaimana Memastikan Unit Test Sudah Cukup?**
Gunakan **Code Coverage** untuk mengukur cakupan tes.  
Code Coverage menunjukkan **berapa persen kode yang diuji oleh unit test**.

### **Jenis-Jenis Code Coverage**
1. **Statement Coverage** - Mengukur persentase pernyataan (statements) dalam kode yang dieksekusi oleh tes.
2. **Branch Coverage** - Memastikan setiap percabangan (`if`, `switch`) dalam kode telah diuji dengan semua kemungkinan jalur.
3. **Function Coverage** - Mengukur apakah setiap fungsi atau metode dalam kode telah dipanggil setidaknya sekali dalam tes.
4. **Line Coverage** - Menghitung jumlah baris kode yang dieksekusi oleh unit test dibandingkan total baris kode yang ada.
5. **Condition Coverage** - Memastikan setiap kondisi boolean dalam ekspresi logika (`&&`, `||`) diuji dengan semua kemungkinan nilai.


### **Apakah 100% Code Coverage Berarti Tidak Ada Bug?**
Tidak. Meskipun **100% code coverage** berarti semua baris kode telah diuji, itu **tidak menjamin tidak ada bug**.  
Coverage hanya mengukur **apakah kode dieksekusi**, bukan **apakah hasilnya benar**.

Untuk memastikan kualitas pengujian yang lebih baik, penting untuk:
- **Mengujikan skenario edge cases** yang mungkin terjadi di dunia nyata.
- **Menggunakan kombinasi unit test, integration test, dan functional test** untuk menguji sistem secara menyeluruh.
- **Melakukan code review** untuk mengevaluasi apakah ada bagian yang belum diuji dengan baik.

---

## **2. Evaluasi Clean Code dalam Functional Test Suite Baru**
Jika saya diminta untuk membuat **Functional Test Suite baru untuk memverifikasi jumlah produk dalam daftar**, ini dapat **menurunkan kualitas kode** jika tidak dikelola dengan baik.

### **Potensi Masalah dalam Clean Code**
1. **Duplikasi Kode**
    - Setup dan konfigurasi Selenium mungkin **diulang** di beberapa file test.
    - Hal ini dapat menyebabkan kode lebih sulit dipelihara.

2. **Terlalu Banyak Test Suite Terpisah**
    - Jika setiap fitur diuji dalam file yang berbeda, maintainability menjadi lebih sulit.
    - Lebih baik mengelompokkan **tes yang berkaitan dalam satu file** untuk meningkatkan efisiensi.

3. **Sulit dalam Refactoring**
    - Jika ada perubahan dalam **konfigurasi test**, semua file test harus diperbarui satu per satu.
    - Hal ini dapat memperlambat pengembangan dan meningkatkan kemungkinan error akibat ketidakkonsistenan.

---

### **Solusi untuk Menjaga Clean Code**
- **Gunakan Base Test Class untuk Setup Selenium**  
  Daripada menulis ulang setup di setiap test, buat satu **kelas dasar** yang berisi konfigurasi Selenium.
- **Gabungkan Tes yang Berkaitan**  
  Jika memungkinkan, **uji beberapa fitur dalam satu test suite** untuk mengurangi jumlah file test yang berlebihan.
- **Gunakan Data-Driven Testing**  
  Gunakan parameterisasi dalam test case agar satu test bisa menguji berbagai skenario tanpa perlu menduplikasi kode.

Dengan menerapkan prinsip **Clean Code** dan **DRY (Don't Repeat Yourself)**, functional test akan lebih mudah dirawat, lebih efisien, dan lebih fleksibel terhadap perubahan di masa depan.
