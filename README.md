# readFileCSV

Proyecto para leer dos archivos y encontrar diferencias

Uso para la busqueda de información entre ambas partes y cerar un documento con todo los registros no existente en el segundo leido
ideal para líneas que no se encuntran en cbs.

se instala el proceso, se genera el componente jar,
iniciar el proceso con el siguiente comando

->java -jar readFileCSV file1, file2

crear una carpeta en la ruta c:/file/

colocar los dos archivo con los nombres file1 y file2

file1:
  Documento con la información que está completa
 file2: Documento con la información que posiblemente no tenga los datos correctos
 
 ejemplo:
 
  file 1        file2
    telefono      telefono
    5534782918    5534782918
    5534782919    5534782044
    5534782044
    
    salida:
    telefono
    5534782919
