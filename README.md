# readFileCSV

Proyecto para leer dos archivos y encontrar diferencias

Uso para la busqueda de información entre ambas partes y crear un documento con todo los registros que no existente en el segundo archivo,
ideal para líneas que no se encuentran en cbs.

se instala el proceso, se genera el componente jar,
iniciar el proceso con el siguiente comando

java -jar readFileCSV file1, file2

crear una carpeta en la ruta c:/file/

colocar los dos archivo con los nombres file1 y file2

#file1:
  Documento con la información que está completa
#file2: 
 Documento con la información que posiblemente no tenga los datos completos no encontrados en cbs
 
 entrada:
 
  file 1        file2
    telefono      telefono
    5534782918    5534782918
    5534782919    5534782044
    5534782044
    
    salida:
    telefono
    5534782919
