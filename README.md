# M6_UF2_P4

# Pràctica DAO - Patró d'Accés a Dades

Aquest projecte implementa el patró DAO per gestionar l'accés a les dades. Aquí responc a les preguntes de l'enunciat.

---

### **1. De quina lògica d’aplicació s’encarrega el Patró DAO?**
El patró DAO s'encarrega de tot el que té a veure amb l'accés a les dades (com fer consultes, guardar o eliminar dades). Separa aquesta part i ferla mes modular i resutilitzable, així és més fàcil canviar la base de dades sense tocar tot el codi.

---

### **2. Per què considereu què és útil el patró DAO i en què us ha servit?**
El patró DAO és útil perquè:
- **Organitza el codi**: Totes les consultes a la base de dades estan als DAOs.
- **Evita repetir codi**: Els mètodes comuns estan a `GenDAOImpl`, així no cal escriure'ls per cada objecte.
- **Facilita els canvis**: Si canvio de base de dades, només he de tocar els DAOs.
---

### **3. Heu hagut de fer cap ajust al vostre codi d’aplicació?**
Sí, he fet alguns canvis:
- **Refactorització dels DAOs**: He eliminat codi repetit i l'he mogut a `GenDAOImpl`.
- **Mètodes específics**: He afegit mètodes propis per els SDAOs que ho necessitaven.
- **Maneig d'errors**: `try-catch` per gestionar excepcions i evitar que el programa peti.

---

### **4. Diagrama de classes**
- **Classes del model**: `Client`, `Empleat`, `Reserva`, `Restaurant`.
- **DAOs**: `ClientDAO`, `EmpleatDAO`, `ReservaDAO`, `RestaurantDAO`.
- **Relacions**: Cada classe del model té el seu DAO, i tots els DAOs heredan de `GenDAOImpl`.

---

## **Conclusió**
Amb aquesta pràctica he après a utilitzar el patró DAO per gestionar les dades de manera més organitzada. Tot i que al principi costava una mica, ara veig que és molt útil per fer aplicacions més professionals.

---
