import urllib.request
import os

url = "https://repo1.maven.org/maven2/com/github/librepdf/openpdf/1.3.30/openpdf-1.3.30.jar"
dest = r"C:\Users\WINDOWS 11 PRO\Documents\NetBeansProjects\AplikasiAdministrasiNilaiWeb\web\WEB-INF\lib\openpdf-1.3.30.jar"

print(f"Downloading {url} to {dest}...")
try:
    urllib.request.urlretrieve(url, dest)
    print("Download successful!")
    print(f"File size: {os.path.getsize(dest)} bytes")
except Exception as e:
    print(f"Download failed: {e}")
