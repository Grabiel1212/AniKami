import mysql.connector
import os

DB_CONFIG = {
    "host": "127.0.0.1",
    "user": "root",
    "password": "1212",
    "database": "anikamibd"
}

SQL_DIR = r"C:\Users\juang\Documents\JAVA_PROYECTO_TRABAJOS\PROYECTOS\amikami_android\AniKami\backend\AniKami\demo\src\main\java\com\example\demo\scrips\SQL_ANIKAMI"

# 🔥 LISTA MANUAL – ORDENA COMO QUIERAS
SQL_FILES = [
    "creacion_de_la_base_datos.sql",
    "insercion_mangas_genero_autores.sql",

    "sql_2.5 Dimensional Seduction.sql",
    "sql_Black Clover.sql",
    "sql_Blue Lock.sql",
    "sql_Chainsawman.sql",
    "sql_Charlotte.sql",
    "sql_Dandadan.sql",
    "sql_Fairy Tail 100 Years Quest.sql",
    "sql_Goblin Slayer.sql",
    "sql_Haite Kudasai, Takamine San.sql",
    "sql_Kakegurui.sql",
    "sql_Keiken-chi chochiku de nonbiri.sql",
    "sql_Killing Bites.sql",
    "sql_Mamahaha no tsurego.sql",
    "sql_Megami no Cafe Terrace.sql",
    "sql_Mieruko-chan.sql",
    "sql_Nozomanu Fushi no Boukensha.sql",
    "sql_One Punch Man.sql",
    "sql_Otonari no Tenshi-sama ni Itsu no Ma ni ka Dame Ningen ni Sareteita Ken.sql",
    "sql_Sakamoto Days.sql",
    "sql_Spy x Family.sql",
    "sql_Tawawa on Monday.sql",
    "sql_Tougen Anki.sql",
    "sql_Yancha Gal no Anjo-san.sql",
    "sql_Zombie 100.sql",
   
]

def ejecutar_sql(cursor, sql_text, archivo):
    sentencias = sql_text.split(";")

    for idx, stmt in enumerate(sentencias, start=1):
        stmt = stmt.strip()
        if stmt:
            try:
                cursor.execute(stmt)
                if cursor.with_rows:
                    cursor.fetchall()
            except Exception as e:
                print(f"❌ ERROR en {archivo} (sentencia {idx})")
                print(e)
                raise

conn = mysql.connector.connect(**DB_CONFIG)
cursor = conn.cursor(buffered=True)

print("Conectado a MySQL")

cursor.execute("SET FOREIGN_KEY_CHECKS = 0")

for file in SQL_FILES:
    path = os.path.join(SQL_DIR, file)

    if not os.path.exists(path):
        print(f"⚠️ Archivo no encontrado: {file}")
        continue

    print(f"Ejecutando {file}...")

    with open(path, "r", encoding="utf-8") as f:
        sql = f.read()

    try:
        ejecutar_sql(cursor, sql, file)
        conn.commit()
    except:
        break

cursor.execute("SET FOREIGN_KEY_CHECKS = 1")

cursor.close()
conn.close()

print("✅ Proceso terminado")
