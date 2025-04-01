import http.server
import socketserver

PORT = 8000

class MyHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/':
            self.path = 'index.html'
        elif self.path == '/data.csv':
            self.send_response(200)
            self.send_header("Content-type", "text/csv")
            self.end_headers()
            with open("data.csv", "rb") as file:
                self.wfile.write(file.read())
            return
        return http.server.SimpleHTTPRequestHandler.do_GET(self)

    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        content_type = self.headers.get('Content-Type', '')

        if 'multipart/form-data' in content_type:
            boundary = content_type.split("boundary=")[1]
            boundary = boundary.encode()

            # Read the POST data
            post_data = self.rfile.read(content_length)

            # Split the data by boundary
            parts = post_data.split(boundary)

            for part in parts:
                # Check if the part contains the file content
                if b'Content-Disposition: form-data; name="file";' in part:
                    # Find the start of the file content (after headers and two CRLFs)
                    file_start = part.find(b"\r\n\r\n") + 4
                    file_end = part.rfind(b"\r\n")

                    # Extract the file content between the start and end markers
                    file_content = part[file_start:file_end]

                    # Save the file
                    with open("temp.csv", "wb") as f:
                        f.write(file_content)

                    self.send_response(200)
                    self.end_headers()
                    self.wfile.write(b"File uploaded successfully.")
                    return

            self.send_response(400)
            self.end_headers()
            self.wfile.write(b"No valid file found.")
        else:
            self.send_response(400)
            self.end_headers()
            self.wfile.write(b"Invalid content type.")

Handler = MyHandler

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print(f"Serving at port {PORT}")
    httpd.serve_forever()
