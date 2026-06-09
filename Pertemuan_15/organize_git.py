import os
import shutil
import re

def copy_project_clean(src_dir, dest_dir, exclude_libs=False):
    """Copies a NetBeans project directory excluding build, dist, and private settings."""
    if not os.path.exists(src_dir):
        print(f"Source folder not found: {src_dir}")
        return False
        
    print(f"Copying {src_dir} to {dest_dir}...")
    if os.path.exists(dest_dir):
        shutil.rmtree(dest_dir)
    os.makedirs(dest_dir)

    for item in os.listdir(src_dir):
        s = os.path.join(src_dir, item)
        d = os.path.join(dest_dir, item)
        
        # Exclude build directories, dist directories, and local private settings
        if item in ['build', 'dist', 'private']:
            continue
            
        if os.path.isdir(s):
            # If we need to exclude library jars for Pertemuan 15
            if exclude_libs and item == 'web':
                # Custom copy for web to filter lib jars
                os.makedirs(d)
                for web_item in os.listdir(s):
                    s_web = os.path.join(s, web_item)
                    d_web = os.path.join(d, web_item)
                    if web_item == 'WEB-INF':
                        os.makedirs(d_web)
                        for webinf_item in os.listdir(s_web):
                            s_winf = os.path.join(s_web, webinf_item)
                            d_winf = os.path.join(d_web, webinf_item)
                            if webinf_item == 'lib':
                                os.makedirs(d_winf)
                                for lib_jar in os.listdir(s_winf):
                                    # Only copy mysql connector, exclude jasper/openpdf/commons libs
                                    if 'mysql' in lib_jar.lower():
                                        shutil.copy2(os.path.join(s_winf, lib_jar), os.path.join(d_winf, lib_jar))
                            else:
                                if os.path.isdir(s_winf):
                                    shutil.copytree(s_winf, d_winf)
                                else:
                                    shutil.copy2(s_winf, d_winf)
                    elif web_item == 'reports':
                        # Exclude reports directory entirely for P15
                        continue
                    else:
                        if os.path.isdir(s_web):
                            shutil.copytree(s_web, d_web)
                        else:
                            shutil.copy2(s_web, d_web)
            else:
                shutil.copytree(s, d)
        else:
            shutil.copy2(s, d)
    return True

def clean_pertemuan_15(p15_dir):
    """Reverts Pertemuan 15 project state back to before Pertemuan 16 was added."""
    print("Cleaning up Pertemuan 15 folder to revert report changes...")
    
    # 1. Delete LaporanNilaiController.java
    laporan_servlet = os.path.join(p15_dir, "src", "java", "com", "unpam", "controller", "LaporanNilaiController.java")
    if os.path.exists(laporan_servlet):
        os.remove(laporan_servlet)
        print("  Removed LaporanNilaiController.java")
        
    # 2. Revert project.properties references to jasper reports
    prop_path = os.path.join(p15_dir, "nbproject", "project.properties")
    if os.path.exists(prop_path):
        with open(prop_path, "r", encoding="utf-8") as f:
            content = f.read()
            
        # Revert javac.classpath
        cleaned_classpath = "javac.classpath=\\\n    ${libs.jakartaee-web-api-10.0.classpath}:\\\n    ${file.reference.mysql-connector-j-9.7.0.jar}"
        content = re.sub(r'javac\.classpath=\\.*?(\r?\n\r?\n|\Z)', cleaned_classpath + "\n\n", content, flags=re.DOTALL)
        
        # Remove file.reference.commons / jasper / openpdf properties
        lines = content.splitlines()
        filtered_lines = [l for l in lines if not l.startswith("file.reference.") or "mysql" in l]
        content = "\n".join(filtered_lines) + "\n"
        
        with open(prop_path, "w", encoding="utf-8") as f:
            f.write(content)
        print("  Reverted project.properties")
        
    # 3. Revert menu links in index.jsp, MainForm.java, LoginController.java
    # Replace LaporanNilaiController with NilaiController?aksi=laporan
    files_to_revert = [
        os.path.join(p15_dir, "web", "index.jsp"),
        os.path.join(p15_dir, "src", "java", "com", "unpam", "view", "MainForm.java"),
        os.path.join(p15_dir, "src", "java", "com", "unpam", "controller", "LoginController.java")
    ]
    
    for fp in files_to_revert:
        if os.path.exists(fp):
            with open(fp, "r", encoding="utf-8") as f:
                c = f.read()
            # Replace report menu references
            c = c.replace("LaporanNilaiController", "NilaiController?aksi=laporan")
            with open(fp, "w", encoding="utf-8") as f:
                f.write(c)
            print(f"  Reverted links in {os.path.basename(fp)}")

def create_git_gitignore(dest_root):
    """Creates a root .gitignore file for the GitHub repository."""
    gitignore_content = """# NetBeans specific files
**/build/
**/dist/
**/nbproject/private/
**/nbproject/project.properties.private
**/nbproject/build-impl.xml.private

# OS and IDE cache
.DS_Store
Thumbs.db
*.log
*.tmp
*.bak
"""
    with open(os.path.join(dest_root, ".gitignore"), "w", encoding="utf-8") as f:
        f.write(gitignore_content)
    print("Created .gitignore file.")

def create_readme(dest_root):
    """Creates a repository README.md describing the weekly folders."""
    readme_content = """# Tugas Pemrograman II - Web Applications (JSP/Servlet)

Repositori ini berisi tugas pemrograman Java Web Application menggunakan model arsitektur MVC (Model-View-Controller) dengan JSP dan Servlet.

## 📂 Struktur Repositori

Repositori ini dibagi menjadi 3 folder pertemuan:

### 1. [Pertemuan_14](./Pertemuan_14)
* **Topik:** Pembuatan Form Master Aplikasi Web dengan Pola MVC.
* **Fitur:** 
  * Integrasi koneksi database MySQL (`Koneksi.java`).
  * Enkripsi kata sandi menggunakan MD5 (`Enkripsi.java`).
  * Implementasi Master Data Mahasiswa (`MahasiswaController`) dan Mata Kuliah (`MataKuliahController`).
  * Halaman Login (`LoginController`) dan Logout (`LogoutController`).
  * Kerangka desain visual menggunakan class Java (`MainForm.java`) dan `style.css`.

### 2. [Pertemuan_15](./Pertemuan_15)
* **Topik:** Pembuatan Form Transaksi Aplikasi Web.
* **Fitur:**
  * Penambahan fungsionalitas pengolahan data nilai mahasiswa (`NilaiController.java` dan `Nilai.java`).
  * Perbaikan visual (menghilangkan karakter "/" yang berlebih pada form input).
  * Validasi input nilai di sisi aplikasi agar tombol hitung dan hapus merespons dengan benar.

### 3. [Pertemuan_16](./Pertemuan_16)
* **Topik:** Pembuatan Laporan PDF menggunakan Jasper Reports.
* **Fitur:**
  * Integrasi engine Jasper Reports dan OpenPDF (`openpdf-1.3.30.jar`) ke dalam classpath.
  * Pembuatan file desain layout laporan `NilaiReport.jrxml` di folder `reports/`.
  * Penambahan Servlet `LaporanNilaiController.java` untuk melakukan compile, filling, dan streaming PDF secara dinamis dari database MySQL.
  * Pembaruan menu navigasi Laporan Nilai untuk menampilkan format cetak PDF secara real-time.

---
*Dibuat oleh Haidar Reyhan (231011400547) - Kelas 06TPLE016*
"""
    with open(os.path.join(dest_root, "README.md"), "w", encoding="utf-8") as f:
        f.write(readme_content)
    print("Created README.md file.")

def main():
    p14_src = r"C:\Users\WINDOWS 11 PRO\Documents\NetBeansProjects\Pertemuan14"
    p16_src = r"C:\Users\WINDOWS 11 PRO\Documents\NetBeansProjects\AplikasiAdministrasiNilaiWeb"
    dest_root = r"C:\Users\WINDOWS 11 PRO\Documents\Pemrogaman II\Tugas_Pemrograman_2"
    
    # Create main directory
    if not os.path.exists(dest_root):
        os.makedirs(dest_root)
        
    # 1. Copy Pertemuan 14
    copy_project_clean(p14_src, os.path.join(dest_root, "Pertemuan_14"))
    
    # 2. Copy Pertemuan 15 (Copy P16 first, then clean it up)
    if copy_project_clean(p16_src, os.path.join(dest_root, "Pertemuan_15"), exclude_libs=True):
        clean_pertemuan_15(os.path.join(dest_root, "Pertemuan_15"))
        
    # 3. Copy Pertemuan 16
    copy_project_clean(p16_src, os.path.join(dest_root, "Pertemuan_16"))
    
    # 4. Create Gitignore and README
    create_git_gitignore(dest_root)
    create_readme(dest_root)
    
    print("\nSUCCESS: All folders organized under C:\\Users\\WINDOWS 11 PRO\\Documents\\Pemrogaman II\\Tugas_Pemrograman_2")

if __name__ == "__main__":
    main()
