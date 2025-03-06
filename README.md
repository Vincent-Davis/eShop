# Module 4

## Refleksi TDD Berdasarkan Pertanyaan Reflektif Percival (2017)

Pada penerapan Test-Driven Development (TDD) kali ini, saya merasa prosesnya sangat membantu dalam menjaga fokus pada kebutuhan fungsional sejak awal. Dengan menulis tes terlebih dahulu, saya bisa memastikan bahwa kode yang ditulis benar-benar memenuhi kriteria keberhasilan yang diinginkan. Selain itu, pendekatan ini membuat saya lebih percaya diri saat melakukan refactoring, karena serangkaian tes dapat menjamin bahwa fungsionalitas tidak terpengaruh oleh perubahan kode.

Namun, saya juga menyadari bahwa alur TDD ini masih dapat ditingkatkan. Misalnya, saya perlu lebih teliti dalam menulis tes yang mencakup skenario negatif (unhappy path) agar benar-benar mencerminkan situasi di dunia nyata. Di kesempatan berikutnya, saya akan menambahkan lebih banyak variasi tes, terutama yang berkaitan dengan beban kinerja atau integrasi dengan komponen lain, sehingga kualitas kode semakin baik dan dapat diandalkan.

---

## Refleksi Prinsip F.I.R.S.T. dalam Pembuatan Unit Test

Secara umum, tes yang dibuat telah berusaha mengikuti prinsip F.I.R.S.T. (Fast, Independent, Repeatable, Self-validating, Timely). Tes dijalankan dengan cepat, tidak saling bergantung satu sama lain, dan memiliki hasil yang jelas (lulus atau gagal) tanpa memerlukan pengecekan manual. Selain itu, tes juga ditulis berdekatan dengan waktu penulisan kode (Timely), sehingga kesalahan dapat terdeteksi sejak dini.

Meski begitu, ada beberapa hal yang perlu diperbaiki. Salah satunya adalah memastikan setiap tes benar-benar terisolasi (Independent) dengan meminimalkan penggunaan data atau state bersama. Ke depannya, saya akan lebih disiplin dalam menerapkan teknik _mocking_ atau menyiapkan ulang data di setiap tes, agar tidak terjadi ketergantungan antara satu tes dan tes lainnya. Dengan demikian, setiap tes akan lebih konsisten dan mudah dipelihara.


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
