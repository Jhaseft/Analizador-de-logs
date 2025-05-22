AnalizadorLogs_Linux

Autores

Joseph Harold Humerez Soliz

Jhaseft Rene Saat Solares

Jhoel Paredes Pava

Valeria Sarai Vazquez Laynes

Descripción

AnalizadorLogs_Linux es una aplicación de escritorio desarrollada en Java que permite importar y analizar logs generados por un servidor FTP (vsftpd), almacenarlos en una base de datos relacional y generar reportes desde una interfaz gráfica. El sistema está diseñado para ejecutarse en Linux, con servicios configurados localmente y utilizando phpMyAdmin para la administración de base de datos.

1. Pre-requisitos
Sistema Operativo:

openSUSE Leap 15.6 (64 bits)

Requerimientos de Hardware:

Procesador: mínimo 1 núcleo (recomendado 2 o más)

Memoria RAM: mínimo 2 GB (recomendado 4 GB o más)

Espacio en disco: mínimo 5 GB libres (recomendado 10 GB)

Conectividad de red: NAT o modo puente

Dependencias:

Java Development Kit (JDK) versión 23.0.2

Apache2 (servidor web)

MariaDB o MySQL

phpMyAdmin

vsftpd (servidor FTP)

Fuentes TrueType (Arial y Arial Rounded MT)

Archivos necesarios:

AnalizadorLogs_Linux.jar

Archivo de log: /var/log/vsftpd.log

Script SQL de la base de datos (incluido en el archivo .zip)

2. Instalación
Paso 1: Instalar JDK 23
bash
Copiar
Editar
mkdir -p /opt/java
cd /home/usuario/Descargas
tar -xvzf jdk-23.0.2_linux-x64_bin.tar.gz
mv jdk-23.0.2 /opt/java
update-alternatives --install /usr/bin/java java /opt/java/jdk-23.0.2/bin/java 1000
update-alternatives --config java
java -version
Paso 2: Extraer la aplicación
bash
Copiar
Editar
unzip AnalizadorLogs_Linux.zip
Paso 3: Importar la base de datos
Abrir http://localhost/phpmyadmin

Iniciar sesión

Ir a la pestaña "SQL", copiar y ejecutar el script desde la carpeta “script base de datos”

Paso 4: Instalar fuentes
bash
Copiar
Editar
sudo zypper install msttcorefonts
mkdir -p ~/.fonts
cp arial_rounded_mt.ttf ~/.fonts/
fc-cache -fv
fc-list | grep -i "arial"
Paso 5: Ejecutar la aplicación
bash
Copiar
Editar
cd dist
java -jar AnalizadorLogs_Linux.jar
Ingresar credenciales para conexión con la base de datos.

3. Configuración de servicios
Apache2
bash
Copiar
Editar
sudo apt install apache2 -y
sudo systemctl enable apache2
sudo systemctl start apache2
MariaDB
bash
Copiar
Editar
zypper install mariadb
sudo systemctl enable mariadb
sudo systemctl start mariadb
sudo mysql_secure_installation
Crear usuario:

sql
Copiar
Editar
CREATE USER 'usuario'@'localhost' IDENTIFIED BY 'contraseña';
GRANT ALL PRIVILEGES ON basededatos.* TO 'usuario'@'localhost';
FLUSH PRIVILEGES;
phpMyAdmin
bash
Copiar
Editar
sudo apt install phpmyadmin -y
sudo ln -s /etc/phpmyadmin/apache.conf /etc/apache2/conf-available/phpmyadmin.conf
sudo a2enconf phpmyadmin
sudo systemctl reload apache2
vsftpd
bash
Copiar
Editar
zypper install vsftpd
sudo systemctl enable vsftpd
sudo systemctl start vsftpd
Editar /etc/vsftpd.conf para asegurar que el log se genere en /var/log/vsftpd.log.

4. Solución de Problemas
Problema: Falta de JDK 23
Síntoma:
La app no ejecuta el .jar correctamente (error de class file version 67).

Solución:
Instalar JDK 23 como se detalla en el paso 1.

Problema: Error al importar la base de datos
Síntoma:
Error en el campo "Sistema Operativo" por tener un espacio.

Solución:

Modificar el nombre en el script SQL eliminando el espacio: SistemaOperativo

Importar el script

Luego, desde phpMyAdmin, cambiar manualmente el nombre de la columna a Sistema Operativo
