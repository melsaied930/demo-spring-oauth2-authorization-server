```bash
#!/bin/bash

curl -k https://localhost:9000/api/public/hello
curl -k -u user:user https://localhost:9000/api/user/hello
curl -k -u admin:admin https://localhost:9000/api/user/hello
curl -k -u admin:admin https://localhost:9000/api/admin/hello
curl -k -u user:user https://localhost:9000/api/admin/hello
curl -k https://localhost:9000/api/user/hello
curl -k https://localhost:9000/api/admin/hello
```