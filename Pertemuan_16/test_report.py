import urllib.request
import urllib.error

url = "http://localhost:8080/AplikasiAdministrasiNilaiWeb/LaporanNilaiController"
print(f"Requesting {url}...")
try:
    with urllib.request.urlopen(url) as response:
        print(f"Status Code: {response.status}")
        print("Headers:")
        for k, v in response.getheaders():
            print(f"  {k}: {v}")
        
        content = response.read()
        print(f"Total content length: {len(content)} bytes")
        if len(content) > 0:
            print(f"First 100 bytes: {content[:100]}")
            with open("report.pdf", "wb") as f:
                f.write(content)
            print("Saved as report.pdf")
        
except urllib.error.HTTPError as e:
    print(f"HTTP Error {e.code}: {e.reason}")
    print(e.read().decode('utf-8', errors='ignore'))
except Exception as e:
    print(f"Error: {e}")
