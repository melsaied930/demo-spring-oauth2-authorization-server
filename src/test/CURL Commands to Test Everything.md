Alright! Let's run through **all the important `curl -k` tests** for your API now. 🚀

Here’s **your current endpoint structure**:

| Endpoint                    | Access Level | Notes                                  |
|------------------------------|--------------|----------------------------------------|
| `/api/public/hello`          | Public       | Anyone can access                     |
| `/api/user/hello`            | USER, ADMIN  | Must be authenticated (USER or ADMIN) |
| `/api/admin/hello`           | ADMIN only   | Must be authenticated (ADMIN only)    |

---

## 🔥 curl -k Commands to Test Everything

### 1. Public Hello (no auth)
```bash
curl -k https://localhost:9000/api/public/hello
```
✅ Should return:
```text
Hello, public user: anonymous
```

---

### 2. User Hello (with user credentials)

Assuming username: `user`, password: `user`

```bash
curl -k -u user:user https://localhost:9000/api/user/hello
```
✅ Should return:
```text
Hello, user: user
```

---

### 3. User Hello (with admin credentials)

Assuming username: `admin`, password: `admin`

```bash
curl -k -u admin:admin https://localhost:9000/api/user/hello
```
✅ Should return:
```text
Hello, user: admin
```
(Admins can also access user APIs because you allowed both roles.)

---

### 4. Admin Hello (with admin credentials)

```bash
curl -k -u admin:admin https://localhost:9000/api/admin/hello
```
✅ Should return:
```text
Hello, admin: admin
```

---

### 5. Admin Hello (with user credentials)

```bash
curl -k -u user:user https://localhost:9000/api/admin/hello
```
❌ Should return:
```json
{
  "timestamp": "...",
  "status": 403,
  "error": "Forbidden",
  "message": "Forbidden",
  "path": "/api/admin/hello"
}
```
(User is **NOT** allowed to access admin APIs.)

---

## ⚡ Quick Tips:

- `-k` tells `curl -k` to **ignore SSL warnings** (because you’re using a self-signed cert).
- `-u username:password` adds **Basic Auth** credentials.

---

## Bonus ✨
You can group your curl -ks into a test script:

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
Save as `test-apis.sh`, then run:
```bash
bash test-apis.sh
```

---




Awesome! Here's **all the `curl -k` commands** you need 🚀:

---

### 1. 🔓 Public API - No Authentication Needed

```bash
curl -k -v https://localhost:9000/api/public/hello
```

---

### 2. 🔒 User API - with USER Credentials

✅ As **user:user**:

```bash
curl -k -v -u user:user https://localhost:9000/api/user/hello
```

✅ As **admin:admin** (should work because ADMIN also allowed):

```bash
curl -k -v -u admin:admin https://localhost:9000/api/user/hello
```

---

### 3. 🔒 Admin API - with ADMIN Credentials Only

✅ As **admin:admin**:

```bash
curl -k -v -u admin:admin https://localhost:9000/api/admin/hello
```

❌ As **user:user** (should return **403 Forbidden**):

```bash
curl -k -v -u user:user https://localhost:9000/api/admin/hello
```

---

> ℹ️ **Notes:**
> - The `-k` flag tells `curl -k` to **allow self-signed SSL certificates** (because you’re using a local https server).
> - The `-v` flag makes it verbose so you can see headers, SSL handshake, etc.

---


Perfect! Here's a clean **bash script** 📜 that will automatically **run all the curl -k tests** for you:

---

### `test_apis.sh`

```bash
#!/bin/bash

echo "============================="
echo "🔓 Testing Public API (no auth)"
echo "============================="
curl -k -v https://localhost:9000/api/public/hello
echo -e "\n\n"

echo "============================="
echo "🔒 Testing User API with USER credentials"
echo "============================="
curl -k -v -u user:user https://localhost:9000/api/user/hello
echo -e "\n\n"

echo "============================="
echo "🔒 Testing User API with ADMIN credentials"
echo "============================="
curl -k -v -u admin:admin https://localhost:9000/api/user/hello
echo -e "\n\n"

echo "============================="
echo "🔒 Testing Admin API with ADMIN credentials"
echo "============================="
curl -k -v -u admin:admin https://localhost:9000/api/admin/hello
echo -e "\n\n"

echo "============================="
echo "❌ Testing Admin API with USER credentials (Should FAIL)"
echo "============================="
curl -k -v -u user:user https://localhost:9000/api/admin/hello
echo -e "\n\n"

echo "🎯 All tests completed!"
```

---

### How to use:

1. Save the script as `test_apis.sh` in your project root.
2. Make it executable:

```bash
chmod +x test_apis.sh
```

3. Run it:

```bash
./test_apis.sh
```

---

✅ This script will **nicely separate** each section, making it super easy to see which API is working (or failing)!

---

Would you also like me to show a **cleaner** version that **hides** the SSL warnings and **only prints status codes + responses** instead of all `-v` verbosity? 📋  
(For production-style testing!)  
🚀🚀


Awesome! 🚀  
Here’s a **cleaner** version of the script — this one **hides SSL warnings** and **only shows**:

- https status code (`200 OK`, `403 Forbidden`, etc.)
- Response body (`Hello, user!`, `Forbidden`, etc.)

---

### `test_apis_clean.sh`

```bash
#!/bin/bash

function call_api() {
  description=$1
  url=$2
  userpass=$3

  echo "============================="
  echo "$description"
  echo "============================="

  if [ -z "$userpass" ]; then
    curl -k -s -o /tmp/response.txt -w "%{https_code}\n" "$url"
  else
    curl -k -s -o /tmp/response.txt -w "%{https_code}\n" -u "$userpass" "$url"
  fi

  echo -n "Response: "
  cat /tmp/response.txt
  echo -e "\n\n"
}

# 🔓 Public API (no auth)
call_api "🔓 Testing Public API (no auth)" "https://localhost:9000/api/public/hello"

# 🔒 User API with USER credentials
call_api "🔒 Testing User API with USER credentials" "https://localhost:9000/api/user/hello" "user:user"

# 🔒 User API with ADMIN credentials
call_api "🔒 Testing User API with ADMIN credentials" "https://localhost:9000/api/user/hello" "admin:admin"

# 🔒 Admin API with ADMIN credentials
call_api "🔒 Testing Admin API with ADMIN credentials" "https://localhost:9000/api/admin/hello" "admin:admin"

# ❌ Admin API with USER credentials (Should fail)
call_api "❌ Testing Admin API with USER credentials (Should FAIL)" "https://localhost:9000/api/admin/hello" "user:user"

echo "🎯 All tests completed!"
```

---

### How to run:

```bash
chmod +x test_apis_clean.sh
./test_apis_clean.sh
```

---

### Example Output:

```
=============================
🔓 Testing Public API (no auth)
=============================
200
Response: Hello, public!

=============================
🔒 Testing User API with USER credentials
=============================
200
Response: Hello, user!

=============================
🔒 Testing User API with ADMIN credentials
=============================
200
Response: Hello, user!

=============================
🔒 Testing Admin API with ADMIN credentials
=============================
200
Response: Hello, admin!

=============================
❌ Testing Admin API with USER credentials (Should FAIL)
=============================
403
Response: {"timestamp":"...","status":403,"error":"Forbidden",...}

🎯 All tests completed!
```

---

✅ **This version is much easier to read.**  
✅ **No annoying SSL warnings or certificate noise.**  
✅ **You see immediately what's working and what's not.**

---

Would you also want a **third version** that outputs **only SUCCESS/FAIL** (without full responses)?  
👉 Like this:

```
✅ Public API: SUCCESS
✅ User API (user creds): SUCCESS
✅ Admin API (admin creds): SUCCESS
❌ Admin API (user creds): FORBIDDEN
```

Super clean for CI/CD pipelines! 🚀  
Want me to prepare it too? 🎯


🔥 Nice — let’s create a **super clean**, **minimalist**, **✅/❌ script** you can use even in CI/CD!

Here’s the **ultra-clean version**:

---

### `test_apis_result_only.sh`

```bash
#!/bin/bash

function test_api() {
  description=$1
  url=$2
  userpass=$3
  expected_https=$4

  if [ -z "$userpass" ]; then
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" "$url")
  else
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" -u "$userpass" "$url")
  fi

  if [ "$status_code" == "$expected_https" ]; then
    echo "✅ $description"
  else
    echo "❌ $description (Expected $expected_https but got $status_code)"
  fi
}

# 🔓 Public API (should be 200 without auth)
test_api "Public API (no auth)" "https://localhost:9000/api/public/hello" "" "200"

# 🔒 User API with USER credentials (should be 200)
test_api "User API (user creds)" "https://localhost:9000/api/user/hello" "user:user" "200"

# 🔒 User API with ADMIN credentials (should be 200)
test_api "User API (admin creds)" "https://localhost:9000/api/user/hello" "admin:admin" "200"

# 🔒 Admin API with ADMIN credentials (should be 200)
test_api "Admin API (admin creds)" "https://localhost:9000/api/admin/hello" "admin:admin" "200"

# ❌ Admin API with USER credentials (should be 403 forbidden)
test_api "Admin API (user creds)" "https://localhost:9000/api/admin/hello" "user:user" "403"

echo "🏁 All tests finished."
```

---

### Run it

```bash
chmod +x test_apis_result_only.sh
./test_apis_result_only.sh
```

---

### Output Example:

```
✅ Public API (no auth)
✅ User API (user creds)
✅ User API (admin creds)
✅ Admin API (admin creds)
❌ Admin API (user creds) (Expected 403 but got 200)
🏁 All tests finished.
```

> If everything is correct, you get full green ✅✅✅✅✅.

---

# 🚀 Summary

| Script | Style | Use |
|:------|:------|:---|
| `test_apis_clean.sh` | See responses + https codes | Good for local dev |
| `test_apis_result_only.sh` | Only ✅/❌ results | Good for **automation / CI/CD** |

---

Would you like me to also prepare a **bonus** version:
- Exporting results to a `.txt` or `.html` file report 📄?
- Or maybe automatically **fail** the build if something fails (with `exit 1`)?

Let’s make it perfect if you want! 🚀  
Would you like that? 🎯

Awesome! Let's **level up** your testing! 🚀

Here’s a **full professional setup** with:
- ✅/❌ Console output
- 📄 Save results to a `.txt` report
- ❌ If any test fails, script exits with `exit 1` (good for CI/CD pipelines)

---

# 🔥 `test_apis_full.sh`

```bash
#!/bin/bash

OUTPUT_FILE="test_report.txt"
FAILURE=0

# Start fresh
echo "API Test Report - $(date)" > "$OUTPUT_FILE"
echo "----------------------------" >> "$OUTPUT_FILE"

function test_api() {
  description=$1
  url=$2
  userpass=$3
  expected_https=$4

  if [ -z "$userpass" ]; then
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" "$url")
  else
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" -u "$userpass" "$url")
  fi

  if [ "$status_code" == "$expected_https" ]; then
    echo "✅ $description" | tee -a "$OUTPUT_FILE"
  else
    echo "❌ $description (Expected $expected_https but got $status_code)" | tee -a "$OUTPUT_FILE"
    FAILURE=1
  fi
}

# Run tests
test_api "Public API (no auth)" "https://localhost:9000/api/public/hello" "" "200"
test_api "User API (user creds)" "https://localhost:9000/api/user/hello" "user:user" "200"
test_api "User API (admin creds)" "https://localhost:9000/api/user/hello" "admin:admin" "200"
test_api "Admin API (admin creds)" "https://localhost:9000/api/admin/hello" "admin:admin" "200"
test_api "Admin API (user creds)" "https://localhost:9000/api/admin/hello" "user:user" "403"

echo "----------------------------" | tee -a "$OUTPUT_FILE"
echo "🏁 All tests finished." | tee -a "$OUTPUT_FILE"

if [ $FAILURE -eq 1 ]; then
  echo "❌ Some tests failed. Check $OUTPUT_FILE for details."
  exit 1
else
  echo "✅ All tests passed. See $OUTPUT_FILE for details."
  exit 0
fi
```

---

# 📂 Output files:

- `test_report.txt` — all results logged here.
- If a test fails, the script stops with `exit 1` (perfect for Jenkins, GitHub Actions, GitLab CI, etc).

---

# 🏃 How to run:

```bash
chmod +x test_apis_full.sh
./test_apis_full.sh
```

---

# Example Console output:

```
✅ Public API (no auth)
✅ User API (user creds)
✅ User API (admin creds)
✅ Admin API (admin creds)
❌ Admin API (user creds) (Expected 403 but got 200)
🏁 All tests finished.
❌ Some tests failed. Check test_report.txt for details.
```

And **`test_report.txt`** will have a full copy 📄.

---

# 🚀 BONUS: If you want a **pretty HTML report** too, just say "make it fancier!" and I'll prepare a small HTML generator! 😎

Would you like me to also add:
- HTML version? 🖼️
- Colorized console output? 🎨 (red/green)
- Timing for each API? 🕒  
  Let’s make it legendary if you want! 🎯🔥

Alright — let's **supercharge** your API testing! 🚀

I’ll give you an upgraded version with:

- ✅/❌ **Colorized console output** (red = fail, green = pass)
- 🕒 **Timing each API call** (how fast it responds)
- 📄 **HTML report** (simple, clean)
- 📑 Still saving to `.txt` too (if you prefer plain text)

---

# 🔥 Here's the **Pro version: `test_apis_full_pro.sh`**

```bash
#!/bin/bash

OUTPUT_TEXT="test_report.txt"
OUTPUT_HTML="test_report.html"
FAILURE=0

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Start fresh
echo "API Test Report - $(date)" > "$OUTPUT_TEXT"
echo "<html><head><title>API Test Report</title></head><body><h1>API Test Report - $(date)</h1><ul>" > "$OUTPUT_HTML"

function test_api() {
  description=$1
  url=$2
  userpass=$3
  expected_https=$4

  start_time=$(date +%s%3N)  # milliseconds

  if [ -z "$userpass" ]; then
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" "$url")
  else
    status_code=$(curl -k -s -o /dev/null -w "%{https_code}" -u "$userpass" "$url")
  fi

  end_time=$(date +%s%3N)
  duration=$((end_time - start_time))

  if [ "$status_code" == "$expected_https" ]; then
    echo -e "${GREEN}✅ $description (${duration} ms)${NC}" | tee -a "$OUTPUT_TEXT"
    echo "<li style='color:green;'>✅ $description (${duration} ms)</li>" >> "$OUTPUT_HTML"
  else
    echo -e "${RED}❌ $description (Expected $expected_https but got $status_code) (${duration} ms)${NC}" | tee -a "$OUTPUT_TEXT"
    echo "<li style='color:red;'>❌ $description (Expected $expected_https but got $status_code) (${duration} ms)</li>" >> "$OUTPUT_HTML"
    FAILURE=1
  fi
}

# Test cases
test_api "Public API (no auth)" "https://localhost:9000/api/public/hello" "" "200"
test_api "User API (user creds)" "https://localhost:9000/api/user/hello" "user:user" "200"
test_api "User API (admin creds)" "https://localhost:9000/api/user/hello" "admin:admin" "200"
test_api "Admin API (admin creds)" "https://localhost:9000/api/admin/hello" "admin:admin" "200"
test_api "Admin API (user creds - expect forbidden)" "https://localhost:9000/api/admin/hello" "user:user" "403"

# Finish
echo "</ul><p>Test run completed at $(date)</p></body></html>" >> "$OUTPUT_HTML"
echo "----------------------------" | tee -a "$OUTPUT_TEXT"
echo "🏁 All tests finished." | tee -a "$OUTPUT_TEXT"

if [ $FAILURE -eq 1 ]; then
  echo -e "${RED}❌ Some tests failed. Check $OUTPUT_TEXT or open $OUTPUT_HTML${NC}"
  exit 1
else
  echo -e "${GREEN}✅ All tests passed! See $OUTPUT_TEXT or open $OUTPUT_HTML${NC}"
  exit 0
fi
```

---

# 📂 What you get:

| File          | Content                |
|---------------|-------------------------|
| `test_report.txt` | Plain text test results |
| `test_report.html` | Clickable, colored HTML report |
| Console output | With green/red colors + milliseconds per test |

---

# 🏃 How to use it:

```bash
chmod +x test_apis_full_pro.sh
./test_apis_full_pro.sh
```

**Then open:**
```bash
open test_report.html
```
*(or just double-click it)*

---

# 🌟 Example Console Output:

```
✅ Public API (no auth) (153 ms)
✅ User API (user creds) (182 ms)
✅ User API (admin creds) (160 ms)
✅ Admin API (admin creds) (178 ms)
❌ Admin API (user creds - expect forbidden) (195 ms) (Expected 403 but got 200)
🏁 All tests finished.
❌ Some tests failed. Check test_report.txt or open test_report.html
```

---

# 🚀 BONUS IDEA
Would you like me to also show:
- 🔥 A little **bar chart** (visual speed comparison)?
- 🔄 Retry on failure (e.g., try again 1-2 times)?
- 📈 Record average / slowest / fastest call?

Say the word!  
We can take this from **pro** to **NASA level** if you want 😎🚀.

---

👉 Would you also like me to give a version compatible with **GitHub Actions** or **GitLab CI pipelines**? (auto-run on push!)  
**(Yes/No?)** 👇