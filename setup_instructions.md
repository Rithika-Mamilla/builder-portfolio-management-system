
## 🚀 Project Setup Instructions

Follow the steps below to set up and run the **Builder Portfolio Management System** on your local machine.

---

### 1️⃣ Prerequisites

Before starting, ensure you have the following installed:

* **Java JDK 17+**
* **PostgreSQL** 
* **Git** (for cloning the repository)
* **IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java support)

---

### 2️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/builder-portfolio-management.git
cd builder-portfolio-management
```

---

### 3️⃣ Database Setup

1. Create a new database in PostgreSQL:

   ```sql
   CREATE DATABASE builder_portfolio_db;
   ```
2. Import the schema:

   ```bash
   mysql -u root -p builder_portfolio_db < schema.sql
   ```

   (This will create all tables and insert sample data.)

---

### 4️⃣ Configure Database Connection

Update the `application.properties` (or `application.yml`) file in `src/main/resources/`


---

### 5️⃣ Build the Project


---

### 6️⃣ Run the Application


---

### 7️⃣ Project Structure

* `src/main/java` → Application code (Entities, Services, DAOs, Controllers)
* `src/main/resources` → Configurations (`application.properties`)
* `schema.sql` → Database schema & sample data
* `README.md` → Project overview
* `SETUP_INSTRUCTIONS.md` → Setup guide

---

✅ The project should now be up and running!

---
s