![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)

# 🚚 SpeedFast - Sistema de Gestión de Pedidos

Este proyecto corresponde a una aplicación de escritorio desarrollada en **Java + Swing + JDBC**, que implementa un sistema de gestión para la empresa **SpeedFast**.

El foco principal del proyecto es demostrar la aplicación del **Patrón DAO (Data Access Object)** como mecanismo de separación de responsabilidades entre:

- Lógica de negocio  
- Acceso a datos  
- Interfaz de usuario  

---

## 👨‍🎓 Autor del proyecto

- **Nombre:** Pablo Nicolás Alonso Gallardo Gallardo
- **Carrera:** Analista programador
- **Curso:** Desarrollo orientado a objetos II
- **Profesor:** Pablo Carmona
- **Institución:** DUOC UC

---

## 🎯 Objetivo del Proyecto

Construir una aplicación que permita:

- Gestión de Repartidores (CRUD)
- Gestión de Pedidos (CRUD)
- Gestión de Entregas (CRUD)
- Persistencia de datos en MySQL
- Interacción mediante interfaz gráfica en Java Swing

Aplicando:

✔ Arquitectura por capas  
✔ JDBC  
✔ Base de datos relacional  
✔ Patrón DAO  
✔ Validación de datos  
✔ Manejo de excepciones  

---

## 🏗 Arquitectura del Proyecto

El sistema está estructurado en capas:
Vista (Swing)
↓
Controladores
↓
DAO (Acceso a Datos)
↓
Base de Datos MySQL

---

## 🧠 Rol del Patrón DAO

El patrón DAO es el núcleo arquitectónico del proyecto.

Su función es:

- Encapsular toda la lógica SQL
- Aislar la base de datos del resto del sistema
- Permitir cambios en la persistencia sin afectar la lógica de negocio

Sin DAO, los controladores tendrían código SQL mezclado con reglas de negocio.

Con DAO:

| Capa         | Responsabilidad |
|--------------|-----------------|
| Vista        | Interacción con el usuario |
| Controlador  | Lógica de negocio |
| DAO          | Persistencia (JDBC) |
| Base de Datos| Almacenamiento |

---

## 🛠 DAO Implementados

| DAO | Responsabilidad |
|------|----------------|
| RepartidorDAO | CRUD de repartidores |
| PedidoDAO | CRUD de pedidos |
| EntregaDAO | CRUD de entregas |

Todos utilizan:

- `PreparedStatement`
- `ResultSet`
- Manejo de excepciones SQL
- Cierre adecuado de recursos

---

## 🗄 Configuración de Base de Datos

Antes de ejecutar la aplicación, debes crear la base de datos en MySQL.

Ejecuta el siguiente script:

```sql
CREATE DATABASE speedfast_db;
USE speedfast_db;

CREATE TABLE repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(100) NOT NULL,
    tipo ENUM('COMIDA','ENCOMIENDA','EXPRESS'),
    estado ENUM('PENDIENTE','EN_REPARTO','ENTREGADO')
);

CREATE TABLE entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_repartidor INT,
    fecha DATE,
    hora TIME,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    FOREIGN KEY (id_repartidor) REFERENCES repartidores(id)
);
```

🔌 Configuración de Conexión

En la clase ConexionBD.java debes configurar:
```java
private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña";
```
---
© Duoc UC | Escuela de Informática y Telecomunicaciones | Evaluación Formativa Semana 5
