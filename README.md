# Module 2
# 1. Fixed code scanning:

1. Security-Policy
Strategi Perbaikan:
Saya menambahkan file SECURITY.md di root repository yang menjelaskan kebijakan keamanan proyek, termasuk cara melaporkan kerentanan dan prosedur penanganannya. Hal ini memastikan pengguna dan kontributor mengetahui bagaimana menangani isu keamanan.

2. Unused import 'org.springframework.web.bind.annotation.*'
Strategi Perbaikan:
Alih-alih menggunakan import wildcard, saya mengganti dengan import spesifik hanya untuk kelas-kelas yang benar-benar digunakan (misalnya @Controller, @RequestMapping, @GetMapping, @PostMapping, @PathVariable, dan @ModelAttribute). Dengan demikian, kode menjadi lebih jelas dan mengurangi potensi ambiguitas.

3. Unused import 'java.util.UUID'
Strategi Perbaikan:
Karena import tersebut tidak digunakan di dalam kode, saya menghapusnya. Hal ini membantu menjaga kebersihan kode dan mencegah import yang tidak perlu.

4. "This utility class has a non-private constructor" (PMD warning di EshopApplication.java)
Strategi Perbaikan:
Meskipun PMD menganggap EshopApplication sebagai utility class karena hanya memiliki method statis, kelas ini merupakan entry point aplikasi Spring Boot yang membutuhkan constructor publik agar framework dapat menginstansiasinya dengan benar. Oleh karena itu, daripada mengubah constructor menjadi private (yang akan menyebabkan error saat booting), saya memilih untuk menonaktifkan warning ini dengan menambahkan anotasi @SuppressWarnings("PMD.UseUtilityClass") pada kelas tersebut. Dengan begitu, saya menjaga agar aplikasi tetap berfungsi sambil tetap mengakui peringatan dari PMD.

# 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Implementasi CI/CD yang ada sudah memenuhi definisi Continuous Integration dan Continuous Deployment. Pertama, setiap push dan pull request memicu workflow CI yang menjalankan unit test dan analisis kode (seperti PMD dan Scorecard), sehingga membantu mendeteksi error dan masalah kualitas kode sejak dini. Kedua, dengan adanya Dockerfile yang terintegrasi dan mekanisme auto-deploy ke Koyeb, setiap perubahan yang sudah di-merge ke branch utama secara otomatis dibangun dan dideploy ke lingkungan produksi. Ketiga, pipeline yang terstruktur dan terotomatisasi ini mengurangi risiko human error, mempercepat feedback loop, serta memastikan bahwa setiap commit dapat diverifikasi secara konsisten sebelum masuk ke produksi.
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
